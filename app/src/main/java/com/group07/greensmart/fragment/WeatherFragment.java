package com.group07.greensmart.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group07.greensmart.R;
import com.group07.greensmart.Socket.BaseSocket;
import com.group07.greensmart.model.Weather;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import io.socket.emitter.Emitter;

/**
 * Created by nguyenvuhuy on 3/28/18.
 */


public class WeatherFragment extends BaseFragment {
    public static String TAG = "Manh";
    private final Handler mHandler = new Handler();
    double mLastRandom = 2;
    Random mRand = new Random();
    Weather weather;
    private LineGraphSeries<DataPoint> mSeriesTemperature;
    private double graphLastXValue = 5d;
    private LineGraphSeries<DataPoint> mSeriesHumidity;

    private Emitter.Listener onWeatherSensor = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.d(TAG, data.toString());
                    try {
                        weather.setWeather(data);
                        graphLastXValue += 0.25d;
                        mSeriesTemperature.appendData(new DataPoint(graphLastXValue, weather.getTemperature()), true, 22);
                        mSeriesHumidity.appendData(new DataPoint(graphLastXValue, weather.getHumidity()), true, 22);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weather = new Weather();
        BaseSocket.mSocket.on(BaseSocket.EVENT_WEATHER_SENSOR, onWeatherSensor);
    }

    private double getRandom() {
        return mLastRandom += mRand.nextDouble() * 0.5 - 0.25;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        @SuppressLint("InflateParams") ConstraintLayout constraintLayout = (ConstraintLayout) inflater.inflate(R.layout.fragment_weather, null);
        GraphView graphTemperature = constraintLayout.findViewById(R.id.graph_temperature);
        GraphView graphHumidity = constraintLayout.findViewById(R.id.graph_humidity);
        graphTemperature.getViewport().setXAxisBoundsManual(true);
        graphTemperature.getViewport().setMinX(0);
        graphTemperature.getViewport().setMaxX(4);
        graphTemperature.getGridLabelRenderer().setLabelVerticalWidth(100);

        graphHumidity.getViewport().setXAxisBoundsManual(true);
        graphHumidity.getViewport().setMinX(0);
        graphHumidity.getViewport().setMaxX(4);
        graphHumidity.getGridLabelRenderer().setLabelVerticalWidth(100);

        mSeriesTemperature = new LineGraphSeries<>();
        mSeriesHumidity = new LineGraphSeries<>();

        mSeriesTemperature.setDrawDataPoints(true);
        mSeriesTemperature.setDrawBackground(true);
        mSeriesHumidity.setDrawBackground(true);
        mSeriesHumidity.setDrawDataPoints(true);

        graphTemperature.addSeries(mSeriesTemperature);
        graphHumidity.addSeries(mSeriesHumidity);

        return constraintLayout;
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseSocket.mSocket.off(BaseSocket.EVENT_WEATHER_SENSOR, onWeatherSensor);
    }
}
