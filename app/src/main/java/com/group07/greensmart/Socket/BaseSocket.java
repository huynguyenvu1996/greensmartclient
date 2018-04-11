package com.group07.greensmart.Socket;

import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class BaseSocket {
    public static final String EVENT_PUSH_NOTIFICATION = "event_push_notification";
    public static final String EVENT_WEATHER_SENSOR = "event_weather_sensor";
    private static final String TAG = "BaseSocket";
    public static Socket mSocket;

    public static void setConnect() {
        try {
            mSocket = IO.socket("http://192.168.43.101:3000");
        } catch (URISyntaxException ignored) {
            Log.d(TAG, "instance initializer: " + ignored);
        }
    }
}
