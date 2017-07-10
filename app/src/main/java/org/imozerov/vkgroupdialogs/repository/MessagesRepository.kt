package org.imozerov.vkgroupdialogs.repository

import io.reactivex.Flowable
import org.imozerov.vkgroupdialogs.chat.Message
import org.imozerov.vkgroupdialogs.db.dao.MessageDao
import javax.inject.Inject

class MessagesRepository @Inject
constructor(private val messageDao: MessageDao) {
    fun messages(chatId: Long) : Flowable<List<Message>> {
        return messageDao.loadMessages(chatId)
    }
}