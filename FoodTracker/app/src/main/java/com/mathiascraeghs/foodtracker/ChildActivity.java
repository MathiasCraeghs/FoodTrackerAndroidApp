package com.mathiascraeghs.foodtracker;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ChildActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TextView score;
    private TextView address;
    private TextView name;
    private int pos;
    private MapFragment mMapFragment;
    private String lat,lng;
    private String nameR;
    private double latI,lngI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        name = (TextView)findViewById(R.id.name);
        score = (TextView) findViewById(R.id.score);
        address = (TextView) findViewById(R.id.address);

        Intent intent=getIntent();
        if(intent.hasExtra(this.getString(R.string.id_key))){
            pos = intent.getIntExtra(this.getString(R.string.id_key), 0);
        }
        if(intent.hasExtra(this.getString(R.string.name_key))){
            nameR=String.valueOf(intent.getStringExtra(this.getString(R.string.name_key)));
            name.setText(nameR);
        }
        if(intent.hasExtra(this.getString(R.string.address_key))){
            address.setText(intent.getStringExtra(this.getString(R.string.address_key)));
        }
        if(intent.hasExtra(this.getString(R.string.score_key))){

            score.setText(intent.getStringExtra(this.getString(R.string.score_key)));
        }
        else score.setText("no rating availble");

        if(intent.hasExtra(this.getString(R.string.lat_key))){
            lat = String.valueOf(intent.getStringExtra(this.getString(R.string.lat_key)));
            latI = Double.parseDouble(lat);
        }
        if(intent.hasExtra(this.getString(R.string.lng_key))){
            lng = String.valueOf(intent.getStringExtra(this.getString(R.string.lng_key)));
            lngI =Double.parseDouble(lng);
        }

        final SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(latI,lngI);
        googleMap.addMarker(new MarkerOptions()
                .position(location)
                .title(nameR));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

}
