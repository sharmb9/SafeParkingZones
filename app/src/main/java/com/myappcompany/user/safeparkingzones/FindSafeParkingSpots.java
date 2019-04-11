package com.myappcompany.user.safeparkingzones;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindSafeParkingSpots {

    static ArrayList<LatLng> safeSpots;

    //represents the user location
    public static Location user;

    //start with one as the first location would be the user
    public static int totalNumberOfParkingSpots = 1;

    //list of all theft zones
    public static List<Location> theftZones = new ArrayList<Location>();

    //hash table representing the user location and all the parking spots
    public static LinearProbingHashST<Integer, Location> hashT = new
            LinearProbingHashST<Integer, Location>(8600);

    //frequency of all locationZones including the user, they are in the same order as in the hashTable
    public static List<Integer> theftFrequencyList = new ArrayList<Integer>();

    //variable containing the graph
    public static Digraph G;

    //contains the list of adjacentSpots to every parking spot
    //its indices are in the same order as the hashTable
    //used to create the edges in the graph
    public static List<List<Integer>> adjacentSpots = new ArrayList<List<Integer>>();

    //stores the list of final parking spots
    public static List<Integer> finalParkingSpots = new ArrayList<Integer>();

    //contains all theft locations
    public static Location[] theftDataSet;

    //puts them in the array in order
    public static int theftIndex;

    //collects the overall size of the theftDataSet used
    public static int theftDataSetSize = 0;

    public static boolean hasPath;


    //calculates the distance between two coordinates
    public static double distance(double userLat, double userLon, double zoneLat, double zoneLon) {
        double radius = 6371; //km
        double latDist = Math.toRadians(zoneLat - userLat);
        double lonDist = Math.toRadians(zoneLon - userLon);

        double a = Math.sin(latDist/2) * Math.sin(latDist/2) +
                Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(zoneLat)) *
                        Math.sin(lonDist/2) * Math.sin(lonDist/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = radius * c;
        return dist;
    }

    //sets up user location in the hashTable
    public static void setupUserLocation(double userLatitude, double userLongitude)
    {
        //distance is set to 0 because user is the source
        user = new Location(userLatitude, userLongitude, 0.0);

        //provide user location (University of Chicago used for testing)
        hashT.put(0, user);

    }

    public static void countTheftData(double theftRadius, Context context) throws IOException {
        //Scanner input;
        CSVReader input;
        try {
            //input = new Scanner(new File("data/final_motor_theft_set.csv"));
            input = new CSVReader((new InputStreamReader(context.getAssets().open("sep_motor_locations.csv"))));
            String line[];
            //input.nextLine(); //skip first line
            input.readNext(); //skip first line

            //takes all theft locations and adds them to the theft array
            while((line = input.readNext()) != null) {

                //String line = input.nextLine();
                //String[] tokens = line.split(",");

                //stores coordinates of the theft spot
                double theftSpotLatitude = Double.parseDouble(line[0]);
                double theftSpotLongitude = Double.parseDouble(line[1]);

                if(distance(user.getLat(), user.getLon(), theftSpotLatitude, theftSpotLongitude) <= theftRadius) {
                    theftDataSetSize += 1;
                }
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //setup theft data array
    public static void loadTheftData(double theftRadius, Context context) throws IOException {
        //only uses theft data that is within the given radius times 3
        //it accounts for the radius surrounding the user, the final parking spot and the
        //furthest parking spot from the parking spot (just in case)
        countTheftData(theftRadius * 3, context);

        theftDataSet = new Location[theftDataSetSize];

        //Scanner input;
        CSVReader input;

        Location theftSpot;

        theftIndex = 0;

        double theftSpotLatitude;
        double theftSpotLongitude;

        try {
            //input = new Scanner(new File("data/final_motor_theft_set.csv"));
            input=new CSVReader((new InputStreamReader(context.getAssets().open("sep_motor_locations.csv"))));
            input.readNext(); //skip first line
            String[] line;

            //takes all theft locations and adds them to the theft array
            while((line = input.readNext()) != null) {

                //String line = input.nextLine();
                //String[] tokens = line.split(",");

                //stores coordinates of the theft spot
                theftSpotLatitude = Double.parseDouble(line[0]);
                theftSpotLongitude = Double.parseDouble(line[1]);

                //distance is not gonna be preset for these
                double distance = 0.00;

                //creates new location object for the parking spot
                theftSpot = new Location(theftSpotLatitude, theftSpotLongitude, distance);

                if(distance(user.getLat(), user.getLon(), theftSpotLatitude, theftSpotLongitude) <= theftRadius*3) {
                    theftDataSet[theftIndex] = theftSpot;
                    theftIndex += 1;
                }
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    //sets up user theftThreshold
    public static void setupUserTheftFrequency(double theftThreshold)
    {
        theftFrequencyList.add(findParkingTheftFrequencies(user.getLat(),
                user.getLon(), theftThreshold));
    }

    //finds the theft frequencies of each given location within 2 kilometers
    public static int findParkingTheftFrequencies(double latitude, double longitude, double threshold)
    {
        int theftCounter = 0;

        for(int i = 0; i < theftDataSet.length; i++) {
            theftDataSet[i].setDist(distance(latitude, longitude, theftDataSet[i].getLat(),
                    theftDataSet[i].getLon()));
        }

        Merge.sortMerge(theftDataSet, theftDataSet.length);

        for(int i = 0; i < theftDataSet.length; i++) {
            if(theftDataSet[i].getDist() <= threshold) {
                theftCounter += 1;
            } else {
                //stops iterating once all items within the threshold have been counted
                break;
            }
        }

        return theftCounter;
    }

    //adds each parking spot to the hashtable, it also adds the their theftFrequencies to the respective list
    //CHANGE TO PRIVATE
    public static void addParkingSpotsToHT(double theftFrequencyRadius, double findParkingSpotRadius, Context context) throws IOException {
        //used to temporarily represent parking spots while adding them to the hashtable
        Location parkingSpots;

        //setup parking spots so that they each have a location object and place them in the table
        //Scanner input;
        CSVReader input;

        try {
            //input = new Scanner(new File("data/final_parking_coord.csv"));
            input = new CSVReader((new InputStreamReader(context.getAssets().open("final_parking_coord.csv"))));

            input.readNext(); //skip first line
            String[] line;

            //takes all parking spots and adds them to the hash table
            while((line = input.readNext()) != null) {

                //String line = input.nextLine();
                //String[] tokens = line.split(",");

                //stores coordinates of the parking spot
                double parkingSpotLatitude = Double.parseDouble(line[0]);
                double parkingSpotLongitude = Double.parseDouble(line[1]);

                //stores the parking spot's distance from the user
                double spotDistanceFromUser = distance(user.getLat(), user.getLon(),
                        parkingSpotLatitude, parkingSpotLongitude);

                //only adds parking spots that within this distance in km from the user
                if(spotDistanceFromUser > findParkingSpotRadius) {
                    continue;
                } else {
                    //creates new location object for the parking spot
                    parkingSpots = new Location(parkingSpotLatitude, parkingSpotLongitude, spotDistanceFromUser);

                    //adds their respective frequencies to the list at the same index
                    theftFrequencyList.add(findParkingTheftFrequencies(parkingSpots.getLat(),
                            parkingSpots.getLon(), theftFrequencyRadius));

                    //puts the parking spot onto the next position in the hash table
                    hashT.put(totalNumberOfParkingSpots, parkingSpots);

                    //increase counter
                    totalNumberOfParkingSpots += 1;
                }
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //finds all adjacent parking spots to each
    public static void findAdjacentParkingSpots(double threshold)
    {
        List<Integer> adjacentList;
        for(int i = 0; i < hashT.size(); i++) {
            //stores respective adjacent location list of each parking spot, used to append
            //to the bigger list
            adjacentList = new ArrayList<Integer>();

            //index from i to avoid creating a double ended edge
            for(int j = i+1; j < hashT.size(); j++) {

                //check if the distance is within the threshold
                if(distance(hashT.get(i).getLat(), hashT.get(i).getLon(),
                        hashT.get(j).getLat(), hashT.get(j).getLon()) <= threshold) {

                    //if distance is within the threshold append it to the temporary list
                    adjacentList.add(j);
                }
            }

            adjacentSpots.add(adjacentList);
        }
    }

    //initializes the digraph and adds the respective edges
    public static void setupGraph()
    {
        G = new Digraph(totalNumberOfParkingSpots);

        //looks at the list of all adjacent spots for each parking spot
        for(int i = 0; i < adjacentSpots.size() ; i++) {

            //at the individual adjacent spots to the respective parking spot
            for(int j = 0; j < adjacentSpots.get(i).size(); j++) {

                //adds edges from the respective spot to its adjacent spots
                G.addEdge(i, adjacentSpots.get(i).get(j));
            }
        }
    }


    //if there is a path to the parking spot from the source it gives parking spots that
    //are connected to it, otherwise it gives the adjacent parking spots
    public static void findPathToParkingSpot(double lat, double lon)
    {

        BFS finalG = new BFS(G, 0);

        //used to find the correct parking spots from the given coordinate
        Double currentLat;
        Double currentLon;

        Double spotLat = lat;
        Double spotLon = lon;

        for(int i = 0; i < hashT.size(); i++) {
            currentLat = hashT.get(i).getLat();
            currentLon = hashT.get(i).getLon();

            if(spotLat.equals(currentLat) && spotLon.equals(currentLon)) {
                if(finalG.hasPathTo(i)) {
                    for(Integer item: finalG.pathTo(i)) {
                        //add the next parking spots
                        finalParkingSpots.add(item);
                    }

                    hasPath = true;

                    break;
                } else {
                    //TODO: Change the distance in these parking spots to be compared with the main one
                    for(Integer item: G.adj(i)) {
                        hashT.get(item).setDist(distance(hashT.get(i).getLat(), hashT.get(i).getLon(),
                                hashT.get(item).getLat(), hashT.get(item).getLon()));
                        //add the next parking spots
                        finalParkingSpots.add(item);
                    }

                    hasPath = false;

                    break;
                }

            }
        }
    }

    //returns a hashtable containing location as keys, and their respective theft frequencies
    //as the values to those keys
    public static LinearProbingHashST<Location, Integer> finalListOfLocations()
    {
        int numberOfLocations = 0;
        LinearProbingHashST<Location, Integer> finalHT;

        for(Integer item: finalParkingSpots) {
            numberOfLocations += 1;
        }

        finalHT = new LinearProbingHashST<Location, Integer>(numberOfLocations);

        for(Integer item: finalParkingSpots) {
            finalHT.put(hashT.get(item), theftFrequencyList.get(item));
        }

        return finalHT;
    }

    /**
     * Returns a hash table with the parking spots on the way as keys, and their theft frequencies
     * as the values
     *
     * @param  parkingSpotRadius looks at parking spots within the given km radius (recommended value: 2.00)
     * @param  userLatitude the user's latitude
     * @param  userLongitude the user's longitude
     * @param  theftFrequencyRadius  calculates theft frequencies within the given km radius (recommend value: 2.00)
     * @param  destLatitude the destination parking spot's latitude
     * @param  destLongitude the destination parking spot's longitude
     * @return a hash table containing all necessary information about the parking spots en route and
     * their respective theft frequencies
     */
    public static LinearProbingHashST<Location, Integer> getGraphData(double parkingSpotRadius,
                                                                      double userLatitude, double userLongitude, double theftFrequencyRadius, double destLatitude,
                                                                      double destLongitude, Context context) throws IOException {

//		//sets the user on the HashT and on the theft frequency list
//		setupUserLocation(userLatitude, userLongitude);
//
//		//obtains relevant theft frequency/location data
//		loadTheftData(parkingSpotRadius, context);
//
//		//calculate theft frequency within the given radius
//		setupUserTheftFrequency(theftFrequencyRadius);
//
//		addParkingSpotsToHT(theftFrequencyRadius, parkingSpotRadius, context);
//
//        //all parking spots within the given parking spot radius divided by 4 are considered
//        //adjacent
        findAdjacentParkingSpots(parkingSpotRadius/4);

        setupGraph();

        //find path to the parking spot
        findPathToParkingSpot(destLatitude, destLongitude);

        return finalListOfLocations();

    }

    /**
     * Displays if the there is a direct path between the user and the parking spot searched for
     *
     * @return true if there is a direct path, false otherwise
     */
    public static boolean wasPathFound()
    { return hasPath; }

    //loading safest parking spots b/w source and destination
    public static void loadSafeParkingZones(Context context) throws IOException {

        safeSpots = new ArrayList<LatLng>();
        //declare hashTable
        LinearProbingHashST<Location, Integer> finalHT;
        setupUserLocation(41.790637, -87.598927);
        loadTheftData(2.00, context);
        setupUserTheftFrequency(2.00);
        addParkingSpotsToHT(2.00, 2.00, context);

        //assign it the final hashTable produced by the getGraphData function
        finalHT = FindSafeParkingSpots.getGraphData(2.00, 41.790637, -87.598927,
                2.00, 41.783947,-87.595862, context); //change these values by getting them from input

        if(FindSafeParkingSpots.wasPathFound()) {
            //means there is a direct path from the user to the parking spot

            //System.out.println("List of parking spots on the way: ");
            Toast toast = Toast.makeText(context, "Parking spots on the way:" , Toast.LENGTH_SHORT);
            toast.show();

        } else {
            //no direct path was found, the adjacent list will be shown instead
//            System.out.println("No direct path was found, here are the nearby parking spots, close"
//                    + " to the searched parking spot: ");

            Toast toast = Toast.makeText(context, "No direct path was found, here are the nearby parking spots, close"
                    + " to the searched parking spot: " , Toast.LENGTH_SHORT);
            toast.show();
        }

        //if there is a directPath the distance is relevant to the user, otherwise it is relevant
        //to the parking spot being searched for
//        for(Location key: finalHT.keys()) {
//            System.out.println(key + " Theft Frequency: " + finalHT.get(key));
//        }
        for(Location key: finalHT.keys()) {
            //System.out.println(key + " Theft Frequency: " + finalHT.get(key));
            safeSpots.add(new LatLng(key.getLat(), key.getLon()));
        }

//        for(Location key: finalHT.keys()) {
//            //System.out.println(key + " Theft Frequency: " + finalHT.get(key));
//            Log.i("spots found: ", Double.toString(key.getLat()) + Double.toString(key.getLon()));
//        }

//        for (int z = 0; z < safeSpots.size(); z++) {
//            LatLng point =new LatLng(safeSpots.get(z).latitude,safeSpots.get(z).longitude);
//            //Log.i("Spots found:", String.valueOf(safeSpots.get(z).latitude) + safeSpots.get(z).longitude);
//            Log.i("Spots found:", String.valueOf(point));
//        }

    }


    //example call
//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//
//        //declare hashTable
//        LinearProbingHashST<Location, Integer> finalHT;
//
//
//        //dont call these, this is only for testing since the methods should be private to begin with
////        setupUserLocation(41.799722, -87.699724);
////        loadTheftData(2.00);
////        setupUserTheftFrequency(2.00);
////        addParkingSpotsToHT(2.00, 2.00);
//
//        //assign it the final hashTable produced by the getGraphData function
//        finalHT = FindSafeParkingSpots.getGraphData(2.00, 41.789722, -87.599724,
//                2.00, hashT.get(18).getLat(), hashT.get(18).getLon());
//
//        if(FindSafeParkingSpots.wasPathFound()) {
//            //means there is a direct path from the user to the parking spot
//
//            System.out.println("List of parking spots on the way: ");
//
//        } else {
//            //no direct path was found, the adjacent list will be shown instead
//            System.out.println("No direct path was found, here are the nearby parking spots, close"
//                    + " to the searched parking spot: ");
//        }
//
//        //if there is a directPath the distance is relevant to the user, otherwise it is relevant
//        //to the parking spot being searched for
//        for(Location key: finalHT.keys()) {
//            System.out.println(key + " Theft Frequency: " + finalHT.get(key));
//        }
//
//
//    }

}

