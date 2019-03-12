package com.myappcompany.user.safeparkingzones;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SortedByDistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorted_by_dist);

        //add code to sort the parking spots by distance
        ListView sortedListView= findViewById(R.id.sortedListView);

        //adding parking spots from dataset for now(unsorted)
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("final_parking_zones.csv")))) {
            //an arrayList of sorted coordinates (replace this with sorted coordinates)
            ArrayList<String> sortedSpots = new ArrayList<String>();
            String line = "";
            //skips first line
            try {
                reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            int count = 0;
            try {
                while (count <= 15) // Reads first 15 coordinates to load data quickly, change this later to search coordinates within 500 m os searches location
                {
                    line = reader.readLine();
                    //adds sorted parking spots to array list
                    sortedSpots.add(line);
                    count++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
