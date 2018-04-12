package com.group07.greensmart.rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * A pack of helpful getter and setter methods for reading/writing to {@link SharedPreferences}.
 */
public class DefaultSharedPrefsUtils {

    public static final String KEY_FIRST_LAUNCH_APPS = "first_launch_apps";

    public static final String KEY_NOTIFICATION = "notification";
    public static final String KEY_SERVER = "server";
    public static final String VALUE_SERVER_DEFAULT = "http://host:port/";

    public static final String KEY_LOCATION_LAT = "location_lat";
    public static final String KEY_LOCATION_LNG = "location_lng";
    //HCMUS coord
    public static final String VALUE_LOCATION_LAT_DEFAULT = "10.7624218";
    public static final String VALUE_LOCATION_LNG_DEFAULT = "106.6790126";


    private DefaultSharedPrefsUtils() {
    }


    public static boolean isFirstLaunchApps(Context context) {
        return getBooleanPreference(context, KEY_FIRST_LAUNCH_APPS, true);
    }

    public static boolean setFirstLaunchApps(Context context, boolean isFirst) {
        return setBooleanPreference(context, KEY_FIRST_LAUNCH_APPS, isFirst);
    }


    public static String getServer(Context context) {
        String server = DefaultSharedPrefsUtils.getStringPreference(context, KEY_SERVER);
        if (server != null && !server.isEmpty()) {
            return server;
        } else {
            return VALUE_SERVER_DEFAULT;
        }
    }

    public static boolean setServer(Context context, String server) {
        return DefaultSharedPrefsUtils.setStringPreference(context, KEY_SERVER, server);

    }

    public static String getLocationLat(Context context) {
        String server = DefaultSharedPrefsUtils.getStringPreference(context, KEY_LOCATION_LAT);
        if (server != null && !server.isEmpty()) {
            return server;
        } else {
            return VALUE_LOCATION_LAT_DEFAULT;
        }
    }

    public static boolean setLocationLat(Context context, String lat) {
        return DefaultSharedPrefsUtils.setStringPreference(context, KEY_LOCATION_LAT, lat);

    }


    public static String getLocationLng(Context context) {
        String server = DefaultSharedPrefsUtils.getStringPreference(context, KEY_LOCATION_LNG);
        if (server != null && !server.isEmpty()) {
            return server;
        } else {
            return VALUE_LOCATION_LNG_DEFAULT;
        }
    }

    public static boolean setLocationLng(Context context, String lng) {
        return DefaultSharedPrefsUtils.setStringPreference(context, KEY_LOCATION_LNG, lng);

    }

    public static boolean isEnabledNotification(Context context) {
        return getBooleanPreference(context, KEY_NOTIFICATION, false);
    }

    /**
     * Helper method to retrieve a String value from {@link SharedPreferences}.
     *
     * @param context a {@link Context} object.
     * @param key
     * @return The value from shared preferences, or null if the value could not be read.
     */
    public static String getStringPreference(Context context, String key) {
        String value = null;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getString(key, null);
        }
        return value;
    }

    /**
     * Helper method to write a String value to {@link SharedPreferences}.
     *
     * @param context a {@link Context} object.
     * @param key
     * @param value
     * @return true if the new value was successfully written to persistent storage.
     */
    public static boolean setStringPreference(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            return editor.commit();
        }
        return false;
    }

    /**
     * Helper method to retrieve a float value from {@link SharedPreferences}.
     *
     * @param context      a {@link Context} object.
     * @param key
     * @param defaultValue A default to return if the value could not be read.
     * @return The value from shared preferences, or the provided default.
     */
    public static float getFloatPreference(Context context, String key, float defaultValue) {
        float value = defaultValue;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getFloat(key, defaultValue);
        }
        return value;
    }

    /**
     * Helper method to write a float value to {@link SharedPreferences}.
     *
     * @param context a {@link Context} object.
     * @param key
     * @param value
     * @return true if the new value was successfully written to persistent storage.
     */
    public static boolean setFloatPreference(Context context, String key, float value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putFloat(key, value);
            return editor.commit();
        }
        return false;
    }

    /**
     * Helper method to retrieve a long value from {@link SharedPreferences}.
     *
     * @param context      a {@link Context} object.
     * @param key
     * @param defaultValue A default to return if the value could not be read.
     * @return The value from shared preferences, or the provided default.
     */
    public static long getLongPreference(Context context, String key, long defaultValue) {
        long value = defaultValue;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getLong(key, defaultValue);
        }
        return value;
    }

    /**
     * Helper method to write a long value to {@link SharedPreferences}.
     *
     * @param context a {@link Context} object.
     * @param key
     * @param value
     * @return true if the new value was successfully written to persistent storage.
     */
    public static boolean setLongPreference(Context context, String key, long value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong(key, value);
            return editor.commit();
        }
        return false;
    }

    /**
     * Helper method to retrieve an integer value from {@link SharedPreferences}.
     *
     * @param context      a {@link Context} object.
     * @param key
     * @param defaultValue A default to return if the value could not be read.
     * @return The value from shared preferences, or the provided default.
     */
    public static int getIntegerPreference(Context context, String key, int defaultValue) {
        int value = defaultValue;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = Integer.parseInt(preferences.getString(key, String.valueOf(defaultValue)));
        }
        return value;
    }

    /**
     * Helper method to write an integer value to {@link SharedPreferences}.
     *
     * @param context a {@link Context} object.
     * @param key
     * @param value
     * @return true if the new value was successfully written to persistent storage.
     */
    public static boolean setIntegerPreference(Context context, String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key, value);
            return editor.commit();
        }
        return false;
    }

    /**
     * Helper method to retrieve a boolean value from {@link SharedPreferences}.
     *
     * @param context      a {@link Context} object.
     * @param key
     * @param defaultValue A default to return if the value could not be read.
     * @return The value from shared preferences, or the provided default.
     */
    public static boolean getBooleanPreference(Context context, String key, boolean defaultValue) {
        boolean value = defaultValue;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getBoolean(key, defaultValue);
        }
        return value;
    }

    /**
     * Helper method to write a boolean value to {@link SharedPreferences}.
     *
     * @param context a {@link Context} object.
     * @param key
     * @param value
     * @return true if the new value was successfully written to persistent storage.
     */
    public static boolean setBooleanPreference(Context context, String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(key, value);
            return editor.commit();
        }
        return false;
    }
}