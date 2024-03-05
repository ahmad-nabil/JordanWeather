package com.ahmad.jordanweather.ApiWeatherPackage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiWeatherInterface {
    @GET("onecall")
    public Call<ForecastDay> getweather(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("exclude") String exclude,
            @Query("appid") String appid

    );

}
