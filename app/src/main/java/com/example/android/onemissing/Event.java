package com.example.android.onemissing;

import com.google.android.gms.location.places.Place;

/**
 * Created by Arfera on 05/03/2017.
 */

public class Event {

    private String imgPath;
    private String eventName;
    private String sport;

    private double LAT;
    private double LON;


    public Event(String imgPath, String eventName, String sport, double lat, double lon){
        this.imgPath = imgPath;
        this.eventName = eventName;
        this.sport = sport;
        LAT = lat;
        LON = lon;

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

    public double getLAT() {
        return LAT;
    }

    public void setLAT(double LAT) {
        this.LAT = LAT;
    }

    public double getLON() {
        return LON;
    }

    public void setLON(double LON) {
        this.LON = LON;
    }

}
