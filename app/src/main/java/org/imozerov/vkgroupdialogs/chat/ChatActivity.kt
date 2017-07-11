package org.imozerov.vkgroupdialogs.chat

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.chat_layout.*
import org.imozerov.vkgroupdialogs.R
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity
import javax.inject.Inject

class ChatActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val disposable: CompositeDisposable = CompositeDisposable()
    private lateinit var adapter: ChatAdapter
    private lateinit var viewModel: ChatViewModel
    private var chatId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_layout)

        chatId = intent.getLongExtra(KEY_CHAT_ID, 0)

        adapter = ChatAdapter()
        chat.adapter = adapter

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        val messagesSubscription =
                viewModel.messages(chatId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            adapter.setMessages(it)
                            updateVisibility(isDataPresent = true)
                        }

        disposable.add(messagesSubscription)
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
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
