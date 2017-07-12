package org.imozerov.vkgroupdialogs.chat

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.imozerov.vkgroupdialogs.R
import org.imozerov.vkgroupdialogs.db.model.Message
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
                override fun getOldListSize() = messages!!.size

                override fun getNewListSize() =  newChatList.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return messages!![oldItemPosition].id == newChatList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val message = newChatList[newItemPosition]
                    val old = newChatList[oldItemPosition]
                    return message.id == old.id
                }
            })
            messages = newChatList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layoutId = when (viewType) {
            MY -> R.layout.message_right_item
            OTHER -> R.layout.message_left_item
            else -> throw RuntimeException("Unknown viewType $viewType")
        }

        val binding = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(messages!![position])
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages!![position]

        return if (message.isMine) MY else OTHER
    }

    override fun getItemCount() = if (messages == null) 0 else messages!!.size

    class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val body: TextView = view.findViewById(R.id.message_body)
        private val time: TextView = view.findViewById(R.id.message_time)
        private val avatar: ImageView? = view.findViewById(R.id.message_user_avatar)
        private val attachment: ImageView = view.findViewById(R.id.message_photo)

        fun bind(message: Message) {
            with(message) {
                body.text  = text
                time.text = DateUtil.chatLastMessage(date)
                avatar?.load(senderPhoto)
                photo?.apply {
                    attachment.visibility = View.VISIBLE
                    attachment.load(photo)
                }
            }
        }
    }
}