package com.group07.greensmart.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.group07.greensmart.R;
import com.group07.greensmart.activity.MainActivity;
import com.group07.greensmart.socket.BaseSocket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

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
            if (Objects.requireNonNull(vibrator).hasVibrator()) {

                vibrateFor500ms();

                customVibratePatternNoRepeat();

                customVibratePatternRepeatFromSpecificIndex();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createOneShotVibrationUsingVibrationEffect();
                    createWaveFormVibrationUsingVibrationEffect();
                    createWaveFormVibrationUsingVibrationEffectAndAmplitude();
                }


            } else {
                Toast.makeText(BService.this, "Device does not support vibration", Toast.LENGTH_SHORT).show();
            }
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

        BaseSocket.setConnect(BService.this);
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

    private void vibrateFor500ms() {
        vibrator.vibrate(500);
    }

    private void customVibratePatternNoRepeat() {

        // 0 : Start without a delay
        // 400 : Vibrate for 400 milliseconds
        // 200 : Pause for 200 milliseconds
        // 400 : Vibrate for 400 milliseconds
        long[] mVibratePattern = new long[]{0, 400, 200, 400};

        // -1 : Do not repeat this pattern
        // pass 0 if you want to repeat this pattern from 0th index
        vibrator.vibrate(mVibratePattern, -1);

    }

    private void customVibratePatternRepeatFromSpecificIndex() {
        long[] mVibratePattern = new long[]{0, 400, 800, 600, 800, 800, 800, 1000};

        // 3 : Repeat this pattern from 3rd element of an array
        vibrator.vibrate(mVibratePattern, 3);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createOneShotVibrationUsingVibrationEffect() {
        // 1000 : Vibrate for 1 sec
        // VibrationEffect.DEFAULT_AMPLITUDE - would perform vibration at full strength
        VibrationEffect effect = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);
        vibrator.vibrate(effect);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createWaveFormVibrationUsingVibrationEffect() {
        long[] mVibratePattern = new long[]{0, 400, 1000, 600, 1000, 800, 1000, 1000};
        // -1 : Play exactly once
        VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern, -1);
        vibrator.vibrate(effect);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createWaveFormVibrationUsingVibrationEffectAndAmplitude() {

        long[] mVibratePattern = new long[]{0, 400, 800, 600, 800, 800, 800, 1000};
        int[] mAmplitudes = new int[]{0, 255, 0, 255, 0, 255, 0, 255};
        // -1 : Play exactly once

        if (vibrator.hasAmplitudeControl()) {
            VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern, mAmplitudes, -1);
            vibrator.vibrate(effect);
        }
    }
}
