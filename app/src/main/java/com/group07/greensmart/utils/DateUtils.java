package com.group07.greensmart.utils;

import android.text.format.DateFormat;

/**
 * Created by nguyenvuhuy on 4/4/18.
 */

public class DateUtils {

    public static String DATE_FORMAT_SIMPLE = "h:mm a dd/MM/yyyy";

    public static String convertDate(String dateInMilliseconds, String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }

}
