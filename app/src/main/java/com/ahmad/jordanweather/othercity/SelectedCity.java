package com.ahmad.jordanweather.othercity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmad.jordanweather.ApiWeatherPackage.ForecastDay;
import com.ahmad.jordanweather.ApiWeatherPackage.WeatherAPI;
import com.ahmad.jordanweather.adapter.MainRv;
import com.ahmad.jordanweather.databinding.FragmentSelectedCityBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
       weatherAPI=new WeatherAPI();
       Bundle bundle=getArguments();
       if (bundle!=null){
           getWeather(bundle);
       }
       getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return selectedCityBinding.getRoot();
    }

    private void getWeather(Bundle bundle) {
        String lat=bundle.getString("lat");
        String lon=bundle.getString("lon");
        weatherAPI.getDataListcity(lat, lon,selectedCityBinding) ;
    }
}