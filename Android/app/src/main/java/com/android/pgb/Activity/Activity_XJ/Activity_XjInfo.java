package com.android.pgb.Activity.Activity_XJ;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.pgb.Bean.XJBean;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.Dialog;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.Log;
import com.android.pgb.Utils.Net.network;
import com.android.pgb.Utils.PhotoPicker.PhotoT;
import com.android.pgb.View.CBarView;
import com.android.pgb.box.QuoteState;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_XjInfo extends Activity implements ExChange.CallBackForData {
    private ExChange ex;
    private int  xjid;
    private XJBean xjBean;
    private Button submit;
    private PhotoT fj_layout;
    private android.app.Dialog dialog;
    private TextView xjbdw, xjlx, szcsmc, bc, bz, nbxj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__xj_info);
        new CBarView(this, new CBarView.OnClickListener() {
            @Override
            public void onLeftClick() {
                super.onLeftClick();
                finish();
            }
        }, null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ex = new ExChange(this);
        Intent I = getIntent();
        xjid = I.getIntExtra("xjid", 0);
        ex.getxjInfo(ex.userId, xjid);
        dialog = Dialog.showWaitDialog(Activity_XjInfo.this, "加载中...", false, false);
        countDownTimer.start();
        InitView();
    }

    //防止无限加载
    CountDownTimer countDownTimer = new CountDownTimer(5*1000, 1000) {
        @Override
        public void onTick(long l) {
        }

        @Override
        public void onFinish() {

            if (dialog.isShowing()) {
                network.CheckNetWork(Activity_XjInfo.this);
                dialog.setCanceledOnTouchOutside(true);
            }
        }
    };

    private void InitView() {
        fj_layout = (PhotoT) this.findViewById(R.id.fj_layout);
        fj_layout.setActivity(this);
        submit = (Button) this.findViewById(R.id.submit);
        nbxj = (TextView) this.findViewById(R.id.nbxj);
        xjbdw = (TextView) this.findViewById(R.id.xjbdw);
        xjlx = (TextView) this.findViewById(R.id.xjlx);
        szcsmc = (TextView) this.findViewById(R.id.szcsmc);
        bz = (TextView) this.findViewById(R.id.bz);
        bc = (TextView) this.findViewById(R.id.bc);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_XjInfo.this, Activity_addBJ.class);
                intent.putExtra("mj", xjBean.getMj());
                intent.putExtra("xjid", xjBean.getXjid());
                intent.putExtra("xjlb", xjBean.getXjlb());
                intent.putExtra("wydz", xjBean.getXjbdw());
                startActivity(intent);
            }
        });



        //是否显示我要报价按钮
        if(getIntent().getBooleanExtra("my",true))
        {
            submit.setVisibility(View.VISIBLE);

        }else {
            submit.setVisibility(View.GONE);

        }
    }

    private void setBJBean(XJBean xjBean) {
        xjbdw.setText(xjBean.getXjbdw());
        xjlx.setText(xjBean.getWylx());
        szcsmc.setText(xjBean.getSzscmc());
        bz.setText(xjBean.getBz());
        bc.setText(xjBean.getBc()/0.85+"元");

        String XJType = "";
        try {

            //内部询价里面的数据是否为空
            //如果为空则判定为外部询价
            if (xjBean.getNb_list() != null) {
                for (int p = 0; p < xjBean.getNb_list().length(); p++) {
                    XJType += ((JSONObject)((JSONArray)xjBean.getNb_list().get(p)).get(0)).get("xm") + "、";
                }
            }else {
                XJType="外部询价";
            }

            //显示图片信息
            List<String> pList = new ArrayList<>();
            for (int i = 0; i < xjBean.getCqz_img().length(); i++) {
                pList.add(JSONUtils.getString((JSONObject) xjBean.getCqz_img().get(i),
                        "img_url"));
            }
            fj_layout.setUrl(pList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        nbxj.setText(XJType);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String value = msg.getData().getString("getxjInfo");
            JSONObject obj = null;
            JSONObject temp = null;
            try {
                Dialog.closeDialog(dialog);
                obj = new JSONObject(value);
                JSONObject arr = (JSONObject) JSONUtils.getJSONArray(obj, "data").get(0);
                xjBean = new XJBean(arr);
                setBJBean(xjBean);
                if(xjBean.getZt()== QuoteState.XJIng.value)
                {
                    submit.setVisibility(View.VISIBLE);
                }else {
                    submit.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onMessage(String str, String cmd, int code) {
        android.util.Log.e("4eeeee",str);
        if (cmd.equals("business.getxjInfo")) {
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString("getxjInfo", str);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    }
}
