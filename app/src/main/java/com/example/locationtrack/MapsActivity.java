package com.example.locationtrack;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DatabaseReference;

public class MapsActivity extends AppCompatActivity{

    private GoogleMap gMap;
    FrameLayout map;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


    }
}
