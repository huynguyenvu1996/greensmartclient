package com.group07.greensmart.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.group07.greensmart.R;
import com.group07.greensmart.adapter.NotificationsAdapter;
import com.group07.greensmart.model.Notifications;

import java.util.ArrayList;

/**
 * Created by nguyenvuhuy on 3/28/18.
 */

public class NotificationsFragment extends Fragment {

    private RecyclerView rvNotifications = null;
    private ArrayList<Notifications> listNotifications = null;
    private NotificationsAdapter notificationsAdapter = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_notifications, null);

        listNotifications = new ArrayList<>();
        listNotifications.add(new Notifications("00000", "hello", "subject", "content", false, "1522897200000", "adsdsadsadsa"));
        listNotifications.add(new Notifications("00000", "hello", "subject", "content", true, "1522897200000", "adsdsadsadsa"));

        notificationsAdapter = new NotificationsAdapter(getContext(), listNotifications);

        rvNotifications = relativeLayout.findViewById(R.id.rv_notifications);

        rvNotifications.setAdapter(notificationsAdapter);
        rvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));


        return relativeLayout;
    }
}
