package org.imozerov.vkgroupdialogs.vo

data class Message(
        val id: Long,
        val date: Long,
        val text: String,
        val photoUrl: String,
        val sender: User,
        val isMine: Boolean
)