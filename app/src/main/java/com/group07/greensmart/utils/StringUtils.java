package com.group07.greensmart.utils;

/**
 * Created by nguyenvuhuy on 4/12/18.
 */

public class StringUtils {

    public static String substring(String src, int lenght) {
        if (src.length() >= lenght) {
            return src.substring(0, lenght) + "...";
        }
        return src;
    }
}
