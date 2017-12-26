package com.android.pinggubang.Activity.Activity_Job;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.Utils.Log;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Activity_jobInfo extends AppCompatActivity implements ExChange.CallBackForData {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.gsmc)
    TextView gsmc;
    @Bind(R.id.lxdh)
    TextView lxdh;
    @Bind(R.id.qyxx)
    TextView qyxx;
    @Bind(R.id.gzxz)
    TextView gzxz;
    @Bind(R.id.gzdd)
    TextView gzdd;
    @Bind(R.id.zgyq)
    TextView zgyq;
    @Bind(R.id.gwyq)
    TextView gwyq;
    @Bind(R.id.money)
    TextView money;
    @Bind(R.id.bz)
    TextView bz;
    @Bind(R.id.fbsj)
    TextView fbsj;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.gw)
    ScrollView gw;
    @Bind(R.id.qz_xm)
    TextView qzXm;
    @Bind(R.id.qz_lxdh)
    TextView qzLxdh;
    @Bind(R.id.qz_gw)
    TextView qzGw;
    @Bind(R.id.qz_gzxz)
    TextView qzGzxz;
    @Bind(R.id.qz_gzdd)
    TextView qzGzdd;
    @Bind(R.id.qz_yyzg)
    TextView qzYyzg;
    @Bind(R.id.qz_xl)
    TextView qzXl;
    @Bind(R.id.qz_gzjy)
    TextView qzGzjy;
    @Bind(R.id.qz_bz)
    TextView qzBz;
    @Bind(R.id.qz_fbsj)
    TextView qzFbsj;
    @Bind(R.id.qz_xcyq)
    TextView qzXcyq;
    @Bind(R.id.qz)
    ScrollView qz;
    @Bind(R.id.activity_job_info)
    LinearLayout activityJobInfo;

    private ExChange ex;
    private int Id;
    private int type = 0;
    private final int GW = 1;
    private final int QZ = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_info);
        ButterKnife.bind(this);

        type = getIntent().getIntExtra("type", 0);
        Id = getIntent().getIntExtra("MyId", 0);
        ex = new ExChange(this);
        WicthFrom();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    void WicthFrom() {

        if (type == GW) {
            ex.getGwInfo(Id);
            gw.setVisibility(View.VISIBLE);
        }
        if (type == QZ) {
            ex.getRcInfo(Id);
            qz.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == SUCCESS) {
                JSONObject OB = JSONUtils.StringToJSON(_str);
                try {
                    JSONObject data = ((JSONObject) JSONUtils.getJSONArray(OB, "data").get(0));
                    gsmc.setText(data.getString("gsmc"));
                    lxdh.setText(data.getString("lxdh"));
                    gzdd.setText(data.getString("gzdd"));
                    gwyq.setText(data.getString("gwyq"));
                    zgyq.setText(data.getString("zgyq"));
                    qyxx.setText(data.getString("qyyx"));
                    fbsj.setText(data.getString("fbsj"));
                    gzxz.setText(data.getString("gzxz"));
                    bz.setText(data.getString("bz"));
                    money.setText(data.getString("xcbz"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (msg.what == SUCCESS_RC) {
                JSONObject OB = JSONUtils.StringToJSON(_str);
                try {
                    JSONObject data = ((JSONObject) JSONUtils.getJSONArray(OB, "data").get(0));
                    qzXm.setText(data.getString("xm"));
                    qzLxdh.setText(data.getString("lxsj"));
                    qzGw.setText(data.getString("gw"));
                    qzBz.setText(data.getString("bz"));
                    qzFbsj.setText(data.getString("fbsj"));
                    qzGzdd.setText(data.getString("gzdd"));
                    qzGzjy.setText(data.getString("gzjy"));
                    qzGzxz.setText(data.getString("gzxz"));
                    qzXl.setText(data.getString("xl"));
                    qzXcyq.setText(data.getString("xcyq"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    };
    private String _str = "";
    private final int SUCCESS = 200;
    private final int SUCCESS_RC = 201;

    @Override
    public void onMessage(String str, String cmd, int code) {

        if (cmd.equals("business.getGwInfo")) {
            _str = str;
            handler.sendEmptyMessage(SUCCESS);
        }

        if (cmd.equals("business.getRcInfo")) {
            _str = str;
            handler.sendEmptyMessage(SUCCESS_RC);
        }


    }
}
