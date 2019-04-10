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

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Class to show a list view of parking spots sorted by distance from the user
 */
public class SortedByDistActivity extends AppCompatActivity {

    /**
     *Defines the starting state of SortedByDistActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorted_by_dist);

        //add code to sort the parking spots by distance
        ListView sortedListView = findViewById(R.id.sortedDistListView);
        ArrayList<String> sortedSpots = new ArrayList<String>();

        //Shows 30 nearest spots in sorted order
        int count=0;
        for(Location spot : MapsActivity.parkingZones){
            if(count<=30){
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                String spotAddress="";
                try {
                    addresses = geocoder.getFromLocation(spot.getLat(), spot.getLon(), 1);
                    String address = addresses.get(0).getAddressLine(0);
                    //String city = addresses.get(0).getLocality();
                    spotAddress = address;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sortedSpots.add(spotAddress);
                count++;
            }
        }

        //put the data addresses from the arraylist in listView
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sortedSpots);
        sortedListView.setAdapter(arrayAdapter);
    }
}
