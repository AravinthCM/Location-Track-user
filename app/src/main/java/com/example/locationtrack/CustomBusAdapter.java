package com.example.locationtrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomBusAdapter extends ArrayAdapter<String> {

    public CustomBusAdapter(Context context, int resource, List<String> objects) {
        super(context, R.layout.bus_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bus_item, parent, false);
        }

        // Get the data item for this position
        String bus = getItem(position);

        // Lookup view for data population
        TextView tvBusName = convertView.findViewById(android.R.id.text1); // Use android.R.id.text1 here

        // Populate the data into the template view using the data object
        if (bus != null) {
            tvBusName.setText(bus);
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
