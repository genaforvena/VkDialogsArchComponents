package org.imozerov.vkgroupdialogs.repository

import android.arch.lifecycle.LiveData
import org.imozerov.vkgroupdialogs.chat.Message
import org.imozerov.vkgroupdialogs.db.dao.MessageDao
import javax.inject.Inject

class MessagesRepository @Inject
constructor(private val messageDao: MessageDao) {
    fun messages(chatId: Long) : LiveData<List<Message>> {
        return messageDao.loadMessages(chatId)
    }
}