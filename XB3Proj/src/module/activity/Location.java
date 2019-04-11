/** 
 * @author Shivam Taneja, Lab 02, Group 10
 **/
package module.activity;


public class Location implements Comparable<Location>{
	
	/**
	 * Defines parking zone latitude
	 */
	private final double lat; 

	/**
	 * Defines parking zone longitude
	 */
	private final double lon; 	
	
	/**
	 * Defines the distance from the user
	 */
	private final double dist; 

	
	/**
	 * @return Gets the parking zone latitude
	 */
	public double getLat() {return lat;} 
	
	/**
	 * @return Gets the parking zone longitude
	 */
	public double getLon() {return lon;} 
	
	/**
	 * @return Gets the parking zone distance from user
	 */
	public double getdist() {return dist;} 
	
	
	/**
	 * @return string representation of the latitude -> used for debugging
	 */	public String toString(){
		return (lat + ", " + lon);
	}

	/**
	 * @param location2 Other location to be compared -> compared by distance
	 * @return checks which one is the further away from the user
	 */
	@Override
	//compare locations by distance from user
	public int compareTo(Location location2) {
		if (this.dist < location2.dist) return -1;
		if (this.dist > location2.dist) return 1;
		return 0;
	}
	
	/**
	 * Constructor for the Location class
	 */
	public Location(double lat, double lon, double dist) {
		this.lat = lat;
		this.lon = lon;
		this.dist = dist;
	}
}