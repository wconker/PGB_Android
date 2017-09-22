package com.android.pgb.Fragment.CK;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.pgb.Adapter.FragAdapter;

import com.android.pgb.R;
import com.android.pgb.View.CBarView;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class survey extends Fragment implements View.OnClickListener {

    private ViewPager vpMain;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "招标中", "我的招标", "我的投标", "我的任务"
    };
    private MyPagerAdapter mAdapter;
    private FragAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_survey, container, false);
        new CBarView(getActivity(),new CBarView.OnClickListener(){

        },view);
        mFragments.add(new ZbList());
        mFragments.add(new MyZbList());
        mFragments.add(new MyTbList());
        mFragments.add(new MyTask());
        View decorView = getActivity().getWindow().getDecorView();
        vpMain = (ViewPager) view.findViewById(R.id.E_vp);

        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        vpMain.setAdapter(mAdapter);

        /** indicator圆角色块 */
        SlidingTabLayout tabLayout = (SlidingTabLayout) view.findViewById(R.id.topSlide);

        vpMain.setOffscreenPageLimit(4);
        tabLayout.setViewPager(vpMain);
        vpMain.setCurrentItem(0);
        return view;
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





    @Override
    public void onClick(View v) {

    }
}
