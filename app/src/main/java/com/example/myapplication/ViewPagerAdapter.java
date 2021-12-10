package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new starter();
        } else if (position == 1) {
            fragment = new meals();
        }
        else if(position == 2){
            fragment = new soups();
        }
        else if (position == 3){
            fragment = new order();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "starter";
        } else if (position == 1) {
            title = "meals";
        }
        else if (position == 2){
            title = "soups";
        }
        else if (position == 3){
            title = "order";
        }
        return title;
    }
}