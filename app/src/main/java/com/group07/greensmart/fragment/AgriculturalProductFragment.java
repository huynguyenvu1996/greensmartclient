package com.group07.greensmart.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.group07.greensmart.R;
import com.group07.greensmart.activity.agp.AddAgriculturalProductActivity;
import com.group07.greensmart.activity.agp.UpdateAgriculturalProductActivity;
import com.group07.greensmart.activity.agp.ViewAGPWeatherForecastActivity;
import com.group07.greensmart.adapter.AgriculturalProductAdapter;
import com.group07.greensmart.listener.OnMenuItemAGPClickListener;
import com.group07.greensmart.listener.RecycleViewOnItemClickListener;
import com.group07.greensmart.model.AgriculturalProduct;
import com.group07.greensmart.model.ApiResponse;
import com.group07.greensmart.utils.ToastUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.group07.greensmart.constant.Constant.CODE_SUCCESS;
import static com.group07.greensmart.constant.Constant.KEY_CODE;
import static com.group07.greensmart.constant.Constant.KEY_ID;
import static com.group07.greensmart.constant.Constant.KEY_MESSAGE;
import static com.group07.greensmart.constant.Constant.KEY_REV;
import static com.group07.greensmart.constant.Constant.REQUEST_CODE_ADD_AGP;

/**
 * Created by nguyenvuhuy on 3/28/18.
 */

public class AgriculturalProductFragment extends BaseFragment {

    private FloatingActionButton fabAddAGP;
    private RecyclerView rvAGP;
    private ProgressBar progressBar;
    private SwipeRefreshLayout srAGP;
    private ArrayList<AgriculturalProduct> listAGP;
    private boolean isSwipeRefresh = false;
    private AgriculturalProductAdapter agriculturalProductAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADD_AGP:
                    ToastUtils.makeSimpleText(getActivity(), data.getIntExtra(KEY_CODE, 400), data.getStringExtra(KEY_MESSAGE));
                    if (data.getIntExtra(KEY_CODE, 400) == CODE_SUCCESS) {
                        loadAGPListFromServer();
                    }
                    break;
            }
        }


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
                startActivityForResult(addAGPIntent, REQUEST_CODE_ADD_AGP);
            }
        });

        listAGP = new ArrayList<>();

        agriculturalProductAdapter = new AgriculturalProductAdapter(getActivity(), listAGP);

        rvAGP = relativeLayout.findViewById(R.id.rv_agricultural_product);
        progressBar = relativeLayout.findViewById(R.id.pb_agricultural_product);
        srAGP = relativeLayout.findViewById(R.id.sr_agricultural_product);

        rvAGP.setAdapter(agriculturalProductAdapter);
        rvAGP.setLayoutManager(new LinearLayoutManager(getActivity()));

        srAGP.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isSwipeRefresh = true;
                loadAGPListFromServer();
            }
        });

        showHideWhenScroll();
        loadAGPListFromServer();

        agriculturalProductAdapter.setRecycleViewOnItemClickListener(new RecycleViewOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent updateAGPIntent = new Intent(getActivity(), UpdateAgriculturalProductActivity.class);
                updateAGPIntent.putExtra(KEY_ID, listAGP.get(position).getId());
                updateAGPIntent.putExtra(KEY_REV, listAGP.get(position).getRev());
                startActivityForResult(updateAGPIntent, REQUEST_CODE_ADD_AGP);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });

        agriculturalProductAdapter.setOnMenuItemAGPClickListener(new OnMenuItemAGPClickListener() {
            @Override
            public void onViewWeatherForecastClick(View view, int position) {

                Toast.makeText(getActivity(), "G", Toast.LENGTH_SHORT).show();
                Intent agpWeatherForecastIntent = new Intent(getActivity(), ViewAGPWeatherForecastActivity.class);
                agpWeatherForecastIntent.putExtra("AGP", listAGP.get(position));
                startActivity(agpWeatherForecastIntent);
            }

            @Override
            public void onDeleteAGPClick(View view, final int position) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.title_notification));
                builder.setMessage(getString(R.string.message_delete_agp));
                builder.setNegativeButton(getText(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton(getText(R.string.btn_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteAGP(listAGP.get(position).getId(), listAGP.get(position).getRev());
                    }
                });
                builder.show();
            }
        });

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


    private void loadAGPListFromServer() {

        if (!isSwipeRefresh) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            srAGP.setRefreshing(true);
        }
        apiInterface.getAGPList().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {

                if (!isSwipeRefresh) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    srAGP.setRefreshing(false);
                    isSwipeRefresh = false;
                }

                if (response.isSuccessful()) {
                    if (!response.body().isError()) {

                        if (listAGP != null && !listAGP.isEmpty()) {
                            listAGP.clear();
                        }

                        JsonArray listAGPArray = gson.toJsonTree(response.body().getData()).getAsJsonArray();

                        for (int i = 0; i < listAGPArray.size(); i++) {
                            AgriculturalProduct agp = gson.fromJson(listAGPArray.get(i), AgriculturalProduct.class);
                            listAGP.add(agp);
                        }

                        agriculturalProductAdapter.notifyDataSetChanged();


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

                if (!isSwipeRefresh) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    srAGP.setRefreshing(false);
                    isSwipeRefresh = false;
                }
//                showErrorMessage();
                Log.d("MainActivity", "error loading from API" + t.getMessage());

            }
        });
    }


    private void deleteAGP(String id, String rev) {
        apiInterface.deleteAGP(id, rev).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {

                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        loadAGPListFromServer();
                    } else {
                    }
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
