package module.activity;


/** 
 * @author Shivam Taneja, Lab 02
 **/
 public class Merge {
	
    /** 
     * This class should not be instantiated.
     **/
	private Merge() { }
    
	/**
	 * auxiliary comparable array for swap and sub-arrays 
	 */ 
	private static Comparable[] aux;	

	/**
	 * top-down merge sort using Comparable
	 * @param x - the input array containing products that need to be sorted.
	 * @param n - the size of the input array
	 */
	public static void sortMerge ( Comparable[] x, int n ) {
	   aux = new Comparable[n];   
	   sort(x, 0, n - 1);
	}
	

	/**
	 * Recursive Functions
	 */
	
	
	/**
	 * Recursive calls to merge sorting the array
	 * @param x - unsorted arrays/sub-arrays
	 * @param low - lower index of the sub-array 
	 * @param mid - partitioning point for the sub-array
	 * @param hi - upper index of the sub-array
	 */
	private static void merge(Comparable[] x, int low, int mid, int hi) {
		int i = low, j = mid+1;
		for (int k = low;k <=hi;k++) {
			aux[k] = x[k];
		}
		for (int k = low; k <= hi;k++) {
			if(i > mid) {
				x[k] = aux[j++];
			} else if (j > hi) {
				x[k] = aux[i++];
			} else if (less(aux[j],aux[i])) {
				x[k] = aux[j++];
			} else {
				x[k] = aux[i++];
			}
		}
	}
	
	/**
	 * Recursive calls to merge sorting the array
	 * @param a - unsorted arrays/sub-arrays
	 * @param lo - lower index of the sub-array 
	 * @param hi - upper index of the sub-array
	 */
	private static void sort(Comparable[] a, int lo, int hi){
		if (hi <= lo) {
			return;
		}
	   int mid = lo + (hi - lo)/2;
	   sort(a, lo, mid);       // Sort left half.
	   sort(a, mid+1, hi);     // Sort right half.
	   merge(a, lo, mid, hi);  // Merge results (code on page 271).
	}
	
	/**
	 * Helper Functions
	 */
		
	/**
	 * Functions for comparisons 
	 * @param v - first element to be compared
	 * @param w - second element to be compared
	 * @return true/false is 'w' is bigger than 'w'
	 */
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    
	/**
	 * Functions for swaps 
	 * @param a - the input array containing elements that needs swaping.
	 * @param i - First element's index
	 * @param j - Second element's index
	 */       
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    
	/**
	 * Print array to standard output
	 * @param a - Array that needs to be printed.
	 */	 
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
}