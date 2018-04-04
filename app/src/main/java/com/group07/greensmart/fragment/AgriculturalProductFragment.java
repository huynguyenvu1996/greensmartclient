package com.group07.greensmart.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.group07.greensmart.R;
import com.group07.greensmart.activity.AddAgriculturalProductActivity;
import com.group07.greensmart.adapter.AgriculturalProductAdapter;
import com.group07.greensmart.adapter.NotificationsAdapter;
import com.group07.greensmart.model.AgriculturalProduct;
import com.group07.greensmart.model.Notifications;

import java.util.ArrayList;

/**
 * Created by nguyenvuhuy on 3/28/18.
 */

public class AgriculturalProductFragment extends Fragment {

    private FloatingActionButton fabAddAGP;
    private RecyclerView rvAGP = null;
    private ArrayList<AgriculturalProduct> listAGP = null;
    private AgriculturalProductAdapter agriculturalProductAdapter = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_agricultural_product, null);

        fabAddAGP = (FloatingActionButton) relativeLayout.findViewById(R.id.fab_add_agricultural_product);
        fabAddAGP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addAGPIntent = new Intent(getActivity(), AddAgriculturalProductActivity.class);
                startActivity(addAGPIntent);
            }
        });

        listAGP = new ArrayList<>();

        listAGP.add(new AgriculturalProduct("00000", "Ca phe", "sadsad", 25, 30, 20, 80, true, true, "dcdcdcd", "Asdasd"));
        listAGP.add(new AgriculturalProduct("00000", "Ca phe", "sadsad", 25, 30, 20, 80, true, true, "dcdcdcd", "Asdasd"));
        listAGP.add(new AgriculturalProduct("00000", "Ca phe", "sadsad", 25, 30, 20, 80, true, true, "dcdcdcd", "Asdasd"));
        listAGP.add(new AgriculturalProduct("00000", "Ca phe", "sadsad", 25, 30, 20, 80, true, true, "dcdcdcd", "Asdasd"));
        listAGP.add(new AgriculturalProduct("00000", "Ca phe", "sadsad", 25, 30, 20, 80, true, true, "dcdcdcd", "Asdasd"));
        listAGP.add(new AgriculturalProduct("00000", "Ca phe", "sadsad", 25, 30, 20, 80, true, true, "dcdcdcd", "Asdasd"));
        listAGP.add(new AgriculturalProduct("00000", "Ca phe", "sadsad", 25, 30, 20, 80, true, true, "dcdcdcd", "Asdasd"));
        listAGP.add(new AgriculturalProduct("00000", "Ca phe", "sadsad", 25, 30, 20, 80, true, true, "dcdcdcd", "Asdasd"));
        listAGP.add(new AgriculturalProduct("00000", "Ca phe", "sadsad", 25, 30, 20, 80, true, true, "dcdcdcd", "Asdasd"));
        listAGP.add(new AgriculturalProduct("00000", "Ca phe", "sadsad", 25, 30, 20, 80, true, true, "dcdcdcd", "Asdasd"));

        agriculturalProductAdapter = new AgriculturalProductAdapter(getContext(), listAGP);

        rvAGP = relativeLayout.findViewById(R.id.rv_agricultural_product);

        rvAGP.setAdapter(agriculturalProductAdapter);
        rvAGP.setLayoutManager(new LinearLayoutManager(getContext()));

        showHideWhenScroll();
        return relativeLayout;
    }

    private void showHideWhenScroll() {
        rvAGP.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //dy > 0: scroll up; dy < 0: scroll down
                if (dy > 0) fabAddAGP.hide();
                else fabAddAGP.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}
