package com.group07.greensmart.utils;

import android.content.Context;
import android.widget.Toast;

import static com.group07.greensmart.constant.Constant.CODE_SUCCESS;

/**
 * Created by nguyenvuhuy on 4/4/18.
 */

public class ToastUtils {

    public static void makeSimpleText(Context context, int code, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
