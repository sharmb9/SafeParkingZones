package module.activity;


public class Location implements Comparable<Location>{
	
	private final double lat; //parking zone latitude
	private final double lon; //parking zone longitude
	private final double dist; // distance from user
	

	
	public double getLat() {return lat;} 
	
	public double getLon() {return lon;} 
	
	public double getdist() {return dist;} 
	
	public String toString(){
		return ("(" + lat + "," + lon + ")");
	}
	@Override
	//compare locations by distance from user
	public int compareTo(Location location2) {
		if (this.dist < location2.dist) return -1;
		if (this.dist > location2.dist) return 1;
		return 0;
	}
	public Location(double lat, double lon, double dist) {
		this.lat = lat;
		this.lon = lon;
		this.dist = dist;
	}
}