package com.hostitwise.pokemommas;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationServices;

public class ChildActivity extends AppCompatActivity
        implements ConnectionCallbacks, OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            Log.d("init", "mGoogleApiClient initialized");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void clickListen(View view){
        Log.d("clicked", "locButton Clicked");
        Location loc = getLastKnownLocation();
        if (loc != null) {
            String coords = formatLocation(loc, 5);
            new AlertDialog.Builder(this).setTitle("Location").setMessage(coords).setNeutralButton("OK",
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {}
            }).show();
            System.out.println("locButton " + coords);
        } else {
            System.out.println("locButton returns null data");
        }
    }

    private String formatLocation(Location loc, int precision){
        char deg = '\u00B0';
        char latDir = (loc.getLatitude() >= 0) ? 'N' : 'S';
        char longDir = (loc.getLongitude() >= 0) ? 'E' : 'W';
        return String.format("%." + precision + "f" + deg + latDir + ", %." + precision + "f" + deg + longDir,
                Math.abs(loc.getLatitude()), Math.abs(loc.getLongitude()));
    }

    //Returns location using Google API LocationServices, null if permission denied
    private Location getLastKnownLocation() {
        Location mLastLocation;
        if (checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION },
                    42);
        }
        if (checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            System.out.println("location retrieved");
            //System.out.println(mLastLocation.toString());
        } else {
            mLastLocation = null;
            System.out.println("null location, permission check failed");
        }
        return mLastLocation;
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        Log.d("status", "Google API started");
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        Log.d("status", "Google API stopped");
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {

    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.d("status", "Google API suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.d("status", "Google API failed");
    }
}
