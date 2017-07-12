package org.imozerov.vkgroupdialogs.repository

import org.imozerov.vkgroupdialogs.Executors
import org.imozerov.vkgroupdialogs.chat.Message
import org.imozerov.vkgroupdialogs.db.dao.MessageDao
import javax.inject.Inject

class MessagesRepository @Inject
constructor(private val messageDao: MessageDao,
            private val executors: Executors) {
    fun messages(chatId: Long) = object: NetworkBoundResource<List<Message>>(executors) {
            override fun shouldFetch(data: List<Message>?): Boolean {
                return true
            }

            override fun loadFromDb() = messageDao.loadMessages(chatId)
        }.asLiveData()
}