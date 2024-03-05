package com.ahmad.jordanweather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.ahmad.jordanweather.databinding.ActivityPickLocationBinding;

public class pickLocation extends AppCompatActivity {
ActivityPickLocationBinding activityPickLocationBinding;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPickLocationBinding=ActivityPickLocationBinding.inflate(getLayoutInflater());
        setContentView(activityPickLocationBinding.getRoot());
        sharedPreferences=getSharedPreferences("location",MODE_PRIVATE);
getLocation getLocation=new getLocation(activityPickLocationBinding,sharedPreferences);
getLocation.InitilizeMapbox();
    }
}