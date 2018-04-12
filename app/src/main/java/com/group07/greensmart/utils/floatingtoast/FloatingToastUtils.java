package com.group07.greensmart.utils.floatingtoast;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;


/**
 * Created by huyjackson on 7/17/17.
 */

public class FloatingToastUtils {

    public final static String DEFAULT_TITLE_COLOR = "#000000";
    public final static String DEFAULT_SUB_TITLE_COLOR = "#80000000";
    public final static String DEFAULT_BACKGROUND_COLOR = "#FFFFFF";
    public final static String DEFAUL_TOAST_TITLE_COLOR = "#FFFFFF";
    public final static String DEFAUL_TOASTT_BACKGROUND_COLOR = "#333333";
    public final static int LENGTH_SHORT = 1000;
    public final static int LENGTH_LONG = 2000;


    public static FloatingToast makeText(Context context, ViewGroup parentView, String title, String subTitle,
                                         int duration) {

        return new FloatingToast(context, parentView)
                .setTitle(title)
                .setSubtitleColor(Color.parseColor(DEFAULT_SUB_TITLE_COLOR))
                .setSubTitle(subTitle)
                .setBackgroundColor(Color.parseColor(DEFAULT_BACKGROUND_COLOR))
                .setTitleColor(Color.parseColor(DEFAULT_TITLE_COLOR))
                .setDuration(duration)
                .isLarge(false)
                .build();
    }

    public static FloatingToast makeText(Context context, ViewGroup parentView, String title,
                                         String subTitle, Drawable icon,
                                         String backgroundColor,
                                         String titleColor,
                                         String subTitleColor,
                                         int duration) {
        return new FloatingToast(context, parentView)
                .setTitle(title)
                .setSubtitleColor(Color.parseColor(subTitleColor))
                .setSubTitle(subTitle)
                .setBackgroundColor(Color.parseColor(backgroundColor))
                .setIcon(icon)
                .setDuration(duration)
                .setTitleColor(Color.parseColor(titleColor))
                .alignTop(false)
                .isLarge(false)
                .build();
    }

    public static FloatingToast makeText(Context context, ViewGroup parentView, String title, Drawable icon, int duration) {
        return new FloatingToast(context, parentView)
                .setTitle(title)
                .setBackgroundColor(Color.parseColor(DEFAUL_TOASTT_BACKGROUND_COLOR))
                .setTitleColor(Color.parseColor(DEFAUL_TOAST_TITLE_COLOR))
                .setIcon(icon)
                .setDuration(duration)
                .alignTop(false)
                .isLarge(false)
                .build();
    }
}

