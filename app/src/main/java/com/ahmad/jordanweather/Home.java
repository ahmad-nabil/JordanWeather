package com.ahmad.jordanweather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ahmad.jordanweather.ApiGeocodingPackage.ApiGecoding;
import com.ahmad.jordanweather.ApiWeatherPackage.ForecastDay;
import com.ahmad.jordanweather.ApiWeatherPackage.WeatherAPI;
import com.ahmad.jordanweather.databinding.ActivityHomeBinding;
import com.ahmad.jordanweather.databinding.DialogepicklocationBinding;


public class Home extends AppCompatActivity {
WeatherAPI weatherAPI;
ActivityHomeBinding homeBinding;
SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());
        sharedPreferences=getSharedPreferences("location",MODE_PRIVATE);
        String lat=sharedPreferences.getString("Lat",null);
        String lon=sharedPreferences.getString("Lon",null);
        if (lat!=null&&lon!=null){
            ApiGecoding apiGecoding=new ApiGecoding(Double.parseDouble(lat),Double.parseDouble(lon),homeBinding);
            apiGecoding.getAddress();
            homeBinding.picklocationbtn.setVisibility(View.GONE);
        }
        weatherAPI = new WeatherAPI(homeBinding);
        weatherAPI.getData();
        homeBinding.picklocationbtn.setOnClickListener(this::showDialoge);

    }

    private void showDialoge(View picklocationbtn) {
        Dialog dialog=new Dialog(Home.this);
        DialogepicklocationBinding dialogepicklocationBinding=DialogepicklocationBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogepicklocationBinding.getRoot());
        dialog.show();
        getLocation getLocation=new getLocation(homeBinding,sharedPreferences);
        dialogepicklocationBinding.auto.setOnClickListener(v -> {
            getLocation.DetectLocationFusedProvider();
            dialog.dismiss();
        });
        dialogepicklocationBinding.pickerbtn.setOnClickListener(v->{
            startActivity(new Intent(this, pickLocation.class));
            dialog.dismiss();
            finish();
        });

    }


}