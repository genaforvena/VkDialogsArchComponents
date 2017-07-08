package org.imozerov.vkgroupdialogs.chatlist

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.chat_list_fragment.view.*
import org.imozerov.vkgroupdialogs.MainActivity
import org.imozerov.vkgroupdialogs.R
import org.imozerov.vkgroupdialogs.persistance.db.entities.ChatEntity
import org.imozerov.vkgroupdialogs.persistance.model.Chat
import org.imozerov.vkgroupdialogs.viewmodel.ChatListViewModel

/**
 * Fragment that displays chat list.
 */
class ChatListFragment : LifecycleFragment() {

    var adapter: ChatListAdapter? = null
    var chatList: RecyclerView? = null
    var loadingView: View? = null

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
        val viewModel = ViewModelProviders.of(this).get(ChatListViewModel::class.java)

        subscribeUi(viewModel)
    }

    private fun subscribeUi(viewModel: ChatListViewModel) {
        viewModel.chats.observe(this, Observer<List<ChatEntity>> { chats ->
            if (chats != null) {
                adapter!!.setChatList(chats)
                updateVisibility(isDataPresent = true)
            } else {
                updateVisibility(isDataPresent = false)
            }
        })
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
        override fun onClick(chat: Chat) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as MainActivity).show(chat)
            }
        }
    }

    companion object {
        val TAG = "ChatListFragment"
    }
}