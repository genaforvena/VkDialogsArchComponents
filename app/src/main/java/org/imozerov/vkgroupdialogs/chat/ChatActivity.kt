package org.imozerov.vkgroupdialogs.chat

import android.app.Activity
import android.arch.lifecycle.*
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.chat_layout.*
import org.imozerov.vkgroupdialogs.R
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity
import javax.inject.Inject

class ChatActivity : AppCompatActivity(), LifecycleRegistryOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle() = lifecycleRegistry

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var adapter: ChatAdapter
    private lateinit var viewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_layout)

        adapter = ChatAdapter()
        chat.adapter = adapter

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
        viewModel.setChatId(intent.getLongExtra(KEY_CHAT_ID, 0))
        viewModel.messages
                .observe(this, Observer<List<Message>> {
                            if (it == null) {
                                return@Observer
                            }

                            adapter.setMessages(it)
                            updateVisibility(isDataPresent = true)
                        })
    }

    private fun updateVisibility(isDataPresent: Boolean) {
        if (isDataPresent) {
            chat.visibility = View.VISIBLE
            loading_tv.visibility = View.GONE
        } else {
            chat.visibility = View.GONE
            loading_tv.visibility = View.VISIBLE
        }
    }

    companion object {
        fun forChat(activity: Activity, chat: ChatEntity): Intent {
            val intent = Intent(activity, ChatActivity::class.java)
            intent.putExtra(KEY_CHAT_ID, chat.id)
            return intent
        }

        private val KEY_CHAT_ID = "chat_id"
    }
}
