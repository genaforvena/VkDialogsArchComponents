package org.imozerov.vkgroupdialogs.vo

data class Chat (
        val id: Long,
        val photo50: String,
        val name: String,
        val lastMessage: Message
)