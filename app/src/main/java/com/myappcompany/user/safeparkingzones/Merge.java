package com.myappcompany.user.safeparkingzones;

/**
 * @author Seda Mete
 */
public class Merge {
    /**
     * top-down merge sort using Comparable
     *
     * @param x - the input array containing products that need to be sorted.
     * @param n - the size of the input array
     */
    public static void sortMerge ( Comparable[] x, int n ) {
        aux = new Comparable[n];
        sort(x, 0, n-1);
    }

    //top-down merge sort
    //referenced code from page 273 of Algorithms by Sedgewick textbook
    private static void sort(Comparable[] x, int low, int high) {
        if (high <= low) return;
        int middle = low +(high - low)/2;
        sort(x, low, middle);
        sort(x, middle+1, high);
        merge(x, low, middle, high);
    }

    //auxiliary array to store copy of original array
    private static Comparable[] aux;

    //merges two subarrays into one sorted array
    //referenced code from page 271 of Algorithms by Sedgewick textbook
    private static void merge(Comparable[] x, int low, int middle, int high) {
        int i = low;
        int j = middle+1;
        for (int k = low; k <= high; k++)
            aux[k] = x[k];
        for (int k = low; k <= high; k++) {
            if (i > middle)
                x[k] = aux[j++];
            else if (j > high)
                x[k] = aux[i++];
            else if (aux[j].compareTo(aux[i]) < 0)
                x[k] = aux[j++];
            else
                x[k] = aux[i++];
        }
    }
}
