package com.example.safeparkingzones;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

/*

All starting map stuff goes on GoogleMapFragment (when the app starts)

For menu click events go to the onNavigationItemSelected method at the bottom of this class.

 */


public class MapsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        //setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        //Create listeners for click events
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //sets menu actions for open and close
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //takes care of menu icon and drawer itself
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Fragment fragment = new GoogleMapFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.map, fragment);
        transaction.commit();

        //TODO: might want to set near and safe as the default start screen???

        //if statement basically checks for whether the app is opened the first time or if it was closed before
//        if(savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.map, new GoogleMapFragment()).commit();
//            navigationView.setCheckedItem(R.id.nav_near_and_safe);
//        }


    }

    //closes or opens menu depending on its state
    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /*
    Here edit each case in the switch statement to edit the icons/actions to be taken once a menu item:
    near and safe, near or safe is clicked.
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()) {
            case R.id.nav_near_and_safe:

                /*
                    insert actions for nav_near_and_safe,

                    if it doesn't work by itself you might need to change the line before break:

                    Before adding it though, you should copy paste GoogleMapFragment class and paste it again
                    with a different name to create another map fragment to display the correct info based on the
                    action

                    Then change the new GoogleMapFragment() part to new NameOfTheNewFragment()



                 */

                getSupportFragmentManager().beginTransaction().replace(R.id.map, new GoogleMapFragment()).commit();

                break;

            case R.id.nav_near:

                /*
                    insert actions for nav_near,

                    if it doesn't work by itself you might need the following line:

                    Before adding it though, you should copy paste GoogleMapFragment class and paste it again
                    with a different name to create another map fragment to display the correct info based on the
                    action

                    Then change the new GoogleMapFragment() part to new NameOfTheNewFragment()

                    getSupportFragmentManager().beginTransaction().replace(R.id.map, new GoogleMapFragment()).commit();

                 */

                break;

            case R.id.nav_safe:

                /*
                    insert actions for nav_safe,

                    if it doesn't work by itself you might need the following line:

                    Before adding it though, you should copy paste GoogleMapFragment class and paste it again
                    with a different name to create another map fragment to display the correct info based on the
                    action

                    Then change the new GoogleMapFragment() part to new NameOfTheNewFragment()

                    getSupportFragmentManager().beginTransaction().replace(R.id.map, new GoogleMapFragment()).commit();

                 */

                break;

            case R.id.nav_share:

                //makes the sharing popup appear
                Toast.makeText(this,"Share",Toast.LENGTH_SHORT).show();

                //TODO: Need to setup share action

                break;

            case R.id.nav_profile:

                //TODO: Setup Profile Fragment look

                getSupportFragmentManager().beginTransaction().replace(R.id.map, new ProfileFragment()).commit();

                break;

            case R.id.nav_settings:

                //TODO: Setup Settings Fragment Look

                getSupportFragmentManager().beginTransaction().replace(R.id.map, new SettingsFragment()).commit();

                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
