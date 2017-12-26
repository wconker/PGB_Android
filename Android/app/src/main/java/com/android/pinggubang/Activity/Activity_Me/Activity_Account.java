package com.android.pinggubang.Activity.Activity_Me;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.android.pinggubang.Fragment.Account.Account;
import com.android.pinggubang.Fragment.Account.MyLsList;
import com.android.pinggubang.Fragment.Account.MyTxList;
import com.android.pinggubang.R;
import com.android.pinggubang.View.CBarView;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

public class Activity_Account extends FragmentActivity {

    public int Index = 0;
    private Activity_Account.MyPagerAdapter adapter;
    public ViewPager vpMain;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"充值", "收支", "提现"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__account);
        new CBarView(this, new CBarView.OnClickListener() {
            @Override
            public void onLeftClick() {
                super.onLeftClick();

                finish();
            }
            @Override
            public void onRightClick() {
                super.onRightClick();
            }
        }, null);

        Index = getIntent().getIntExtra("index", 0);

        mFragments.add(new Account());

        mFragments.add(new MyLsList());

        mFragments.add(new MyTxList());

        vpMain = (ViewPager) this.findViewById(R.id.E_vp);

        adapter = new Activity_Account.MyPagerAdapter(getSupportFragmentManager());
        vpMain.setAdapter(adapter);

        /** indicator圆角色块 */
        SlidingTabLayout tabLayout = (SlidingTabLayout) this.findViewById(R.id.topSlide);
        tabLayout.setViewPager(vpMain);
        vpMain.setCurrentItem(Index);

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
