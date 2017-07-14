package org.imozerov.vkgroupdialogs.chat

import android.app.Activity
import android.arch.lifecycle.*
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import dagger.android.AndroidInjection
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.chat_activity.*
import org.imozerov.vkgroupdialogs.R
import org.imozerov.vkgroupdialogs.db.model.Chat
import org.imozerov.vkgroupdialogs.db.model.ChatInfo
import org.imozerov.vkgroupdialogs.db.model.Message
import org.imozerov.vkgroupdialogs.repository.Resource
import org.imozerov.vkgroupdialogs.util.load
import javax.inject.Inject


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
    private val usersCountLabel by
            lazy { resources.getString(R.string.chat_info_users_count_label) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_activity)

        adapter = ChatAdapter()
        chat.adapter = adapter

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
        viewModel.setChatId(intent.getLongExtra(KEY_CHAT_ID, 0))
        viewModel.messages
                .observe(this, Observer<Resource<List<Message>>> {
                    if (it == null || it.data == null) {
                        return@Observer
                    }

                    if (it.status == Resource.Status.ERROR) {
                        // TODO handle error
                        return@Observer
                    }

                    val needToScrollDown  = adapter.itemCount < 3

                    adapter.setMessages(it.data)
                    if (needToScrollDown) {
                        chat.scrollToPosition(0)
                    }
                    updateLoadingStatus(isDataPresent = true)
                })

        viewModel.chatInfo
                .observe(this, Observer<Resource<ChatInfo>> {
                    if (it == null || it.data == null) {
                        return@Observer
                    }

                    if (it.status == Resource.Status.ERROR) {
                        // TODO handle error
                        return@Observer
                    }

                    supportActionBar?.display(it.data)
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
        // TODO use plurals instead (work ok for russian but who knows for other languages)
        subtitle = "${chatInfo.usersCount} $usersCountLabel"

        if (chatInfo.photo != null) {
            setGroupImage { it.load(chatInfo.photo) }
        } else {
            setGroupImage { it.setImageBitmap(chatInfo.photoFallback) }
        }
    }

    private inline fun ActionBar.setGroupImage(setImageCall: (imageView: ImageView) -> Unit) {
        displayOptions = displayOptions or ActionBar.DISPLAY_SHOW_CUSTOM
        val imageView = CircleImageView(themedContext)
        setImageCall(imageView)
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
        fun forChat(activity: Activity, chat: Chat): Intent {
            val intent = Intent(activity, ChatActivity::class.java)
            intent.putExtra(KEY_CHAT_ID, chat.id)
            return intent
        }

        private val KEY_CHAT_ID = "chat_id"
    }
}
