package org.imozerov.vkgroupdialogs

import org.imozerov.vkgroupdialogs.chat.ChatFragment
import org.imozerov.vkgroupdialogs.chatlist.ChatListFragment
import org.imozerov.vkgroupdialogs.vo.Chat
import javax.inject.Inject

class Navigator @Inject constructor(private val mainActivity: MainActivity) {

    fun navigateToChatList() {
        val chatListFragment = ChatListFragment()

        mainActivity.supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, chatListFragment, ChatListFragment.TAG)
                .commit()
    }

    fun navigateToChat(chat: Chat) {
        val chatFragment = ChatFragment.forChat(chat)

        mainActivity.supportFragmentManager
                .beginTransaction()
                .addToBackStack("chat")
                .replace(R.id.fragment_container,
                        chatFragment, null).commit()
    }
}