package org.imozerov.vkgroupdialogs.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import org.imozerov.vkgroupdialogs.persistance.db.DatabaseCreator
import org.imozerov.vkgroupdialogs.persistance.repository.ChatRepository
import org.imozerov.vkgroupdialogs.vo.Message

class ChatViewModel(application: Application, private val chatId: Int) : AndroidViewModel(application) {
    private val chatRepo = ChatRepository(application)

    class Factory(private val application: Application, private val chatId: Int) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatViewModel(application, chatId) as T
        }
    }

    fun messages() = chatRepo.messages(chatId)
}
