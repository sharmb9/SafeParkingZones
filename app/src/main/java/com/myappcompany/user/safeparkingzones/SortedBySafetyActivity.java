package com.myappcompany.user.safeparkingzones;
/**
 * @author Bilaval Sharma
 */
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SortedBySafetyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorted_by_safety);

        //Change this and dadd code to sort parking spots by safety here
        ListView sortedSafetyListView = findViewById(R.id.sortedSafetyListView);
        ArrayList<Location> sortedSpots = new ArrayList<Location>();

        for(Location spot : MapsActivity.parkingZones){
            sortedSpots.add(spot);
        }


//        sortedSpots.add("Spot A");
//        sortedSpots.add("Spot B");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sortedSpots);
        sortedSafetyListView.setAdapter(arrayAdapter);

//        //add code to sort the parking spots by safety
//        ListView sortedListView = findViewById(R.id.sortedSafetyListView);
//        ArrayList<String> sortedSpots = new ArrayList<String>();
//
//        //REMOVE THIS LATER
//        sortedSpots.add("Spot A");
//        sortedSpots.add("Spot B");
//
//        //put the data addresses from the arraylist in listView
//
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sortedSpots);
//        sortedListView.setAdapter(arrayAdapter);
    }
}
