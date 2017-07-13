package org.imozerov.vkgroupdialogs.repository.fetchers

import android.arch.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKApiConst
import com.vk.sdk.api.VKParameters
import org.imozerov.vkgroupdialogs.Executors
import org.imozerov.vkgroupdialogs.api.ApiCall
import org.imozerov.vkgroupdialogs.api.ApiResponse
import org.imozerov.vkgroupdialogs.db.AppDatabase
import org.imozerov.vkgroupdialogs.db.dao.ChatDao
import org.imozerov.vkgroupdialogs.db.dao.MessageDao
import org.imozerov.vkgroupdialogs.db.dao.UserDao
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity
import org.imozerov.vkgroupdialogs.db.entities.MessageEntity
import org.imozerov.vkgroupdialogs.db.entities.UserEntity
import org.imozerov.vkgroupdialogs.util.AbsentLiveData
import org.imozerov.vkgroupdialogs.util.batchDo
import java.util.*

class ChatsFetchResource(private val appDatabase: AppDatabase,
                         private val chatDao: ChatDao,
                         private val userDao: UserDao,
                         private val messageDao: MessageDao,
                         executors: Executors): NetworkBoundResource<List<ChatEntity>>(executors) {
    companion object {
        private val CHATS_LIMIT = 30
    }

    override fun createCall(): ApiCall {
        val parameters = VKParameters.from(
                VKApiConst.COUNT, CHATS_LIMIT,
                VKApiConst.PREVIEW_LENGTH, 100)

        return ApiCall(VKApi.messages().getDialogs(parameters))
    }

    override fun processApiResponse(response: ApiResponse) {
        val json = response.response!!.json.toString()
        val dialogResponse = Gson().fromJson<DialogsResponse>(json, DialogsResponse::class.java)
        val groupMessages = dialogResponse.getDialogsAnswer.items
                .filter {
                    it.message.usersCount > 0
                }
        val chatEntities = groupMessages.map {
            val chatEntity = ChatEntity()
            chatEntity.fromMessage(it.message)
            return@map chatEntity
        }

        val messageEntities = groupMessages.map {
            val messageEntity = MessageEntity()
            messageEntity.fromMessage(it.message)
            return@map messageEntity
        }

        val userIds = groupMessages.map {
            val userEntity = UserEntity()
            userEntity.id = it.message.senderId
            return@map userEntity
        }

        appDatabase.batchDo {
            chatDao.insertAll(chatEntities)
            userDao.insertAll(userIds)
            messageDao.insertAll(messageEntities)
        }
    }

    override fun shouldFetch(data: List<ChatEntity>?) = true

    override fun loadFromDb() : LiveData<List<ChatEntity>> {
        // TODO it's a shame to fix this missing DAO like this. need to investigate.
        if (chatDao != null) {
            return chatDao.loadChats()
        } else {
            return AbsentLiveData.create()
        }
    }
}

data class DialogsResponse(@SerializedName("response") val getDialogsAnswer: GetDialogsAnswer)
data class GetDialogsAnswer(@SerializedName("items") val items: List<Chat>)
data class Chat(@SerializedName("message") val message: Message)
data class Message(@SerializedName("id") val id: Long,
                   @SerializedName("user_id") val senderId: Long,
                   @SerializedName("chat_id") val chatId: Long,
                   @SerializedName("users_count") val usersCount: Int,
                   @SerializedName("body") val text: String,
                   @SerializedName("date") val date: Long,
                   @SerializedName("photo_50") val photo50: String?,
                   @SerializedName("title") val chatName: String?)


fun ChatEntity.fromMessage(message: Message) {
    id = message.chatId
    name = message.chatName
    lastMessageText = message.text
    lastMessageTime = Date(message.date)
    usersCount = message.usersCount
    message.photo50?.apply {
        photo = message.photo50
    }
}

fun MessageEntity.fromMessage(message: Message) {
    id = message.id
    senderId = message.date
    chatId = message.chatId
    text = message.text
    // TODO We're trashing GC with with new calls
    date = java.util.Date(message.date)
}
