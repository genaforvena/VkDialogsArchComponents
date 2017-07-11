package org.imozerov.vkgroupdialogs.repository

import android.arch.lifecycle.LiveData
import org.imozerov.vkgroupdialogs.chat.ChatInfo
import org.imozerov.vkgroupdialogs.db.dao.ChatDao
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity
import javax.inject.Inject

class ChatsRepository @Inject
constructor(private val chatDao: ChatDao) {
    fun chats() : LiveData<List<ChatEntity>> {
        return chatDao.loadChats()
    }

    fun chatInfo(chatId: Long) : LiveData<ChatInfo> {
        return chatDao.loadChatInfo(chatId)
    }
}
