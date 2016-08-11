package com.hostitwise.pokemommas;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements LoginChooser.OnFragmentInteractionListener, LoginGeneric.OnFragmentInteractionListener {

    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        //logged in
        if (isLoggedIn()) {
            //Do something
        }

    }

    private boolean isLoggedIn(){
        if (auth.getCurrentUser() != null) {
            return true;
        }
        return false;
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
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.login_chooser_id, new LoginGeneric()).addToBackStack(null);
        transaction.commit();

    }

    public void loginGeneric(View view){
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                inputPassword.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    public void forgotPasswordGeneric(View view){
        //startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
    }

    public void createAccountGeneric(View view){
        //startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }
}
