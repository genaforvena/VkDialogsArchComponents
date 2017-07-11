package org.imozerov.vkgroupdialogs.chatlist

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.imozerov.vkgroupdialogs.Navigator
import org.imozerov.vkgroupdialogs.R
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity
import javax.inject.Inject

class ChatListActivity : AppCompatActivity() {
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    private lateinit var adapter: ChatListAdapter
    private lateinit var viewModel: ChatListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        adapter = ChatListAdapter(onChatClickCallback)
        adapter.setHasStableIds(true)
        chat_list.adapter = adapter

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatListViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return false
    }

    override fun onStart() {
        super.onStart()

        val chatsSubscription = viewModel.chats
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter.setChats(it)
                    updateVisibility(isDataPresent = true)
                })

        subscriptions.add(chatsSubscription)
    }

    override fun onStop() {
        super.onStop()

        subscriptions.clear()
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
        override fun onClick(chat: ChatEntity) {
            navigator.navigateToChat(chat)
        }
    }
}
