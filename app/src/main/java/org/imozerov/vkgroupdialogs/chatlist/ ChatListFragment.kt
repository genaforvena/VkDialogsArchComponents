package org.imozerov.vkgroupdialogs.chatlist

import android.arch.lifecycle.*
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.chat_list_fragment.view.*
import org.imozerov.vkgroupdialogs.Navigator
import org.imozerov.vkgroupdialogs.R
import org.imozerov.vkgroupdialogs.di.Injectable
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity
import javax.inject.Inject

class ChatListFragment : LifecycleFragment(), Injectable {
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    private var adapter: ChatListAdapter? = null
    private var chatList: RecyclerView? = null
    private var loadingView: View? = null
    private var viewModel: ChatListViewModel? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.chat_list_fragment, container, false)

        chatList = rootView!!.chat_list
        loadingView = rootView.loading_tv

        adapter = ChatListAdapter(onChatClickCallback)
        rootView.rootView.chat_list.adapter = adapter

        return rootView.rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatListViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        val chatsSubscription = viewModel?.chats!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter!!.setChats(it)
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
            chatList!!.visibility = View.VISIBLE
            loadingView!!.visibility = View.GONE
        } else {
            chatList!!.visibility = View.GONE
            loadingView!!.visibility = View.VISIBLE
        }
    }

    private val onChatClickCallback = object : ChatClickCallback {
        override fun onClick(chat: ChatEntity) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                navigator.navigateToChat(chat)
            }
        }
    }

    companion object {
        val TAG = "ChatListFragment"
    }
}