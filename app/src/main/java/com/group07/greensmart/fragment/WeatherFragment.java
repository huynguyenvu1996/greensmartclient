package com.group07.greensmart.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.group07.greensmart.R;
import com.group07.greensmart.model.ApiResponse;
import com.group07.greensmart.model.OpenWeather;
import com.group07.greensmart.model.Weather;
import com.group07.greensmart.rest.DefaultSharedPrefsUtils;
import com.group07.greensmart.socket.BaseSocket;
import com.group07.greensmart.utils.DateUtils;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nguyenvuhuy on 3/28/18.
 */


public class WeatherFragment extends BaseFragment {
    private static final String TAG = WeatherFragment.class.getSimpleName();
    private Weather weather;
    private LineGraphSeries<DataPoint> mSeriesTemperature;
    private double graphLastXValue = 5d;
    private LineGraphSeries<DataPoint> mSeriesHumidity;
    private TextView txtRealTimeTemp, txtRealTimeHumidity, txtInternetTemp, txtInternetHumidity, txtInternetDate, txtInternetCity, txtInternetDescription;
    private ImageView imgInternetIcon;
    private OpenWeather openWeather;

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
                        txtRealTimeHumidity.setText(String.valueOf((int) weather.getHumidity()) + "°C");
                        txtRealTimeTemp.setText(String.valueOf((int) weather.getTemperature()) + "%");
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

        loadCurrentWeatherInternetFromServer();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        @SuppressLint("InflateParams") RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_weather, null);
        GraphView graphTemperature = relativeLayout.findViewById(R.id.graph_weather_real_time_temperature);
        GraphView graphHumidity = relativeLayout.findViewById(R.id.graph_weather_real_time_humidity);
        txtRealTimeTemp = relativeLayout.findViewById(R.id.txt_weather_real_time_humidity);
        txtRealTimeHumidity = relativeLayout.findViewById(R.id.txt_weather_real_time_temperature);
        txtInternetTemp = relativeLayout.findViewById(R.id.txt_weather_internet_temperature);
        txtInternetHumidity = relativeLayout.findViewById(R.id.txt_weather_internet_hudimity);
        txtInternetCity = relativeLayout.findViewById(R.id.txt_weather_internet_city);
        txtInternetDescription = relativeLayout.findViewById(R.id.txt_weather_internet_description);
        txtInternetDate = relativeLayout.findViewById(R.id.txt_weather_internet_date);
        imgInternetIcon = relativeLayout.findViewById(R.id.img_weather_internet_image);


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

        return relativeLayout;
    }

    private void loadCurrentWeatherInternetFromServer() {

        apiInterface.getCurrentInternetWeather(DefaultSharedPrefsUtils.getLocationLat(getActivity()),
                DefaultSharedPrefsUtils.getLocationLng(getActivity()))
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                        if (response.isSuccessful()) {
                            if (!response.body().isError()) {
                                JsonObject agpObject = gson.toJsonTree(response.body().getData()).getAsJsonObject();
                                openWeather = gson.fromJson(agpObject, OpenWeather.class);
                                txtInternetCity.setText(openWeather.getCityName());
                                txtInternetDate.setText(DateUtils.convertDate(openWeather.getDt(), DateUtils.DATE_FORMAT_SIMPLE));
                                txtInternetDescription.setText(openWeather.getDescription());
                                txtInternetTemp.setText(String.valueOf((int) openWeather.getTemperature()));
                                txtInternetHumidity.setText(getString(R.string.item_agp_humidity_forecast, String.valueOf((int) openWeather.getHumidity())) + "%");
                                Picasso.get().load(openWeather.getIcon()).into(imgInternetIcon);
                            } else {

                            }
                            Log.d("MainActivity", "posts loaded from API");
                        } else {
                            int statusCode = response.code();
                            // handle request errors depending on status code
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                        Log.d("MainActivity", "error loading from API" + t.getMessage());
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
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
