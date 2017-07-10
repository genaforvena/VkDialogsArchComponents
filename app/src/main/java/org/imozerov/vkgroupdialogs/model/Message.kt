package org.imozerov.vkgroupdialogs.model

data class Message(
        val id: Long,
        val date: Long,
        val text: String,
        val photoUrl: String,
        val senderAvatar: String,
        val isMine: Boolean
)