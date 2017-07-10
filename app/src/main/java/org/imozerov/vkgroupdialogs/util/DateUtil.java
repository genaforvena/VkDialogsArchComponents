package org.imozerov.vkgroupdialogs.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateUtil {
    public static String chatLastMessage(Date date) {
        // TODO remove this! date in valid data should not be null!
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }
}
