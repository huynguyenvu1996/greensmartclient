package com.group07.greensmart.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.group07.greensmart.R;
import com.group07.greensmart.activity.notification.ViewNotificationActivity;
import com.group07.greensmart.rest.DefaultSharedPrefsUtils;
import com.group07.greensmart.socket.BaseSocket;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class BService extends Service {
    private final String TAG = this.getClass().getName();
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
            Log.d(TAG, "call: push");
            JSONObject data = (JSONObject) args[0];
            String title = "Title";
            String subject = "Subject";
            String id = "id";
            try {
                title = data.getString("title");
                subject = data.getString("subject");
                id = data.getString("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (DefaultSharedPrefsUtils.isEnabledNotification(getApplicationContext())) {
                Log.d(TAG, "call: createNoti");
                that.createNotification(title, subject, id);
            }
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

    public void createNotification(String title, String text, String id) {
        final int NOTIFICATION_ID = 1;
        final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true);

// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, ViewNotificationActivity.class);
        resultIntent.putExtra("id", id);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingNotificationIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        if (mNotificationManager != null) {
            Log.d(TAG, "createNotification: ");
            mNotificationManager.notify(NOTIFICATION_ID, builder.build());
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
