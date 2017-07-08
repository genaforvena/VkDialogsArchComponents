package org.imozerov.vkgroupdialogs.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import org.imozerov.vkgroupdialogs.repository.ChatRepository
import javax.inject.Inject
import javax.inject.Singleton

class ChatViewModel @Inject
constructor(application: Application,
            private val chatRepo: ChatRepository) : AndroidViewModel(application) {
    fun messages(chatId: Int) = chatRepo.messages(chatId)
}
