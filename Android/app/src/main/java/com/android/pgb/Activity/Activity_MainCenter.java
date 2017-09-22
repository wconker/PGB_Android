package com.android.pgb.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.android.pgb.Activity.Activity_CK.CreateKC;
import com.android.pgb.Bean.TabEntity;
import com.android.pgb.Bean.UserInfo;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.BroadCast.NewInfoSerice;
import com.android.pgb.Fragment.CK.MyZbList;
import com.android.pgb.Fragment.Map.map;
import com.android.pgb.Fragment.XJ.enquiry;
import com.android.pgb.Fragment.index;
import com.android.pgb.Fragment.me;
import com.android.pgb.Fragment.CK.survey;
import com.android.pgb.R;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.SharedPreferences.SharedPreferencesUtils;
import com.android.pgb.Utils.ViewFindUtils;
import com.baidu.mapapi.SDKInitializer;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class Activity_MainCenter extends FragmentActivity implements ExChange.CallBackForData {
    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();

    private String[] mTitles = {"首页", "询价", "地图", "勘察", "个人中心"};
    private int[] mIconUnselectIds = {
            R.mipmap.icon_index_gray,
            R.mipmap.icon_ask_gray,
            R.drawable.icon_map_gray,
            R.drawable.icon_task_gray,
            R.drawable.icon_mine_gray};
    private int[] mIconSelectIds = {
            R.mipmap.icon_index,
            R.drawable.xunjia_active,
            R.drawable.icon_map,
            R.drawable.icon_task,
            R.drawable.icon_mine};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private View mDecorView;
    private ExChange ex;
    public CommonTabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main_center);
        ex = new ExChange(this);
        if (SharedPreferencesUtils.readObject(Activity_MainCenter.this, "userinfo") == null)
            ex.getUserinfo();

        mFragments.add(new index());
        mFragments.add(new enquiry());
        mFragments.add(new map());
        mFragments.add(new survey());
        mFragments.add(new me());

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mDecorView = getWindow().getDecorView();
        //        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        /** with ViewPager */
        mTabLayout = ViewFindUtils.find(mDecorView, R.id.tl);


        mTabLayout.setTabData(mTabEntities, this, R.id.container, mFragments);
        //底部设置通知
        //mTabLayout.showDot(2);
        // 启动Service
        startService(new Intent(Activity_MainCenter.this, NewInfoSerice.class));

        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if(position==2)
                {
                    mFragments.get(position).onResume();
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    private NotificationManager myManager = null;
    private Notification myNotification;
    private static final int NOTIFICATION_ID_1 = 1;

    @Override
    protected void onResume() {
        super.onResume();
    }

    private int BackPress = 0;

    @Override
    public void onBackPressed() {

        if (BackPress == 1) {
            android.os.Process.killProcess(android.os.Process.myPid());

        } else {
            com.android.pgb.Utils.Log.MyToast("再按一次退出！", Activity_MainCenter.this);
            BackPress++;
        }

    }

    private String _str = "";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                JSONArray object = JSONUtils.getJSONArray(JSONUtils.StringToJSON(_str), "data");
                Log.e("getUserinfo", "" + object);

                try {
                    UserInfo userInfo = new UserInfo((JSONObject) object.get(0));
                    SharedPreferencesUtils.saveObject(Activity_MainCenter.this, "userinfo", userInfo);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void onMessage(String str, String cmd, int code) {
        if (cmd.equals("user.getUserinfo")) {
            if (code == 0) {
                _str = str;
                Message message = Message.obtain();
                message.what = 100;
                handler.sendMessage(message);

                ex.getDocuments();
            }
        }
        Log.e("Conker", str);
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

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
