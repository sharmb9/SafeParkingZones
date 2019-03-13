package com.myappcompany.user.safeparkingzones;

/**
 * @author Seda Mete
 */
public class Location implements Comparable<Location>{
    private final double lat; //parking zone latitude
    private final double lon; //parking zone longitude
    private final double dist; // distance from user

    public Location(final double lat, final double lon, final double dist) {
        this.lat = lat;
        this.lon = lon;
        this.dist = dist;
    }

    public double getLat() {return lat;}

    public double getLon() {return lon;}

    public double getdist() {return dist;}

    public String toString(){
        return ("(" + lat + "," + lon + ")");
    }

    //compare locations by distance from user
    @Override
    public int compareTo(Location location2) {
        if (this.dist < location2.dist) return -1;
        if (this.dist > location2.dist) return 1;
        return 0;
    }
}
