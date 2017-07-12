package org.imozerov.vkgroupdialogs.repository

import android.util.Log
import com.vk.sdk.api.VKApi
import org.imozerov.vkgroupdialogs.Executors
import org.imozerov.vkgroupdialogs.api.ApiResponse
import org.imozerov.vkgroupdialogs.api.ApiCall
import org.imozerov.vkgroupdialogs.chat.Message
import org.imozerov.vkgroupdialogs.db.dao.MessageDao
import javax.inject.Inject

class MessagesRepository @Inject
constructor(private val messageDao: MessageDao,
            private val executors: Executors) {
    fun messages(chatId: Long) = object : NetworkBoundResource<List<Message>>(executors) {
        override fun parseResponse(response: ApiResponse): Any {
            return response.response!!.parsedModel
        }

        override fun saveCallResult(item: Any) {
            Log.v("ILYA", item.toString())
//            messageDao.insertAll()
        }

        override fun createCall() = ApiCall(VKApi.messages().get())

        override fun shouldFetch(data: List<Message>?) = false

        override fun loadFromDb() = messageDao.loadMessages(chatId)
    }.asLiveData()
}