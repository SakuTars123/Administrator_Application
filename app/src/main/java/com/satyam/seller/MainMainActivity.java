package com.satyam.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainMainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener{

    private static final int HOME_FRAGMENT =0;
    private static final int PAYMENT_FRAGMENT=1;
    private static final int DELIVERY_ORDERS_FRAGMENT=2;
    private static final int ADD_PRODUCTS=4;
    private static final int ADDED_PRODUCTS=5;
    private static final int MY_ACCOUNT=6;


    public static Boolean showCart=false;
    public static DrawerLayout drawer;

    private Toolbar toolbar;

    private FrameLayout frameLayout;
    private int CURRENTFRAGMENT=-1;
    private NavigationView navigationView;

    private ImageView actionbarlogo;
    private TextView fullname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);
         toolbar = findViewById(R.id.toolbar);
        actionbarlogo=findViewById(R.id.actionbarlogo) ;
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setCheckable(true);
        fullname= navigationView.getHeaderView(0).findViewById(R.id.main_full_name);
        fullname.setText(DBqueries.shopname);

        frameLayout=findViewById(R.id.main_framelayout);



        if (showCart) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            gotoFragment("Add Products", new AddProductsFragment(), 4);
        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_app_bar_open_drawer_description, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragment(), HOME_FRAGMENT);
        }

    }


    @Override
    protected void onStart() {
        fullname.setText(DBqueries.shopname);
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(CURRENTFRAGMENT==HOME_FRAGMENT){
                CURRENTFRAGMENT=-1;
                super.onBackPressed();
            }
            else {
                if (showCart) {
                    showCart=false;
                    finish();

                } else {
                    invalidateOptionsMenu();
                    setFragment(new HomeFragment(), HOME_FRAGMENT);
                    navigationView.getMenu().getItem(0).setCheckable(true);

                }
            }

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(CURRENTFRAGMENT==HOME_FRAGMENT)
        {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getMenuInflater().inflate(R.menu.main_main, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.main_search_icon)
        {
            return  true;
        }
        else if (id==R.id.main_notification_action){
            return  true;
        }
        else if (id==R.id.mian_cart){

                gotoFragment("Add Products", new AddProductsFragment(), ADD_PRODUCTS);


            return true;


            /////////////////////////////////////////////////////////////////////////////////check dialogue box above

        } else if(id==android.R.id.home){
            if(showCart){
                showCart = false;
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementsWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);


            int id = item.getItemId();
            if (id == R.id.nav_my_orders) {
                gotoFragment("My Orders", new DeliveryOrderFragment(), DELIVERY_ORDERS_FRAGMENT);
            }  else if (id == R.id.nav_payments) {

                gotoFragment("My Payments", new PaymentFragment(), PAYMENT_FRAGMENT);


            } else if (id == R.id.nav_myAccount) {
                gotoFragment("My Account", new MyAccountFragment(), MY_ACCOUNT);

              } else if (id == R.id.nav_add_products) {
                 gotoFragment("Add Products", new AddProductsFragment(), ADD_PRODUCTS);
              }
           else if (id == R.id.nav_added_products) {
            gotoFragment("My Added Products", new AddedProductsFragment(), ADDED_PRODUCTS);
         }
            else if (id == R.id.nav_signout) {

                FirebaseAuth.getInstance().signOut();
               // DBqueries.cleardata();
                Intent registerintent = new Intent(MainMainActivity.this, RegistrationActivity.class);
                startActivity(registerintent);
                finish();

            }
            drawer.closeDrawer(GravityCompat.START);
            return  true;

        }



    private void gotoFragment(String title, Fragment fragment, int fragmentNo) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        actionbarlogo.setVisibility(View.GONE);
        invalidateOptionsMenu();
        setFragment(fragment,fragmentNo);
        if(fragmentNo==DELIVERY_ORDERS_FRAGMENT){
            navigationView.getMenu().getItem(2).setCheckable(true);
        }
    }


    private void setFragment(Fragment fragment,int fragmentNo) {
        if (fragmentNo != CURRENTFRAGMENT) {
            CURRENTFRAGMENT = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }
    }

}
