package com.hostitwise.pokemommas;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.SignInButton;

public class LoginActivity extends AppCompatActivity implements LoginChooser.OnFragmentInteractionListener, LoginGeneric.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    @Override
    public void onLoginChooserFragmentInteraction(Uri uri){

    }

    @Override
    public void onLoginGenericFragmentInteraction(Uri uri){

    }

    public void googleSignIn(View view){

    }

    public void switchLogins(View view){
        Fragment fragment = new LoginGeneric();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_location, fragment);
        transaction.commit();
    }
}
