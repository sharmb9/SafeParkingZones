package module.activity;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TheftDis {
	
	private TheftDis() { }

	//for testing

	public static List<Location> TheftZones = new ArrayList<Location>();


	private static double distance(double userLat, double userLon, double zoneLat, double zoneLon) {
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
	
	
	public static List<Location> result(String data, double threshholdDis) {
		
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
				if(dist <= threshholdDis) {
					TheftZones.add(new Location(lat, lon, dist));
				}
				
			}
			readFile.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		Collections.sort(TheftZones);

		return TheftZones;


	}
//	public static void main(String[] args) {
//		List<Location> res = TheftDis.result("data/theft.csv", 1);
//
//		for (int i =0 ;i < res.size(); i++) {
//			System.out.println(res.get(i));
//
//	
//		}
//
//	}
	}
