package org.imozerov.vkgroupdialogs.chatlist

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.chat_list_fragment.view.*
import org.imozerov.vkgroupdialogs.MainActivity
import org.imozerov.vkgroupdialogs.R
import org.imozerov.vkgroupdialogs.persistance.model.Chat

/**
 * Fragment that displays chat list.
 */
class ChatListFragment : LifecycleFragment() {

    var adapter: ChatListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.chat_list_fragment, container, false)

        adapter = ChatListAdapter(onChatClickCallback)
        rootView!!.rootView.chat_list.adapter = adapter

        return rootView.rootView
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        val viewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java!!)
//
//        subscribeUi(viewModel)
//    }
//
//    private fun subscribeUi(viewModel: ProductListViewModel) {
//        // Update the list when the data changes
//        viewModel.getProducts().observe(this, object : Observer<List<ProductEntity>> {
//            override fun onChanged(myProducts: List<ProductEntity>?) {
//                if (myProducts != null) {
//                    mBinding!!.setIsLoading(false)
//                    mProductAdapter!!.setProductList(myProducts)
//                } else {
//                    mBinding!!.setIsLoading(true)
//                }
//            }
//        })
//    }
//
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