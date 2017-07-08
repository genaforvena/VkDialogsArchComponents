package org.imozerov.vkgroupdialogs.viewmodel

import android.app.Application
import android.arch.core.util.Function
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import org.imozerov.vkgroupdialogs.persistance.db.DatabaseCreator
import org.imozerov.vkgroupdialogs.persistance.db.entities.MessageEntity

class ChatViewModel(application: Application,
                    private val chatId: Int) : AndroidViewModel(application) {
    val messages: LiveData<List<MessageEntity>>

    init {
        ABSENT.value = null

        val databaseCreator = DatabaseCreator.getInstance(this.getApplication<Application>())

        messages = Transformations.switchMap(databaseCreator.isDatabaseCreated) { isDbCreated ->
            if (!isDbCreated) {
                ABSENT
            } else {
                databaseCreator.database!!.messageDao().loadMessages(this@ChatViewModel.chatId)
            }
        }

        databaseCreator.createDb(this.getApplication<Application>())
    }

    class Factory(private val application: Application, private val chatId: Int) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatViewModel(application, chatId) as T
        }
    }

    companion object {
        private val ABSENT = MutableLiveData<List<MessageEntity>>()
    }
}
