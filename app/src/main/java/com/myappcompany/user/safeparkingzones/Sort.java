package com.myappcompany.user.safeparkingzones;

/**
 * @author Seda Mete
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Sort {

    public static void main(String[] args) {
        //for testing
        double userLat = 41.867150;
        double userLon = -87.656809;

        //double userLat = Double.parseDouble(args[0]);
        //double userLon = Double.parseDouble(args[1]);

        //create list for parking zone coordinates
        String dataset="File.csv";
        final Location[] parkingZones = new Location[countLines(dataset)];

        //read dataset and add coordinates with their distance from the user location/searched location to list
        BufferedReader readFile;
        try {
            readFile = new BufferedReader(new FileReader(dataset)); //file for testing only, change later
            String line = readFile.readLine();
            int index = 0;
            while ((line = readFile.readLine()) != null) {
                double lat = Double.parseDouble(line.split(",")[0]);
                double lon = Double.parseDouble(line.split(",")[1]);
                double dist = distance(userLat, userLon, lat, lon);
                parkingZones[index] = new Location(lat, lon, dist);
                index++;
            }
            readFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //sort the list by distance from user
        Merge.sortMerge(parkingZones, parkingZones.length);

        //delete later, just for testing(Gets 15 nearest parking spots from the user)
        for (int i = 0; i < 15; i++) {
            System.out.println(parkingZones[i].toString());
        }

    }

    // calculates distance between two coordinates(put source)
    public static double distance(double userLat, double userLon, double zoneLat, double zoneLon) {
        double radius = 6371; //Radious of earth in km
        double latDist = Math.toRadians(zoneLat - userLat);
        double lonDist = Math.toRadians(zoneLon - userLon);

        double a = Math.sin(latDist / 2) * Math.sin(latDist / 2) +
                Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(zoneLat)) *
                        Math.sin(lonDist / 2) * Math.sin(lonDist / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = radius * c;
        return dist;
    }

    //count number of lines in the file
    public static int countLines(String dataset) {
        int lines = 0;
        BufferedReader readFile;
        try {
            readFile = new BufferedReader(new FileReader(dataset));
            String line = readFile.readLine();
            while ((line = readFile.readLine()) != null) {
                lines++;
            }
            readFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}