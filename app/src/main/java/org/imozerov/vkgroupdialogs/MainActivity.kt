package org.imozerov.vkgroupdialogs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import org.imozerov.vkgroupdialogs.chat.ChatFragment
import org.imozerov.vkgroupdialogs.chatlist.ChatListFragment
import org.imozerov.vkgroupdialogs.persistance.model.Chat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            val chatListFragment = ChatListFragment()

            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, chatListFragment, ChatListFragment.TAG)
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return false
    }

    fun show(chat: Chat) {
        val chatFragment = ChatFragment.forChat(chat)

        supportFragmentManager
                .beginTransaction()
                .addToBackStack("chat")
                .replace(R.id.fragment_container,
                        chatFragment, null).commit()
    }

}
