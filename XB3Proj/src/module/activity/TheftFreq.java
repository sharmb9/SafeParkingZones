package module.activity;

import java.util.*;

public class TheftFreq {	
	
	
	public List<List<Location>> TheftList = new ArrayList<List<Location>>(10);
	public List<Integer> TheftFreq = new ArrayList<Integer>(10);
	
	
	public List<Integer> freq(List<List<Location>> input) {
		int i = 0;
		while(i < 10) {
			
			TheftFreq.add(i,input.get(i).size());
	
		}
		return TheftFreq;
	}
	
	public List<List<Location>> dist(Location[] input, double threshhold) {
		//assuming input is the Theft location within the threshhold;
		
		double oneTenth = threshhold/10;
		int iter = 0;
		while(!(iter== input.length)){
			if(input[iter].getdist() <= oneTenth) {
				TheftList.get(0).add(input[iter]);
			} else if ( input[iter].getdist() <= (oneTenth*2)) {
				TheftList.get(1).add(input[iter]);

			}else if ( input[iter].getdist() <= (oneTenth*3)) {
				TheftList.get(2).add(input[iter]);

			}else if ( input[iter].getdist() <= (oneTenth*4)) {
				TheftList.get(3).add(input[iter]);

			}else if ( input[iter].getdist() <= (oneTenth*5)) {
				TheftList.get(4).add(input[iter]);

			}else if ( input[iter].getdist() <= (oneTenth*6)) {
				TheftList.get(5).add(input[iter]);

			}else if ( input[iter].getdist() <= (oneTenth*7)) {
				TheftList.get(6).add(input[iter]);

			}else if ( input[iter].getdist() <= (oneTenth*8)) {
				TheftList.get(7).add(input[iter]);

			}else if ( input[iter].getdist() <= (oneTenth*9)) {
				TheftList.get(8).add(input[iter]);

			}else if ( input[iter].getdist() <= (oneTenth*10)) {
				TheftList.get(9).add(input[iter]);
			}	
		}
		return TheftList;

	}
	
	
	
}
