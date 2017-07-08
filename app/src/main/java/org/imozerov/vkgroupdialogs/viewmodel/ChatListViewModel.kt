package org.imozerov.vkgroupdialogs.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import org.imozerov.vkgroupdialogs.repository.ChatRepository
import javax.inject.Inject

class ChatListViewModel @Inject
constructor(application: Application, repo: ChatRepository) :
        AndroidViewModel(application) {
    val chats = repo.chats()
}
