package com.cz2006.helloworld.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.adapters.ViewpagerleaderboardAdapter;
import com.cz2006.helloworld.fragments.AlltimeLeaderboardFragment;
import com.cz2006.helloworld.fragments.MonthleaderboardFragment;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class LeaderboardActivity extends AppCompatActivity  {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // Set Activity Title
        setTitle("Leaderboard");


        TabLayout leaderboardtablayout = findViewById(R.id.leaderboardtabLayout);
        TabItem monthTab=findViewById(R.id.tabMonth);
        TabItem Alltimetab=findViewById(R.id.tabAlltime);
        ViewPager leaderboardViewPager= findViewById(R.id.leaderboardviewPager);


       ViewpagerleaderboardAdapter pageAdapter= new ViewpagerleaderboardAdapter(getSupportFragmentManager(),leaderboardtablayout.getTabCount());

       leaderboardViewPager.setAdapter(pageAdapter);

       leaderboardViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(leaderboardtablayout));
       leaderboardtablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(leaderboardViewPager));



    }

    public void setTitle(String title) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.colorWhite));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
        getSupportActionBar().setIcon(R.drawable.ic_back_24dp);
    }
}
