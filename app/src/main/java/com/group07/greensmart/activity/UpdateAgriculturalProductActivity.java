package com.group07.greensmart.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.esafirm.imagepicker.model.Image;
import com.google.gson.JsonArray;
import com.group07.greensmart.R;
import com.group07.greensmart.model.AgriculturalProduct;
import com.group07.greensmart.model.ApiResponse;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAgriculturalProductActivity extends BaseActivity {

    private String TAG = "UpdateAgriculturalProductActivity";
    ProgressBar progressBar;
    private Button btnImage;
    private Button btnUpdate;
    private Button btnDelete;
    private CircleImageView imgAGP;
    private SeekBar sbTempMin;
    private SeekBar sbTempMax;
    private SeekBar sbHumidityMin;
    private SeekBar sbHumidityMax;
    private TextView txtTempMin;
    private TextView txtTempMax;
    private TextView txtHumidityMin;
    private TextView txtHumidityMax;
    private EditText edtName;
    private Switch swDetectRain;
    private Switch swDrying;
    private Switch swNotification;
    private Image imageFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_agricultural_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_agp_update));

        progressBar = findViewById(R.id.pb_update_agp);
        btnImage = findViewById(R.id.btn_update_agp_image);
        btnUpdate = findViewById(R.id.btn_update_agp_update);
        btnDelete = findViewById(R.id.btn_update_agp_delete);
        imgAGP = findViewById(R.id.img_update_agp_image);
        sbTempMin = findViewById(R.id.sb_update_agp_temp_min);
        sbTempMax = findViewById(R.id.sb_btn_update_agp_temp_max);
        sbHumidityMin = findViewById(R.id.sb_update_agp_humidity_min);
        sbHumidityMax = findViewById(R.id.sb_update_agp_humidity_max);
        txtTempMin = findViewById(R.id.txt_update_agp_temp_min);
        txtTempMax = findViewById(R.id.txt_update_agp_temp_max);
        txtHumidityMin = findViewById(R.id.txt_update_agp_humidity_min);
        txtHumidityMax = findViewById(R.id.txt_update_agp_humidity_max);
        edtName = findViewById(R.id.edt_update_agp_name);
        swDetectRain = findViewById(R.id.sw_update_agp_detect_rain);
        swDrying = findViewById(R.id.sw_update_agp_drying);
        swNotification = findViewById(R.id.sw_update_agp_notification);


        setupSeekBar();


    }

    private void setupSeekBar() {

        sbTempMin.setMax(100);
        sbTempMax.setMax(100);
        sbHumidityMin.setMax(100);
        sbHumidityMax.setMax(100);

        sbTempMin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtTempMin.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbTempMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtTempMax.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbHumidityMin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtHumidityMin.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbHumidityMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtHumidityMax.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void loadAGPListFromServer() {

        progressBar.setVisibility(View.VISIBLE);
        apiInterface.viewAGP().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {


                progressBar.setVisibility(View.GONE);


                if (response.isSuccessful()) {
                    if (!response.body().isError()) {

                        JsonArray listAGPArray = gson.toJsonTree(response.body().getData()).getAsJsonArray();

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
                    progressBar.setVisibility(View.GONE);
                Log.d("MainActivity", "error loading from API" + t.getMessage());

            }
        });
    }
}
