package com.myappcompany.user.safeparkingzones;


import android.content.Context;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Sorts parking zones by distance from user
 *
 * @author Seda Mete
 *
 */
public class Sort {

    static Location[] parkingZones;
    static CSVReader readFile;
    /**
     * Sorts parking zones by distance from user
     *
     * @param userLat - user's latitude
     * @param userLon - user's longitude
     * @param parkingZones - a list of parkingZones
     *
     */
    public static void nearestParkingZones(double userLat, double userLon, Location[] parkingZones) {
        double dist; // parking zone distance from user

        //set every parking zone's distance from user
        for (int i = 0; i < parkingZones.length; i++) {
            dist = distance(userLat, userLon, parkingZones[i].getLat(), parkingZones[i].getLon());
            parkingZones[i].setDist(dist);
        }

        //sort the list by distance from user
        Merge.sortMerge(parkingZones, parkingZones.length);
    }

    /**
     * Reads data set and returns a list of parking zone locations.
     * Parking zone distances are initially set to 0.
     *
     * @param fileName - name of data set to be read
     * @return An array of parking zone locations
     */
    public static Location[] readData(String fileName, Context context) {
         parkingZones= new Location[8556];

        //read dataset and add coordinates to a list
        //BufferedReader readFile;
        try {
            readFile = new CSVReader((new InputStreamReader(context.getAssets().open(fileName))));
            String[] line;
            readFile.readNext(); //skips the first line, since that's coloumn names
            int index = 0;
            while (index<=8555) {
                line = readFile.readNext();
                double lat = Double.parseDouble(line[0]);
                double lon = Double.parseDouble(line[1]);
                parkingZones[index] = new Location(lat, lon, 0);
                index++;
            }
            readFile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return parkingZones;
    }

    /**
     * Finds the distance between user and parking zone
     *
     * @param userLat - user's latitude
     * @param userLon - user's longitude
     * @param zoneLat - parking zone's latitude
     * @param zoneLon - parking zone's longitude
     * @return The distance between the user and a parking zone
     *
     */
    public static double distance(double userLat, double userLon, double zoneLat, double zoneLon) {
        double radius = 6371; //radius of Earth in km
        double latDist = Math.toRadians(zoneLat - userLat);
        double lonDist = Math.toRadians(zoneLon - userLon);

        double a = Math.sin(latDist/2) * Math.sin(latDist/2) +
                Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(zoneLat)) *
                        Math.sin(lonDist/2) * Math.sin(lonDist/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = radius * c;
        return dist;
    }

    //counts number of lines in the file
    private static int countLines(String fileName, Context context) {
        int lines = 0;
        //readFile;
        try {
            new CSVReader((new InputStreamReader(context.getAssets().open(fileName))));
            String[] line = readFile.readNext();
            while ((line = readFile.readNext()) != null) {
                lines++;
            }
            readFile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}