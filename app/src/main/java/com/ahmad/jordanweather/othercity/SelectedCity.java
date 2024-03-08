package com.ahmad.jordanweather.othercity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmad.jordanweather.ApiWeatherPackage.WeatherAPI;
import com.ahmad.jordanweather.databinding.FragmentSelectedCityBinding;


public class SelectedCity extends DialogFragment {
    FragmentSelectedCityBinding selectedCityBinding;
public SelectedCity newInstance(Bundle bundle){
    SelectedCity selectedCity=new SelectedCity();
    selectedCity.setArguments(bundle);
    return selectedCity;
    }
WeatherAPI weatherAPI;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
selectedCityBinding=FragmentSelectedCityBinding.inflate(inflater,container,false);
    //initilize opjects weather api to get weather for city picked
       weatherAPI=new WeatherAPI();
       Bundle bundle=getArguments();
      // check bundle if null or not
       if (bundle!=null){
           getWeather(bundle);
       }
       //set background drawble transparent
       getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return selectedCityBinding.getRoot();
    }

    private void getWeather(Bundle bundle) {
        String lat=bundle.getString("lat");
        String lon=bundle.getString("lon");
        weatherAPI.getDataPickedcity(lat, lon,selectedCityBinding) ;
    }
}