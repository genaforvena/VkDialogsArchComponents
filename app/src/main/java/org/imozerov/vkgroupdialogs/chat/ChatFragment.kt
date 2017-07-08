package org.imozerov.vkgroupdialogs.chat

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.imozerov.vkgroupdialogs.R
import org.imozerov.vkgroupdialogs.persistance.db.entities.MessageEntity
import org.imozerov.vkgroupdialogs.persistance.model.Chat
import org.imozerov.vkgroupdialogs.viewmodel.ChatViewModel

class ChatFragment : LifecycleFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.chat_list_fragment, container, false)

        return rootView!!.rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ChatViewModel.Factory(activity.application, arguments.getInt(KEY_CHAT_ID))

        val viewModel = ViewModelProviders.of(this, factory).get(ChatViewModel::class.java)

        subscribeUi(viewModel)
    }

    private fun subscribeUi(viewModel: ChatViewModel) {
        viewModel.messages.observe(this, Observer<List<MessageEntity>> { messages ->
            // TODO
        })
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