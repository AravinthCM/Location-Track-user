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
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bus_item, parent, false);
        }
        String bus = getItem(position);

        TextView tvBusName = convertView.findViewById(android.R.id.text1);
        if (bus != null) {
            tvBusName.setText(bus);
        }

        return convertView;
    }
}
