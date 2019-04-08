package com.myappcompany.user.safeparkingzones;
import android.content.Context;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.*;

public class Read {
    static CSVReader readFile;
    private static List<String> myList = new ArrayList<String>();
    public static List<String> read(String fileName, Context context) {
        try {
            //File f = new File("data/parking.csv");
            readFile = new CSVReader((new InputStreamReader(context.getAssets().open(fileName))));
            readFile.readNext();

            String[] str = readFile.readNext();

            while(str != null) {
                myList.add(str[0].toLowerCase()); //check this

                str = readFile.readNext();
            }


            readFile.close();
        }
        catch ( Exception e) {
            System.out.println(e);
        }
        return myList;
    }
}
