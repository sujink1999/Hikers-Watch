package com.sujin.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputText = (TextView) findViewById(R.id.output);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                String output = "";

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                List<Address> listAddresses = null;
                try {
                    listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                output+= "Latitude : " + String.valueOf(location.getLatitude()) +"\r\n"+ "\r\n";
                output+= "Longitude : " + String.valueOf(location.getLongitude()) +"\r\n"+ "\r\n";
                output+= "Accuracy : " + String.valueOf(location.getAccuracy()) +"\r\n"+ "\r\n";
                output+= "Altitude : " + String.valueOf(location.getAltitude()) +"\r\n"+ "\r\n";
                output+= "Address : \r\n";

                if (listAddresses != null && listAddresses.size() > 0) {
                    if(listAddresses.get(0).getThoroughfare() != null)
                    {
                        Log.i("Info", listAddresses.get(0).toString());

                        output+=  listAddresses.get(0).getThoroughfare().toString() +" " ;
                    }
                    if(listAddresses.get(0).getLocality() != null)
                    {


                        output+= listAddresses.get(0).getLocality().toString() +" " ;
                    }
                    if(listAddresses.get(0).getCountryName() != null)
                    {
                        Log.i("Info", listAddresses.get(0).toString());

                        output+= listAddresses.get(0).getCountryName().toString() +" " ;
                    }
                }
                outputText.setText(output);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }
}




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
