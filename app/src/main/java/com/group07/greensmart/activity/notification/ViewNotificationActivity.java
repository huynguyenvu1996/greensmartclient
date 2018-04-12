package com.group07.greensmart.activity.notification;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.group07.greensmart.R;
import com.group07.greensmart.activity.BaseActivity;
import com.group07.greensmart.activity.agp.ViewAGPWeatherForecastActivity;
import com.group07.greensmart.model.ApiResponse;
import com.group07.greensmart.model.Notifications;
import com.group07.greensmart.utils.DateUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewNotificationActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = ViewAGPWeatherForecastActivity.class.getSimpleName();
    private TextView txtTitle, txtDate;
    private EditText edtContent;
    private ProgressBar progressBar;
    private Button btnDone;
    private Notifications notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notification);

        txtTitle = findViewById(R.id.txt_notification_title);
        txtDate = findViewById(R.id.txt_notification_date);
        edtContent = findViewById(R.id.edt_notification_content);
        progressBar = findViewById(R.id.pb_notification);
        btnDone = findViewById(R.id.btn_notification_done);

        btnDone.setOnClickListener(this);

        loadNotificationFromServer("121212121");


    }

    private void isLoadingNotification(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void handleError() {
        isLoadingNotification(false);
        edtContent.setText(getString(R.string.message_notification_load_failed));
    }


    public void loadNotificationFromServer(String id) {

        isLoadingNotification(true);

        apiInterface.viewNotification(id).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                isLoadingNotification(false);

                if (response.isSuccessful()) {
                    if (!response.body().isError()) {

                        JsonObject notificationObject = gson.toJsonTree(response.body().getData()).getAsJsonObject();
                        notifications = gson.fromJson(notificationObject, Notifications.class);
                        txtTitle.setText(notifications.getTitle());
                        txtDate.setText(DateUtils.convertDate(notifications.getCreatedAt(), DateUtils.DATE_FORMAT_SIMPLE));
                        edtContent.setText(notifications.getContent());

                    } else {
                        handleError();
                    }
                } else {
                    handleError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                handleError();
                Log.d(TAG, "Error loading from API: " + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_notification_done: {
                finish();
            }
            ;
            break;
        }
    }
}
