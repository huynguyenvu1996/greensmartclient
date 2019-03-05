package com.group07.greensmart.activity.agp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.google.gson.JsonArray;
import com.group07.greensmart.R;
import com.group07.greensmart.activity.BaseActivity;
import com.group07.greensmart.adapter.OpenWeatherAdapter;
import com.group07.greensmart.model.AgriculturalProduct;
import com.group07.greensmart.model.ApiResponse;
import com.group07.greensmart.model.OpenWeather;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAGPWeatherForecastActivity extends BaseActivity {

    private RecyclerView rvOpenWeather;
    private ProgressBar progressBar;
    private OpenWeatherAdapter openWeatherAdapter;
    private ArrayList<OpenWeather> listOpenWeather;
    private AgriculturalProduct agp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_agp_weather_forecast);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_agp_forecast));

        rvOpenWeather = findViewById(R.id.rv_agp_weather_forecast);
        progressBar = findViewById(R.id.pb_agp_weather_forecast);

        agp = (AgriculturalProduct) getIntent().getSerializableExtra("AGP");

        if (agp != null) {
            getWeatherForecastFromServer(agp);
        }

        listOpenWeather = new ArrayList<>();
        openWeatherAdapter = new OpenWeatherAdapter(this, listOpenWeather);

        rvOpenWeather.setAdapter(openWeatherAdapter);
        rvOpenWeather.setLayoutManager(new LinearLayoutManager(ViewAGPWeatherForecastActivity.this));


    }


    private void getWeatherForecastFromServer(AgriculturalProduct agp) {

        apiInterface.getCompatibleWeatherList(agp).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {

                if (response.isSuccessful()) {
                    if (!response.body().isError()) {

                        if (listOpenWeather != null && !listOpenWeather.isEmpty()) {
                            listOpenWeather.clear();
                        }

                        JsonArray listOWArray = gson.toJsonTree(response.body().getData()).getAsJsonArray();

                        for (int i = 0; i < listOWArray.size(); i++) {
                            OpenWeather openWeather = gson.fromJson(listOWArray.get(i), OpenWeather.class);
                            listOpenWeather.add(openWeather);
                        }

                        openWeatherAdapter.notifyDataSetChanged();


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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
