package com.hostitwise.pokemommas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * There will be a stored preference file (internally just an xml file) that stores user preferences for this activity.
 * Once the user logs into either child or parent, it should have a boolean indicating if they are a child or a parent
 * This persists until the user hits logout.
 * Based on this stored boolean, it will either open directly to the child intent, parent intent, or this launch screen.
 */
public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME="UserPreferenceFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        setContentView(R.layout.activity_main);

    }

    private boolean preferenceExists(String preferenceFlag){
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        if(settings.getString(preferenceFlag,null)==null){
            return false;
        }
        return true;
    }
    //return null if preference doesn't exist.
    private String getPreferenceString(String preferenceFlag){
        if(!preferenceExists(preferenceFlag)){
            return null;
        }
        else{
            return getPreferences(MODE_PRIVATE).getString((preferenceFlag),null);
        }
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
