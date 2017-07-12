package org.imozerov.vkgroupdialogs.repository

import org.imozerov.vkgroupdialogs.Executors
import org.imozerov.vkgroupdialogs.chat.ChatInfo
import org.imozerov.vkgroupdialogs.db.dao.ChatDao
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity
import javax.inject.Inject

class ChatsRepository @Inject
constructor(private val chatDao: ChatDao,
            private val executors: Executors) {
    fun chats() = object: NetworkBoundResource<List<ChatEntity>>(executors) {
        override fun shouldFetch(data: List<ChatEntity>?): Boolean {
            return true
        }

        override fun loadFromDb() = chatDao.loadChats()
    }.asLiveData()

    fun chatInfo(chatId: Long) = object : NetworkBoundResource<ChatInfo>(executors) {
        override fun loadFromDb() = chatDao.loadChatInfo(chatId)

        override fun shouldFetch(data: ChatInfo?): Boolean {
            return true
        }
    }.asLiveData()
}
