package com.myappcompany.user.safeparkingzones;

/**
 * Searches for a text in given pattern
* @author Shivam Taneja, Lab 02, Group 10
**/
public class SearchAlg
{
    /**
     * Number of different character available to user -> 256
     */
    static int NO_OF_CHARS = 256;


    /**
     * The pre-processing function
     * @param str pattern in the form of character array
     * @param size length of the pattern in character array
     * @param badchar array where the bad character will be placed
     */
    static void badChar( char str[], int size,int badchar[])
    {

        // Initialize all occurrences as -1
        for (int i = 0; i < NO_OF_CHARS; i++) {
            badchar[i] = -1;

        }


        // Fill the actual value of last occurrence
        // of a character
        for ( int i = 0; i < size; i++) {
            badchar[(int) str[i]] = i;
//	System.out.println((int) str[i]);
        }


    }

    /**
     * A pattern searching function that uses Bad Character
     * @param txt String where the function needs to found -> in character array
     * @param pat pattern that needs to be found -> in character array
     */
    static boolean search( char txt[], char pat[])
    {
        int m = pat.length;
        int n = txt.length;
        int badchar[] = new int[NO_OF_CHARS];


        badChar(pat, m, badchar);

        int s = 0; // s is shift of the pattern with
        // respect to text
        while(s <= (n - m))
        {
            int j = m-1;


            while(j >= 0 && pat[j] == txt[s+j]) {
                j--;
            }


            if (j < 0)
            {

                return true;



            }

            else {
                int maxx = max(1, j - badchar[txt[s+j]]);
                s += maxx;
            }

        }

        return false;


    }

    /**
     * A utility function to get maximum of two integers
     * @param a first number
     * @param b second number
     * @return a if a > b else b
     */
    static int max (int a, int b) { return (a > b)? a: b; }
}