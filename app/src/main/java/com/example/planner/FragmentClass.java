package com.example.planner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import java.util.ArrayList;
import java.util.List;

public class FragmentClass extends FragmentStatePagerAdapter {
    private  final List<Fragment> fragmentList = new ArrayList<>();
    public FragmentClass(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment frag){

        fragmentList.add(frag);
    }

    @Override
    public Fragment getItem(int pos) {
        return fragmentList.get(pos);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
