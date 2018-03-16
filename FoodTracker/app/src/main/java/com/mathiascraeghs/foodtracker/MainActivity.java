package com.mathiascraeghs.foodtracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Cursor mCursor;

    private String googleSearchResults;
    private double latitude;
    private double longitude;
    private double radius =5000;

    private RestaurantAdapter adapter;
    private RecyclerView mNumberList;
    private NetworkUtils mNetworkUtils;


    final String Google_Places_URL =
            "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=51.154907,5.383397&radius=2000&type=restaurant&key=AIzaSyAWp6MXdRMNjutTPL1qr-8EPX6UgEaU4ac";



    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       //  button =  (Button) findViewById(R.id.tv_location);
      //  configureButton();


        mNumberList = (RecyclerView) findViewById(R.id.rv_numbers) ;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumberList.setLayoutManager(layoutManager);
        mNumberList.setHasFixedSize(true);
        URL googleSearchUrl = mNetworkUtils.buildUrl(Google_Places_URL);

        new GoogleQueryTask().execute(googleSearchUrl);
     //   makeGoogleSearchQuery();
       // adapter = new RestaurantAdapter(MainActivity.this, mCursor);
     //   mNumberList.setAdapter(adapter);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude= location.getLongitude();



            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.INTERNET
                        }, 10);
                        return;
            }
        } else {
            configureButton();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
        case 10:
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
     }
    }

    private void configureButton() {
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

            }
        });

    }
    private Cursor getJSONCursor(String response){
        try{
            Log.i("main", response);
            JSONArray array = new JSONArray(response);
            return new JSONArrayCursor(array);
        } catch(JSONException exception)
        {
            String ex = exception.getMessage();
        }
        return null;
    }
    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getRadius() {
        return radius;
    }


    private void makeGoogleSearchQuery() {
        URL googleSearchUrl = mNetworkUtils.buildUrl(Google_Places_URL);

        new GoogleQueryTask().execute(googleSearchUrl);
    }


    public class GoogleQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];

            try {
                HttpURLConnection conn = (HttpURLConnection) params[0].openConnection();
                InputStream in = conn.getInputStream();
                String line, text;
                text = "";
                byte[] byteArray = new byte[1024];
                BufferedReader reader = new BufferedReader(new InputStreamReader(in), byteArray.length);
                while((line = reader.readLine()) != null){
                    text = text + line;
                }
                googleSearchResults = text;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Log.i("main", googleSearchResults);
            int start, end;
            start = googleSearchResults.indexOf("[      {");
            end = googleSearchResults.indexOf("}   ]");
            Log.i("main", start +" "+ end);
            if(start == -1 && end == -1) {
                start = googleSearchResults.indexOf("[{");
                end = googleSearchResults.indexOf("}]");
                Log.i("main", start +" "+ end);
            }
            googleSearchResults = googleSearchResults.substring(start,end)+"}]";

            //  mCursor = getJSONCursor(googleSearchResults);

            return googleSearchResults;
        }


        // COMPLETED (3) Override onPostExecute to display the results in the TextView
        @Override
        protected void onPostExecute(String googleSearchResults) {
            if (googleSearchResults != null && !googleSearchResults.equals("")) {
                mCursor = getJSONCursor(googleSearchResults);
                adapter =new RestaurantAdapter(MainActivity.this, mCursor);
                mNumberList.setAdapter(adapter);
            }
        }

    }





}
