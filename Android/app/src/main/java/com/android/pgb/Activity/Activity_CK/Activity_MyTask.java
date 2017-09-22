package com.android.pgb.Activity.Activity_CK;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.ServiceWorkerClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pgb.Bean.ZBBean;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.Fragment.CK.MyTask;
import com.android.pgb.R;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.PhotoPicker.PhotoT;
import com.android.pgb.View.CBarView;
import com.android.pgb.View.SquareImage;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.Poi;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.utils.L;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Activity_MyTask extends Activity implements ExChange.CallBackForData, View.OnClickListener {

    private Button qd_btn, submit;
    private String LocalTime = "";
    private TextView rw_qdsj;
    private ImageView callphone;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取定位结果
            LocalTime = location.getTime();
            rw_qdsj.setText(location.getTime());
            ex.xcSign(zbId, location.getLongitude(), location.getLatitude(), "");

        }
    };
    private int zbId = 0;
    private ExChange ex;
    private PhotoT rw_fj;
    private LinearLayout kcb;
    private ZBBean zbBean;
    private TextView rw_bdw, rw_zbsm, rw_xclxr, rw_lxdh, rw_dkyh, rw_pgjg, rw_kcmb;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 101) {
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
            if (msg.what == 102) {

                Toast.makeText(Activity_MyTask.this, ex.getMessageInfo(_str), Toast.LENGTH_SHORT).show();
                finish();

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__my_task);
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

    private String _str = "";

    //不写关键字时为friendly 为当前class 和同意package
    void initView() {
        submit = (Button) this.findViewById(R.id.submit);
        callphone = (ImageView) this.findViewById(R.id.callphone);
        rw_qdsj = (TextView) this.findViewById(R.id.rw_qdsj);
        qd_btn = (Button) this.findViewById(R.id.qd_btn);
        rw_fj = (PhotoT) this.findViewById(R.id.rw_fj);
        rw_fj.setActivity(this);
        kcb = (LinearLayout) this.findViewById(R.id.kcb);
        rw_bdw = (TextView) this.findViewById(R.id.rw_bdw);
        rw_zbsm = (TextView) this.findViewById(R.id.rw_zbsm);
        rw_xclxr = (TextView) this.findViewById(R.id.rw_xclxr);
        rw_lxdh = (TextView) this.findViewById(R.id.rw_lxdh);
        rw_dkyh = (TextView) this.findViewById(R.id.rw_dkyh);
        rw_pgjg = (TextView) this.findViewById(R.id.rw_pgjg);
        rw_kcmb = (TextView) this.findViewById(R.id.rw_kcmb);
        qd_btn.setOnClickListener(this);
        submit.setOnClickListener(this);
        callphone.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ex = new ExChange(this);
        initView();
        zbId = getIntent().getIntExtra("zbid", 0);
        ex.getZbInfo(zbId);
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);

    }

    void setData(ZBBean zbBean) {
        if (zbBean.getFj() != null) {
            try {
                JSONArray fj_img = zbBean.getFj();
                List<String> pList = new ArrayList<>();
                for (int i = 0; i < fj_img.length(); i++) {
                    JSONObject obj = (JSONObject) fj_img.get(i);
                    String url = obj.get("img_url").toString();
//                    ImageView imageView = new SquareImage(this);
//                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(150, 150);
//                    parms.setMargins(15, 0, 0, 0);
//                    imageView.setLayoutParams(parms);
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                    Glide.with(this).load(url).override(250, 250).into(imageView);
                    pList.add(url);

                }
                rw_fj.setUrl(pList);
            } catch (Exception e) {

            }
        }

        rw_xclxr.setText(zbBean.getLxr());
        rw_bdw.setText(zbBean.getBdw() + "");
        rw_lxdh.setText(zbBean.getLxdh());
        rw_zbsm.setText(zbBean.getXxsm());
        rw_dkyh.setText(zbBean.getDkyh());
        rw_pgjg.setText(zbBean.getPgjg());
        rw_qdsj.setText(zbBean.getQdsj());
        rw_kcmb.setText(zbBean.getKcbmbmc());
        kcb.setOnClickListener(this);
        if (zbBean.getZt() == 3) {
            submit.setVisibility(View.GONE);
        }

    }

    @Override
    public void onMessage(String str, String cmd, int code) {
        Message msg = Message.obtain();
        com.android.pgb.Utils.Log.e(str);
        switch (cmd) {
            case "business.getZbInfo":
                Bundle bundle = new Bundle();
                bundle.putString("getZbInfo", str);
                msg.setData(bundle);
                msg.what = 101;
                handler.sendMessage(msg);
                break;
            case "business.xcSign":
                com.android.pgb.Utils.Log.e(str);
                break;
            case "business.submitKcjl":
                _str = str;
                handler.sendEmptyMessage(102);
                break;
        }
    }

    // FIXME: 2017/3/27 点击处理
    @Override
    public void onClick(View view) {
        int vId = view.getId();
        switch (vId) {
            case R.id.kcb:
                Intent i = new Intent(Activity_MyTask.this, Activity_Record.class);
                i.putExtra("zbid", zbBean.getZbid());
                i.putExtra("from", "");
                startActivity(i);
                break;
            case R.id.qd_btn:
                mLocationClient.start();
                break;
            case R.id.submit:
                ex.submitKcjl(zbId);
                break;
            case R.id.callphone:
                String phoneNum = rw_lxdh.getText().toString().trim();
                if (isMobileNO(phoneNum)) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    //url:统一资源定位符
                    //uri:统一资源标示符（更广）
                    intent.setData(Uri.parse("tel:" + phoneNum));
                    startActivity(intent);
                }else {
                    com.android.pgb.Utils.Log.MyToast("无效的手机号码", Activity_MyTask.this);
                }
                break;
        }

    }

    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        System.out.println(m.matches() + "---");

        return m.matches();

    }
}
