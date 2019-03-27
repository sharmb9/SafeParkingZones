package com.myappcompany.user.safeparkingzones;
/**
 * @author Bilaval Sharma
 */
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.opencsv.CSVReader;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.myappcompany.user.safeparkingzones.R;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    EditText locationSearch;
    List<Address> userAddressList;
    String location="";
    Geocoder geocoder;
    double userLat;
    double userLon;
    LatLng userLocation;
    static Location[] parkingZones;
    static ArrayList<Location> parkingZonesArrayList;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void onMapSearch(View view){
        locationSearch=(EditText) findViewById(R.id.editTextSearch);
        location = locationSearch.getText().toString();

        //hide the keyboard after user enters the location
        InputMethodManager mgr= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(locationSearch.getWindowToken(),0);


        if (location != null || !location.equals("")) {
            geocoder = new Geocoder(this);
            try {
                userAddressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address searchedAddress = userAddressList.get(0);
            userLat= searchedAddress.getLatitude();
            userLon= searchedAddress.getLongitude();
            userLocation= new LatLng(userLat, userLon);
            //adds marker to searched location
            mMap.animateCamera(CameraUpdateFactory.newLatLng(userLocation));

            //loads parkings spots (enter an if-else condition here)
            showMarkers("sep_motor_locations.csv",userLat, userLon); //change to final_parking_zones.csv
        }else{
            //not working
            Toast.makeText(getApplicationContext(),"Invalid Location", Toast.LENGTH_SHORT).show();;
        }
    }

    //Shows markers to google maps
    //public void showMarkers(String dataset, double userLat, double userLon)
    public void showMarkers(String dataset, double userLat, double userLon){
        try {
            CSVReader reader = new CSVReader((new InputStreamReader(getAssets().open(dataset))));
            String[] nextLine;
            parkingZones = new Location [9000]; //parkingZonesArrayList = new ArrayList<Location>();
            reader.readNext(); //skips the first line, since that's coloumn names
            int index = 0;
            //while ((nextLine=reader.readNext())!=null)
            while (index<=8999) // Reads first 50 address from the dataset for now(change later)
            {
                nextLine = reader.readNext();
                double lat = Double.parseDouble(nextLine[0]);
                double lon = Double.parseDouble(nextLine[1]);
                String addressLine= String.valueOf(lat) + " " + String.valueOf(lon);
                //Log.i("Address", addressLine); checks that coordinates are being read
                double dist = Sort.distance(userLat, userLon, lat, lon);

                //adds to convereted coordinates with their distance from searched location to array
                parkingZones[index]=(new Location(lat, lon, dist));
                //parkingZonesArrayList.add(new Location(lat, lon, dist));
                index++;
            }

            //Log.i("Unsorted address", String.valueOf(parkingZonesArrayList.get(0)));
            //checking first one just to make sure stuff is added to array
            for(int i=0; i<parkingZones.length; i++){
                //Log.i("Unsorted data: ", String.valueOf(spot.getLat() + " " + String.valueOf(spot.getLon())));
                Log.i("Spot:",String.valueOf(parkingZones[i].getLon()));
            }
            //Log.i("Unsorted data: ", String.valueOf(parkingZones[0].getLat() + " " + String.valueOf(parkingZones[0].getLon())));
            //Merge.sortMerge(parkingZonesArrayList, parkingZonesArrayList.size());

            //sort the list by distance from user
            Merge.sortMerge(parkingZones, parkingZones.length);


            //Log.i("Sorted address", String.valueOf(parkingZonesArrayList.get(0)));
            //Log.i("Sorted address", String.valueOf(parkingZones));

            //remove previous markers
            if(marker != null){
                marker.remove();
            }
            //Add different markers
            addMarkers(parkingZones);
            //addMarkers(parkingZonesArrayList);

            //adds marker and zooms camera to searched location
            mMap.addMarker(new MarkerOptions().position(userLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,14));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Adds different markers to 15 nearest parking spots from searched location based on safety(needs to be modified once safety sorting is done)
    // private void addMarkers(ArrayList<Location> parkingZonesList )
    private void addMarkers(Location[] parkingZones ){
        //int coordCount=0;
        for(int i=0; i<parkingZones.length; i++)
        {
            LatLng parkingSpot = new LatLng(parkingZones[i].getLat(),parkingZones[i].getLon());
            //adding different color markers to different coordinates, will change this later where colors will be assigned according to safety level
            if(i<=5){
                mMap.addMarker(new MarkerOptions()
                        .position(parkingSpot)
                        .title("Parking Spot")// change title to something more descriptive
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            }
            else if (i>5 && i <=10){
                mMap.addMarker(new MarkerOptions()
                        .position(parkingSpot)
                        .title("Parking Spot")// change title to something more descriptive
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            else if (i>10 && i <=15){
                mMap.addMarker(new MarkerOptions()
                        .position(parkingSpot)
                        .title("Parking Spot")// change title to something more descriptive
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
            //coordCount++;
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng chicagoLocation = new LatLng(41.8781, -87.6298);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chicagoLocation,14));
        }

    //goes to SortedByDistActivity on clicking the sort by distance button
     public void goToSortedDistView(View view){
         Intent intent = new Intent(getApplicationContext(), SortedByDistActivity.class);
         startActivity(intent);
     }

    //goes to SortedBySafetyActivty on clicking the sort by safety button
    public void goToSortedSafetyView(View view){
        Intent intent = new Intent(getApplicationContext(), SortedBySafetyActivity.class);
        startActivity(intent);
    }
    }

