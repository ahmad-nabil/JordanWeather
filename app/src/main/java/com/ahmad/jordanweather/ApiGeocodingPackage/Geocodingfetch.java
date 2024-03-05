package com.ahmad.jordanweather.ApiGeocodingPackage;


import java.util.List;

public class Geocodingfetch {
     String type;
     List<Double> query;
     List<GeocodingPlace> features;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getQuery() {
        return query;
    }

    public void setQuery(List<Double> query) {
        this.query = query;
    }

    public List<GeocodingPlace> getFeatures() {
        return features;
    }

    public void setFeatures(List<GeocodingPlace> features) {
        this.features = features;
    }


}
