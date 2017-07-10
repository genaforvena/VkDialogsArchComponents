package org.imozerov.vkgroupdialogs.chat

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.chat_list_fragment.view.*
import org.imozerov.vkgroupdialogs.R
import org.imozerov.vkgroupdialogs.di.Injectable
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity
import javax.inject.Inject

class ChatFragment : LifecycleFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val disposable: CompositeDisposable = CompositeDisposable()
    private var adapter: ChatAdapter? = null
    private var chatList: RecyclerView? = null
    private var loadingView: TextView? = null

    private var viewModel: ChatViewModel? = null

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

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        val messagesSubscription =
                viewModel
                        ?.messages(arguments.getLong(KEY_CHAT_ID))!!
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            adapter!!.setMessages(it)
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
            chatList!!.visibility = View.VISIBLE
            loadingView!!.visibility = View.GONE
        } else {
            chatList!!.visibility = View.GONE
            loadingView!!.visibility = View.VISIBLE
        }
    }

    companion object {
        fun forChat(chat: ChatEntity): ChatFragment {
            val fragment = ChatFragment()
            val args = Bundle()
            args.putLong(KEY_CHAT_ID, chat.id)
            fragment.arguments = args
            return fragment
        }

        val KEY_CHAT_ID = "chat_id"
    }
}