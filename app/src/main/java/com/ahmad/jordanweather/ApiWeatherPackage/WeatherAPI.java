package com.ahmad.jordanweather.ApiWeatherPackage;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmad.jordanweather.adapter.MainRv;
import com.ahmad.jordanweather.databinding.ActivityHomeBinding;
import com.ahmad.jordanweather.databinding.FragmentSelectedCityBinding;
import com.ahmad.jordanweather.othercity.SelectedCity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherAPI {

    String ApiUrl;
    ActivityHomeBinding homeBinding;


    public WeatherAPI(ActivityHomeBinding homeBinding) {
        this.homeBinding = homeBinding;
        ApiUrl="https://api.openweathermap.org/data/3.0/";
    }

    public WeatherAPI() {
        ApiUrl="https://api.openweathermap.org/data/3.0/";
    }

    public void getData(){
     Retrofit retrofit=new Retrofit.Builder().baseUrl(ApiUrl)
             .addConverterFactory(GsonConverterFactory.create())
             .build();
     ApiWeatherInterface weatherInterface=retrofit.create(ApiWeatherInterface.class);

        SharedPreferences sharedPreferences=homeBinding.getRoot().getContext().getSharedPreferences("location",MODE_PRIVATE);
        String lat=sharedPreferences.getString("Lat",null);
        String lon=sharedPreferences.getString("Lon",null);
        String appid="f47c4c09c307dc0a8858c2d113d5ba67";
        if (lat!=null&&lon!=null){
     weatherInterface.getweather(lat,lon,"hourly,minutely",appid).enqueue(new Callback<ForecastDay>() {
         @Override
         public void onResponse(Call<ForecastDay> call, Response<ForecastDay> response) {
             MainRv rv = new MainRv(response.body().daily, homeBinding.getRoot().getContext());
             homeBinding.Rvweathereightday.setLayoutManager(new LinearLayoutManager(homeBinding.getRoot().getContext(), LinearLayoutManager.HORIZONTAL, false));
             homeBinding.Rvweathereightday.setAdapter(rv);
             homeBinding.daytemprt.setText(Math.round(response.body().daily.get(0).feels_like.day-273.15)+"\t C : day");
             homeBinding.eveningtemp.setText(Math.round( response.body().daily.get(0).feels_like.eve-273.15)+"\t C : evening");
             homeBinding.morning.setText( Math.round(response.body().daily.get(0).feels_like.morn-273.15)+"\t C : morning");
             homeBinding.nighttempr.setText(Math.round( response.body().daily.get(0).feels_like.night-273.15)+"\t C :night");
         }

         @Override
         public void onFailure(Call<ForecastDay> call, Throwable t) {

         }
     });


 }else {
            Toast.makeText(homeBinding.getRoot().getContext(), "pick location ", Toast.LENGTH_SHORT).show();
        }
    }
    public void getDataListcity(String lat, String lon, FragmentSelectedCityBinding binding){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(ApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiWeatherInterface weatherInterface=retrofit.create(ApiWeatherInterface.class);
        String appid="f47c4c09c307dc0a8858c2d113d5ba67";
        if (lat!=null&&lon!=null){
            weatherInterface.getweather(lat,lon,"hourly,minutely",appid).enqueue(new Callback<ForecastDay>() {
                @Override
                public void onResponse(Call<ForecastDay> call, Response<ForecastDay> response) {
                    MainRv rv = new MainRv(response.body().getDaily(),binding.getRoot().getContext());
                   binding.rveightdaycity.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext(),LinearLayoutManager.HORIZONTAL,false));
                    binding.rveightdaycity.setAdapter(rv);

                }

                @Override
                public void onFailure(Call<ForecastDay> call, Throwable t) {
                }
            });
        }
    }
 }

