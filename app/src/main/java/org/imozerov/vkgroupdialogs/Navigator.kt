package org.imozerov.vkgroupdialogs

import org.imozerov.vkgroupdialogs.chat.ChatActivity
import org.imozerov.vkgroupdialogs.chatlist.ChatListActivity
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity
import javax.inject.Inject

class Navigator @Inject constructor(private val chatListActivity: ChatListActivity) {
    fun navigateToChat(chat: ChatEntity) {
        val intent = ChatActivity.forChat(chatListActivity, chat)
        chatListActivity.startActivity(intent)
    }
}