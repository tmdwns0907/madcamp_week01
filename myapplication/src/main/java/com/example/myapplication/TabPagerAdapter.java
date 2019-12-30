package com.example.myapplication;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.fragments.PageOneFragment;
import com.example.myapplication.fragments.PageThreeFragment;
import com.example.myapplication.fragments.PageTwoFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }
    @Override
    public Fragment getItem(int position){
        switch(position){
            case 0:
                PageOneFragment pageOneFragment = new PageOneFragment();
                return pageOneFragment;
            case 1:
                PageTwoFragment pageTwoFragment = new PageTwoFragment();
                return pageTwoFragment;
            case 2:
                PageThreeFragment pageThrFragment = new PageThreeFragment();
                return pageThrFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount(){
        return tabCount;
    }
}
