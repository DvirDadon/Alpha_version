package com.example.alpha_version;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class GPS extends AppCompatActivity {
    TextView tv1,tv2;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final int PERMISSIONS_FINE_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_p_s);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        LocationRequest LR= new LocationRequest();
        LR.setInterval(30000);
        LR.setFastestInterval(5000);
        LR.setPriority(100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions , @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode , permissions , grantResults);
        switch(requestCode){
            case PERMISSIONS_FINE_LOCATION:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    updateGPS();
                }
                else{
                    Toast.makeText(this, "This app requires permission to be granted in order to work properly !", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

        }
    }

    public void ShowLocation(View view) {
        updateGPS();
    }

    private void updateGPS() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(GPS.this);
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                ==PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    updateUIValues(location);
                }
            });
        }
        else{
            if(Build.VERSION.SDK_INT>=23){
                requestPermissions(new  String [] {Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_FINE_LOCATION);
            }
        }
    }

    private void updateUIValues( Location location) {
        tv1.setText("Latitude :" + String.valueOf(location.getLatitude()));
        tv2.setText("Longitude :" + String.valueOf(location.getLongitude()));
    }




    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menuStorage){
            Intent si = new Intent(this,Storage.class);
            startActivity(si);
        }
        else{
            Intent si = new Intent(this , QrCode_reader.class);
            startActivity(si);
        }
        return true;
    }

}