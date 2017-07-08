package org.imozerov.vkgroupdialogs.repository

import io.reactivex.Flowable
import org.imozerov.vkgroupdialogs.db.dao.ChatDao
import org.imozerov.vkgroupdialogs.vo.Chat
import javax.inject.Inject

class ChatsRepository @Inject
constructor(private val chatDao: ChatDao) {
    fun chats() : Flowable<List<Chat>> {
        return chatDao.loadChats()
    }
}
