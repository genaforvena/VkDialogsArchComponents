package org.imozerov.vkgroupdialogs.chat

import android.app.Activity
import android.arch.lifecycle.*
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.chat_layout.*
import org.imozerov.vkgroupdialogs.R
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity
import javax.inject.Inject
import android.view.Gravity
import de.hdodenhof.circleimageview.CircleImageView


class ChatActivity : AppCompatActivity(), LifecycleRegistryOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle() = lifecycleRegistry

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var adapter: ChatAdapter
    private lateinit var viewModel: ChatViewModel

    // Performance optimisation to avoid resource look ups
    private val groupIconPadding by
            lazy { resources.getDimensionPixelSize(R.dimen.chat_user_group_image_margin_right) }
    private val groupIconMarginRight by
            lazy { resources.getDimensionPixelSize(R.dimen.chat_user_group_image_padding) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_layout)

        adapter = ChatAdapter()
        chat.adapter = adapter

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
        viewModel.setChatId(intent.getLongExtra(KEY_CHAT_ID, 0))
        viewModel.messages
                .observe(this, Observer<List<Message>> {
                    if (it == null) {
                        return@Observer
                    }

                    adapter.setMessages(it)
                    updateLoadingStatus(isDataPresent = true)
                })

        viewModel.chatInfo
                .observe(this, Observer<ChatInfo> {
                    if (it == null) {
                        return@Observer
                    }

                    supportActionBar?.display(it)
                })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun ActionBar.display(chatInfo: ChatInfo) {
        title = chatInfo.name
        subtitle = chatInfo.userIds.size.toString()

        if (chatInfo.photo == null) {
            // todo ("remove this check when data available")
            return;
        }
        setGroupImage(chatInfo.photo)
    }

    private fun ActionBar.setGroupImage(image: Bitmap) {
        displayOptions = displayOptions or ActionBar.DISPLAY_SHOW_CUSTOM
        val imageView = CircleImageView(themedContext)
        imageView.setImageBitmap(image)
        val layoutParams = ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT or Gravity.CENTER_VERTICAL)
        layoutParams.rightMargin = groupIconMarginRight
        imageView.layoutParams = layoutParams
        imageView.setPadding(groupIconPadding, groupIconPadding,
                groupIconPadding, groupIconPadding)

        customView = imageView
    }

    private fun updateLoadingStatus(isDataPresent: Boolean) {
        if (isDataPresent) {
            chat.visibility = View.VISIBLE
            loading_tv.visibility = View.GONE
        } else {
            chat.visibility = View.GONE
            loading_tv.visibility = View.VISIBLE
        }
    }

    companion object {
        fun forChat(activity: Activity, chat: ChatEntity): Intent {
            val intent = Intent(activity, ChatActivity::class.java)
            intent.putExtra(KEY_CHAT_ID, chat.id)
            return intent
        }

        private val KEY_CHAT_ID = "chat_id"
    }
}
