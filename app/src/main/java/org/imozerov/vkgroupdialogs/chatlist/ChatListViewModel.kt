package org.imozerov.vkgroupdialogs.chatlist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import org.imozerov.vkgroupdialogs.repository.ChatsRepository
import javax.inject.Inject

class ChatListViewModel @Inject
constructor(application: Application, repo: ChatsRepository) :
        AndroidViewModel(application) {
    val chats = repo.chats()
}
