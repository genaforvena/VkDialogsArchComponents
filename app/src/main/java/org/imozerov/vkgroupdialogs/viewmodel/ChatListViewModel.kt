package org.imozerov.vkgroupdialogs.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import org.imozerov.vkgroupdialogs.persistance.db.DatabaseCreator
import org.imozerov.vkgroupdialogs.persistance.db.entities.ChatEntity


class ChatListViewModel(application: Application) : AndroidViewModel(application) {
    val chats: LiveData<List<ChatEntity>>

    init {
        ABSENT.value = null

        val databaseCreator = DatabaseCreator.getInstance(getApplication<Application>())

        chats = Transformations.switchMap(databaseCreator.isDatabaseCreated) { isDbCreated ->
            if (java.lang.Boolean.TRUE != isDbCreated) {
                ABSENT
            } else {
                databaseCreator.database!!.chatDao().loadChats()
            }
        }

        databaseCreator.createDb(application)
    }

    companion object {
        private val ABSENT = MutableLiveData<List<ChatEntity>>()
    }
}
