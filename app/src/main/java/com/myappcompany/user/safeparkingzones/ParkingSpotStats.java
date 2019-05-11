package com.myappcompany.user.safeparkingzones;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds the given statistics for each parking spot currently displayed on the screen.
 * It represents each of the parking spots as a vertex and those that are within 0.2 km are considered
 * adjacent. The graph is meant to show the relationships between the vertices and their information as
 * well, creating a web of inter-connected parkingSpots
 *
 * @author Orlando Ortega
 */

public class ParkingSpotStats {

    static Location[] parkingSpots;
    static Location[] sortedByDSSpots;
    static Graph G;

    //used for the nearest 15 spots, added 5 more to avoid re-hashing just in case
    static LinearProbingHashST<Integer, Location> hashST = new LinearProbingHashST<Integer, Location>(31);

    //one space for the final parking spot searched
    static LinearProbingHashST<Location, Integer> finalHT = new LinearProbingHashST<Location, Integer>(1);

    /**
     * Adds the edges to the graph
     *
     * @param G The undirected graph being used
     * @param parkingZones The array with all the parkingZones being currently displayed to the user
     */
    public static void addEdges(Graph G, Location[] parkingZones) {

        //add each parking spot to the symbol table
        for(int i = 0; i < parkingZones.length; i++) {
            hashST.put(i, parkingZones[i]);
        }

        //adds the edges
        for(int i = 0; i < parkingZones.length; i++) {
            for(int j = i; j < parkingZones.length; j++) {
                if(parkingZones[i].getLat() == parkingZones[j].getLat() &&
                        parkingZones[i].getLon() == parkingZones[j].getLon()) {
                    continue;
                } else {
                    //those within 0.3 km are considered adjacent
                    if(Sort.distance(parkingZones[i].getLat(), parkingZones[i].getLon(),
                            parkingZones[j].getLat(), parkingZones[j].getLon()) <= 0.2) {
                        //use indices since they are indexed the same way as the hash table
                        G.addEdge(i, j);
                    }
                }
            }
        }
    }

    /**
     * Provides the statistics for the search parking spot with the given coordinates
     *
     * @param spotLat The latitude of the parking spot
     * @param spotLon The longitude of the parking spot
     * @return a hashTable containing as it's key the information of the parking spot (the location
     * object) and the value being it's safety rankings when compared to other parking spots that
     * are being displayed to the user
     */
    public static LinearProbingHashST<Location, Integer> getStats(double spotLat, double spotLon)
    {

        //set to 0 cause i need to initialize it first
        Location searchSpot = new Location(0, 0, 0, 0);

        //holds list of adjacents
        List<Location> adjacentList = new ArrayList<Location>();

        //setup the list
        for(Integer item: hashST.keys()) {
            if(hashST.get(item).getLat() == spotLat && hashST.get(item).getLon() == spotLon) {
                searchSpot = hashST.get(item);
                for(Integer adjacentSpots : G.adj(item)) {
                    adjacentList.add(hashST.get(adjacentSpots));
                }

                break;
            }
        }

        //create new location item with adjacent list
        Location finalSpot = new Location(searchSpot.getLat(), searchSpot.getLon(), searchSpot.getDist(),
                searchSpot.getFreq(), adjacentList);

        //get the ranking of safety compared to its adjacent ones
        Location[] sortedByFreq = new Location[adjacentList.size()+1];
        for(int i = 0; i < sortedByFreq.length; i++) {
            if(i == 0) {
                sortedByFreq[i] = finalSpot;
            } else {
                sortedByFreq[i] = adjacentList.get(i - 1);
            }
        }

        //sort them by frequency
        Insertion.sortInsert(sortedByFreq);

        int val = 0;

        //find the ranking
        for(int i = 0; i < sortedByFreq.length; i++) {
            if(sortedByFreq[i] == finalSpot) {
                val = i;
            }
        }

        finalHT.put(finalSpot, val);

        return finalHT;
    }

    /**
     * Initializes the graph and adds the corresponding edges
     *
     * @param sortedSpots The list of sorted parking spots
     */
    public static void markerInfo(Location[] sortedSpots){
        G = new Graph(sortedSpots.length);
        addEdges(G,sortedSpots);
    }


}