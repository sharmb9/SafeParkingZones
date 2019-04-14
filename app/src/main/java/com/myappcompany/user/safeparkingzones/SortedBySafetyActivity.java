package com.myappcompany.user.safeparkingzones;
/**
 * @author Bilaval Sharma
 */
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Class to show a list view of parking spots sorted by safety and distance from the user
 */
public class SortedBySafetyActivity extends AppCompatActivity {

    /**
     * Defines the starting state of the class
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorted_by_safety);

        ListView sortedSafetyListView = findViewById(R.id.sortedSafetyListView);
        ArrayList<String> sortedSpots = new ArrayList<String>();

        //Shows 30 nearest spots sorted by safety
        int count=0;
        for(Location spot : MapsActivity.safestNearestParkingZones){
            if(count<=15){
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                String spotAddress="";
                try {
                    addresses = geocoder.getFromLocation(spot.getLat(), spot.getLon(), 1);
                    String address = addresses.get(0).getAddressLine(0);
                    spotAddress = address;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sortedSpots.add(spotAddress);
                count++;
            }
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sortedSpots);
        sortedSafetyListView.setAdapter(arrayAdapter);

    }
}
