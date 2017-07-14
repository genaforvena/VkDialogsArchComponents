package org.imozerov.vkgroupdialogs.chatlist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import org.imozerov.vkgroupdialogs.db.model.Chat
import org.imozerov.vkgroupdialogs.repository.ChatsRepository
import org.imozerov.vkgroupdialogs.repository.Resource
import org.imozerov.vkgroupdialogs.util.AbsentLiveData
import javax.inject.Inject

class ChatListViewModel @Inject
constructor(application: Application, repo: ChatsRepository) :
        AndroidViewModel(application) {
    private val shouldFetch = MutableLiveData<Boolean>()

    init {
        shouldFetch.value = false
    }

    val chats = Transformations.switchMap(shouldFetch) { shouldFetch ->
        if (shouldFetch) {
            return@switchMap repo.chats()
        } else {
            return@switchMap AbsentLiveData.create<Resource<List<Chat>>>()
        }
    }

    fun fetch() {
        shouldFetch.value = true
    }
}
