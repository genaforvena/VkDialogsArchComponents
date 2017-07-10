package org.imozerov.vkgroupdialogs.repository

import io.reactivex.Flowable
import org.imozerov.vkgroupdialogs.db.dao.MessageDao
import org.imozerov.vkgroupdialogs.db.entities.MessageEntity
import javax.inject.Inject

class MessagesRepository @Inject
constructor(private val messageDao: MessageDao) {
    fun messages(chatId: Long) : Flowable<List<MessageEntity>> {
        return messageDao.loadMessages(chatId)
    }
}