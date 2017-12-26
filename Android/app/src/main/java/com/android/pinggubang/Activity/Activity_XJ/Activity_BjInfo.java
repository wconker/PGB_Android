package com.android.pinggubang.Activity.Activity_XJ;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pinggubang.Bean.BJBean;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.Dialog;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.Utils.Log;
import com.android.pinggubang.Utils.Net.network;
import com.android.pinggubang.Utils.PhotoPicker.PhotoT;
import com.android.pinggubang.View.CBarView;
import com.android.pinggubang.View.SquareImage;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_BjInfo extends Activity implements ExChange.CallBackForData {


    private ExChange ex;
    private ImageView tx;
    private TextView bj_mj;
    private android.app.Dialog dialog;
    private PhotoT relativeLayout;
    private TextView xjbdw, bj_dj, bj_zj, bj_jgyj, bj_sj, name, bjsl, title, kcsl;
    private ImageView phoneCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity__bj_info);
        new CBarView(this, new CBarView.OnClickListener() {
            @Override
            public void onLeftClick() {
                super.onLeftClick();
                finish();
            }
        }, null);


    }


    private TextView base_info;

    //防止无限加载
    CountDownTimer countDownTimer = new CountDownTimer(5 * 1000, 1000) {
        @Override
        public void onTick(long l) {
        }

        @Override
        public void onFinish() {

            if (dialog.isShowing()) {
                network.CheckNetWork(Activity_BjInfo.this);
                dialog.setCanceledOnTouchOutside(true);
            }
        }
    };

    private void initView() {
        base_info = (TextView) this.findViewById(R.id.base_info);
        relativeLayout = (PhotoT) this.findViewById(R.id.fj_layout);
        relativeLayout.setActivity(this);
        xjbdw = (TextView) this.findViewById(R.id.xjbdw);
        tx = (ImageView) this.findViewById(R.id.tx);
        phoneCall = (ImageView) this.findViewById(R.id.phonecall);
        bj_dj = (TextView) this.findViewById(R.id.bj_dj);

        bj_mj = (TextView) this.findViewById(R.id.bj_mj);
        bj_zj = (TextView) this.findViewById(R.id.bj_zj);
        kcsl = (TextView) this.findViewById(R.id.kcsl);
        bj_jgyj = (TextView) this.findViewById(R.id.bj_jgyj);

        bj_sj = (TextView) this.findViewById(R.id.bj_sj);
        title = (TextView) this.findViewById(R.id.title_info);
        bjsl = (TextView) this.findViewById(R.id.bjsl);
        name = (TextView) this.findViewById(R.id.name);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ex = new ExChange(this);
        int bjId = getIntent().getIntExtra("bjid", 0);
        String type = getIntent().getStringExtra("type");
        LinearLayout personInfo = (LinearLayout) this.findViewById(R.id.personInfo);
        dialog = Dialog.showWaitDialog(Activity_BjInfo.this, "加载中...", false, false);
        countDownTimer.start();
        initView();

        if (type.equals("info")) {
            ex.getbjInfo(ex.userId, String.valueOf(bjId));
            personInfo.setVisibility(View.VISIBLE);
            base_info.setVisibility(View.VISIBLE);
        } else {
            personInfo.setVisibility(View.GONE);
            base_info.setVisibility(View.GONE);
            ex.getMybjInfo(ex.userId, bjId);
        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dialog.cancel();
            JSONObject obj = null;
            switch (msg.what) {
                case 101:
                    String value = msg.getData().getString("getMybjInfo");

                    try {
                        Dialog.closeDialog(dialog);
                        obj = new JSONObject(value);
                        JSONObject arr = (JSONObject) JSONUtils.getJSONArray(obj, "data").get(0);

                        BJBean bjBean = new BJBean(arr);

                        setBJBean(bjBean);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 102:
                    String value2 = msg.getData().getString("getbjInfo");

                    try {
                        Dialog.closeDialog(dialog);
                        obj = new JSONObject(value2);
                        JSONObject arr = (JSONObject) JSONUtils.getJSONArray(obj, "data").get(0);
                        BJBean bjBean = new BJBean(arr);
                        setBJBean(bjBean);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    };
    private void setBJBean(final BJBean bjBean) {
        kcsl.setText(bjBean.getKcs() + "");
        xjbdw.setText(bjBean.getXjbdw());
        bj_dj.setText(bjBean.getDj());
        bj_zj.setText(bjBean.getZj());
        bj_mj.setText(bjBean.getMj());
        bj_jgyj.setText(bjBean.getBz());
        bj_sj.setText(bjBean.getBjsj());
        phoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber =
                        bjBean.getSjhm();
                if (!phoneNumber.equals("")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    //url:统一资源定位符
                    //uri:统一资源标示符（更广）
                    intent.setData(Uri.parse("tel:" + phoneNumber));
                    //开启系统拨号器
                    startActivity(intent);
                } else {
                    Log.MyToast("未找到手机号码", Activity_BjInfo.this);
                }
            }
        });
        Glide.with(this).load(bjBean.getTx_img()).asBitmap().into(tx);
        title.setText(bjBean.getTitle() + "");
        name.setText(bjBean.getNc());
        if (bjBean.getFj_img() != null) {
            try {

                List<String> pList = new ArrayList<>();
                if (bjBean.getFj_img().get(0).getClass().getName().equals("org.json.JSONArray")) {

                    JSONArray AR = (JSONArray) bjBean.getFj_img().get(0);

                    for (int i = 0; i < AR.length(); i++) {
                        JSONObject obj = (JSONObject) AR.get(i);
                        String url = obj.get("fj_img").toString();
                        pList.add(url);
                    }
                } else {
                    for (int i = 0; i < bjBean.getFj_img().length(); i++) {
                        JSONObject obj = (JSONObject) bjBean.getFj_img().get(i);
                        String url = obj.get("img_url").toString();

                        pList.add(url);

                    }
                }
                relativeLayout.setUrl(pList);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMessage(String str, String cmd, int code) {
        Message msg = Message.obtain();
        Log.e(str);
        if (cmd.equals("business.getMybjInfo")) {

            msg.what = 101;
            Bundle bundle = new Bundle();
            bundle.putString("getMybjInfo", str);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }

        if (cmd.equals("business.getbjInfo")) {
            Log.e(str);
            msg.what = 102;
            Bundle bundle = new Bundle();
            bundle.putString("getbjInfo", str);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    }
}
