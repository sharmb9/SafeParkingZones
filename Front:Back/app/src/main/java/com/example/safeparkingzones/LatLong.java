package com.example.safeparkingzones;

import com.google.android.gms.maps.model.LatLng;

class LatLong {

//    private String Name;
    private double Long;
    private double Lat;

    //getters

    public double getLong() {
        return Long;
    }

//    public String getName() {
//        return Name;
//    }

    public double getLat() {
        return Lat;
    }

    public LatLng getGeo() {
        LatLng co = new LatLng(Lat, Long);
        return co;
    }

    //Setters
//    public void setName(String aName) {
//        Name = aName;
//    }

    public void setLong(double aLong) {
        Long = aLong;
    }

    public void setLat(double lat) {
        Lat = lat;
    }


//    public String toString() {
//        return "Street: " + this.Name + "Latitude: " + this.Lat + "Longitude: " + this.Long;
//    }
    public LatLong(double Lat, double Long) {

//    public LatLong(String Name, double Lat, double Long) {

//        this.Name = Name;
        this.Lat = Lat;
        this.Long = Long;

    }
}







