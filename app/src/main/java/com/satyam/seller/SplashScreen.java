package com.satyam.seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseUser currentuser;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        firebaseAuth = FirebaseAuth.getInstance();

        SystemClock.sleep(3000);

    }

    @Override
    protected void onStart() {
        super.onStart();

         currentuser = firebaseAuth.getCurrentUser();
        if(currentuser==null){
            Intent loginintent = new Intent(SplashScreen.this,RegistrationActivity.class);
            startActivity(loginintent);
            finish();

        }else{
            Intent mainintent = new Intent(SplashScreen.this,MapsActivity.class);
            startActivity(mainintent);
            finish();
        }
    }

}
