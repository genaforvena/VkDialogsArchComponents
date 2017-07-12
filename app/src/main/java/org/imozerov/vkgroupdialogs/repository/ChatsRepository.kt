package org.imozerov.vkgroupdialogs.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKApiConst
import com.vk.sdk.api.VKParameters
import org.imozerov.vkgroupdialogs.Executors
import org.imozerov.vkgroupdialogs.api.ApiCall
import org.imozerov.vkgroupdialogs.api.ApiResponse
import org.imozerov.vkgroupdialogs.db.model.ChatInfo
import org.imozerov.vkgroupdialogs.db.dao.ChatDao
import org.imozerov.vkgroupdialogs.db.dao.MessageDao
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity
import org.imozerov.vkgroupdialogs.db.entities.MessageEntity
import java.util.*
import javax.inject.Inject


class ChatsRepository @Inject
constructor(private val chatDao: ChatDao,
            private val messageDao: MessageDao,
            private val executors: Executors) {
    private val CHATS_LIMIT = 30

    fun chats() = object : NetworkBoundResource<List<ChatEntity>>(executors) {
        override fun createCall(): ApiCall {
            val parameters = VKParameters.from(
                    VKApiConst.COUNT, CHATS_LIMIT,
                    VKApiConst.PREVIEW_LENGTH, 100)

            return ApiCall(VKApi.messages().getDialogs(parameters))
        }

        override fun processApiResponse(response: ApiResponse) {
            val json = response.response!!.json.toString()
            val dialogResponse = Gson().fromJson<Response>(json, Response::class.java)
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
                messageEntity.fromMesssage(it.message)
                return@map messageEntity
            }
            // TODO We can do this in one transaction instead of two
            chatDao.insertAll(chatEntities)
            messageDao.insertAll(messageEntities)
        }

        override fun shouldFetch(data: List<ChatEntity>?) = true

        override fun loadFromDb() = chatDao.loadChats()
    }.asLiveData()

    fun chatInfo(chatId: Long) = object : NetworkBoundResource<ChatInfo>(executors) {
        override fun createCall(): ApiCall {
            val parameters = VKParameters()
            parameters.put("count", CHATS_LIMIT)
            parameters.put("preview_length", 100)

            return ApiCall(VKApi.messages().getDialogs(parameters))
        }

        override fun processApiResponse(response: ApiResponse) {
            Log.v("Ilya", "response: $response")
        }

        override fun shouldFetch(data: ChatInfo?) = false

        override fun loadFromDb() = chatDao.loadChatInfo(chatId)
    }.asLiveData()
}

data class Response(@SerializedName("response") val getDialogsAnswer: GetDialogsAnswer)


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

fun MessageEntity.fromMesssage(message: Message) {
    id = message.id
    senderId = message.date
    chatId = message.chatId
    text = message.text
    // TODO We're trashing GC with with new calls
    date = java.util.Date(message.date)
}