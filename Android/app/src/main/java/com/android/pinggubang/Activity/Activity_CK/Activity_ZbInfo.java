package com.android.pinggubang.Activity.Activity_CK;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pinggubang.Activity.Activity_XJ.Activity_XjInfo;
import com.android.pinggubang.Bean.ZBBean;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.Dialog;
import com.android.pinggubang.Utils.Image.Photo;
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

public class Activity_ZbInfo extends Activity implements ExChange.CallBackForData, View.OnClickListener {

    private ExChange ex;
    private ZBBean zbBean;
    private PhotoT showIamge;
    private Button submit, ok, canel;
    private TextView zblx_et, bdw_et, szqs_et, xclxr_et, lxdh_et, dkyh_et, pgjg_et,
            bz_et, zbfw_et, yxsj_et, bc_et, yqwcsj_et, yqwcrq_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__zb_info);
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


    }

    @Override
    protected void onResume() {
        super.onResume();
        ex = new ExChange(this);
        int zbId = getIntent().getIntExtra("zbid", 0);
        ex.getZbInfo(zbId);
        dialog = Dialog.showWaitDialog(Activity_ZbInfo.this, "加载中...", false, false);
        countDownTimer.start();
        setControl();
    }

    //防止无限加载
    CountDownTimer countDownTimer = new CountDownTimer(5 * 1000, 1000) {
        @Override
        public void onTick(long l) {
        }

        @Override
        public void onFinish() {

            if (dialog.isShowing()) {
                network.CheckNetWork(Activity_ZbInfo.this);
                dialog.setCanceledOnTouchOutside(true);
            }
        }
    };
    private android.app.Dialog dialog;

    private void setControl() {
        showIamge = (PhotoT) this.findViewById(R.id.showimage);
        showIamge.setActivity(this);
        bdw_et = (TextView) this.findViewById(R.id.bdw_et);
        submit = (Button) this.findViewById(R.id.submit);

        zblx_et = (TextView) this.findViewById(R.id.zblx_et);
        szqs_et = (TextView) this.findViewById(R.id.szqs_et);
        xclxr_et = (TextView) this.findViewById(R.id.xclxr_et);
        lxdh_et = (TextView) this.findViewById(R.id.lxdh_et);
        dkyh_et = (TextView) this.findViewById(R.id.dkyh_et);
        pgjg_et = (TextView) this.findViewById(R.id.pgjg_et);
        bz_et = (TextView) this.findViewById(R.id.bz_et);
        zbfw_et = (TextView) this.findViewById(R.id.zbfw_et);
        yxsj_et = (TextView) this.findViewById(R.id.yxsj_et);
        bc_et = (TextView) this.findViewById(R.id.bc_et);
        yqwcsj_et = (TextView) this.findViewById(R.id.yqwcsj_et);
        yqwcrq_et = (TextView) this.findViewById(R.id.yqwcrq_et);


        submit.setOnClickListener(this);

    }

    private void setData(ZBBean zbBean) {
        if (zbBean.getZt() == 3) {
            ok = (Button) this.findViewById(R.id.ok);
            ok.setOnClickListener(this);
            ok.setVisibility(View.VISIBLE);
            canel = (Button) this.findViewById(R.id.canel);
            canel.setOnClickListener(this);
            canel.setVisibility(View.VISIBLE);

        }
        if (zbBean.getZt() > 2) {
            submit.setVisibility(View.GONE);

        }
        bdw_et.setText(zbBean.getBdw());
        zblx_et.setText(zbBean.getZblb_name());
        szqs_et.setText(zbBean.getSzcsmc());
        xclxr_et.setText(zbBean.getLxr());
        lxdh_et.setText(zbBean.getLxdh());
        dkyh_et.setText(zbBean.getDkyh());
        pgjg_et.setText(zbBean.getPgjg());
        bz_et.setText(zbBean.getXxsm());
        zbfw_et.setText(zbBean.getNbzb());
        yxsj_et.setText(zbBean.getZbyxsjtime());
        bc_et.setText("￥" + zbBean.getBc());
        yqwcsj_et.setText(zbBean.getBgjzsj());
        try {
            List<String> pList = new ArrayList<>();
            for (int i = 0; i < zbBean.getFj().length(); i++) {

//                ImageView view = new SquareImage(this);
//                view.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
//                view.setScaleType(ImageView.ScaleType.FIT_XY);
//                Glide.with(Activity_ZbInfo.this).load((JSONUtils.getString((JSONObject) zbBean.getFj().get(i), "img_url"))).into(view);
                pList.add((JSONUtils.getString((JSONObject) zbBean.getFj().get(i), "img_url")));
            }
            showIamge.setUrl(pList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // FIXME: 2017/3/14 数据处理中心
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 135) {
                finish();
                Log.MyToast(ex.getMessageInfo(_Confirm_kcbg), Activity_ZbInfo.this);
            } else {
                String value = msg.getData().getString("getZbInfo");
                JSONObject data;
                try {
                    data = new JSONObject(value);
                    JSONArray arr = JSONUtils.getJSONArray(data, "data");
                    zbBean = new ZBBean((JSONObject) arr.get(0));
                    setData(zbBean);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    private String _Confirm_kcbg = "";

    @Override
    public void onMessage(String str, String cmd, int code) {
        Log.e(str);
        Message msg = Message.obtain();
        dialog.cancel();
        switch (cmd) {
            case "business.getZbInfo":
                Bundle bundle = new Bundle();
                bundle.putString("getZbInfo", str);
                msg.setData(bundle);
                handler.sendMessage(msg);
                break;
            case "business.Confirm_kcbg":
                _Confirm_kcbg = str;
                handler.sendEmptyMessage(135);
                break;
        }
    }

    private void inputTitleDialog(final int zbid, final int fsjg, String title) {

        final EditText inputServer = new EditText(Activity_ZbInfo.this);
        inputServer.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_ZbInfo.this);
        builder.setTitle(title).setIcon(
                R.drawable.apptalk).setView(inputServer).setNegativeButton(
                "", null);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ex.Confirm_kcbg(zbid, fsjg, inputServer.getText().toString(), 1);
                    }
                });
        builder.show();
    }

    @Override
    public void onClick(View view) {
        int zbId = zbBean.getZbid();
        int vid = view.getId();
        switch (vid) {
            case R.id.submit:
                Intent i = new Intent(Activity_ZbInfo.this, Activity_AddTb.class);
                i.putExtra("zbid", zbId);
                startActivity(i);
                break;
            case R.id.ok:

                inputTitleDialog(zbId, 1, "确认意见");
                break;
            case R.id.canel:

                inputTitleDialog(zbId, -1, "退回意见");
                break;
        }
    }
}