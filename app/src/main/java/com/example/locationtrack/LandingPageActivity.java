package com.example.locationtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LandingPageActivity extends AppCompatActivity {

    private ListView busListView;
    private SearchView searchView;
    private ArrayAdapter<String> adapter;

    private List<String> busList;
    private List<String> filteredBusList; // Add this line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        // Initialize the bus list

        // Set up the SearchView
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the bus list based on the search query
                filterBuses(newText);
                return false;
            }
        });
    }

    private void filterBuses(String query) {
        filteredBusList.clear();

        if (query.isEmpty()) {
            filteredBusList.addAll(busList);
        } else {
            for (String bus : busList) {
                if (bus.toLowerCase().contains(query.toLowerCase())) {
                    filteredBusList.add(bus);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
