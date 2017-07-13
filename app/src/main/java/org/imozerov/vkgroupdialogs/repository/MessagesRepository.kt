package org.imozerov.vkgroupdialogs.repository

import android.arch.lifecycle.Transformations
import org.imozerov.vkgroupdialogs.db.dao.MessageDao
import javax.inject.Inject

class MessagesRepository @Inject
constructor(private val messageDao: MessageDao) {

    fun messages(chatId: Long) = Transformations.map(messageDao.loadMessages(chatId)) {
        Resource.success(it)
    }
}