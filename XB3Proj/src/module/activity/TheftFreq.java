package module.activity;

import java.util.*;

public class TheftFreq {	
	
	
	public static List<List<Location>> TheftList = new ArrayList<List<Location>>();
	public static List<Integer> TheftFreq = new ArrayList<Integer>();
	
	
	public static List<Integer> freqDis(List<List<Location>> input) {
		int i = 0;
		while(i < 10) {
			
			TheftFreq.add(i,input.get(i).size());
			i++;
		}
		return TheftFreq;
	}
	
	public static int freqNor(List<Location> in) {
		
		return in.size();
	}
	
	
	
	public static void dist(List<Location> input, double threshhold) {
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


	}
	
	
		public static void main(String[] args) {
		double threshhold = 2;
		List<Location> theft = TheftDis.result("data/theft.csv", threshhold);
		
		
		/**
		 * Distributes the list of locations into 10 parts 
		 * To help with heat maps
		 */
		dist(theft,threshhold);
		System.out.println(TheftList.size());
		
		
		/**
		 * THE Code divided the list with threshold distance into 10 equal parts
		 *  by distance(for ex: dis = 2, first element will contain all the location with 0.2 km
		 */
		
		//To print out all the elements 
//		for(int j = 0 ; j < TheftList.size();j++) {
//			for(int k =0; k < TheftList.get(j).size();k++) {
//				System.out.println(TheftList.get(j).get(k));
//
//			}
//		System.out.println("Jth element in the list: " + j);
//		System.out.println();
//		System.out.println();			
//		}
		
		/**
		 * FreqDis is for  the output of dist() method
		 */
		freqDis(TheftList);
		for(int i =0 ; i < TheftFreq.size();i++) {
			System.out.println("For the "+ i + "th element in the TheftList -> " +TheftFreq.get(i));
		}
		/**
		 * FreqNor is for the output of TheftDis.res()
		 */
		
//		for(int i =0 ; i < thef.size();i++) {
			System.out.println("No. of elements in the theft -> " + freqNor(theft));
//		}
		
		/**
		 * This print out all the location with the threshhold distance
		 */
//		for(int k =0; k < theft.size();k++) {
//			System.out.println(theft.get(k));
//
//		}
		
	}
	
	
}
