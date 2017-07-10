package org.imozerov.vkgroupdialogs.repository

import io.reactivex.Flowable
import org.imozerov.vkgroupdialogs.db.dao.ChatDao
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity
import org.imozerov.vkgroupdialogs.db.entities.UserEntity
import javax.inject.Inject

class ChatsRepository @Inject
constructor(private val chatDao: ChatDao) {
    fun chats() : Flowable<List<ChatEntity>> {
        return chatDao.loadChats()
    }

    fun usersInChat(chatId: Long) : Flowable<List<UserEntity>> {
        return chatDao.loadUsers(chatId)
    }
}
