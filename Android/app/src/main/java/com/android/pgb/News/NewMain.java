package com.android.pgb.News;

import android.content.Context;
import android.database.Observable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.ViewFindUtils;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;


public class NewMain extends FragmentActivity implements  OnTabSelectListener{
    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "热门头条"
    };
    private NewMain.MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance(title));
        }


        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);
        mAdapter = new NewMain.MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);

        /** indicator圆角色块 */
        com.flyco.tablayout.SlidingTabLayout tabLayout_9 = ViewFindUtils.find(decorView, R.id.tl_9);

        tabLayout_9.setViewPager(vp);

        tabLayout_9.setIndicatorColor(R.color.colorPrimaryDark);

        vp.setCurrentItem(1);

        tabLayout_9.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                Toast.makeText(mContext, "onTabSelect&position--->" + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onTabSelect(int position) {
        Toast.makeText(mContext, "onTabSelect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReselect(int position) {
        Toast.makeText(mContext, "onTabReselect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            return mFragments.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
