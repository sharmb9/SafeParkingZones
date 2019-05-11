package com.myappcompany.user.safeparkingzones;

import java.util.List;

/**
 * ADT for parking zone location
 *
 * @author Seda Mete
 *
 */
public class Location implements Comparable<Location>{
    private final double lat; //parking zone latitude
    private final double lon; //parking zone longitude
    private double dist; // distance from user
    private int freq; // frequency of crime
    private List<Location> adjacent; //adjacent parking spots

    /**
     * ADT for parking zone
     *
     * @param lat - parking zone latitude
     * @param lon - parking zone longitude
     * @param dist - parking zone distance from user
     * @param freq- frequency of theft within a certain radius of a parking spot
     */
    public Location(double lat, double lon, double dist, int freq) {
        this.lat = lat;
        this.lon = lon;
        this.dist = dist;
        this.freq = freq;
    }

    /**
     * ADT for parking zone
     *
     * @param lat - parking zone latitude
     * @param lon - parking zone longitude
     * @param dist - parking zone distance from user
     * @param freq - frequency of crime for parking zone
     * @param adjacent - array of adjacent parking spots
     */
    public Location(double lat, double lon, double dist, int freq, List<Location> adjacent) {
        this.lat = lat;
        this.lon = lon;
        this.dist = dist;
        this.freq = freq;
        this.adjacent = adjacent;
    }

    /**
     * Get parking zone latitude
     * @return Parking zone latitude
     */
    public double getLat() {return lat;}

    /**
     * Get parking zone longitude
     * @return Parking zone longitude
     */
    public double getLon() {return lon;}

    /**
     * Get parking zone distance from user
     * @return Parking zone distance from user
     */
    public double getDist() {return dist;}

    /**
     * Get parking zone theft frequency
     * @return Parking zone theft frequency
     */
    public int getFreq() {return freq;}

    /**
     * Set parking zone theft frequency
     * @param theftFreq zone theft frequency
     */
    public void setFreq(int theftFreq) {this.freq = theftFreq;}

    public List<Location> getAdj() { return adjacent;}

    public void setAdj(List<Location> adj) { this.adjacent = adj;}

    /**
     * Set parking zone distance from user
     * @param distFromUser distance from user
     */
    public void setDist(double distFromUser) {this.dist = distFromUser;}

    /**
     * Get string representation of parking zone location
     * @return String representation of parking zone location
     */
    public String toString(){
        return ("(" + lat + "," + lon + ")");
    }

    /**
     * Compare one parking zone's distance from user to another parking zone's distance
     * @param location2 - parking zone that's being compared to
     * @return Comparison result
     */
    @Override
    public int compareTo(Location location2) {
        if (this.dist < location2.dist) return -1;
        if (this.dist > location2.dist) return 1;
        return 0;
    }
}
