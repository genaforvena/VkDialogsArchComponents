package org.imozerov.vkgroupdialogs.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object DateUtil {
    fun chatLastMessage(date: Date?): String {
        // TODO remove this! date in valid data should not be null!
        if (date == null) {
            return ""
        }
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val formattedDate = simpleDateFormat.format(date)
        return formattedDate
    }
}
