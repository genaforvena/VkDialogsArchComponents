package org.imozerov.vkgroupdialogs.chat

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.chat_list_fragment.view.*
import org.imozerov.vkgroupdialogs.R
import org.imozerov.vkgroupdialogs.persistance.db.entities.MessageEntity
import org.imozerov.vkgroupdialogs.persistance.model.Chat
import org.imozerov.vkgroupdialogs.viewmodel.ChatViewModel

class ChatFragment : LifecycleFragment() {

    private var adapter: ChatAdapter? = null
    private var chatList: RecyclerView? = null
    private var loadingView: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.chat_list_fragment, container, false)

        chatList = rootView!!.chat_list
        loadingView = rootView.loading_tv

        adapter = ChatAdapter()
        rootView.rootView.chat_list.adapter = adapter

        return rootView.rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ChatViewModel.Factory(activity.application, arguments.getInt(KEY_CHAT_ID))

        val viewModel = ViewModelProviders.of(this, factory).get(ChatViewModel::class.java)

        subscribeUi(viewModel)
    }

    private fun subscribeUi(viewModel: ChatViewModel) {
        viewModel.messages.observe(this, Observer<List<MessageEntity>> { messages ->
            if (messages != null) {
                adapter!!.setMessages(messages)
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

    companion object {
        fun forChat(chat: Chat): ChatFragment {
            val fragment = ChatFragment()
            val args = Bundle()
            args.putInt(KEY_CHAT_ID, chat.id)
            fragment.arguments = args
            return fragment
        }

        val KEY_CHAT_ID = "chat_id"
    }
}