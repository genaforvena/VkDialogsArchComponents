package org.imozerov.vkgroupdialogs.repository.fetchers

import android.arch.lifecycle.LiveData
import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKApiConst
import com.vk.sdk.api.VKParameters
import org.imozerov.vkgroupdialogs.Executors
import org.imozerov.vkgroupdialogs.api.ApiCall
import org.imozerov.vkgroupdialogs.api.ApiResponse
import org.imozerov.vkgroupdialogs.db.AppDatabase
import org.imozerov.vkgroupdialogs.db.dao.ChatDao
import org.imozerov.vkgroupdialogs.db.dao.MessageDao
import org.imozerov.vkgroupdialogs.db.dao.UserDao
import org.imozerov.vkgroupdialogs.db.entities.ChatEntity
import org.imozerov.vkgroupdialogs.db.entities.MessageEntity
import org.imozerov.vkgroupdialogs.db.entities.UserEntity
import org.imozerov.vkgroupdialogs.db.model.Chat
import org.imozerov.vkgroupdialogs.util.batchDo
import java.util.*

class ChatsFetchResource(private val userId: Long,
                         private val appDatabase: AppDatabase,
                         private val chatDao: ChatDao,
                         private val userDao: UserDao,
                         private val messageDao: MessageDao,
                         executors: Executors): NetworkBoundResource<List<Chat>>(executors) {
    companion object {
        private val CHATS_LIMIT = 30
    }

    override fun createCall(): ApiCall {
        val parameters = VKParameters.from(
                VKApiConst.COUNT, CHATS_LIMIT,
                VKApiConst.PREVIEW_LENGTH, 100)

        return ApiCall(VKApi.messages().getDialogs(parameters))
    }

    override fun processApiResponse(response: ApiResponse) {
        val json = response.response!!.json.toString()
        Log.v("ILYA", "$json")
        val dialogResponse = Gson().fromJson<DialogsResponseJson>(json, DialogsResponseJson::class.java)
        val groupMessages = dialogResponse.getDialogsAnswer.items
                .filter {
                    it.message.usersCount > 0
                }
        val chatEntities = groupMessages.map {
            val chatEntity = ChatEntity()
            chatEntity.fromMessage(it.message)
            return@map chatEntity
        }

        val messageEntities = groupMessages.map {
            val messageEntity = MessageEntity()
            messageEntity.fromMessage(it.message)
            messageEntity.isMine = userId == it.message.senderId
            return@map messageEntity
        }

        val userIds = groupMessages.map {
            val userEntity = UserEntity()
            userEntity.id = it.message.senderId
            return@map userEntity
        }

        appDatabase.batchDo {
            chatDao.insertAll(chatEntities)
            userDao.insertAll(userIds)
            messageDao.insertAll(messageEntities)
        }
    }

    override fun shouldFetch(data: List<Chat>?) = true

    override fun loadFromDb() = chatDao.loadChats()
}

internal data class DialogsResponseJson(@SerializedName("response") val getDialogsAnswer: GetDialogsAnswerJson)
internal data class GetDialogsAnswerJson(@SerializedName("items") val items: List<ChatJson>)
internal data class ChatJson(@SerializedName("message") val message: MessageJson)
internal data class MessageJson(@SerializedName("id") val id: Long,
                                @SerializedName("user_id") val senderId: Long,
                                @SerializedName("chat_id") val chatId: Long,
                                @SerializedName("users_count") val usersCount: Int,
                                @SerializedName("body") val text: String,
                                @SerializedName("date") val date: Long,
                                @SerializedName("attachments") val attachments: List<AttachmentJson>?,
                                @SerializedName("photo_50") val photo50: String?,
                                @SerializedName("title") val chatName: String?)
internal data class AttachmentJson(@SerializedName("photo") val photo: PhotoJson?)
internal data class PhotoJson(@SerializedName("photo_604") val photoUrl: String?)


internal fun ChatEntity.fromMessage(message: MessageJson) {
    id = message.chatId
    name = message.chatName
    lastMessageText = message.text
    lastMessageTime = Date(message.date)
    usersCount = message.usersCount
    message.photo50?.apply {
        photo = message.photo50
    }
}

internal fun MessageEntity.fromMessage(message: MessageJson) {
    id = message.id
    senderId = message.senderId
    chatId = message.chatId
    text = message.text
    photo = message.attachments?.get(0)?.photo?.photoUrl
    // TODO We're trashing GC with with new calls
    date = java.util.Date(message.date)
}
