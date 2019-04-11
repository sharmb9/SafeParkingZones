/** 
 * @author Shivam Taneja, Lab 02, Group 10
 **/
package Search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Read {
	/**
	 * Defines the list of parking lot's information
	 */
	
	private static List<String> myList = new ArrayList<String>();
	
	/**
	 * Puts parking lot information in the list
	 * @return parking lot information -> list of string
	 */
	public static List<String> read() {
		try {
			File f = new File("data/parking.csv");
    		BufferedReader br = new BufferedReader(new FileReader(f));

    		String str = br.readLine();
    		str = br.readLine();
			
			while(str != null) {
   			myList.add(str.toLowerCase());

      			str = br.readLine();
    		}
			

			br.close();
		}
		catch ( Exception e) {
			System.out.println(e);
		}
		return myList;
	}
}
