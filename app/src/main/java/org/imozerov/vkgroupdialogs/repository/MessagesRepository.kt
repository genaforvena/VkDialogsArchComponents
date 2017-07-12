package org.imozerov.vkgroupdialogs.repository

import android.util.Log
import com.vk.sdk.api.VKParameters
import com.vk.sdk.api.VKRequest
import org.imozerov.vkgroupdialogs.Executors
import org.imozerov.vkgroupdialogs.api.ApiCall
import org.imozerov.vkgroupdialogs.api.ApiResponse
import org.imozerov.vkgroupdialogs.db.dao.MessageDao
import org.imozerov.vkgroupdialogs.db.model.Message
import javax.inject.Inject

class MessagesRepository @Inject
constructor(private val messageDao: MessageDao,
            private val executors: Executors) {
    fun messages(chatId: Long) = object : NetworkBoundResource<List<Message>>(executors) {
        override fun processApiResponse(response: ApiResponse) {
            Log.v("Ilya", "response: ${response.response?.json}")
            response.response!!.json
        }

        override fun createCall(): ApiCall {
            val parameters = VKParameters()
            // Magic number refers to group request
            // There's also no corresponding VKApiCont. Oh really!
            parameters.put("peer_id", 2000000000 + chatId)

            return ApiCall(VKRequest("messages.getDialogs", parameters))
        }

        override fun shouldFetch(data: List<Message>?) = true

        override fun loadFromDb() = messageDao.loadMessages(chatId)
    }.asLiveData()
}