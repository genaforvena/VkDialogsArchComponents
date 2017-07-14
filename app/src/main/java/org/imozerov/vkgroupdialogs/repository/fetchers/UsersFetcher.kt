package org.imozerov.vkgroupdialogs.repository.fetchers

import android.app.Application
import android.graphics.BitmapFactory
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKError
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
    fun start() {
        chatDao.loadChats().observeForever {
            it?.forEach {
                val chatId = it.id
                val request = VKRequest("messages.getChatUsers",
                        VKParameters.from("chat_id", chatId, "fields", "photo"))
                request.executeWithListener(object : VKRequest.VKRequestListener() {
                    override fun onComplete(response: VKResponse) {
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
                                relation.chatId = chatId
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
                            collageEntity.id = chatId
                            collageEntity.collage = collage

                            chatDao.insertCollage(collageEntity)
                        }
                    }

                    override fun onError(error: VKError?) {
                        super.onError(error)
                    }
                })
            }

            // Can't pass vararg here :(
            // requests?.apply {
            //  VKBatchRequest(it)
            // }
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