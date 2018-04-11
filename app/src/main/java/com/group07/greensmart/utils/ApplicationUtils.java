package com.group07.greensmart.utils;

import android.content.Context;

import com.group07.greensmart.rest.DefaultSharedPrefsUtils;

/**
 * Created by nguyenvuhuy on 4/8/18.
 */

public class ApplicationUtils {

    public static String getServerUrl(Context context) {

        return DefaultSharedPrefsUtils.getServer(context);
    }

    public static String getImageUrl(Context context, String pathImage) {

        return getServerUrl(context) + pathImage;
    }
}
