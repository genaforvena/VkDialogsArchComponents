package org.imozerov.vkgroupdialogs.chat

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.imozerov.vkgroupdialogs.R
import org.imozerov.vkgroupdialogs.util.DateUtil
import org.imozerov.vkgroupdialogs.util.load

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    companion object {
        val OTHER = 455
        val MY = 335
    }

    private var messages: List<Message>? = null

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
                    return this@ChatAdapter.messages!![oldItemPosition].self.id == newChatList[newItemPosition].self.id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val message = newChatList[newItemPosition]
                    val old = newChatList[oldItemPosition]
                    return message.self.id == old.self.id
                }
            })
            messages = newChatList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding: View
        if (viewType == MY) {
            binding = LayoutInflater.from(parent.context).inflate(R.layout.message_right_item, parent, false)
        } else {
            binding = LayoutInflater.from(parent.context).inflate(R.layout.message_left_item, parent, false)
        }
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(messages!![position])
    }

    override fun getItemCount(): Int {
        return if (messages == null) 0 else messages!!.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages!![position]

        return if (message.self.isMine) MY else OTHER
    }

    class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val body: TextView = view.findViewById(R.id.message_body)
        private val time: TextView = view.findViewById(R.id.message_time)
        private val avatar: ImageView? = view.findViewById(R.id.message_user_avatar)

        fun bind(message: Message) {
            with(message) {
                body.text  = self.text
                time.text = DateUtil.chatLastMessage(self.date)
                avatar?.load(senderPhoto)
            }
        }
    }
}