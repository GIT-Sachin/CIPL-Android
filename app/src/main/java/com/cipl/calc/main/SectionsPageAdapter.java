package com.cipl.calc.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sachin on 30/07/17.
 * This class adds the fragments to the activity.
 */

class SectionsPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> fragmentNames = new ArrayList<>();

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragmemt(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentNames.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentNames.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
