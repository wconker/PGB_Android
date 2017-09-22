package com.android.pgb.Activity.Activity_Job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pgb.Activity.Core.SelectCity;
import com.android.pgb.Activity.Core.Select_DXMore;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.View.CBarView;
import com.android.pgb.View.CustomBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_AddZP extends Activity implements ExChange.CallBackForData {

    @Bind(R.id.qyyx)
    EditText qyyx;
    private ExChange ex;
    @Bind(R.id.gsmc)
    EditText gsmc;
    @Bind(R.id.lxdh)
    EditText lxdh;
    @Bind(R.id.zpgw_tv)
    TextView zpgwTv;
    @Bind(R.id.zpgw)
    LinearLayout zpgw;
    @Bind(R.id.gzdd_tv)
    TextView gzddTv;
    @Bind(R.id.gzdd)
    LinearLayout gzdd;
    @Bind(R.id.zgyq_tv)
    TextView zgyqTv;
    @Bind(R.id.zgyq)
    LinearLayout zgyq;
    @Bind(R.id.gwyq)
    EditText gwyq;
    @Bind(R.id.xcbz)
    EditText xcbz;
    @Bind(R.id.gzxz_tv)
    TextView gzxzTv;
    @Bind(R.id.gzxz)
    LinearLayout gzxz;
    @Bind(R.id.bar)
    CustomBar bar;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    @Bind(R.id.bz)
    EditText bz;
    String gwLists = "总经理|总经理助理|副总经理|技术总监|信息技术部经理|总估价师|业务部经理|分公司负责人|部门经理|高级估价师|中级估价师|初级估价师|评估助理|业务员|办公室主任|人事部经理|信息采集员|征收部经理|征收人员|办公文员|估价员|实习生";
    String xlLists = "博士研究生|硕士研究生|本科|专科";
    String zgyqLists = "房估|土估|资估";
    String gzxzLists = "专职|兼职|不限";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_zp);

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
        ex = new ExChange(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 101) {
            zpgwTv.setText(data.getStringExtra("StrValue"));

        }
        if (resultCode == 230) {
            gzddTv.setText(data.getStringExtra("dqmc"));

        }
        if (requestCode == 103) {
            zgyqTv.setText(data.getStringExtra("StrValue"));

        }
        if (requestCode == 104) {
            gzxzTv.setText(data.getStringExtra("StrValue"));
        }
    }

    @OnClick({R.id.zpgw, R.id.gzdd, R.id.zgyq, R.id.gzxz, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zpgw:
                Intent value = new Intent(Activity_AddZP.this, Select_DXMore.class);
                value.putExtra("value", gwLists);
                startActivityForResult(value, 101);

                break;
            case R.id.gzdd:
                Intent value_city = new Intent(Activity_AddZP.this, SelectCity.class);
                startActivityForResult(value_city, 100);
                break;

            case R.id.zgyq:
                Intent value2 = new Intent(Activity_AddZP.this, Select_DXMore.class);
                value2.putExtra("value", zgyqLists);
                startActivityForResult(value2, 103);
                break;
            case R.id.gzxz:
                Intent value3 = new Intent(Activity_AddZP.this, Select_DXMore.class);
                value3.putExtra("value", gzxzLists);
                startActivityForResult(value3, 104);
                break;
            case R.id.btn_submit:

                ex.addGw(gsmc.getText().toString().trim(),
                        lxdh.getText().toString().trim(),
                        zpgwTv.getText().toString().trim(),
                        gzxzTv.getText().toString().trim(),
                        gzddTv.getText().toString().trim(),
                        zgyqTv.getText().toString().trim(),
                        bz.getText().toString().trim(),
                        qyyx.getText().toString().trim(),
                        gwyq.getText().toString().trim(),
                        xcbz.getText().toString().trim());

                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ADDGW:
                    com.android.pgb.Utils.Log.MyToast(ex.getMessageInfo(_str), Activity_AddZP.this);
                    break;
            }
        }
    };

    private final int ADDGW = 233;
    private String _str = "";

    @Override
    public void onMessage(String str, String cmd, int code) {
        switch (cmd) {
            case "business.addGw":

                if (code == 0) {
                    _str = str;
                    handler.sendEmptyMessage(ADDGW);
                }
                break;
        }

    }
}
