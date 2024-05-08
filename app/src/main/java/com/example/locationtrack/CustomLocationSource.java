package com.example.locationtrack;

import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.LatLng;

public class CustomLocationSource implements LocationSource {

    private OnLocationChangedListener onLocationChangedListener;

    public CustomLocationSource() {
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        this.onLocationChangedListener = null;
    }

    public void updateLocation(LatLng latLng) {
        if (onLocationChangedListener != null) {
            onLocationChangedListener.onLocationChanged(null);
        }
    }
}
