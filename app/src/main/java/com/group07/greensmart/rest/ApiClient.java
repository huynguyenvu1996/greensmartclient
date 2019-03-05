package com.group07.greensmart.rest;

import android.content.Context;

import com.group07.greensmart.utils.ApplicationUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nguyenvuhuy on 4/2/18.
 */

public class ApiClient {

    private static Retrofit retrofit = null;


    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApplicationUtils.getServerUrl(context) + "api/")
                    /*https://stackoverflow.com/questions/44293018/retrofit-2-0-multipart-request-send-boolean-type-in-form-data-including-file*/
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
