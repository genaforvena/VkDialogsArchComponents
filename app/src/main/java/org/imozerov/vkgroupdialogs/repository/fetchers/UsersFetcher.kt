package org.imozerov.vkgroupdialogs.repository.fetchers

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
import org.imozerov.vkgroupdialogs.db.entities.ChatUserRelationEntity
import org.imozerov.vkgroupdialogs.db.entities.UserEntity
import org.imozerov.vkgroupdialogs.util.batchDo
import javax.inject.Inject

class UsersFetcher @Inject
constructor(private val appDatabase: AppDatabase,
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

data class UsersResponse(@SerializedName("response") val getUsersAnswer: List<User>)
data class User(@SerializedName("id") val id: Long,
                @SerializedName("last_name") val lastName: String,
                @SerializedName("first_name") val firstName: String,
                @SerializedName("photo") val photo: String)


fun UserEntity.fromUser(user: User) {
    id = user.id
    firstName = user.firstName
    lastName = user.lastName
    photoUrl = user.photo
}