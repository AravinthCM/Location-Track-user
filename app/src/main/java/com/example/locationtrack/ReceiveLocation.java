package com.example.locationtrack;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReceiveLocation extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private TextView locationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_location);

        mapView = findViewById(R.id.mapView);
        locationTextView = findViewById(R.id.locationTextView);

        // Reference to the location data in Firebase
        DatabaseReference locationRef = FirebaseDatabase.getInstance().getReference("user_location");

        // Add a ValueEventListener to listen for changes in the location data
        locationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Retrieve the location data from the snapshot
                String locationData = dataSnapshot.getValue(String.class);

                // Update the UI with the received location data
                if (locationData != null) {
                    locationTextView.setText("Location: " + locationData);

                    // Parse latitude and longitude from the location data
                    String[] coordinates = locationData.split(", ");
                    if (coordinates.length == 2) {
                        double latitude = Double.parseDouble(coordinates[0]);
                        double longitude = Double.parseDouble(coordinates[1]);

                        // Update the map with the received location
                        updateMap(new LatLng(latitude, longitude));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors, if any
            }
        });

        // Initialize the MapView and call getMapAsync to set up the map
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    private void updateMap(LatLng location) {
        if (googleMap != null) {
            // Clear previous markers
            googleMap.clear();

            // Add a marker for the received location
            googleMap.addMarker(new MarkerOptions().position(location).title("Driver's Location"));

            // Move the camera to the received location
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f)); // Adjust the zoom level as needed
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
