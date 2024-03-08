package com.ahmad.jordanweather.ApiGeocodingPackage;

import android.util.Log;
import android.widget.Toast;

import com.ahmad.jordanweather.databinding.ActivityHomeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiGecoding {
    double Lat;
    double longitude;
  //base url for api to use in retrofit
    String baseUrl="https://api.mapbox.com/geocoding/v5/mapbox.places/";
    //public key for access
String pbAccsessToken="pk.eyJ1IjoiYWhtYWRuYWJpbHMiLCJhIjoiY2xxZWtwNDNrMG04ODJ1bWthMTB0bTlmdSJ9.G5HKqLOMtoGwvzB1h4kxvg";
  ActivityHomeBinding homeBinding;

    public ApiGecoding(double lat, double longitude, ActivityHomeBinding homeBinding) {
        Lat = lat;
        this.longitude = longitude;
        this.homeBinding = homeBinding;
    }

    /**
     * Retrieves the address information using the Mapbox Geocoding API.
     */
    public void getAddress() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiGecodingInterface gecodingInterface=retrofit.create(ApiGecodingInterface.class);
gecodingInterface.getlocation(Lat,longitude,"place", pbAccsessToken).enqueue(new Callback<Geocodingfetch>() {
    @Override
    public void onResponse(Call<Geocodingfetch> call, Response<Geocodingfetch> response) {
        assert response.body() != null;
        // Updating the UI
        homeBinding.Address.setText(response.body().features.get(0).text);

    }

    @Override
    public void onFailure(Call<Geocodingfetch> call, Throwable t) {

    }
});


    }
}
