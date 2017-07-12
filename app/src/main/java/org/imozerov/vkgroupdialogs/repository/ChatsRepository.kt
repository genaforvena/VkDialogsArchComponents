package org.imozerov.vkgroupdialogs.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKParameters
import org.imozerov.vkgroupdialogs.Executors
import org.imozerov.vkgroupdialogs.api.ApiCall
import org.imozerov.vkgroupdialogs.api.ApiResponse
import org.imozerov.vkgroupdialogs.chat.ChatInfo
import org.imozerov.vkgroupdialogs.db.dao.ChatDao
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import javax.inject.Inject


class ChatsRepository @Inject
constructor(private val chatDao: ChatDao,
            private val executors: Executors) {
    private val CHATS_LIMIT = 30

    fun chats() = object: NetworkBoundResource<List<ChatEntity>>(executors) {
        override fun saveCallResult(item: Any) {
            val chats = item as List<ChatEntity>
            chatDao.insertAll(chats)
            Log.v("Ilya", "items $item")
        }

        override fun createCall() : ApiCall {
            val parameters = VKParameters()
            parameters.put("count", CHATS_LIMIT)
            parameters.put("preview_length", 100)

            return ApiCall(VKApi.messages().getDialogs(parameters))
        }

        override fun parseResponse(response: ApiResponse): Any {
            Log.v("Ilya", "response: ${response.response?.json}")
            val response = response.response!!.json.toString()
            val dialogResponse = Gson().fromJson<Response>(response, Response::class.java)
            return dialogResponse.responseAnswer.items
                    .filter {
                        it.message.usersCount > 0
                    }
                    .map {
                        val chatEntity = ChatEntity()
                        with(it) {
                            chatEntity.id = message.chatId
                            chatEntity.name = message.chatName
                            chatEntity.lastMessageText = message.text
                            chatEntity.lastMessageTime = Date(message.date)
                        }
                        return@map chatEntity
                    }
        }

        private fun isGroupChat(data: JSONObject?): Boolean {
            try {
                return data != null && data.has("users_count") && data.getInt("users_count") > 1
            } catch (e: JSONException) {
                return false
            }

        }

        override fun shouldFetch(data: List<ChatEntity>?) = true

        override fun loadFromDb() = chatDao.loadChats()
    }.asLiveData()

    fun chatInfo(chatId: Long) = object: NetworkBoundResource<ChatInfo>(executors) {
        override fun saveCallResult(item: Any) {
            Log.v("Ilya", "items $item")
        }

        override fun createCall() : ApiCall {
            val parameters = VKParameters()
            parameters.put("count", CHATS_LIMIT)
            parameters.put("preview_length", 100)

            return ApiCall(VKApi.messages().getDialogs(parameters))
        }

        override fun parseResponse(response: ApiResponse): Any {
            Log.v("Ilya", "response: $response")
            return Any()
        }

        override fun shouldFetch(data: ChatInfo?) = false

        override fun loadFromDb() = chatDao.loadChatInfo(chatId)
    }.asLiveData()
}

data class Response(@SerializedName("response") val responseAnswer: ResponseAnswer)
data class ResponseAnswer(@SerializedName("items") val items: List<Chat>)
data class Chat(@SerializedName("message") val message: Message)
data class Message(@SerializedName("chat_id") val chatId: Long,
                   @SerializedName("users_count") val usersCount: Int,
                   @SerializedName("body") val text: String,
                   @SerializedName("date") val date: Long,
                   @SerializedName("title") val chatName: String?)