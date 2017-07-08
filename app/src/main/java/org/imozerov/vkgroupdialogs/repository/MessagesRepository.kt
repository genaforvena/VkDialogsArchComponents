package org.imozerov.vkgroupdialogs.repository

import io.reactivex.Flowable
import org.imozerov.vkgroupdialogs.db.dao.MessageDao
import org.imozerov.vkgroupdialogs.vo.Message
import javax.inject.Inject

class MessagesRepository @Inject
constructor(private val messageDao: MessageDao) {
    fun messages(chatId: Int) : Flowable<List<Message>> {
        return messageDao.loadMessages(chatId)
    }
}