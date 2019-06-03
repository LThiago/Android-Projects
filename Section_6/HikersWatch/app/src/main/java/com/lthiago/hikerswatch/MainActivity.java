package com.lthiago.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocationInfo(location);
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastKnownLocation != null) {
                updateLocationInfo(lastKnownLocation);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startListening();
        }
    }

    private void updateLocationInfo(Location location) {
        TextView latitudeTextView = findViewById(R.id.latitudeTextView);
        TextView longitudeTextView = findViewById(R.id.longitudeTextView);
        TextView accuracyTextView = findViewById(R.id.accuracyTextView);
        TextView altitudeTextView = findViewById(R.id.altitudeTextView);
        TextView addressTextView = findViewById(R.id.addressTextView);

        latitudeTextView.setText(String.format("Latitude: %s", location.getLatitude()));
        longitudeTextView.setText(String.format("Longitude: %s", location.getLongitude()));
        accuracyTextView.setText(String.format("Accuracy: %s", location.getAccuracy()));
        altitudeTextView.setText(String.format("Altitude: %s", location.getAltitude()));
        addressTextView.setText(String.format(generateAddress(location)));
    }

    private void startListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    private String generateAddress(Location location) {
        String address = "Could not find address";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addressList != null && addressList.size() > 0) {
                address = "Address:\n";
                if (addressList.get(0).getThoroughfare() != null) {
                    address += addressList.get(0).getThoroughfare() + "\n";
                }
                if (addressList.get(0).getLocality() != null) {
                    address += addressList.get(0).getLocality() + " ";
                }
                if (addressList.get(0).getPostalCode() != null) {
                    address += addressList.get(0).getPostalCode() + " ";
                }
                if (addressList.get(0).getAdminArea() != null) {
                    address += addressList.get(0).getAdminArea();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }
}
