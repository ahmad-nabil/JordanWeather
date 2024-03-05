package com.ahmad.jordanweather;

import static com.mapbox.maps.plugin.gestures.GesturesUtils.getGestures;
import static com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils.getLocationComponent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ahmad.jordanweather.ApiGeocodingPackage.*;
import com.ahmad.jordanweather.ApiWeatherPackage.WeatherAPI;
import com.ahmad.jordanweather.databinding.ActivityHomeBinding;
import com.ahmad.jordanweather.databinding.ActivityPickLocationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.ImageHolder;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.LocationPuck2D;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener;

public class getLocation {
    FusedLocationProviderClient fusedLocationProvider;
    ActivityPickLocationBinding pickLocationBinding;
    ActivityHomeBinding binding;
    SharedPreferences sharedPreferences;

    public getLocation(ActivityPickLocationBinding pickLocationBinding, SharedPreferences sharedPreferences) {
        this.pickLocationBinding = pickLocationBinding;
        this.sharedPreferences = sharedPreferences;
    }

    public getLocation(ActivityHomeBinding binding, SharedPreferences sharedPreferences) {
        this.binding = binding;
        this.sharedPreferences = sharedPreferences;
    }

    public void InitilizeMapbox(){
        MapView mapView=pickLocationBinding.mapView;
        mapView.getMapboxMap().loadStyle("mapbox://styles/ahmadnabils/clqeku91700g401pj8rv4fz43", new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                //setting camera position of the Mapbox and initialize component map view
             mapView.getMapboxMap().setCamera(new CameraOptions.Builder().build());
                LocationComponentPlugin location=getLocationComponent(mapView);
                location.setEnabled(true);
                LocationPuck2D locationPuck2D=new LocationPuck2D();
                //icon to show location user
                locationPuck2D.setBearingImage(ImageHolder.from(R.drawable.baseline_add_location_alt_24));
                location.setLocationPuck(locationPuck2D);
                 location.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
                location.addOnIndicatorBearingChangedListener(indicatorBearingChangedListener);
            }
        });
    }
    final OnIndicatorBearingChangedListener indicatorBearingChangedListener=new OnIndicatorBearingChangedListener() {
        @Override
        public void onIndicatorBearingChanged(double v) {
pickLocationBinding.mapView.getMapboxMap().setCamera(new CameraOptions.Builder().bearing(v).build());
        }
    };
    final OnIndicatorPositionChangedListener onIndicatorPositionChangedListener=new OnIndicatorPositionChangedListener() {
        @Override
        public void onIndicatorPositionChanged(@NonNull Point point) {
pickLocationBinding.mapView.getMapboxMap().setCamera(new CameraOptions.Builder().center(point).build());
getGestures(pickLocationBinding.mapView).setFocalPoint(pickLocationBinding.mapView.getMapboxMap().pixelForCoordinate(point));
        }
    };


    @SuppressLint("MissingPermission")
    public void DetectLocationFusedProvider() {
fusedLocationProvider= LocationServices.getFusedLocationProviderClient(binding.getRoot().getContext());

    fusedLocationProvider.getLastLocation()
            .addOnSuccessListener(location ->{
              SharedPreferences.Editor editor=sharedPreferences.edit();
              editor.putString("Lat",String.valueOf(location.getLatitude())).apply();
                editor.putString("Lon",String.valueOf(location.getLongitude())).apply();

                ApiGecoding Api=new ApiGecoding(location.getLatitude(),location.getLongitude(),binding);
                Api.getAddress();
WeatherAPI weatherAPI=new WeatherAPI(binding);
weatherAPI.getData();
                    }
            )
            .addOnFailureListener(e -> {
                Toast.makeText(binding.getRoot().getContext(), "Error getting location try pick it manual", Toast.LENGTH_SHORT).show();
            });
}

}
