package com.example.locationtrack;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap gMap;
    private FrameLayout map;
    private DatabaseReference databaseReference;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Map<String, Marker> busMarkers = new HashMap<>();
    private ValueEventListener valueEventListener;
    private Handler handler;
    private final int REFRESH_INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("drivers");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        map = findViewById(R.id.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMarkerPositions();
            }
        });

        handler = new Handler();
        startAutoRefresh();
    }

    private void startAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshMarkerPositions();
                handler.postDelayed(this, REFRESH_INTERVAL);
            }
        }, REFRESH_INTERVAL);
    }

    private void refreshMarkerPositions() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    gMap.clear();
                    busMarkers.clear();

                    for (DataSnapshot busSnapshot : dataSnapshot.getChildren()) {
                        String busKey = busSnapshot.getKey();
                        Double latitudeValue = busSnapshot.child("latitude").getValue(Double.class);
                        Double longitudeValue = busSnapshot.child("longitude").getValue(Double.class);

                        if (latitudeValue != null && longitudeValue != null) {
                            double latitude = latitudeValue;
                            double longitude = longitudeValue;
                            LatLng newLocation = new LatLng(latitude, longitude);

                            MarkerOptions markerOptions = new MarkerOptions()
                                    .position(newLocation)
                                    .title(busSnapshot.child("name").getValue(String.class));
                            Marker busMarker = gMap.addMarker(markerOptions);
                            busMarker.showInfoWindow();
                            busMarkers.put(busKey, busMarker);
                        } else {
                            Toast.makeText(MapsActivity.this, "Invalid location data for bus: " + busKey, Toast.LENGTH_SHORT).show();
                        }
                    }
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(MapsActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MapsActivity.this, "Failed to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        double latitude = getIntent().getDoubleExtra("latitude", 0.0);
        double longitude = getIntent().getDoubleExtra("longitude", 0.0);
        if (gMap != null) {
            LatLng busLocation = new LatLng(latitude, longitude);
            BitmapDescriptor busIcon = getResizedBitmapDescriptor(R.drawable.busschool, 80, 80);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(busLocation)
                    .title("Bus Location")
                    .icon(busIcon);
            Marker busMarker = gMap.addMarker(markerOptions);
            busMarker.showInfoWindow();
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(busLocation, 16.0f));
        }
    }
    private BitmapDescriptor getResizedBitmapDescriptor(int resourceId, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return BitmapDescriptorFactory.fromBitmap(resizedBitmap);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.normal_map) {
            gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } else if (itemId == R.id.hybrid_map) {
            gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        } else if (itemId == R.id.satellite_map) {
            gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else if (itemId == R.id.terrain_map) {
            gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
        return super.onOptionsItemSelected(item);
    }
}
