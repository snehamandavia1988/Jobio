package utility;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import entity.ClientFieldLocation;
import entity.MyLocation;

/**
 * Created by SAI on 12/10/2015.
 */
public class GPSTracker {
    private final Context mContext;

    public GPSTracker(Context context) {
        this.mContext = context;
    }

    public void turnGPSOn() {
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        this.mContext.sendBroadcast(intent);

        String provider = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            this.mContext.sendBroadcast(poke);
        }
    }

    // automatic turn off the gps
    public void turnGPSOff() {
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", false);
        this.mContext.sendBroadcast(intent);

        String provider = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (provider.contains("gps")) { //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            this.mContext.sendBroadcast(poke);
        }
    }

    public Location getLocationNetWork(LocationManager locationManager) {
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        Location location = null;
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0, 0, listener);
        Logger.debug("Network Enabled");
        if (locationManager != null) {
            location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            locationManager.removeUpdates(listener);
        }
        return location;
    }

    public Location getLocationGPS(LocationManager locationManager) {
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        Location location = null;
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0, 0, listener);
        Logger.debug("GPS Enabled");
        if (locationManager != null) {
            location = locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.removeUpdates(listener);
        }
        return location;
    }

    public MyLocation getLastLocation() {
        //this.turnGPSOn();
        try {
            LocationManager locationManager = (LocationManager) mContext
                    .getSystemService(mContext.LOCATION_SERVICE);
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Logger.debug("isGPSEnabled:" + isGPSEnabled + " isNetworkEnabled:" + isNetworkEnabled);
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                if (isNetworkEnabled) {
                    Location location = this.getLocationNetWork(locationManager);
                    if(location!=null) {
                        MyLocation objMyLocation = new MyLocation(location.getLatitude(),location.getLongitude(),"A-GPS");
                        return  objMyLocation;
                    }
                }else if (isGPSEnabled) {
                    Location location = this.getLocationGPS(locationManager);
                    if(location!=null) {
                        MyLocation objMyLocation = new MyLocation(location.getLatitude(),location.getLongitude(),"GPS");
                        return  objMyLocation;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //this.turnGPSOff();
        return null;
    }
}
