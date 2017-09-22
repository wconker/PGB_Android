package com.android.pgb.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapRegionDecoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.pgb.Activity.Activity_XJ.CreateXJ;
import com.android.pgb.Bean.NewBean;
import com.android.pgb.Bean.XJBean;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.Fragment.XJ.MyXJList;
import com.android.pgb.R;
import com.android.pgb.Utils.Dialog;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.View.CBarView;
import com.android.pgb.alipay.AuthResult;
import com.android.pgb.alipay.PayResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayConfirm extends Activity implements ExChange.CallBackForData {

    ExChange ex;
    @Bind(R.id.xj_bdw)
    TextView xjBdw;
    @Bind(R.id.xj_szcs)
    TextView xjSzcs;
    @Bind(R.id.xj_dx)
    TextView xjDx;
    @Bind(R.id.xj_bz)
    TextView xjBz;
    @Bind(R.id.xj_pay)
    LinearLayout xjPay;
    @Bind(R.id.ck_pay)
    LinearLayout ckPay;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.btn)
    Button btn;

    private int NewXjId = 0;
    private int RE = -1;
    private double HJ = 0;
    private XJBean Myxjbean;
    private String Group;
    private CBarView bar;
    private String OrderType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_confirm);
        ButterKnife.bind(this);

        bar = new CBarView(this, new CBarView.OnClickListener() {
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

        bar.setTitle("订单确认");
        OrderType = getIntent().getStringExtra("type");
        ex = new ExChange(this);
        if (OrderType.equals("询价")) {
            RE = getIntent().getIntExtra("re", 0);
            Group = getIntent().getStringExtra("group");
            String putxjdx = getIntent().getStringExtra("putxjdx");
            selectedPhotos = getIntent().getStringArrayListExtra("img");
            Myxjbean = getIntent().getParcelableExtra("xjbean");
            if (RE == 100) {
                ex.endXj(Myxjbean.getXjid());
            }
            if (!putxjdx.equals("")) {
                Myxjbean.setXjdx(getJsonArr(putxjdx));
            }
            HJ = Myxjbean.getBc() * Myxjbean.getJsbjtj();
            xjBdw.setText(Myxjbean.getXjbdw());
            xjSzcs.setText(Myxjbean.getSzscmc());
            xjBz.setText(Myxjbean.getBz());
            //共3人 ，报酬:￥2300
            String money = "共" + Myxjbean.getJsbjtj() + "人，报酬￥" + HJ;
            Myxjbean.setYxbjs(Myxjbean.getJsbjtj());
            price.setText(money);
            xjDx.setText(Myxjbean.getXjlb() == 0 ? "对外" : Group);
        }
    }

    JSONArray getJsonArr(String str) {
        final String s = str;

        String[] digitGroup = s.split("\\|");

        JSONArray list = new JSONArray();
        for (int t = 0; t < digitGroup.length; t++)
            list.put(digitGroup[t]);

        return list;
    }


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
                        Intent intent = new Intent();
                        intent.putExtra("pay", "success");
                        setResult(9000, intent);
                        finish();
                        Toast.makeText(PayConfirm.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Intent intent = new Intent();
                        intent.putExtra("pay", "fail");
                        setResult(99001, intent);
                        finish();
                        Toast.makeText(PayConfirm.this, "支付失败", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(PayConfirm.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(PayConfirm.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }


    };

    // FIXME: 2017/4/11 业务逻辑
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PAYERROR:
                    com.android.pgb.Utils.Log.MyToast(ex.getMessageInfo(_pay), PayConfirm.this);
                    break;
                case ERROR:
                    com.android.pgb.Utils.Log.MyToast(ex.getMessageInfo(_add), PayConfirm.this);
                    break;
                case PAY:
                    try {
                        JSONObject data = new JSONObject(_pay);
                        final String sinValue = JSONUtils.getString((JSONObject) data.get("data"), "response");
                        if (sinValue.length() > 0) {
                            Runnable payRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(PayConfirm.this);
                                    Map<String, String> result = alipay.payV2(sinValue, true);
                                    Log.i("msp", result.toString());
                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case ADDXJ:
                    JSONObject object = JSONUtils.StringToJSON(_add);
                    try {
                        JSONObject data = (JSONObject) object.get("data");
                        ImageCount = 0;
                        NewXjId = data.getInt("xjid");
                        for (int c = 0; c < selectedPhotos.size(); c++) {
                            Map<String, String> map = new HashMap<>();
                            map.put("userid", ex.userId.toString());
                            map.put("imgtype", "产权证");
                            map.put("fjbs", data.get("fjbs").toString());
                            map.put("xjid", data.get("xjid").toString());
                            map.put("imgname", String.valueOf(c));
                            ex.send(new File(selectedPhotos.get(c)), map);
                        }
                        if (selectedPhotos.size() > 0) {
                            dialog = Dialog.showWaitDialog(PayConfirm.this, "加载中...", false, false);
                        } else {
                            ex.applyAliPay(HJ, (Integer) data.get("xjid"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATAIMG:


                    if (ImageCount == selectedPhotos.size()) {
                        dialog.cancel();
                        if (NewXjId > 0)
                            ex.applyAliPay(HJ, NewXjId);
                    }
                    break;


            }

        }
    };
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private android.app.Dialog dialog;
    private int ImageCount = 0;
    private final int ERROR = 404;
    private XJBean xjBean;
    private String _pay, _add;
    private final int PAY = 300;
    private final int PAYERROR = 351;
    private final int ADDXJ = 201;
    private final int UPDATAIMG = 3335;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @Override
    public void onMessage(String str, String cmd, int code) {
        com.android.pgb.Utils.Log.e("报酬" + str);
        //发起支付
        if (cmd.equals("user.applyLiXjPay")) {
            if (code == 0) {
                _pay = str;
                handler.sendEmptyMessage(PAY);
            } else {
                _pay = str;
                handler.sendEmptyMessage(PAYERROR);
            }
        }
        //添加询价成功
        if (cmd.equals("business.addxj")) {
            _add = str;

            if (code == 0) {
                handler.sendEmptyMessage(ADDXJ);

            } else {

                handler.sendEmptyMessage(ERROR);
            }
        }
        //上传图片信息
        if (cmd.equals("upload_img")) {
            ImageCount++;
            handler.sendEmptyMessage(UPDATAIMG);

        }
        if (cmd.equals("business.endXj")) {

        }

    }

    //点击提交按钮
    @OnClick(R.id.btn)
    public void onViewClicked() {


        ex.addxj(Myxjbean);


    }
}
