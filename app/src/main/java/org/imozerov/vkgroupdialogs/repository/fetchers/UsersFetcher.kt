package org.imozerov.vkgroupdialogs.repository.fetchers

import android.app.Application
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKBatchRequest
import com.vk.sdk.api.VKParameters
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKResponse
import org.imozerov.vkgroupdialogs.Executors
import org.imozerov.vkgroupdialogs.db.AppDatabase
import org.imozerov.vkgroupdialogs.db.dao.ChatDao
import org.imozerov.vkgroupdialogs.db.dao.ChatUserRelationDao
import org.imozerov.vkgroupdialogs.db.dao.UserDao
import org.imozerov.vkgroupdialogs.db.entities.ChatCollageEntity
import org.imozerov.vkgroupdialogs.db.entities.ChatUserRelationEntity
import org.imozerov.vkgroupdialogs.db.entities.UserEntity
import org.imozerov.vkgroupdialogs.util.CollageCreator
import org.imozerov.vkgroupdialogs.util.batchDo
import javax.inject.Inject

class UsersFetcher @Inject
constructor(private val appDatabase: AppDatabase,
            private val application: Application,
            private val userDao: UserDao,
            private val chatUserRelationDao: ChatUserRelationDao,
            private val chatDao: ChatDao,
            private val executors: Executors) {
    private var lastFetchedIds: List<Long> = listOf()

    fun start() {
        chatDao.loadChats().observeForever {
            if (it == null) {
                return@observeForever
            }

            val ids = it.map {
                it.id
            }

            if (lastFetchedIds.containsAll(ids)) {
                return@observeForever
            }

            lastFetchedIds = ids

            val requests = it.map {
                val chatId = it.id
                VKRequest("messages.getChatUsers",
                        VKParameters.from("chat_id", chatId, "fields", "photo"))
            }
            
            BatchRequestCreator.createFrom(requests.toTypedArray()).executeWithListener(object : VKBatchRequest.VKBatchRequestListener() {
                override fun onComplete(responses: Array<out VKResponse>?) {
                    responses?.forEachIndexed { index, response ->
                        executors.diskIO.execute {
                            val json = response.json.toString()
                            val usersResponse = Gson().fromJson<UsersResponse>(json, UsersResponse::class.java)
                            val users = usersResponse.getUsersAnswer.map {
                                val userEntity = UserEntity()
                                userEntity.fromUser(it)
                                return@map userEntity
                            }

                            val chatUserRelations = usersResponse.getUsersAnswer.map {
                                val relation = ChatUserRelationEntity()
                                relation.chatId = ids[index]
                                relation.userId = it.id
                                return@map relation
                            }

                            appDatabase.batchDo {
                                userDao.insertAll(users)
                                chatUserRelationDao.insertAll(chatUserRelations)
                            }

                            val userImages = users.take(4).map {
                                Glide.with(application).load(it.photoUrl)
                                        .asBitmap().into(500, 500).get()
                            }

                            val collage = CollageCreator().createCollage(userImages)
                            val collageEntity = ChatCollageEntity()
                            collageEntity.id = ids[index]
                            collageEntity.collage = collage

                            chatDao.insertCollage(collageEntity)
                        }
                    }
                }
            })
        }
    }
}

internal data class UsersResponse(@SerializedName("response") val getUsersAnswer: List<User>)
internal data class User(@SerializedName("id") val id: Long,
                @SerializedName("last_name") val lastName: String,
                @SerializedName("first_name") val firstName: String,
                @SerializedName("photo") val photo: String)


internal fun UserEntity.fromUser(user: User) {
    id = user.id
    firstName = user.firstName
    lastName = user.lastName
    photoUrl = user.photo
}