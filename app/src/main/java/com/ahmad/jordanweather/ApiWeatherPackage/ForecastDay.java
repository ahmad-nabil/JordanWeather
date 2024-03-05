package com.ahmad.jordanweather.ApiWeatherPackage;

import java.util.List;

public class ForecastDay {
double lat;
double lon;
String timezone;
int timezone_offset;
current current;
List<daily> daily;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getTimezone_offset() {
        return timezone_offset;
    }

    public void setTimezone_offset(int timezone_offset) {
        this.timezone_offset = timezone_offset;
    }

    public com.ahmad.jordanweather.ApiWeatherPackage.current getCurrent() {
        return current;
    }

    public void setCurrent(com.ahmad.jordanweather.ApiWeatherPackage.current current) {
        this.current = current;
    }

    public List<com.ahmad.jordanweather.ApiWeatherPackage.daily> getDaily() {
        return daily;
    }

    public void setDaily(List<com.ahmad.jordanweather.ApiWeatherPackage.daily> daily) {
        this.daily = daily;
    }


}
