package org.imozerov.vkgroupdialogs.vo

data class Message(
        private val id: Long,
        private val date: Long,
        private val text: String,
        private val photoUrl: String,
        private val sender: User,
        private val isMine: Boolean
)