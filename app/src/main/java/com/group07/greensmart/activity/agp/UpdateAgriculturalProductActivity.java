package com.group07.greensmart.activity.agp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.group07.greensmart.R;
import com.group07.greensmart.activity.BaseActivity;
import com.group07.greensmart.model.AgriculturalProduct;
import com.group07.greensmart.model.ApiResponse;
import com.group07.greensmart.utils.ActivityUtils;
import com.group07.greensmart.utils.ApplicationUtils;
import com.group07.greensmart.utils.floatingtoast.FloatingToast;
import com.group07.greensmart.utils.floatingtoast.FloatingToastUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.group07.greensmart.constant.Constant.CODE_SUCCESS;
import static com.group07.greensmart.constant.Constant.KEY_ID;
import static com.group07.greensmart.constant.Constant.KEY_REV;

public class UpdateAgriculturalProductActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = "UpdateAgriculturalProductActivity";
    private ProgressBar progressBar;
    private Button btnImage;
    private Button btnUpdate;
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
    private Image imageFile;
    private AgriculturalProduct agriculturalProduct;
    private String id;
    private String rev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_agricultural_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_agp_update));

        id = getIntent().getStringExtra(KEY_ID);
        rev = getIntent().getStringExtra(KEY_REV);

        progressBar = findViewById(R.id.pb_update_agp);
        btnImage = findViewById(R.id.btn_update_agp_image);
        btnUpdate = findViewById(R.id.btn_update_agp_update);
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

        btnUpdate.setOnClickListener(this);
        btnImage.setOnClickListener(this);


        setupSeekBar();

        loadAGPFromServer();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update_agp_image: {
                openFileChooser();
            }
            ;
            break;
            case R.id.btn_update_agp_update: {
                updateAGP();
            }
            ;
            break;
        }
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

    private void openFileChooser() {
        ImagePicker.create(this)
                .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                .folderMode(true) // folder mode (false by default)
                .toolbarFolderTitle("Thư mục hình ảnh") // folder selection title
                .toolbarImageTitle("Chọn hình ảnh") // image selection title
                .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                .theme(R.style.ImagePickerTheme) //Theme
                .single() // single mode
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                .start(); // start image picker activity with request code
    }

    private void isLoadingAGP(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
        }
    }

    public void loadAGPFromServer() {

        isLoadingAGP(true);

        apiInterface.viewAGP(id, null).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                isLoadingAGP(false);

                if (response.isSuccessful()) {
                    if (!response.body().isError()) {

                        JsonObject agpObject = gson.toJsonTree(response.body().getData()).getAsJsonObject();
                        agriculturalProduct = gson.fromJson(agpObject, AgriculturalProduct.class);

                        edtName.setText(agriculturalProduct.getName());
                        sbTempMin.setProgress((int) agriculturalProduct.getMinTemperature());
                        sbTempMax.setProgress((int) agriculturalProduct.getMaxHumidity());
                        sbHumidityMin.setProgress((int) agriculturalProduct.getMinHumidity());
                        sbHumidityMax.setProgress((int) agriculturalProduct.getMaxHumidity());
                        swDetectRain.setChecked(agriculturalProduct.isDetectRain());
                        swDrying.setChecked(agriculturalProduct.isDrying());
                        swNotification.setChecked(agriculturalProduct.isNotification());
                        Picasso.get().load(ApplicationUtils.getImageUrl(UpdateAgriculturalProductActivity.this, agriculturalProduct.getImage())).into(imgAGP);
                    } else {
                        isLoadingAGP(false);
                    }
                } else {
                    int statusCode = response.code();
                    isLoadingAGP(false);
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                isLoadingAGP(false);
                Log.d("MainActivity", "error loading from API" + t.getMessage());

            }
        });
    }

    public static RequestBody toRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }


    public String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        Toast.makeText(this, type.toString(), Toast.LENGTH_SHORT).show();
        return type;
    }

    public void updateAGP() {

        isLoadingAGP(true);
        Toast.makeText(this, getString(R.string.message_send_request), Toast.LENGTH_SHORT).show();
        Map<String, RequestBody> map = new HashMap<>();
        map.put("_id", toRequestBody(agriculturalProduct.getId()));
        map.put("name", toRequestBody(edtName.getText().toString().trim()));
        map.put("image", toRequestBody(agriculturalProduct.getImage()));
        map.put("temp_min", toRequestBody(txtTempMin.getText().toString().trim()));
        map.put("temp_max", toRequestBody(txtTempMax.getText().toString().trim()));
        map.put("humidity_min", toRequestBody(txtHumidityMin.getText().toString().trim()));
        map.put("humidity_max", toRequestBody(txtHumidityMax.getText().toString().trim()));
        map.put("detect_rain", toRequestBody(Boolean.toString(swDetectRain.isChecked())));
        map.put("drying", toRequestBody(Boolean.toString(swDrying.isChecked())));
        map.put("notification", toRequestBody(Boolean.toString(swNotification.isChecked())));
        map.put("_rev", toRequestBody(agriculturalProduct.getRev()));

        if (imageFile != null) {
        /*https://stackoverflow.com/questions/36653277/error-uploading-a-file-using-retrofit-2*/
            RequestBody fileBody = RequestBody.create(MediaType.parse(getMimeType(imageFile.getPath())), new File(imageFile.getPath()));
            map.put("image_file\"; filename=\"" + imageFile.getName() + "\"", fileBody);
        }

        apiInterface.updateAGP(map).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {

                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        ActivityUtils.makeResult(UpdateAgriculturalProductActivity.this, CODE_SUCCESS, getString(R.string.message_add_agp_success));
                    } else {
                        Log.d(TAG, "Data response error: " + response.body().getMessage());
                        isLoadingAGP(false);
                    }
                } else {
                    Log.d(TAG, "Response error: " + response.message());
                    isLoadingAGP(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "Error loading from API: " + t.getMessage());
                isLoadingAGP(false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            imageFile = ImagePicker.getFirstImageOrNull(data);
            Picasso.get().load(Uri.fromFile(new File(imageFile.getPath())))
                    .resize(90, 90)
                    .into(imgAGP);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
