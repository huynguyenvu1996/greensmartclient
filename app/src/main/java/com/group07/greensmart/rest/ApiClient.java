package com.group07.greensmart.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by nguyenvuhuy on 4/2/18.
 */

public class ApiClient {

    public static final String BASE_URL = "http://192.168.0.120:3000/api/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    /*https://stackoverflow.com/questions/44293018/retrofit-2-0-multipart-request-send-boolean-type-in-form-data-including-file*/
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
