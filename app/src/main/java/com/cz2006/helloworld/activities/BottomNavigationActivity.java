package com.cz2006.helloworld.activities;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.fragments.HomeFragment;
import com.cz2006.helloworld.fragments.MapFragment;
import com.cz2006.helloworld.fragments.MoreFragment;
import com.cz2006.helloworld.fragments.ScanFragment;
import com.cz2006.helloworld.fragments.TrackFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Represents Bottom Navigation for the application
 * which links to other fragments (Home, Map, Scan, Track, More)
 *
 * @author Rosario Gelli Ann
 *
 */
public class BottomNavigationActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, MapFragment.OnFragmentInteractionListener,
        ScanFragment.OnFragmentInteractionListener, TrackFragment.OnFragmentInteractionListener, MoreFragment.OnFragmentInteractionListener{

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // load the home fragment by default
        setTitle("Home");
        loadFragment(new HomeFragment());
    }

    @Override
    public void onBackPressed() {
        //Prevent User to go back to Log In Activity
        return;
    }

    public void setTitle(String title) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.colorWhite));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            MapFragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("Home");
                    Fragment homeFragment = new HomeFragment();
                    loadFragment(homeFragment);
                    return true;
                case R.id.navigation_map:
                    setTitle("Map");
                    Fragment mapFragment = new MapFragment();
                    loadFragment(mapFragment);
                    return true;
                case R.id.navigation_scan:
                    setTitle("Scan");
                    Fragment scanFragment = new ScanFragment();
                    loadFragment(scanFragment);
                    return true;
                case R.id.navigation_track:
                    setTitle("Track");
                    Fragment trackFragment = new TrackFragment();
                    loadFragment(trackFragment);
                    return true;
                case R.id.navigation_more:
                    setTitle("More");
                    Fragment moreFragment = new MoreFragment();
                    loadFragment(moreFragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
