package com.ahmad.jordanweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.ahmad.jordanweather.ApiGeocodingPackage.ApiGecoding;
import com.ahmad.jordanweather.ApiWeatherPackage.WeatherAPI;
import com.ahmad.jordanweather.databinding.ActivityHomeBinding;
import com.ahmad.jordanweather.othercity.getLocation;
import com.ahmad.jordanweather.othercity.pickLocation;
import com.google.android.material.navigation.NavigationView;



public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
WeatherAPI weatherAPI;
ActivityHomeBinding homeBinding;
SharedPreferences sharedPreferences;
ActionBarDrawerToggle actionBarDrawerToggle;
 getLocation getLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflate home
        homeBinding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());
//initialize navigation drawer
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,homeBinding.drawer,homeBinding.materialToolbar,R.string.open,R.string.close);
        homeBinding.drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        //initialize shared preferences
        sharedPreferences=getSharedPreferences("location",MODE_PRIVATE);
        //initialize object getLocation to get location automatically
        getLocation=new getLocation(homeBinding,sharedPreferences);
        //get values fro, sharedpreferences
        String lat=sharedPreferences.getString("Lat",null);
        String lon=sharedPreferences.getString("Lon",null);
        //check if null or not if null will get location from fused location provider
        if (lat!=null&&lon!=null){
            ApiGecoding apiGecoding=new ApiGecoding(Double.parseDouble(lat),Double.parseDouble(lon),homeBinding);
            apiGecoding.getAddress();
        }else {
            getLocation.DetectLocationFusedProvider();
        }
        //get data for weather api
        weatherAPI = new WeatherAPI(homeBinding);
        weatherAPI.getData();
        //set Listener for nav
        homeBinding.NAV.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.othercity){
            startActivity(new Intent(this, pickLocation.class));
            homeBinding.drawer.closeDrawer(GravityCompat.START);
            return true;
        }else if(item.getItemId()==R.id.exit){
            finishAffinity();
            homeBinding.drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return true;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        homeBinding.drawer.closeDrawer(GravityCompat.START);
    }
}