package com.myappcompany.user.safeparkingzones;
/**
 * @author Bilaval Sharma
 */
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
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
    static CSVReader readFile;
    private static List<String> myList = new ArrayList<String>();
    EditText locationCheck;
    String checkLocation="";
    static List<String> res;
    List<Address> parkingAddressList;
    double parkLat;
    double parkLon;
    LatLng parkLocation;
    Marker markerParking;
    Polyline line;

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

    public List<String> read(String fileName) {
        try {
            //File f = new File("data/parking.csv");
            readFile = new CSVReader((new InputStreamReader(getAssets().open(fileName))));

            readFile.readNext();

            String[] str = readFile.readNext();

            while(str != null) {
                myList.add(str[0].toLowerCase()); //check this
                Log.i("Address",str[0]);
                str = readFile.readNext();
            }
            readFile.close();
        }
        catch ( Exception e) {
            Log.i("Error",e.toString());
        }

        return myList;

    }

    /**
     * Not working for now
     * Checks if there is are any parking spots avaialable on the searched street and shows them on the map
     * @param view
     */
    public void onMapCheck(View view){
        locationCheck=(EditText) findViewById(R.id.editTextSearch);
        checkLocation = locationCheck.getText().toString();

        //hide the keyboard after user enters the location
        InputMethodManager mgr= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(locationCheck.getWindowToken(),0);

        List<String> list = new ArrayList<String>();
        list = read("final_parking_zones.csv");
        res = new ArrayList<String>();
        String pattern = checkLocation.toLowerCase();

        for(int i =0 ; i <list.size();i++) {
            SearchAlg bm = new SearchAlg();

            if(bm.findPattern(list.get(i), pattern)) {
                res.add(list.get(i));
            }
        }
        //if status is found, show markers
        if(res.size()>0){
            Toast toast = Toast.makeText(getApplicationContext(), "Parking spot found at: " + res.get(0), Toast.LENGTH_SHORT);
            toast.show();
            //just showing one spot for now
            Log.i("Search result","Parking spots found!");
                //convert addresses to coordinates here and show them using markers
            geocoder = new Geocoder(this);
            try {
                parkingAddressList = geocoder.getFromLocationName(res.get(0), 1); //just showing one for now because geocoding takes time to convert
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address searchedAddress = parkingAddressList.get(0);
            parkLat= searchedAddress.getLatitude();
            parkLon= searchedAddress.getLongitude();
            parkLocation= new LatLng(parkLat, parkLon);
            //adds marker to searched location
            mMap.animateCamera(CameraUpdateFactory.newLatLng(parkLocation));

            markerParking= mMap.addMarker(new MarkerOptions().position(parkLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(parkLocation,15));


            //uncomment following block to get all spots on street(slow becuase of geociding)

//            for(int i=0; i<res.size(); i++ ){
//                Log.i("Search result","Parking spots found!");
//                //convert addresses to coordinates here and show them using markers
//                geocoder = new Geocoder(this);
//                try {
//                    parkingAddressList = geocoder.getFromLocationName(res.get(0), 1); //just showing one for now because geocoding takes time to convert
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Address searchedAddress = parkingAddressList.get(i);
//                parkLat= searchedAddress.getLatitude();
//                parkLon= searchedAddress.getLongitude();
//                parkLocation= new LatLng(parkLat, parkLon);
//                //adds marker to searched location
//                mMap.animateCamera(CameraUpdateFactory.newLatLng(parkLocation));
//
//                markerParking= mMap.addMarker(new MarkerOptions().position(parkLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(parkLocation,15));
//            }
        }else{
            Log.i("Search result", "Nothing found!");
            Toast toast = Toast.makeText(getApplicationContext(), "nothing found!", Toast.LENGTH_SHORT);
            toast.show();

        }
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
            markerUser= mMap.addMarker(new MarkerOptions().position(userLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15));

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
    }

    /**
     * Shows the the user location and the 30 nearest and safest parking spots on the map
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
            if(i<=10){
                markerArray.add(mMap.addMarker(new MarkerOptions()
                        .position(parkingSpot)
                        .title("Parking Spot")// change title to something more descriptive
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))));
            }
            else if (i>10 && i <=20){
                markerArray.add(mMap.addMarker(new MarkerOptions()
                        .position(parkingSpot)
                        .title("Parking Spot")// change title to something more descriptive
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))));
            }

            else if (i>20 && i <=30){
                markerArray.add(mMap.addMarker(new MarkerOptions()
                        .position(parkingSpot)
                        .title("Parking Spot")// change title to something more descriptive
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))));
            }
        }
    }

    //shows the parking spots in route from destination to source
    //testing using parkings spots marker list for now
    public void showPolyLines(View view){
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        for (int z = 0; z < 30; z++) {
            LatLng point =new LatLng(parkingZones[z].getLat(),parkingZones[z].getLon());
            options.add(point);
        }
        line = mMap.addPolyline(options);
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

