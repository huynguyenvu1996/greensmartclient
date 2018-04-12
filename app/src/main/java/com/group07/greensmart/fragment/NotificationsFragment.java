package com.group07.greensmart.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.JsonArray;
import com.group07.greensmart.R;
import com.group07.greensmart.adapter.NotificationsAdapter;
import com.group07.greensmart.model.ApiResponse;
import com.group07.greensmart.model.Notifications;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nguyenvuhuy on 3/28/18.
 */

public class NotificationsFragment extends BaseFragment {

    private RecyclerView rvNotifications;
    private ArrayList<Notifications> listNotifications;
    private NotificationsAdapter notificationsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_notifications, null);

        listNotifications = new ArrayList<>();

        notificationsAdapter = new NotificationsAdapter(getContext(), listNotifications);

        rvNotifications = relativeLayout.findViewById(R.id.rv_notifications);

        rvNotifications.setAdapter(notificationsAdapter);
        rvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));

        loadAGPListFromServer();

        return relativeLayout;
    }


    private void loadAGPListFromServer() {

        apiInterface.getNotificationList("desc").enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {

                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        if (listNotifications != null && !listNotifications.isEmpty()) {
                            listNotifications.clear();
                        }
                        JsonArray listNotificationArray = gson.toJsonTree(response.body().getData()).getAsJsonArray();
                        for (int i = 0; i < listNotificationArray.size(); i++) {
                            Notifications notification = gson.fromJson(listNotificationArray.get(i), Notifications.class);
                            listNotifications.add(notification);
                        }
                        notificationsAdapter.notifyDataSetChanged();
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
}
