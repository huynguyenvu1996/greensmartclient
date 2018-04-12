package com.group07.greensmart.utils;

import android.app.Activity;
import android.content.Intent;

import static com.group07.greensmart.constant.Constant.KEY_CODE;
import static com.group07.greensmart.constant.Constant.KEY_MESSAGE;

/**
 * Created by nguyenvuhuy on 4/5/18.
 */

public class ActivityUtils {

    public static void makeResult(Activity activity, int code, String message) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(KEY_CODE, code);
        returnIntent.putExtra(KEY_MESSAGE, message);
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }
}
