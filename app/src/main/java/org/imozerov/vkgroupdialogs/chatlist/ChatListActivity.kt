package org.imozerov.vkgroupdialogs.chatlist

import android.arch.lifecycle.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.chat_list_activity.*
import org.imozerov.vkgroupdialogs.Navigator
import org.imozerov.vkgroupdialogs.R
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity
import org.imozerov.vkgroupdialogs.db.model.Chat
import org.imozerov.vkgroupdialogs.repository.Resource
import javax.inject.Inject

class ChatListActivity : AppCompatActivity(), LifecycleRegistryOwner {
    override fun getLifecycle() = lifecycleRegistry

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var adapter: ChatListAdapter
    private lateinit var viewModel: ChatListViewModel

    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_list_activity)

        adapter = ChatListAdapter(onChatClickCallback)
        adapter.setHasStableIds(true)
        chat_list.adapter = adapter

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatListViewModel::class.java)
        viewModel.chats
                .observe(this, Observer<Resource<List<Chat>>> {
                    if (it == null || it.data == null) {
                        return@Observer
                    }

                    if (it.status == Resource.Status.ERROR) {
                        // TODO handle error
                        return@Observer
                    }

                    adapter.setChats(it.data)
                    updateVisibility(isDataPresent = true)
                })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return false
    }

    private fun updateVisibility(isDataPresent: Boolean) {
        if (isDataPresent) {
            chat_list.visibility = View.VISIBLE
            loading_tv.visibility = View.GONE
        } else {
            chat_list.visibility = View.GONE
            loading_tv.visibility = View.VISIBLE
        }
    }

    private val onChatClickCallback = object : ChatClickCallback {
        override fun onClick(chat: Chat) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                navigator.navigateToChat(chat)
            }
        }
    }
}
