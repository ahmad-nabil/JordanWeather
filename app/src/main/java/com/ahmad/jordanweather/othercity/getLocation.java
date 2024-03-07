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

    public getLocation(PickLocationBinding pickLocationBinding, SharedPreferences sharedPreferences, FragmentManager fragmentManager) {
        this.pickLocationBinding = pickLocationBinding;
        this.sharedPreferences = sharedPreferences;
        this.fragmentManager = fragmentManager;
    }

    public getLocation(ActivityHomeBinding binding, SharedPreferences sharedPreferences) {
        this.binding = binding;
        this.sharedPreferences = sharedPreferences;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void InitilizeMapbox(){

        Configuration.getInstance().load(pickLocationBinding.getRoot().getContext(),sharedPreferences);
        MapView mapView=pickLocationBinding.mapView;
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
mapView.getController().setZoom(5d);

        Marker marker=new Marker(mapView);
        marker.setIcon(pickLocationBinding.getRoot().getResources().getDrawable(baseline_add_location_alt_24));

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
        MapEventsOverlay mapEventsOverlay=new MapEventsOverlay(mapEventsReceiver);
        mapView.getOverlays().add(mapEventsOverlay);
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
