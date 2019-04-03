package module.activity;


import java.io.*;

public class TheftDis {
	
	private TheftDis() { }

	//for testing

	public static Location[] TheftZones;


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
	
	
	public static Location[] result(String data, int threshholdDis) {
		
		double userLat = 41.867150;
		double userLon = -87.609889;
		


		//create list for parking zone coordinates
		BufferedReader readFile;
		try {
			readFile = new BufferedReader(new FileReader(data)); //file for testing only, change later
			String line = readFile.readLine();
			int index = 0;
			while ((line = readFile.readLine()) != null) {
				double lat = Double.parseDouble(line.split(",")[0]);
				double lon = Double.parseDouble(line.split(",")[1]);
				double dist = distance(userLat, userLon, lat, lon);
				if(dist >= threshholdDis) {
					TheftZones[index] = new Location(lat, lon, dist);
					index++;
				}
				
			}
			readFile.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		//sort the list by distance from user
		Merge.sortMerge(TheftZones, TheftZones.length);
		
		//delete later, just for testing
		for (int i = 0; i < 5; i++) {
			System.out.println(TheftZones[i]);
		}
		return TheftZones;


	}
	public static void main(String[] args) {
		Location[] res = TheftDis.result("data/theft.csv", 1);

		for (int i =0 ;i < 100; i++) {
			System.out.println(res[i]);

	
		}

	}
	}
