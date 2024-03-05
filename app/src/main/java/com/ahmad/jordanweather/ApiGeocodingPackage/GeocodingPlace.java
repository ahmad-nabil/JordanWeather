package com.ahmad.jordanweather.ApiGeocodingPackage;


import java.util.List;

public class GeocodingPlace {
  String id;
     String type;
  List<String> placeType; // Change to List<String> if it's an array of strings
  int relevance;
    property  properties;
   String text;
  String place_name; // Java convention for camel case
    List<String>  bbox;
    List<String> center;
    private Geometrymap geometry;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getPlaceType() {
        return placeType;
    }

    public void setPlaceType(List<String> placeType) {
        this.placeType = placeType;
    }

    public int getRelevance() {
        return relevance;
    }

    public void setRelevance(int relevance) {
        this.relevance = relevance;
    }

    public property getProperties() {
        return properties;
    }

    public void setProperties(property properties) {
        this.properties = properties;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public List<String> getBbox() {
        return bbox;
    }

    public void setBbox(List<String> bbox) {
        this.bbox = bbox;
    }

    public List<String> getCenter() {
        return center;
    }

    public void setCenter(List<String> center) {
        this.center = center;
    }

    public Geometrymap getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometrymap geometry) {
        this.geometry = geometry;
    }
}
