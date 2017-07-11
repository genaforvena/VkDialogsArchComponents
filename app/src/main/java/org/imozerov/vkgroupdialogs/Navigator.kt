package org.imozerov.vkgroupdialogs

import org.imozerov.vkgroupdialogs.chat.ChatActivity
import org.imozerov.vkgroupdialogs.chatlist.ChatListFragment
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity
import javax.inject.Inject

class Navigator @Inject constructor(private val mainActivity: MainActivity) {

    fun navigateToChatList() {
        val chatListFragment = ChatListFragment()

        mainActivity.supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, chatListFragment, ChatListFragment.TAG)
                .commit()
    }

    fun navigateToChat(chat: ChatEntity) {
        val intent = ChatActivity.forChat(mainActivity, chat)
        mainActivity.startActivity(intent)
    }
}