package com.android.pgb.Activity.Activity_CK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.pgb.Activity.Activity_XJ.Activity_BjInfo;
import com.android.pgb.Activity.Activity_XJ.Activity_XjInfo;
import com.android.pgb.Activity.PayConfirm;
import com.android.pgb.Bean.TBBean;
import com.android.pgb.Bean.ZBBean;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.Dialog;
import com.android.pgb.Utils.Image.Photo;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.Log;
import com.android.pgb.Utils.Net.network;
import com.android.pgb.Utils.PhotoPicker.PhotoT;
import com.android.pgb.View.CBarView;
import com.android.pgb.View.SquareImage;
import com.android.pgb.alipay.AuthResult;
import com.android.pgb.alipay.PayResult;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Activity_TbInfo extends Activity implements ExChange.CallBackForData {

    private ExChange ex;
    private TBBean tbBean;
    private ImageView wx_tx;
    private PhotoT fjLayout;
    private Button add_btn;
    private TextView tb_bdw, name, tb_tbsj, tb_zbj, tb_bz, tb_tbj, title_info, bjsl_tv, zbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__tb_info);
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
        int tbId = getIntent().getIntExtra("tbid", 0);
        ex.getTbInfo(tbId);
        dialog = Dialog.showWaitDialog(Activity_TbInfo.this, "加载中...", false, false);
        countDownTimer.start();
        initView();
    }

    private android.app.Dialog dialog;
    //防止无限加载
    CountDownTimer countDownTimer = new CountDownTimer(5 * 1000, 1000) {
        @Override
        public void onTick(long l) {

        }
        @Override
        public void onFinish() {

            if (dialog.isShowing()) {
                network.CheckNetWork(Activity_TbInfo.this);
                dialog.setCanceledOnTouchOutside(true);
            }
        }
    };

    private void initView() {
        fjLayout = (PhotoT) this.findViewById(R.id.tbinfo_fj);
        fjLayout.setActivity(this);
        tb_bdw = (TextView) this.findViewById(R.id.tb_bdw);
        add_btn = (Button) this.findViewById(R.id.add_btn);
        tb_zbj = (TextView) this.findViewById(R.id.tb_zbj);
        tb_bz = (TextView) this.findViewById(R.id.tb_bz);
        tb_tbj = (TextView) this.findViewById(R.id.tb_tbj);
        title_info = (TextView) this.findViewById(R.id.title_info);
        bjsl_tv = (TextView) this.findViewById(R.id.bjsl_tv);
        zbs = (TextView) this.findViewById(R.id.zbs);
        tb_tbsj = (TextView) this.findViewById(R.id.tb_tbsj);
        name = (TextView) this.findViewById(R.id.name);
        wx_tx = (ImageView) this.findViewById(R.id.wx_tx);
    }

    private  final int SDK_PAY_FLAG = 1;
    private  final int SDK_AUTH_FLAG = 2;
    // FIXME: 2017/4/11  支付逻辑
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(Activity_TbInfo.this, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(Activity_TbInfo.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(Activity_TbInfo.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(Activity_TbInfo.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }


    };
    private void setData() {

        JSONObject User = tbBean.getUser();
        tb_bdw.setText(tbBean.getBdw());
        tb_zbj.setText(tbBean.getZbbj().toString() + "");
        tb_tbj.setText(tbBean.getTbj());
        tb_bz.setText(tbBean.getTbsm());
        tb_tbsj.setText(tbBean.getTbsj());
        name.setText(JSONUtils.getString(User, "nc"));
        title_info.setText(JSONUtils.getString(User, "title"));
        bjsl_tv.setText(JSONUtils.getString(User, "bjs"));
        zbs.setText(JSONUtils.getString(User, "zbs"));
        Glide.with(this).load(JSONUtils.getString(User,"tx_img")).into(wx_tx);
        if (tbBean.getZt()==0) {
            add_btn.setVisibility(View.VISIBLE);
            add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Double.valueOf(tbBean.getTbj())
                    ex.applyLiZbPay(Double.valueOf(tbBean.getTbj()),tbBean.getTbid(),tbBean.getZbid());
                }
            });
        }
        if (tbBean.getFj_img() != null) {
            try {
                JSONArray fj_img = tbBean.getFj_img();
                List<String> pList = new ArrayList<>();
                for (int i = 0; i < fj_img.length(); i++) {
                    JSONObject obj = (JSONObject) fj_img.get(i);
                    String url = obj.get("img_url").toString();
                    pList.add(url);
                }
                fjLayout.setUrl(pList);
                fjLayout.setClickable(false);
            } catch (Exception e) {

            }
        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what)
            {
                case PAY:
                    try {
                        JSONObject data = new JSONObject(_pay);

                        if(data.getInt("code")==0){
                        final String sinValue = JSONUtils.getString((JSONObject) data.get("data"), "response");

                        if (sinValue.length() > 0) {
                            Runnable payRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(Activity_TbInfo.this);
                                    Map<String, String> result = alipay.payV2(sinValue, true);
                                    android.util.Log.i("msp", result.toString());
                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };

                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        }

                        }
                        else {
                            Log.MyToast(data.getString("message"),Activity_TbInfo.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 100:
                    String value = msg.getData().getString("getTbInfo");
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(value);
                        JSONObject arr = (JSONObject) JSONUtils.getJSONArray(obj, "data").get(0);
                        tbBean = new TBBean(arr);
                        tbBean.setUser((JSONObject) JSONUtils.getJSONArray(arr, "userinfo").get(0));
                        setData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }


        }
    };
    private  String _pay="";
    private final int PAY = 300;
    @Override
    public void onMessage(String str, String cmd, int code) {
        Message msg = Message.obtain();
      Log.e(str);
        dialog.cancel();
        if (cmd.equals("business.getTbInfo")) {
            Bundle bundle = new Bundle();
            bundle.putString("getTbInfo", str);
            msg.setData(bundle);
            msg.what = 100;
            handler.sendMessage(msg);
        }

        if(cmd.equals("user.applyLiZbPay"))
        {
            _pay=str;
            handler.sendEmptyMessage(PAY);
        }
    }
}
