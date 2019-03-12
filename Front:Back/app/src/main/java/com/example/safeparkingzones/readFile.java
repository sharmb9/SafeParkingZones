package com.example.safeparkingzones;




import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class readFile extends MapsActivity {
//    private readFile() { }

//    public List<LatLong> pointThief = new ArrayList<>();




    private void readLines(List<LatLong> pointThief){

        String line ;


        InputStream is = getResources().openRawResource(R.raw.theft);


        BufferedReader br = new BufferedReader( new InputStreamReader(is));//	        );
        try{
            line = br.readLine();
            while((line = br.readLine()) != null ) {

                //split by comma
                line = line.replace("[^a-zA-Z0-9-:,./]","").replace( "\"","").replace( "(","").replace( ")","");
                String[] tokens	= line.split(",");

                int l = tokens.length - 1;

                //removing incomplete data
                if (Double.parseDouble(tokens[l]) > 0 ) {
                    continue;
                } else {
                    LatLong sample = new LatLong(Double.parseDouble(tokens[l-1]), Double.parseDouble(tokens[l]));
                    pointThief.add(sample);
                }
            }

        } catch (IOException e){    }

//        for (int i = 0 ; i < pointThief.size() ; i++ ) {
//            Log.i("MyActivity", "Point Name: " + pointThief.get(i).getName() + " Latitude: " + pointThief.get(i).getLat()+ " Longitude: " + pointThief.get(i).getLong());
//        }
    }
    public    List<LatLong> start(){

        List<LatLong> pointThief = new ArrayList<>(40000);

        readLines(pointThief);
        return pointThief;
    }

}
