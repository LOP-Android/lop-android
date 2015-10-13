package com.example.karen.lop_android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by hebi5 on 10/11/2015.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0: return new Page1Fragment();
            case 1: return new Page2Fragment();
            case 2: return new Page3Fragment();
            case 3: return new LORI();
            default: break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
