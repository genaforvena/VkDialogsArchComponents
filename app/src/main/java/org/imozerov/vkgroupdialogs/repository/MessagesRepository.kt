package org.imozerov.vkgroupdialogs.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import org.imozerov.vkgroupdialogs.Executors
import org.imozerov.vkgroupdialogs.db.dao.MessageDao
import org.imozerov.vkgroupdialogs.repository.fetchers.MessagesFetchResource
import javax.inject.Inject

class MessagesRepository @Inject
constructor(private val messageDao: MessageDao,
            private val executors: Executors) {
    private val offset = MutableLiveData<Int>()

    fun messages(chatId: Long) = Transformations.switchMap(offset) {
        MessagesFetchResource(executors, messageDao, chatId, it).fetch().asLiveData()
    }

    fun load(offset: Int) {
        this.offset.value = offset
    }
}