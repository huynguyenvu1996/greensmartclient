package com.group07.greensmart.socket;

import android.content.Context;
import android.util.Log;

import com.group07.greensmart.utils.ApplicationUtils;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class BaseSocket {
    public static final String EVENT_PUSH_NOTIFICATION = "event_push_notification";
    public static final String EVENT_WEATHER_SENSOR = "event_weather_sensor";
    private static final String TAG = BaseSocket.class.getSimpleName();
    public static Socket mSocket;

    public static void setConnect(Context context) {
        try {
            mSocket = IO.socket(ApplicationUtils.getServerUrl(context));
        } catch (URISyntaxException ignored) {
            Log.d(TAG, "instance initializer: " + ignored);
        }
    }
}
