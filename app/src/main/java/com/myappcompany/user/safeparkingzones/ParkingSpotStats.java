package com.myappcompany.user.safeparkingzones;

import java.util.ArrayList;
import java.util.List;

public class ParkingSpotStats {

    static Location[] parkingSpots;
    static Location[] sortedByDSSpots;
    static Graph G;

    //used for the nearest 15 spots, added 5 more to avoid re-hashing just in case
    static LinearProbingHashST<Integer, Location> hashST = new LinearProbingHashST<Integer, Location>(31);

    //one space for the final parking spot searched
    static LinearProbingHashST<Location, Integer> finalHT = new LinearProbingHashST<Location, Integer>(1);


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

    //returns a hashtable with the searched parking spots and the adjacent locations to it as the key,
    //and the value is the ranking of safety compared to its adjacent ones
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

    public static void markerInfo(Location[] sortedSpots){
        G = new Graph(sortedSpots.length);
        addEdges(G,sortedSpots);
    }


}