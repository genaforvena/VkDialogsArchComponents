package org.imozerov.vkgroupdialogs.repository.fetchers

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.vk.sdk.VKAccessToken
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKError
import com.vk.sdk.api.VKParameters
import com.vk.sdk.api.VKRequest
import org.imozerov.vkgroupdialogs.Executors
import org.imozerov.vkgroupdialogs.api.ApiCall
import org.imozerov.vkgroupdialogs.api.ApiResponse
import org.imozerov.vkgroupdialogs.db.dao.MessageDao
import org.imozerov.vkgroupdialogs.db.entities.MessageEntity
import org.imozerov.vkgroupdialogs.db.model.Message


class MessagesFetchResource(executors: Executors,
                            private val chatId: Long,
                            private val messageDao: MessageDao) :
        NetworkBoundResource<List<Message>>(executors) {
    override fun processApiResponse(response: ApiResponse) {
        val json = response.response!!.json.toString()
        val dialogResponse = Gson().fromJson<MessagesResponse>(json, MessagesResponse::class.java)
        val messages = dialogResponse.response.items.map {
            val entity = MessageEntity()
            entity.fromMessage(it)
            entity.isMine = it.senderId == currentUserId
            return@map entity
        }

        messageDao.insertAll(messages)
    }

    // always try to fetch latest messages
    override fun shouldFetch(data: List<Message>?) = true

    override fun loadFromDb() = messageDao.loadMessages(chatId)

    override fun createCall() = ApiCall(VKRequest("messages.getHistory",
            VKParameters.from("peer_id", getGroupChatPeerId(chatId),
                    // This hopefully will be ignored. If not provided error is returned
                    "user_id", VKAccessToken.currentToken().userId)))

    override fun onFetchFailed(error: VKError?) {
        Log.e("ILYA", "fetch of messages from backend failed with $error")
        super.onFetchFailed(error)
    }

    private fun getGroupChatPeerId(chatId: Long) = GROUP_CHAT_MAGIC + chatId

    companion object {
        private val GROUP_CHAT_MAGIC = 2000000000
        private val currentUserId = VKAccessToken.currentToken().userId.toLong()
    }
}

internal data class MessagesResponse(@SerializedName("response") val response: Payload)
internal data class Payload(@SerializedName("items") val items: List<MessageJson>)


