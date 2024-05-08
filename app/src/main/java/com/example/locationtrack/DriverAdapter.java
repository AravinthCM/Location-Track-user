package com.example.locationtrack;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverViewHolder> {

    private List<DriverModel> driverList;
    private Context context;
    public DriverAdapter(Context context, List<DriverModel> driverList) {
        this.context = context;
        this.driverList = driverList;
    }
    public DriverAdapter(List<DriverModel> driverList) {
        this.driverList = driverList;
    }
    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_driver, parent, false);
        return new DriverViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
        DriverModel driver = driverList.get(position);
        holder.textBusNo.setText(driver.getBusNo());
        holder.textName.setText(driver.getName());
        // Convert latitude and longitude to location name
        String locationName = convertLatLngToLocationName(driver.getLatitude(), driver.getLongitude());
        holder.textLocation.setText(locationName);
    }
    @Override
    public int getItemCount() {
        return driverList.size();
    }
    static class DriverViewHolder extends RecyclerView.ViewHolder {
        TextView textBusNo, textName, textLocation;

        public DriverViewHolder(@NonNull View itemView) {
            super(itemView);
            textBusNo = itemView.findViewById(R.id.text_bus_no);
            textName = itemView.findViewById(R.id.text_name);
            textLocation = itemView.findViewById(R.id.text_location);
        }
    }
    private String convertLatLngToLocationName(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context.getApplicationContext(), Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                StringBuilder addressStringBuilder = new StringBuilder();
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressStringBuilder.append(address.getAddressLine(i));
                    if (i < address.getMaxAddressLineIndex()) {
                        addressStringBuilder.append(", ");
                    }
                }
                return addressStringBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown Location";
    }
}
