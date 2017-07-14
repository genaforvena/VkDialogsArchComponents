package org.imozerov.vkgroupdialogs.chat

import android.app.Application
import android.arch.core.util.Function
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import org.imozerov.vkgroupdialogs.db.model.ChatInfo
import org.imozerov.vkgroupdialogs.db.model.Message
import org.imozerov.vkgroupdialogs.repository.ChatsRepository

import org.imozerov.vkgroupdialogs.repository.MessagesRepository
import org.imozerov.vkgroupdialogs.repository.Resource
import org.imozerov.vkgroupdialogs.util.AbsentLiveData
import javax.inject.Inject

class ChatViewModel @Inject
constructor(application: Application,
            chatsRepository: ChatsRepository,
            private val messagesRepository: MessagesRepository) : AndroidViewModel(application) {
    private var chatId: MutableLiveData<Long> = MutableLiveData()

    val messages = Transformations.switchMap(chatId, {
        if (it == 0L) {
            AbsentLiveData.create<Resource<List<Message>>>()
        } else {
            val messages = messagesRepository.messages(it)
            messagesRepository.load(0)
            return@switchMap messages
        }
    })

    val chatInfo = Transformations.switchMap(chatId, {
        if (it == 0L) {
            AbsentLiveData.create<Resource<ChatInfo>>()
        } else {
            chatsRepository.chatInfo(it)
        }
    })

    fun load(offset: Int) {
        messagesRepository.load(offset)
    }

    fun setChatId(newChatId: Long) {
        chatId.value = newChatId
    }
}
