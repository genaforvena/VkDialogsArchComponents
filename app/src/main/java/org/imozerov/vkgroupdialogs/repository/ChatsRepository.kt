package org.imozerov.vkgroupdialogs.repository

import io.reactivex.Flowable
import org.imozerov.vkgroupdialogs.db.AppDatabase
import org.imozerov.vkgroupdialogs.vo.Chat
import javax.inject.Inject

class ChatsRepository @Inject
constructor(private val db: AppDatabase) {
    fun chats() : Flowable<List<Chat>> {
        return db.chatDao().loadChats()
    }
}
