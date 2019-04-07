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

/**
 *The main map activity to show the map focused on the city of
 *chicago which fetches data from the dataset and provides services as requested by the user.
 */
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
    Marker markerUser;
    Marker markerSpot;
    ArrayList<Marker> markerArray;

    /**
     * Defines the starting state of the MapsActivity
     * @param savedInstanceState Previous saved instance of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Defines what to do when the search button is pressed
     * @param view
     */
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

            //remove previous user location marker
            if(markerUser != null){
                markerUser.remove();
            }

            //loads parkings spots (enter an if-else condition here)
            showMarkers("final_parking_coord.csv",userLat, userLon); //change to final_parking_zones.csv
        }else{
            //not working
            Toast.makeText(getApplicationContext(),"Invalid Location", Toast.LENGTH_SHORT).show();;
        }
    }

    /**
     * Sorts the dataset by distance from the user location and implements the addMarker method
     * @param fileName The input parking spot dataset
     * @param userLat User location latitude
     * @param userLon User location longitude
     */
    public void showMarkers(String fileName, double userLat, double userLon){
        parkingZones=Sort.readData(fileName, getApplicationContext());
        Sort.nearestParkingZones(userLat, userLon,parkingZones);
        //Add different markers
        addMarkers(parkingZones);
        //adds marker and zooms camera to searched location
        markerUser= mMap.addMarker(new MarkerOptions().position(userLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15));
    }

    /**
     * Shows the the user location and the 15 nearest and safest parking spots on the map
     * @param parkingZones The array containing sorted parking spots
     */
    private void addMarkers(Location[] parkingZones ){
        markerArray= new ArrayList<Marker>();
        //remove previous parking spot markers
        for (Marker markerSpot : markerArray){
            if (markerSpot!=null){
                markerSpot.remove();
            }
        }
        for(int i=0; i<parkingZones.length; i++) //i<parkingZones.length && sortedBySafetyParkingZones.length
        {
            LatLng parkingSpot = new LatLng(parkingZones[i].getLat(),parkingZones[i].getLon());
            //adding different color markers to different coordinates, will change this later where colors will be assigned according to safety level
            if(i<=5){
                markerArray.add(mMap.addMarker(new MarkerOptions()
                        .position(parkingSpot)
                        .title("Parking Spot")// change title to something more descriptive
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))));
            }
            else if (i>5 && i <=10){
                markerArray.add(mMap.addMarker(new MarkerOptions()
                        .position(parkingSpot)
                        .title("Parking Spot")// change title to something more descriptive
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))));
            }

            else if (i>10 && i <=15){
                markerArray.add(mMap.addMarker(new MarkerOptions()
                        .position(parkingSpot)
                        .title("Parking Spot")// change title to something more descriptive
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))));
            }
        }
    }


    /**
     * Manuplates the map once ready
     * @param googleMap The google map object
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng chicagoLocation = new LatLng(41.8781, -87.6298);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chicagoLocation,14));
        }

    /**
     * Goes to SortedByDistActivity on clicking the sort by distance button
     * @param view The map view
     */
     public void goToSortedDistView(View view){
         Intent intent = new Intent(getApplicationContext(), SortedByDistActivity.class);
         startActivity(intent);
     }

    /**
     * Goes to SortedBySafetyActivty on clicking the sort by safety button
     * @param view The map view
     */
    public void goToSortedSafetyView(View view){
        Intent intent = new Intent(getApplicationContext(), SortedBySafetyActivity.class);
        startActivity(intent);
    }
    }

