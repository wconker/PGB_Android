package com.android.pgb.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.pgb.Fragment.loginBypwd;
import com.android.pgb.Fragment.loginByyzm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghui on 2017/1/4.
 */

public class TabsAdapter extends FragmentPagerAdapter {
    private String[] title={"验证码登录","密码登录"};
    private List<Fragment> Fragments = new ArrayList<>();

    public TabsAdapter(FragmentManager fm) {

        super(fm);
        Fragments.add(new loginByyzm());
        Fragments.add(new loginBypwd());

    }


    @Override
    public Fragment getItem(int position) {



        return Fragments.get(position);
    }

    @Override
    public int getCount() {
        return Fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
