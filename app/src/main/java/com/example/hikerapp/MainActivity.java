package com.example.hikerapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    Geocoder geocoder;
    TextView TV_Lattitude;
    TextView TV_Longitude;
    TextView TV_Altitude;
    TextView TV_Accuracy;
    TextView TV_AddressContent;

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this,new String[] { Manifest.permission.ACCESS_FINE_LOCATION},1);
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TV_Lattitude = findViewById(R.id.TV_Lattitude);
        TV_Longitude = findViewById(R.id.TV_Longitude);
        TV_Accuracy = findViewById(R.id.TV_Accuracy);
        TV_Altitude = findViewById(R.id.TV_Altitude);
        TV_AddressContent = findViewById(R.id.TV_AddressContent);

         locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

         locationListener = new LocationListener() {
             @Override
             public void onLocationChanged(Location location) {
                 TV_Lattitude.setText("Lattitude: "+ location.getLatitude());
                 TV_Longitude.setText("Longitude: "+ location.getLongitude());
                 TV_Accuracy.setText("Accuracy: "+ location.getAccuracy());
                 TV_Altitude.setText("Altitude: "+ location.getAltitude());
                 geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());


                 try {
                     List <Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                     String Address = "";
                     if(addressList != null && addressList.size() > 0){
                         Address += addressList.get(0).getFeatureName().toString()+ " ";
                         Address += addressList.get(0).getThoroughfare().toString() + ",";
                         Address += addressList.get(0).getLocality().toString() + ",";
                         Address += addressList.get(0).getSubAdminArea().toString() + ",";
                         Address += addressList.get(0).getPostalCode().toString() + ",";
                         Address += addressList.get(0).getCountryName().toString();
                     }
                     else{
                         Address = "Could not find Address ☹️";
                     }
                     TV_AddressContent.setText(Address);

                 }catch (Exception e){
                     e.printStackTrace();
                 }



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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[] { Manifest.permission.ACCESS_FINE_LOCATION},1);
        } else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,0,locationListener);
        }



    }


}
