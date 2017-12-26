package com.android.pinggubang.Activity.Activity_Job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pinggubang.Activity.Core.SelectCity;
import com.android.pinggubang.Activity.Core.Select_DXMore;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.View.CBarView;

public class Activity_AddQZ extends Activity implements ExChange.CallBackForData, View.OnClickListener {

    private LinearLayout qz_dd, qz_xz, qz_xl, qz_zw, qz_yyzg;
    private TextView qz_dd_tv, qz_zw_tv, qz_xz_tv, qz_xl_tv, qz_yyzg_tv;
    private ExChange ex;
    private EditText xm, qz_dh, qz_jy, qz_yx, qz_xc, qz_bz;
    private Button btn_submit;
    private String xz = "专职|兼职|不限";
    private String yyzg = "房地产估价师|土地估价师|资产评估师|估价员|无资格";
    private String X_li_ = "博士研究生|硕士研究生|本科|专科";
    private String S_zw = "总经理|总经理助理|副总经理|技术总监|信息技术部经理|总估价师|业务部经理|分公司负责人|部门经理|高级估价师|中级估价师|初级估价师|评估助理|业务员|办公室主任|人事部经理|信息采集员|征收部经理|征收人员|办公文员|估价员|实习生";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add_qz);
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
        InitView();
    }


    void InitView() {
        xm = (EditText) this.findViewById(R.id.qz_xm);
        qz_dh = (EditText) this.findViewById(R.id.qz_dh);
        qz_jy = (EditText) this.findViewById(R.id.qz_jy);
        qz_xc = (EditText) this.findViewById(R.id.qz_xc);
        qz_bz = (EditText) this.findViewById(R.id.qz_bz);
        qz_yx = (EditText) this.findViewById(R.id.qz_yx);
        qz_dd_tv = (TextView) this.findViewById(R.id.qz_dd_tv);
        qz_zw_tv = (TextView) this.findViewById(R.id.qz_zw_tv);
        qz_xz_tv = (TextView) this.findViewById(R.id.qz_xz_tv);
        qz_xl_tv = (TextView) this.findViewById(R.id.qz_xl_tv);
        qz_yyzg_tv = (TextView) this.findViewById(R.id.qz_yyzg_tv);
        btn_submit = (Button) this.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        qz_dd = (LinearLayout) this.findViewById(R.id.qz_dd);
        qz_dd.setOnClickListener(this);
        qz_yyzg = (LinearLayout) this.findViewById(R.id.qz_yyzg);
        qz_yyzg.setOnClickListener(this);
        qz_zw = (LinearLayout) this.findViewById(R.id.qz_zw);
        qz_zw.setOnClickListener(this);
        qz_xl = (LinearLayout) this.findViewById(R.id.qz_xl);
        qz_xl.setOnClickListener(this);
        qz_xz = (LinearLayout) this.findViewById(R.id.qz_xz);
        qz_xz.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data == null) {
            return;
        }

        if (resultCode == 230)
        {
            qz_dd_tv.setText(data.getStringExtra("dqmc"));
        }

        if (requestCode == 101) {
            qz_zw_tv.setText(data.getStringExtra("StrValue"));

        }
        if (requestCode == 102) {
            qz_xz_tv.setText(data.getStringExtra("StrValue"));

        }
        if (requestCode == 103) {
            qz_xl_tv.setText(data.getStringExtra("StrValue"));

        }
        if (requestCode == 104) {
            qz_yyzg_tv.setText(data.getStringExtra("StrValue"));
        }

    }

    @Override
    public void onClick(View view) {

        int vid = view.getId();
        switch (vid) {
            case R.id.qz_dd:
                Intent value_city = new Intent(Activity_AddQZ.this, SelectCity.class);
                startActivityForResult(value_city,100);
                break;
            case R.id.qz_zw:

                Intent value = new Intent(Activity_AddQZ.this, Select_DXMore.class);
                value.putExtra("value", S_zw);
                startActivityForResult(value, 101);

                break;
            case R.id.qz_xz:

                Intent value1 = new Intent(Activity_AddQZ.this, Select_DXMore.class);
                value1.putExtra("value", xz);
                startActivityForResult(value1, 102);

                break;
            case R.id.qz_xl:

                Intent value2 = new Intent(Activity_AddQZ.this, Select_DXMore.class);
                value2.putExtra("value", X_li_);
                startActivityForResult(value2, 103);

                break;
            case R.id.qz_yyzg:

                Intent value3 = new Intent(Activity_AddQZ.this, Select_DXMore.class);
                value3.putExtra("value", yyzg);
                startActivityForResult(value3, 104);

                break;
            case R.id.btn_submit:


                ex.addRc(xm.getText().toString().trim(),
                        qz_dh.getText().toString().trim(),
                        qz_zw_tv.getText().toString().trim(),
                        qz_xz_tv.getText().toString().trim(),
                        qz_dd_tv.getText().toString().trim(),
                        qz_yx.getText().toString().trim(),
                        qz_xl_tv.getText().toString().trim(),
                        qz_jy.getText().toString().trim(),
                        qz_bz.getText().toString().trim(),
                        qz_xc.getText().toString().trim(),
                        qz_yyzg_tv.getText().toString().trim()
                );


                break;
        }

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 300) {
                 com.android.pinggubang.Utils.Log.MyToast(ex.getMessageInfo(_str), Activity_AddQZ.this);
                finish();
            }
        }
    };
    private String _str;

    @Override
    public void onMessage(String str, String cmd, int code) {


        if (cmd.equals("business.addRc")) {
            _str = str;
            handler.sendEmptyMessage(300);
        }

    }
}
