package org.imozerov.vkgroupdialogs.persistance.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import org.imozerov.vkgroupdialogs.persistance.db.DatabaseCreator
import org.imozerov.vkgroupdialogs.vo.Chat
import org.imozerov.vkgroupdialogs.vo.Message

class ChatRepository(application: Application) {
    private val databaseCreator = DatabaseCreator.getInstance(application)

    init {
        databaseCreator.createDb(application)
    }

    fun messages(chatId: Int) : LiveData<List<Message>> {
        return Transformations.switchMap(databaseCreator.isDatabaseCreated) { isDbCreated ->
            if (!isDbCreated) {
                MutableLiveData<List<Message>>()
            } else {
                databaseCreator.database!!.messageDao().loadMessages(chatId)
            }
        }
    }

    fun chats() : LiveData<List<Chat>> {
        return Transformations.switchMap(databaseCreator.isDatabaseCreated) { isDbCreated ->
            if (!isDbCreated) {
                MutableLiveData<List<Chat>>()
            } else {
                databaseCreator.database!!.chatDao().loadChats()
            }

        }
    }
}
