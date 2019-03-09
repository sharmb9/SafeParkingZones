package com.example.safeparkingzonesapp;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sets up action bar to become our toolbar (A.K.A hamburger menu)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Assign drawer_layout
        drawer = findViewById(R.id.drawer_layout);

        //Allows for listening to click events
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Makes hamburger menu button rotate on open and close
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (savedInstanceState == null) {

            //Syncs the Near and Safe Fragment as the starter screen
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new NearAndSafeFragment()).commit();

        }

        navigationView.setCheckedItem(R.id.nav_near_and_safe);


    }

    //Allows for action to occur when item is selected, also denotes what happens when each menu item is clicked
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_near_and_safe:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NearAndSafeFragment()).commit();
                break;

            case R.id.nav_near:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NearFragment()).commit();
                break;

            case R.id.nav_safe:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SafeFragment()).commit();
                break;

            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;

            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                break;

            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    //handles correct opening and closing of our menu
    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
