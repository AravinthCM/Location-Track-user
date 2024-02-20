package com.example.locationtrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

public class DriverActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private TextView locationTextView; // Assuming you have a TextView to display the location

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123; // Use any integer value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationTextView = findViewById(R.id.locationTextView); // Adjust the ID based on your layout

        // Check for location permissions and request if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request the permissions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permissions are already granted, request location updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // Update UI with current location
        if (locationTextView != null) {
            String locationInfo = "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude();
            locationTextView.setText(locationInfo);
        }

        // Pass location to UserActivity
        Intent intent = new Intent(DriverActivity.this, UserActivity.class);
        intent.putExtra("latitude", location.getLatitude());
        intent.putExtra("longitude", location.getLongitude());
        startActivity(intent);
    }

    // Other LocationListener methods
}

