package com.ahmad.jordanweather.othercity;

import static com.ahmad.jordanweather.R.drawable.baseline_add_location_alt_24;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.ahmad.jordanweather.ApiGeocodingPackage.*;
import com.ahmad.jordanweather.ApiWeatherPackage.WeatherAPI;
import com.ahmad.jordanweather.databinding.ActivityHomeBinding;
import com.ahmad.jordanweather.databinding.PickLocationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

public class getLocation {
    FusedLocationProviderClient fusedLocationProvider;
PickLocationBinding pickLocationBinding;
    ActivityHomeBinding binding;
    SharedPreferences sharedPreferences;
FragmentManager fragmentManager;
//make two constructor one for home and second for activity pick location
    public getLocation(PickLocationBinding pickLocationBinding, SharedPreferences sharedPreferences, FragmentManager fragmentManager) {
        this.pickLocationBinding = pickLocationBinding;
        this.sharedPreferences = sharedPreferences;
        this.fragmentManager = fragmentManager;
    }

    public getLocation(ActivityHomeBinding binding, SharedPreferences sharedPreferences) {
        this.binding = binding;
        this.sharedPreferences = sharedPreferences;
    }
    //initialize open street map

    @SuppressLint("UseCompatLoadingForDrawables")
    public void initializeOsm(){
// loading the configuration for the map
        Configuration.getInstance().load(pickLocationBinding.getRoot().getContext(),sharedPreferences);
       //MapView Initialization
        MapView mapView=pickLocationBinding.mapView;
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
mapView.getController().setZoom(5d);
      //Marker Initialization and create for the map, and its icon is set to a drawable resource
        Marker marker=new Marker(mapView);
        marker.setIcon(pickLocationBinding.getRoot().getResources().getDrawable(baseline_add_location_alt_24));
//MapEventsReceiver Implementation to get data when user press long time
        MapEventsReceiver mapEventsReceiver=new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                mapView.getOverlays().remove(marker);
                marker.setPosition(p);
                mapView.getOverlays().add(marker);
                return true;
            }
        };
        //MapEventsOverlay Initialization to to capture and handle map events reciever
        MapEventsOverlay mapEventsOverlay=new MapEventsOverlay(mapEventsReceiver);
        mapView.getOverlays().add(mapEventsOverlay);
       //get listern when button clicked to show weather data after pass latitude and longitude to fragment selected city
        pickLocationBinding.check.setOnClickListener(v->{
            Bundle addresslatlon=new Bundle();
            String lat=String.valueOf(mapView.getMapCenter().getLatitude());
            String lon=String.valueOf(mapView.getMapCenter().getLongitude());
            addresslatlon.putString("lat",lat);
            addresslatlon.putString("lon",lon);
            SelectedCity selectedCity=new SelectedCity();
            selectedCity.setArguments(addresslatlon);
            selectedCity.show(fragmentManager,"");

        });
    }

    @SuppressLint("MissingPermission")
    public void DetectLocationFusedProvider() {
        //Fused Location Provider Initialization:
fusedLocationProvider= LocationServices.getFusedLocationProviderClient(binding.getRoot().getContext());
//get last location for user
    fusedLocationProvider.getLastLocation()
            .addOnSuccessListener(location ->{
                //Storing Location in SharedPreferences we previousely initilized it in constructor:
              SharedPreferences.Editor editor=sharedPreferences.edit();
              editor.putString("Lat",String.valueOf(location.getLatitude())).apply();
                editor.putString("Lon",String.valueOf(location.getLongitude())).apply();
//Geocoding API  using mapbox
                ApiGecoding Api=new ApiGecoding(location.getLatitude(),location.getLongitude(),binding);
                Api.getAddress();
                //get weather from open weather
WeatherAPI weatherAPI=new WeatherAPI(binding);
weatherAPI.getData();
                    }
            )
            .addOnFailureListener(e -> {
                Toast.makeText(binding.getRoot().getContext(), "Error getting location try pick it manual", Toast.LENGTH_SHORT).show();
            });
}

}
