package com.group07.greensmart.utils;

import android.content.Context;
import android.util.Log;

import com.group07.greensmart.model.ApiResponse;
import com.group07.greensmart.rest.ApiClient;
import com.group07.greensmart.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nguyenvuhuy on 4/4/18.
 */

public class ApiUtils {

    public static ApiInterface getAPIInterface(Context context) {
        return ApiClient.getClient(context).create(ApiInterface.class);
    }

}
