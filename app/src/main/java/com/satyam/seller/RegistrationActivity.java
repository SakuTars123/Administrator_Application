package com.satyam.seller;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class RegistrationActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    public static Boolean setsignupfragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        frameLayout = findViewById(R.id.register_framelayout);

        if (setsignupfragment) {
            setsignupfragment=false;
            setFragment(new SignUpFragment());
        } else {

            setFragment(new SignInFragment());
        }



    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
