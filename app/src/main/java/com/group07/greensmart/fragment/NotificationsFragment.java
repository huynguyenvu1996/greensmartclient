package com.group07.greensmart.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.JsonArray;
import com.group07.greensmart.R;
import com.group07.greensmart.adapter.NotificationsAdapter;
import com.group07.greensmart.listener.RecycleViewOnItemClickListener;
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

    private static final String TAG = NotificationsFragment.class.getSimpleName();
    private RecyclerView rvNotifications;
    private ArrayList<Notifications> listNotifications;
    private NotificationsAdapter notificationsAdapter;
    private ProgressBar progressBar;
    private FloatingActionButton fabFilter;

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
        progressBar = relativeLayout.findViewById(R.id.pb_notification);
        fabFilter = relativeLayout.findViewById(R.id.fab_filter_notifications);

        rvNotifications.setAdapter(notificationsAdapter);
        rvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));

        showHideWhenScroll();
        loadAGPListFromServer();


        notificationsAdapter.setRecycleViewOnItemClickListener(new RecycleViewOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });

        return relativeLayout;
    }

    private void isLoadingNotification(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


    private void showHideWhenScroll() {
        rvNotifications.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //dy > 0: scroll up; dy < 0: scroll down
                if (dy > 0) fabFilter.hide();
                else fabFilter.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void loadAGPListFromServer() {
        isLoadingNotification(true);
        apiInterface.getNotificationList("desc").enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                isLoadingNotification(false);
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
                    Log.d(TAG, "Posts loaded from API");
                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                isLoadingNotification(false);
                Log.d(TAG, "Error loading from API" + t.getMessage());
            }
        });
    }
}
