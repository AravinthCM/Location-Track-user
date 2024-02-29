package com.example.locationtrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LandingPageActivity extends AppCompatActivity {

    private ListView busListView;
    private SearchView searchView;
    private ArrayAdapter<String> adapter;
    private CustomBusAdapter adapter2;
    private List<String> busList;
    private List<String> filteredBusList;

    // Reference to Firebase database
    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);



        // Initialize Firebase
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("drivers");

        // Assuming you have a list of bus names, update it accordingly
        busList = new ArrayList<>(Arrays.asList(
                "Bus 1", "Bus 2", "Bus 3", "Bus 4",
                "Bus 5", "Bus 6", "Bus 7", "Bus 8",
                "Bus 9", "Bus 10", "Bus 11", "Bus 12",
                "Bus 13", "Bus 14", "Bus 15", "Bus 16",
                "Bus 17", "Bus 18", "Bus 19", "Bus 20",
                "Bus 21", "Bus 22", "Bus 23", "Bus 24",
                "Bus 25", "Bus 26", "Bus 27", "Bus 28",
                "Bus 29", "Bus 30", "Bus 31", "Bus 32"));
        filteredBusList = new ArrayList<>(busList);
        busListView = findViewById(R.id.busListView);
        searchView = findViewById(R.id.searchView);
        adapter2 = new CustomBusAdapter(this, R.layout.bus_item, filteredBusList);
        busListView.setAdapter(adapter2);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterBusList(newText);
                return true;
            }
        });

        // Set up item click listener
        busListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedBus = filteredBusList.get(position);
            // Call a method to retrieve and display the location of the selected bus
            displayBusLocation(selectedBus);
        });
    }

    private void filterBusList(String query) {
        filteredBusList.clear();

        for (String bus : busList) {
            if (bus.toLowerCase().contains(query.toLowerCase())) {
                filteredBusList.add(bus);
            }
        }

        adapter2.notifyDataSetChanged();
    }

    private void displayBusLocation(String selectedBus) {
        // Query the Firebase database to get the location of the selected bus
        firebaseDatabase.orderByChild("busNo").equalTo(selectedBus).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot driverSnapshot : snapshot.getChildren()) {
                                Double latitude = driverSnapshot.child("latitude").getValue(Double.class);
                                Double longitude = driverSnapshot.child("longitude").getValue(Double.class);

                                // Log the retrieved values
                                Log.d("BusLocation", "Latitude: " + latitude + ", Longitude: " + longitude);

                                // Call a method to display the location on the map
                                displayLocationOnMap(latitude, longitude);
                                return;  // Break out of the loop if location is found
                            }
                            Toast.makeText(LandingPageActivity.this, "Location not available for " + selectedBus, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LandingPageActivity.this, "Driver data not found for " + selectedBus, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LandingPageActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displayLocationOnMap(Double latitude, Double longitude) {
        Intent mapIntent = new Intent(LandingPageActivity.this, MapsActivity.class);
        mapIntent.putExtra("latitude", latitude);
        mapIntent.putExtra("longitude", longitude);
        startActivity(mapIntent);
    }

}
