package com.group07.greensmart.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    private final Handler mHandler = new Handler();
    double mLastRandom = 2;
    Random mRand = new Random();
    private Runnable mTimer1;
    private Runnable mTimer2;
    private LineGraphSeries<DataPoint> mSeries1;
    private double graph2LastXValue = 5d;
    private Socket mSocket;
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    try {
                        username = data.getString("username");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    {
        try {
            mSocket = IO.socket("http://chat.socket.io");
        } catch (URISyntaxException ignored) {
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSocket.on(Socket.EVENT_CONNECT, onNewMessage)
                .on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                    }

                });
        mSocket.connect();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_weather, null);
        GraphView graph = linearLayout.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(generateData());
        graph.addSeries(series);

        return linearLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimer1 = new Runnable() {
            @Override
            public void run() {
                mSeries1.resetData(generateData());
                mHandler.postDelayed(this, 300);
            }
        };
        mHandler.postDelayed(mTimer1, 300);

//        mTimer2 = new Runnable() {
//            @Override
//            public void run() {
//                graph2LastXValue += 1d;
//                mSeries2.appendData(new DataPoint(graph2LastXValue, getRandom()), true, 40);
//                mHandler.postDelayed(this, 200);
//            }
//        };
//        mHandler.postDelayed(mTimer2, 1000);
    }

    private DataPoint[] generateData() {
        int count = 30;
        DataPoint[] values = new DataPoint[count];
        for (int i = 0; i < count; i++) {
            double x = i;
            double f = mRand.nextDouble() * 0.15 + 0.3;
            double y = Math.sin(i * f + 2) + mRand.nextDouble() * 0.3;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }

    private double getRandom() {
        return mLastRandom += mRand.nextDouble() * 0.5 - 0.25;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("new message", onNewMessage);
    }

}
