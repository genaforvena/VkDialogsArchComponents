package org.imozerov.vkgroupdialogs.chat

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.chat_list_item.view.*
import org.imozerov.vkgroupdialogs.R
import org.imozerov.vkgroupdialogs.vo.Message

/**
 * Created by imozerov on 08/07/2017.
 */
class ChatAdapter() :
        RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    internal var messages: List<Message>? = null

    fun setMessages(newChatList: List<Message>) {
        if (messages == null) {
            messages = newChatList
            notifyItemRangeInserted(0, newChatList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@ChatAdapter.messages!!.size
                }

                override fun getNewListSize(): Int {
                    return newChatList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@ChatAdapter.messages!![oldItemPosition].id == newChatList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val product = newChatList[newItemPosition]
                    val old = newChatList[oldItemPosition]
                    return product.id == old.id
                }
            })
            messages = newChatList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item, parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(messages!![position])
    }

    override fun getItemCount(): Int {
        return if (messages == null) 0 else messages!!.size
    }

    class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(message: Message) {
            with(message) {
                itemView.chat_name.text  = message.id.toString()
            }
        }
    }
}