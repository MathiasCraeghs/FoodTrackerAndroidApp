package com.mathiascraeghs.foodtracker;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ChildActivity extends AppCompatActivity {
    private TextView score;
    private TextView address;
    private TextView name;
    private int pos;
    private MapFragment mMapFragment;


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
            name.setText(intent.getStringExtra(this.getString(R.string.name_key)));
        }
        if(intent.hasExtra(this.getString(R.string.address_key))){
            address.setText(intent.getStringExtra(this.getString(R.string.address_key)));
        }
        if(intent.hasExtra(this.getString(R.string.score_key))){

            score.setText(intent.getStringExtra(this.getString(R.string.score_key)));
        }
        else score.setText("no rating availble");
        /*
         mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.my_container, mMapFragment);
        fragmentTransaction.commit();
*/

    }
/*
    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
    }
*/
}
