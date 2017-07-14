package org.imozerov.vkgroupdialogs.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object DateUtil {
    fun chatLastMessage(date: Long): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val formattedDate = simpleDateFormat.format(date)
        return formattedDate
    }
}
