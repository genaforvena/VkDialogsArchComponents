package org.imozerov.vkgroupdialogs.repository

import android.arch.lifecycle.Transformations
import org.imozerov.vkgroupdialogs.Executors
import org.imozerov.vkgroupdialogs.db.AppDatabase
import org.imozerov.vkgroupdialogs.db.dao.ChatDao
import org.imozerov.vkgroupdialogs.db.dao.MessageDao
import org.imozerov.vkgroupdialogs.db.dao.UserDao
import org.imozerov.vkgroupdialogs.repository.fetchers.ChatsFetchResource
import org.imozerov.vkgroupdialogs.repository.fetchers.UsersFetcher
import javax.inject.Inject


class ChatsRepository @Inject
constructor(private val appDatabase: AppDatabase,
            private val chatDao: ChatDao,
            private val messageDao: MessageDao,
            private val userDao: UserDao,
            private val executors: Executors,
            private val usersFetcher: UsersFetcher) {

    init {
        // This starts observeForever() which might cause a leak
        // But this object has same lifecycle as ViewModel. So it's ok.
        usersFetcher.start()
    }

    fun chats() = ChatsFetchResource(appDatabase, chatDao,
            userDao, messageDao, executors).asLiveData()

    fun chatInfo(chatId: Long) = Transformations.map(chatDao.loadChatInfo(chatId)) {
        return@map Resource.success(it)
    }
}