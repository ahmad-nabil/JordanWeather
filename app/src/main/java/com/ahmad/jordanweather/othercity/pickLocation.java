package com.ahmad.jordanweather.othercity;

import static android.content.Context.MODE_PRIVATE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmad.jordanweather.databinding.PickLocationBinding;

public class pickLocation extends AppCompatActivity {
 PickLocationBinding activityPickLocationBinding;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPickLocationBinding=PickLocationBinding.inflate(getLayoutInflater());
        setContentView(activityPickLocationBinding.getRoot());
        sharedPreferences=getSharedPreferences("location",MODE_PRIVATE);
getLocation getLocation=new getLocation(activityPickLocationBinding,sharedPreferences,getSupportFragmentManager());
getLocation.InitilizeMapbox();
    }
}