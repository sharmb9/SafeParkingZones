package Search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Read {
	private static List<String> myList = new ArrayList<String>();
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
