/** 
 * @author Shivam Taneja, Lab 02, Group 10
 **/
package module.activity;

import java.util.*;

public class TheftFreq {	
	


	/**
	 * Counts the number of location (a.k.a. frequency)
	 * @param input the list of list of location -> (max size 10)
	 */	
	public static List<Integer> freqDis(List<List<Location>> input) {
		 List<Integer> TheftFreq = new ArrayList<Integer>();

		int i = 0;
		while(i < 10) {
			
			TheftFreq.add(i,input.get(i).size());
			i++;
		}
		return TheftFreq;
	}

	/**
	 * Counts the number of location (a.k.a. frequency)
	 * @param in the list of location
	 */
	public static int freqNor(List<Location> in) {
		
		return in.size();
	}
	
	
	/**
	 * Distribute the list of location to 10 parts (equal parts)
	 * The 10 parts are defined by the "threshhold"
	 * @param input list of location
	 * @param Threshhold  distance i.e. location with with more distance than that wont be considered
	 */	
	public static List<List<Location>> dist(List<Location> input, double threshhold) {
		List<List<Location>> TheftList = new ArrayList<List<Location>>();

		double oneTen =  threshhold/10, oneTenCopy = oneTen;
		int j =0, i = 0;
		
		for(i = 0 ; i < input.size();i++) {
			if (!(input.get(i).getdist() <= oneTen) ) {
				TheftList.add(input.subList(j, i));
				j = i;
				
				oneTen = oneTenCopy +  oneTen ;
			}
 		}
		TheftList.add(input.subList(j, i));

		return TheftList;
	}
	
	/**
	 * FOR DEBUGGING PURPOSES ONLY
	 * @param args
	 */
		public static void main(String[] args) {
			
			

		List<Integer> TheftFreq = new ArrayList<Integer>();
		List<List<Location>> TheftList = new ArrayList<List<Location>>();

		double threshhold = 11;
		List<Location> theft = TheftDis.result("data/dataTestDisKnownAll.csv", threshhold);
		
		
		/**
		 * Distributes the list of locations into 10 parts 
		 * To help with heat maps
		 */
		TheftList = dist(theft,threshhold);
//		System.out.println(TheftList.size());
		
		
		/**
		 * THE Code divided the list with threshold distance into 10 equal parts
		 *  by distance(for ex: dis = 2, first element will contain all the location with 0.2 km
		 */
		
		//To print out all the elements 
//		for(int j = 0 ; j < TheftList.size();j++) {
//			System.out.println("Jth element in the list: " + j);
//
//			for(int k =0; k < TheftList.get(j).size();k++) {
//				System.out.println(TheftList.get(j).get(k));
//
//			}
//		System.out.println();
//		System.out.println();			
//		}
		
//		/**
//		 * FreqDis is for  the output of dist() method
//		 */
//		freqDis(TheftList);
//		for(int i =0 ; i < TheftFreq.size();i++) {
//			System.out.println("For the "+ i + "th element in the TheftList -> " +TheftFreq.get(i));
//		}
//		/**
//		 * FreqNor is for the output of TheftDis.res()
//		 */
//		
//		for(int i =0 ; i < thef.size();i++) {
			System.out.println("No. of elements in the theft -> " + freqNor(theft));
//		}
//		
//		/**
//		 * This print out all the location with the threshhold distance
//		 */
////		for(int k =0; k < theft.size();k++) {
////			System.out.println(theft.get(k));
////
////		}
//		


		}
	
	
}
