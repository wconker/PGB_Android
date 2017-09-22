package com.android.pgb.Fragment.XJ;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.pgb.R;
import com.android.pgb.View.CBarView;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class enquiry extends Fragment implements View.OnClickListener {


    private ViewPager vpMain;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "询价中", "我的询价", "我的报价"
    };
    private MyPagerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enquiry, container, false);
        new CBarView(getActivity(), new CBarView.OnClickListener() {

        }, view);
        mFragments.add(new XJList());
        mFragments.add(new MyXJList());
        mFragments.add(new MyBJList());

        View decorView = getActivity().getWindow().getDecorView();
        vpMain = (ViewPager) view.findViewById(R.id.E_vp);

        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        vpMain.setAdapter(mAdapter);


        /** indicator圆角色块 */
        SlidingTabLayout tabLayout = (SlidingTabLayout) view.findViewById(R.id.topSlide);
        tabLayout.setViewPager(vpMain);
        vpMain.setCurrentItem(0);
        vpMain.setOffscreenPageLimit(3);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
        return view;
    }
    @Override
    public void onClick(View v) {

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
