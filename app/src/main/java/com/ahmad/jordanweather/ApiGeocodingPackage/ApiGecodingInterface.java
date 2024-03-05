package com.ahmad.jordanweather.ApiGeocodingPackage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiGecodingInterface {
    @GET("{longitude},{latitude}.json")
   public Call<Geocodingfetch> getlocation(
           @Path("latitude") double lattiude,
           @Path("longitude") double longitude,
           @Query("types") String typeLocation,
    @Query("access_token") String token);
}
