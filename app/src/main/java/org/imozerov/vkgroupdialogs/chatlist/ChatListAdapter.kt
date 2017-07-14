package org.imozerov.vkgroupdialogs.chatlist

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.chat_list_item.view.*
import org.imozerov.vkgroupdialogs.R
import org.imozerov.vkgroupdialogs.db.model.Chat
import org.imozerov.vkgroupdialogs.util.DateUtil
import org.imozerov.vkgroupdialogs.util.load

class ChatListAdapter(private val chatClickCallback: ChatClickCallback) :
        RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {
    private var chats: List<Chat>? = null

    fun setChats(newChatList: List<Chat>) {
        if (chats == null) {
            chats = newChatList
            notifyItemRangeInserted(0, newChatList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return chats!!.size
                }

                override fun getNewListSize(): Int {
                    return newChatList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return chats!![oldItemPosition].id == newChatList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val chat = newChatList[newItemPosition]
                    val old = newChatList[oldItemPosition]
                    return chat.id == old.id
                }
            })
            chats = newChatList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item, parent, false)
        return ChatViewHolder(binding, chatClickCallback)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chats!![position])
    }

    override fun getItemCount() = if (chats == null) 0 else chats!!.size

    class ChatViewHolder(view: View, private val chatClickCallback: ChatClickCallback) : RecyclerView.ViewHolder(view) {
        fun bind(chat: Chat) {
            with(chat) {
                itemView.chat_name.text  = name
                itemView.chat_last_message.text = lastMessageText
                itemView.chat_date.text = DateUtil.chatLastMessage(lastMessageTime)
                if (photo != null) {
                    itemView.chat_image.load(photo)
                } else {
                    itemView.chat_image.setImageBitmap(photoFallback)
                }
                itemView.setOnClickListener { chatClickCallback.onClick(chat) }
            }
        }
    }
}

interface ChatClickCallback {
    fun onClick(chat: Chat)
}