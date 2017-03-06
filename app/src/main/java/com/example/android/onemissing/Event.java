package com.example.android.onemissing;

import com.google.android.gms.location.places.Place;

/**
 * Created by Arfera on 05/03/2017.
 */

public class Event {

    public String imgPath;
    public String eventName;
    public String sport;

    public double lat;
    public double lon;


    public Event() {
    }

    public Event(String imgPath, String eventName, String sport, double lat, double lon){
        this.imgPath = imgPath;
        this.eventName = eventName;
        this.sport = sport;
        this.lat = lat;
        this.lon = lon;

    }


    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lat) {
        this.lon = lat;
    }

}
