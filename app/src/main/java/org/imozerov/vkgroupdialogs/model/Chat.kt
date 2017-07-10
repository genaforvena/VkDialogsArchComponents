package org.imozerov.vkgroupdialogs.model

import java.util.*

data class Chat (
        val id: Long,
        val photo: String,
        val name: String,
        val lastMessageText: String,
        val lastMessageDate: Date
)