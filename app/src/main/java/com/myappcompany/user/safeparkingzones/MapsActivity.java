package com.myappcompany.user.safeparkingzones;

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
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    //Loads markers from dataset to google maps
    public void loadMarkers(String dataset){
        List<LatLng> latLngList;
//        change the file to parking spots because that is what we are supposed to show with markers
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(dataset)))) {
            //an arrayList of type LatLng(40,95)
            latLngList = new ArrayList<LatLng>();
            String line = "";
//        skips first line
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
                    //converts address to coordinates
                    LatLng address = getLocationFromAddress(this, line);
                    //adds to convereted coordinates to arrayList
                    latLngList.add(new LatLng(address.latitude, address.longitude));
                    count++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            int coordCount=0;
            for(LatLng pos : latLngList)
            {
//                Log.i("Position", String.valueOf(pos));
                LatLng parkingSpot = new LatLng(pos.latitude,pos.longitude);
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
                else{
                    mMap.addMarker(new MarkerOptions()
                            .position(parkingSpot)
                            .title("Parking Spot")// change title to something more descriptive
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }

                coordCount++;
            }
            //ZOOMS camera to first location from dataset for now, will change location later
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngList.get(1),12));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onMapSearch(View view){
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address searchedAddress = addressList.get(0);
            LatLng latLng = new LatLng(searchedAddress.getLatitude(), searchedAddress.getLongitude());
            //adds marker to searched location(will change color of this later to make it distinct from parking spots
            mMap.addMarker(new MarkerOptions().position(latLng).title("Location"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            //loads parkings spots
            loadMarkers("final_parking_zones.csv");
        }
    }

    //Use GeoCoding to convert Parking Spot addresses to coordinates
    public LatLng getLocationFromAddress(Context context, String strAddress)
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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //show a marker here too
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(41.8781, -87.62938);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Chicago"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        }


     public void goToSortedView(View view){
         Intent intent = new Intent(getApplicationContext(), SortedByDistActivity.class);
         startActivity(intent);
     }
    }

