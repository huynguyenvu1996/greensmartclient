package com.group07.greensmart.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.group07.greensmart.rest.ApiInterface;
import com.group07.greensmart.utils.ApiUtils;

/**
 * Created by nguyenvuhuy on 4/5/18.
 */

public class BaseFragment extends Fragment {

    protected Gson gson;
    protected ApiInterface apiInterface;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        apiInterface = ApiUtils.getAPIInterface(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
