package Implementation;

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
	static Location[] rankedSpots;
	static Graph G;
	
	//used for the nearest 15 spots, added 5 more to avoid re-hashing just in case
	static LinearProbingHashST<Integer, Location> hashST = new LinearProbingHashST<Integer, Location>(20);
	
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
		
		int val = 0;
		
		//find the ranking
		for(int i = 0; i < sortedByDSSpots.length; i++) {
			if(sortedByDSSpots[i].getLat() == finalSpot.getLat() && 
					sortedByDSSpots[i].getLon() == finalSpot.getLon()) {
				val = i+1;
				break;
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
	public static void markerInfo(Location[] sortedSpots) {
		G = new Graph(sortedSpots.length);
		addEdges(G, sortedSpots);
	}
	
	
	public static void main(String[] args) {
		
		//get data
		parkingSpots = Sort.readData("data/final_parking_coord.csv");
        
		//sort by distance
		Sort.nearestParkingZones(41.789722, -87.599724, parkingSpots);
		
		//soft by near and safe
		sortedByDSSpots = Sort.nearestSafestParkingZones(parkingSpots);
		
//		for(int i = 0; i < sortedByDSSpots.length; i++) {
//			System.out.println(sortedByDSSpots[i].getFreq());
//		}
		
		//initialize graph
		G = new Graph(sortedByDSSpots.length);
		
		//adds the edges to the graph so that those within 0.2 km are considered adjacent
		addEdges(G, sortedByDSSpots);
		
		//get the statistics for any parking spot you chose thats returned by the sorting algo
		getStats(hashST.get(27).getLat(), hashST.get(27).getLon());
		
//		System.out.println(G);
		
		for(Location key: finalHT.keys()) {
			System.out.println("Latitude: " + key.getLat());
			System.out.println("Longitude: " + key.getLon());
			System.out.println("Theft Frequency: " + key.getFreq());
			System.out.println("Adjacent Spots: ");
			if(key.getAdj().size() == 0) {
				System.out.println("No adjacent parking spots found");
			} else {
				for(int i = 0; i < key.getAdj().size(); i++) {
					System.out.println(key.getAdj().get(i));
				}
			}
			System.out.println("It's safety rank compared to the currently displayed spots: " + finalHT.get(key));
		}
    }

}
