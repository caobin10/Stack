package com.xiangsheng.jzfp_sc.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 */

public class LfFragmentPagerAdapter extends FragmentPagerAdapter
{
    private List<Fragment> fragments;
    public LfFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }



    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        if(getCount()>0){
            fragment=fragments.get(i<fragments.size()?i:0);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments==null?0:fragments.size();
    }
}
