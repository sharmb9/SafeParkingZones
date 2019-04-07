package Search;

import java.util.ArrayList;
import java.util.List;

	public class SearchAlg
	{
	    public boolean findPattern(String t, String p)
	    {
	        char[] text = t.toCharArray();
	        char[] pattern = p.toCharArray();
	        int pos = indexOf(text, pattern);
	        if (pos == -1) {
	        return false;}
	        
	        else {
//	        	return pos;
	        	return true;
	        	}
	    }
	    /** Function to calculate index of pattern substring **/
	    public int indexOf(char[] text, char[] pattern) 
	    {
	        if (pattern.length == 0) 
	            return 0;
	        int charTable[] = makeCharTable(pattern);
	        int offsetTable[] = makeOffsetTable(pattern);
	        for (int i = pattern.length - 1, j; i < text.length;) 
	        {
	            for (j = pattern.length - 1; pattern[j] == text[i]; --i, --j) 
	                     if (j == 0) 
	                    return i;
	 
	              i += Math.max(offsetTable[pattern.length - 1 - j], charTable[text[i]]);
	        }
	        return -1;
	      }
	      private int[] makeCharTable(char[] pattern) 
	      {
	        final int ALPHABET_SIZE = 256;
	        int[] table = new int[ALPHABET_SIZE];
	        for (int i = 0; i < table.length; ++i) 
	               table[i] = pattern.length;
	        for (int i = 0; i < pattern.length - 1; ++i) 
	               table[pattern[i]] = pattern.length - 1 - i;
	        return table;
	      }
	      private static int[] makeOffsetTable(char[] pattern) 
	      {
	        int[] table = new int[pattern.length];
	        int lastPrefixPosition = pattern.length;
	        for (int i = pattern.length - 1; i >= 0; --i) 
	        {
	            if (isPrefix(pattern, i + 1)) 
	                   lastPrefixPosition = i + 1;
	              table[pattern.length - 1 - i] = lastPrefixPosition - i + pattern.length - 1;
	        }
	        for (int i = 0; i < pattern.length - 1; ++i) 
	        {
	              int slen = suffixLength(pattern, i);
	              table[slen] = pattern.length - 1 - i + slen;
	        }
	        return table;
	    }
	    private static boolean isPrefix(char[] pattern, int p) 
	    {
	        for (int i = p, j = 0; i < pattern.length; ++i, ++j) 
	            if (pattern[i] != pattern[j]) 
	                  return false;
	        return true;
	    }
	    private static int suffixLength(char[] pattern, int p) 
	    {
	        int len = 0;
	        for (int i = p, j = pattern.length - 1; i >= 0 && pattern[i] == pattern[j]; --i, --j) 
	               len += 1;
	        return len;
	    }
	    public static void main(String[] args) 
	    {    
			List<String> list = new ArrayList<String>();
			list = Read.read();
			List<String> res = new ArrayList<String>();

			//Input from user - - here pre defined
			String pattern = "CLARE".toLowerCase();

			for(int i =0 ; i <list.size();i++) {
				SearchAlg bm = new SearchAlg();

				if(bm.findPattern(list.get(i), pattern)) {
					
					res.add(list.get(i));
					}

			}
		if(res.size() > 0) {
			System.out.println("Found");

		}
	    }
}
	/**
	 * //input from user
	        String txt = "Hey This is hello (pattern is clare) world string".toLowerCase();
	        String pattern = "CLARE".toLowerCase();

			System.out.println("test1\nText input: \n" + txt + "\nInput pattern to be found:\n" +pattern) ;
			System.out.println() ;

	        
	        

	        System.out.println("pattern found at " + bm1.findPattern(txt, pattern));
			System.out.println() ;

	        
	        String pattern1 = "Care".toLowerCase();

			System.out.println("test2\nText input: \n" + txt + "\nInput pattern to be found:\n" +pattern1) ;
			System.out.println() ;

	        
	        
	        SearchAlg bm2 = new SearchAlg(); 

	        System.out.println("pattern found " + bm2.findPattern(txt, pattern1) ? "true");
//	        
////	        for(int i =0 ; i < list.size();i++) {
////	        	
////		        String text = list.get(i).toString();
////
////		        SearchAlg bm = new SearchAlg(); 
////		        
////		        if( bm.findPattern(text, pattern) > 0) {
////		        	res.add(i);
////		        }
////	        }
////	        for(int k =0 ; k < res.size();k++) {
////	        System.out.println(list.get(res.get(k)));
////   
////	        }
 */
