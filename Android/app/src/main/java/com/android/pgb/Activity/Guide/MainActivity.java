package com.android.pgb.Activity.Guide;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pgb.R;
import com.android.pgb.Utils.Constants;
import com.android.pgb.Utils.Log;
import com.android.pgb.Utils.SharedPreferences.SharedPreferencesUtils;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

public class MainActivity extends Activity   {
    private boolean FirstLoad = true;
    private TextView welcome;
    private Button btn_start;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        welcome = (TextView) this.findViewById(R.id.welcome);
        btn_start = (Button) this.findViewById(R.id.btn_start);
        SharedPreferences sp = SharedPreferencesUtils.getSharedPreferences(this);
        FirstLoad = sp.getBoolean("FirstLoad", true);
        //判断是不是第一次登陆
        if (FirstLoad) {
            //第一次登陆就跳转到引导页面
            SharedPreferences.Editor sp_ = SharedPreferencesUtils.getEditor(this);
            sp_.putBoolean("FirstLoad",
                    false);
            sp_.commit();
            Intent i = new Intent(MainActivity.this,
                    ExampleGuideActivity.class);
            startActivity(i);
            finish();
        } else {
            //非第一次登陆就跳到广告页
            Intent i = new Intent(MainActivity.this,
                    Activity_Center.class);
            startActivity(i);
            finish();
        }

    }




}
