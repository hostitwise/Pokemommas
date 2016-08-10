package com.hostitwise.pokemommas;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
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

    protected void onStart() {
        mGoogleApiClient.connect();
        Log.d("status", "Google API started");
        super.onStart();
    }

    public void clickListen(View view){
        Log.d("clicked", "locButton Clicked");
        Location loc = getLastKnownLocation();
        if (loc != null) {
            char deg = '\u00B0';
            char latDir = (loc.getLatitude() >= 0) ? 'N' : 'S';
            char longDir = (loc.getLongitude() >= 0) ? 'E' : 'W';
            String coords = String.format("%.3f" + deg + latDir + ", %.3f" + deg + longDir,
                    Math.abs(loc.getLatitude()), Math.abs(loc.getLongitude()));
            new AlertDialog.Builder(this).setTitle("Location").setMessage(coords).setNeutralButton("OK",
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {}
            }).show();
            System.out.println("locButton " + coords);
        }
        System.out.println("locButton clicked");
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        Log.d("status", "Google API stopped");
        super.onStop();
    }

    public void onConnected(Bundle connectionHint) {

    }

    public void onConnectionSuspended(int cause) {
        Log.d("status", "Google API suspended");
    }

    public void onConnectionFailed(ConnectionResult result) {
        Log.d("status", "Google API failed");
    }

    //Returns location using Google API LocationServices, null if permission denied
    private Location getLastKnownLocation() {
        Location mLastLocation;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION },
                    42);
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            System.out.println("location retrieved");
        } else {
            mLastLocation = null;
            System.out.println("null location, permission check failed");
        }
        return mLastLocation;
    }
}
