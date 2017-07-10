package org.imozerov.vkgroupdialogs.vo

data class Chat (
        private val id: Long,
        private val photo50: String,
        private val users: List<Long>,
        private val name: String,
        private val lastMessage: Message
)