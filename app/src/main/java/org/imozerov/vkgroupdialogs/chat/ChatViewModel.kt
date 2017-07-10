package org.imozerov.vkgroupdialogs.chat

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

import org.imozerov.vkgroupdialogs.repository.MessagesRepository
import javax.inject.Inject

class ChatViewModel @Inject
constructor(application: Application,
            private val messagesRepository: MessagesRepository) : AndroidViewModel(application) {
    fun messages(chatId: Long) = messagesRepository.messages(chatId)
}
