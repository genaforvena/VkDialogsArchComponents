package org.imozerov.vkgroupdialogs.chat

import android.app.Application
import android.arch.core.util.Function
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import org.imozerov.vkgroupdialogs.repository.ChatsRepository

import org.imozerov.vkgroupdialogs.repository.MessagesRepository
import org.imozerov.vkgroupdialogs.util.AbsentLiveData
import javax.inject.Inject

class ChatViewModel @Inject
constructor(application: Application,
            chatsRepository: ChatsRepository,
            messagesRepository: MessagesRepository) : AndroidViewModel(application) {
    private var chatId: MutableLiveData<Long> = MutableLiveData()
    val messages = Transformations.switchMap(chatId, Function<Long, LiveData<List<Message>>> {
        if (it == 0L) {
            return@Function AbsentLiveData.create<List<Message>>()
        } else {
            return@Function messagesRepository.messages(it)
        }
    })

    val chatInfo = Transformations.switchMap(chatId, Function<Long, LiveData<ChatInfo>> {
        if (it == 0L) {
            return@Function AbsentLiveData.create<ChatInfo>()
        } else {
            return@Function chatsRepository.chatInfo(it)
        }
    })

    fun setChatId(newChatId: Long) {
        chatId.value = newChatId
    }
}
