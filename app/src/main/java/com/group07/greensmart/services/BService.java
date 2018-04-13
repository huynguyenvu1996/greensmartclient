package com.group07.greensmart.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.group07.greensmart.R;
import com.group07.greensmart.activity.MainActivity;
import com.group07.greensmart.socket.BaseSocket;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class BService extends Service {
    private static final String TAG = "BServiec";
    public BService that = this;
    Vibrator vibrator;
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
    private Emitter.Listener onPushNotification = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "call: " + args[0].toString());
            that.createNotification(args[0].toString(), "Troi dang mua!!!");
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
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
        if (BaseSocket.mSocket == null) {
            BaseSocket.setConnect(BService.this);
        }
        BaseSocket.mSocket
                .on(Socket.EVENT_CONNECT, onConnect)
                .on(BaseSocket.EVENT_PUSH_NOTIFICATION, onPushNotification)
                .on(Socket.EVENT_DISCONNECT, onDisconnect);
        BaseSocket.mSocket.connect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void createNotification(String title, String text) {
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
        BaseSocket.mSocket.off(BaseSocket.EVENT_PUSH_NOTIFICATION, onPushNotification);
        BaseSocket.mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        BaseSocket.mSocket.off(Socket.EVENT_CONNECT, onConnect);
    }

}
