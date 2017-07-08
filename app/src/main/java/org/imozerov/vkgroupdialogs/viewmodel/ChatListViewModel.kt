package org.imozerov.vkgroupdialogs.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import org.imozerov.vkgroupdialogs.persistance.repository.ChatRepository

class ChatListViewModel(application: Application) : AndroidViewModel(application) {
    val chats = ChatRepository(application).chats()
}
