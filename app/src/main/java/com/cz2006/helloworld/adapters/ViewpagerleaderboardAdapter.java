package com.cz2006.helloworld.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cz2006.helloworld.fragments.AlltimeLeaderboardFragment;
import com.cz2006.helloworld.fragments.MonthleaderboardFragment;
import com.google.android.material.tabs.TabLayout;

public class ViewpagerleaderboardAdapter extends FragmentPagerAdapter {


    //For finger gesture function

    private int numOfTabs;


    public ViewpagerleaderboardAdapter(FragmentManager fm, int numOfTabs) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numOfTabs = numOfTabs;


    }

    @Override

    public Fragment getItem(int position){

        switch(position) {
            case 0:
                return new MonthleaderboardFragment();

            case 1:
                return new AlltimeLeaderboardFragment();


            default:
                return null;
        }
    }


    @Override
    public int getCount(){
        return numOfTabs;
    }

}
