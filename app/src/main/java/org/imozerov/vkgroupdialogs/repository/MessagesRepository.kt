package org.imozerov.vkgroupdialogs.repository

import org.imozerov.vkgroupdialogs.Executors
import org.imozerov.vkgroupdialogs.db.dao.MessageDao
import org.imozerov.vkgroupdialogs.repository.fetchers.MessagesFetchResource
import javax.inject.Inject

class MessagesRepository @Inject
constructor(private val messageDao: MessageDao,
            private val executors: Executors) {
    fun messages(chatId: Long) = MessagesFetchResource(executors, chatId, messageDao)
            .fetch().asLiveData()
}