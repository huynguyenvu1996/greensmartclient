package com.group07.greensmart.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.group07.greensmart.R;
import com.group07.greensmart.Socket.BaseSocket;
import com.group07.greensmart.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class BService extends Service {
    private static final String TAG = "BServiec";
    static SharedPreferences.Editor configEditor;
    public BService that = this;
    SharedPreferences prefs;
    Context ctx;
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {

        @Override
        public void call(Object... args) {

        }
    };
    private Emitter.Listener onRain = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "call: " + args[0].toString());
            JSONObject data = (JSONObject) args[0];
            String title = "Title";
            String subject = "Subject";
            try {
                title = data.getString("title");
                subject = data.getString("subject");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            that.createNotification(title, subject);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        BaseSocket.setConnect();
        BaseSocket.mSocket
                .on(Socket.EVENT_CONNECT, onConnect)
                .on(BaseSocket.EVENT_RAIN_SENSOR, onRain)
                .on(Socket.EVENT_DISCONNECT, onDisconnect);
        BaseSocket.mSocket.connect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void createNotification(String title, String text) {
        /////////////////////////////////////


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, "")
                        .setSmallIcon(R.drawable.ic_add_white)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        if (mNotificationManager != null) {
            mNotificationManager.notify(1, mBuilder.build());
        }

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "disconnected");
        BaseSocket.mSocket.off(BaseSocket.EVENT_RAIN_SENSOR, onRain);
        BaseSocket.mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        BaseSocket.mSocket.off(Socket.EVENT_CONNECT, onConnect);
    }
}
