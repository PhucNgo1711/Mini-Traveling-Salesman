package edu.drexel.cs.ptn32.hw2;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


/**
 * Created by PhucNgo on 8/15/15.
 */
public class GPSManager implements LocationListener {
    private MainWindowActivity mainWindowActivity;
    private String lon, lat;

    public GPSManager(MainWindowActivity mainWindowActivity) {
        this.mainWindowActivity = mainWindowActivity;
    }

    @Override
    public void onLocationChanged(Location loc) {
        Toast.makeText(mainWindowActivity.getBaseContext(), "Location changed: Lat: " + loc.getLatitude() + " Lng: " + loc.getLongitude(), Toast.LENGTH_SHORT).show();
        lon = "Longitude: " + loc.getLongitude();
        lat = "Latitude: " + loc.getLatitude();
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}

