package org.imozerov.vkgroupdialogs

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import org.imozerov.vkgroupdialogs.chatlist.ChatListActivity


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (VKSdk.isLoggedIn()) {
            navigateToChatList()
        } else {
            VKSdk.login(this, "friends", "wall", "photos", "messages")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
            override fun onResult(res: VKAccessToken?) {
                navigateToChatList()
            }
            override fun onError(error: VKError?) {
//                TODO("Handle login error")
                VKSdk.login(this@LoginActivity, "friends", "wall", "photos", "messages")
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun navigateToChatList() {
        val intent = Intent(this, ChatListActivity::class.java)
        startActivity(intent)
        finish()
    }
}
