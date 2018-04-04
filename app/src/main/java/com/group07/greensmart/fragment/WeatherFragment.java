package com.group07.greensmart.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.group07.greensmart.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Random;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by nguyenvuhuy on 3/28/18.
 */

public class WeatherFragment extends Fragment {
    public static String TAG = "Manh";
    private final Handler mHandler = new Handler();
    double mLastRandom = 2;
    Random mRand = new Random();
    private Runnable mTimer;
    private LineGraphSeries<DataPoint> mSeries;
    private double graphLastXValue = 5d;
    private Socket mSocket;
    private Boolean isConnected = true;
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    };
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.d(TAG, data.toString());
                    try {
                        double humidity = data.getDouble("humidity");
                        graphLastXValue += 0.25d;
                        mSeries.appendData(new DataPoint(graphLastXValue, humidity), true, 22);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    {
        try {
            mSocket = IO.socket("http://192.168.1.10:3000");
        } catch (URISyntaxException ignored) {
            Log.d(TAG, "instance initializer: " + ignored);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSocket.on(Socket.EVENT_CONNECT, onConnect)
                .on("chat message", onNewMessage)
                .on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                    }

                });
        mSocket.connect();
    }

    private double getRandom() {
        return mLastRandom += mRand.nextDouble() * 0.5 - 0.25;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_weather, null);
        GraphView graph = linearLayout.findViewById(R.id.graph);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(4);

        graph.getGridLabelRenderer().setLabelVerticalWidth(100);
        mSeries = new LineGraphSeries<>();
        mSeries.setDrawDataPoints(true);
        mSeries.setDrawBackground(true);
        graph.addSeries(mSeries);

        return linearLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
//        mTimer = new Runnable() {
//            @Override
//            public void run() {
//                mSeries.appendData(new DataPoint(graphLastXValue, getRandom()), true, 22);
//                mHandler.postDelayed(this, 2000);
//            }
//        };
//        mHandler.postDelayed(mTimer, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mTimer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        //mSocket.off("chat message", onNewMessage);
    }
}
