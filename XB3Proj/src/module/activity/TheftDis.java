/** 
 * @author Shivam Taneja, Lab 02, Group 10
 **/
package module.activity;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TheftDis {
	
	private TheftDis() { }

	/**
	 * Defines list of locations
	 */	

	/**
	 * Distribute the list of location to 10 parts (equal parts)
	 * The 10 parts are defined by the "threshhold"
	 * @param input list of location
	 * @param Threshhold  distance i.e. location with with more distance than that wont be considered
	 * @author Seda Mete
	 */
	 static double distance(double userLat, double userLon, double zoneLat, double zoneLon) {
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
	
	/**
	 * puts the input coordinates into the TheftZone (list of location) 
	 * @param Threshhold  distance i.e. location with more distance than that threshold wont be considered
	 */
	 static List<Location> result(String data, double threshhold) {
		  List<Location> TheftZones = new ArrayList<Location>();

		double userLat = 41.867150;
		double userLon = -87.609889;
		


		//create list for parking zone coordinates
		BufferedReader readFile;
		try {
			readFile = new BufferedReader(new FileReader(data)); //file for testing only, change later
			String line = readFile.readLine();
			while ((line = readFile.readLine()) != null) {
				double lat = Double.parseDouble(line.split(", ")[0]);
				double lon = Double.parseDouble(line.split(", ")[1]);
				double dist = distance(userLat, userLon, lat, lon);
				if(dist <= threshhold) {
					TheftZones.add(new Location(lat, lon, dist));
				}
				
			}
			readFile.close();
		}
		catch (IOException e) {
			System.out.println(e);
		}
		
		Collections.sort(TheftZones);

		return TheftZones;


	}
	
	
	
//	public static void main(String[] args) {
//	result("data/dataTestDisKnownAll.csv",10);
//	
//	for(int i =0 ; i < TheftZones.size();i++) {
//		System.out.println(TheftZones.get(i));
//	}
//	System.out.println(TheftZones.size());
//
//	}
}