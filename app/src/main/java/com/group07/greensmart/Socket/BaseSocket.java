package com.group07.greensmart.Socket;

import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class BaseSocket {
    public static final String EVENT_ON_RAIN = "on_rain";
    private static final String TAG = "BaseSocket";
    public static Socket mSocket;

    public static void setConnect() {
        try {
            mSocket = IO.socket("http://192.168.1.10:3000");
        } catch (URISyntaxException ignored) {
            Log.d(TAG, "instance initializer: " + ignored);
        }
    }
}
