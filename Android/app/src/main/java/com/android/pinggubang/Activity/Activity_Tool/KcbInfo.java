package com.android.pinggubang.Activity.Activity_Tool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pinggubang.Activity.Activity_CK.Activity_Record;
import com.android.pinggubang.Bean.KCBean;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.Utils.Log;
import com.android.pinggubang.View.CBarView;
import com.android.pinggubang.alipay.PayResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class KcbInfo extends Activity implements ExChange.CallBackForData {


    @Bind(R.id.zblb_tv)
    TextView zblbTv;
    @Bind(R.id.zblb)
    LinearLayout zblb;
    @Bind(R.id.bdw_et)
    TextView bdwEt;
    @Bind(R.id.bdw)
    LinearLayout bdw;
    @Bind(R.id.kcb_tv)
    TextView kcbTv;
    @Bind(R.id.kcb)
    LinearLayout kcb;
    @Bind(R.id.bz_tv)
    TextView bzTv;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    @Bind(R.id.addkcb)
    Button addkcb;

    private String kcId = "";
    private ExChange ex;
    private JSONArray array;
    private final int OK = 200;
    private final int GETINFO = 266;
    private String _str = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case GETINFO:
                    try {
                        KCBean kcBean = new KCBean((JSONObject) array.get(0));
                        bdwEt.setText(kcBean.getCkdz());
                        zblbTv.setText(kcBean.getCklb_name());
                        bzTv.setText(kcBean.getBz());
                        kcbTv.setText(kcBean.getCkrq());
                        id = Integer.parseInt(kcBean.getId());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case OK:
                    Log.MyToast(ex.getMessageInfo(_str), KcbInfo.this);
                    break;
            }

        }
    };
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcb_info);
        ButterKnife.bind(this);
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
        kcId = getIntent().getStringExtra("kcid");
        ex.getCkbInfo(kcId);
        addkcb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KcbInfo.this, Activity_Record.class);
                i.putExtra("zbid", id);
                i.putExtra("from", "to");
                startActivity(i);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ex.Submit_Ck(id);
            }
        });

    }

    @Override
    public void onMessage(String str, String cmd, int code) {

        if (cmd.equals("business.getCkbInfo")) {
            JSONObject object = JSONUtils.StringToJSON(str);
            array = JSONUtils.getJSONArray(object, "data");
            handler.sendEmptyMessage(GETINFO);
        }

        if (cmd.equals("business.Submit_Ck")) {
            _str = str;
            handler.sendEmptyMessage(OK);
        }


    }
}
