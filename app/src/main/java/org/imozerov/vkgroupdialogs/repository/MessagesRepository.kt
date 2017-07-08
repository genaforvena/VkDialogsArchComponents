package org.imozerov.vkgroupdialogs.repository

import io.reactivex.Flowable
import org.imozerov.vkgroupdialogs.db.AppDatabase
import org.imozerov.vkgroupdialogs.vo.Message
import javax.inject.Inject

class MessagesRepository @Inject
constructor(private val db: AppDatabase) {
    fun messages(chatId: Int) : Flowable<List<Message>> {
        return db.messageDao().loadMessages(chatId)
    }
}