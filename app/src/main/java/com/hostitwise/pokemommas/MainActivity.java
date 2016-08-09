package com.hostitwise.pokemommas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME="UserPreferenceFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
        setContentView(R.layout.activity_main);
    }

    public void launchChildStart(View view){
        Intent intent = new Intent(this, ChildActivity.class);
        startActivity(intent);
    }
    public void launchParentStart(View view){
        Intent intent = new Intent(this, ParentActivity.class);
        startActivity(intent);
    }
}
