package org.imozerov.vkgroupdialogs.util

import java.text.SimpleDateFormat
import java.util.*


object DateUtil {
    private val timeZone = TimeZone.getDefault()

    fun chatLastMessage(date: Long): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        simpleDateFormat.timeZone = timeZone
        val formattedDate = simpleDateFormat.format(date)
        return formattedDate
    }
}
