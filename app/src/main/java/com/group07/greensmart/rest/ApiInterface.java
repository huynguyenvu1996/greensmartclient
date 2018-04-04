package com.group07.greensmart.rest;

import com.group07.greensmart.model.ApiResponse;


import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import io.reactivex.Observable;


/**
 * Created by nguyenvuhuy on 4/2/18.
 */

public interface ApiInterface {

    /**
     * TODO API Get park Information
     */

    @GET("movie/top_rated")
    Observable<ApiResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Observable<ApiResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

}
