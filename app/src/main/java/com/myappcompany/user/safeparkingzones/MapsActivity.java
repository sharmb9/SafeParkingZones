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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    //change these values to search a new locatin, will change this later to get user input
//    double userLat = 41.778554;
//    double userLon = -87.651920;


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
            userLocation= new LatLng(userLat, userLon);//put user input converted too coordinates here
            //adds marker to searched location(will change color of this later to make it distinct from parking spots
            mMap.animateCamera(CameraUpdateFactory.newLatLng(userLocation));

            //loads parkings spots (enter an if-else condition here)
            showMarkers("final_parking_zones.csv",userLat, userLon);
        }else{
            //not working
            Toast.makeText(getApplicationContext(),"Invalid Location", Toast.LENGTH_SHORT).show();;
        }
    }

    //Shows markers to google maps
    public void showMarkers(String dataset, double userLat, double userLon){


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(dataset)))) {

            //parkingZones = new Location[Sort.countLines(dataset)];
            parkingZones = new Location[31];
            reader.readLine(); //skips the first line, since that's coloumn names
            String line = "";
            int index = 0;
            //(line = reader.readLine()) != null
            while (index<=30) // Reads first 30 address from the dataset for now(change later)
            {
                line = reader.readLine();
                //converts address to coordinates
                LatLng address = getLocationFromAddress(this, line);

                double lat = address.latitude;
                double lon = address.longitude;
                double dist = Sort.distance(userLat, userLon, lat, lon);

                //adds to convereted coordinates with their distance from searched location to arrayList
                parkingZones[index]=(new Location(lat, lon, dist));
                index++;
            }

            //sort the list by distance from user
            Merge.sortMerge(parkingZones, parkingZones.length);

            //Add different markers
            addMarkers(parkingZones);

            //adds marker and zooms camera to searched location
            mMap.addMarker(new MarkerOptions().position(userLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,12));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Use GeoCoding to convert Parking Spot addresses to coordinates
    private LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;
        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;
    }

    //Adds different markers to 15 nearest parking spots from searched location based on safety(needs to be modified once safety sorting is done)
    private void addMarkers(Location[] parkingZonesList ){
        int coordCount=0;
        //iterate through a LatLng List
        for(Location spot : parkingZonesList)
        {
            LatLng parkingSpot = new LatLng(spot.getLat(),spot.getLon());
            //adding different color markers to different coordinates, will change this later where colors will be assigned according to safety level
            if(coordCount<=5){
                mMap.addMarker(new MarkerOptions()
                        .position(parkingSpot)
                        .title("Parking Spot")// change title to something more descriptive
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            }
            else if (coordCount>5 && coordCount <=10){
                mMap.addMarker(new MarkerOptions()
                        .position(parkingSpot)
                        .title("Parking Spot")// change title to something more descriptive
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            else if (coordCount>10 && coordCount <=15){
                mMap.addMarker(new MarkerOptions()
                        .position(parkingSpot)
                        .title("Parking Spot")// change title to something more descriptive
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
            coordCount++;
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chicagoLocation,12));
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

