package com.ahmad.jordanweather.othercity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.ahmad.jordanweather.databinding.PickLocationBinding;

public class pickLocation extends AppCompatActivity {
 PickLocationBinding activityPickLocationBinding;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflate layout
        activityPickLocationBinding=PickLocationBinding.inflate(getLayoutInflater());
        setContentView(activityPickLocationBinding.getRoot());
        //initialize sharedpreference
        sharedPreferences=getSharedPreferences("location",MODE_PRIVATE);
        //initialize object get location
getLocation getLocation=new getLocation(activityPickLocationBinding,sharedPreferences,getSupportFragmentManager());
//get methode for open street map
getLocation.initializeOsm();
    }
}