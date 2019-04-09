package com.myappcompany.user.safeparkingzones;

/**
 * implements insertion sort to sort parking zones by safety
 *
 * @author Seda Mete
 */
public class Insertion {

    /**
     * regular insertion sort
     *
     * @param x - the input array containing parking zones that need to be sorted.
     */
    //referenced code from page 251 of Algorithms by Sedgewick textbook
    public static void sortInsert( Location[] x ) {
        int L = x.length; //gets length of the array

        //iterate through array
        for (int i = 1; i < L; i++) {
            //iterate through all elements that are to the left
            for (int j = i; j > 0; j--) {
                //swap elements if frequency is smaller
                if (x[j].getFreq() < x[j-1].getFreq())
                    exch(x, j-1, j);
                    //if frequencies are equal
                else if (x[j].getFreq() == x[j-1].getFreq())
                    exch(x, j-1, j);
            }
        }
    }

    //swaps two array elements
    private static void exch(Location[] a, int i, int j) {
        Location temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
