package com.android.pinggubang.Activity.Activity_Me;

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

import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.Utils.Log;
import com.android.pinggubang.View.CBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_TX extends Activity implements ExChange.CallBackForData {

    @Bind(R.id.contact)
    LinearLayout contact;
    @Bind(R.id.money)
    EditText money;
    @Bind(R.id.y_e)
    TextView yE;
    @Bind(R.id.zblx)
    LinearLayout zblx;
    @Bind(R.id.submit)
    Button submit;
    @Bind(R.id.activity_tx)
    LinearLayout activityTx;
    @Bind(R.id.account)
    TextView account;

    private ExChange ex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__tx);
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
        ex.getUserinfo();
        ex.getAccount();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        account.setText("账号" + data.getStringExtra("account"));


    }

    @OnClick({R.id.contact, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contact: //绑定账号
                startActivityForResult(new Intent(Activity_TX.this, Activity_TXBind.class), 101);
                break;
            case R.id.submit: //体现
                if (!money.getText().toString().trim().equals("")) {
                    double getMoney = Double.parseDouble(money.getText().toString().trim());
                    if (getMoney > 0) {
                        ex.ApplyLiTransfer(getMoney, "吴康辉", "15658690695");
                    } else {
                        Log.MyToast("错误，请检查金额", Activity_TX.this);
                    }
                } else {
                    Log.MyToast("错误，请检查金额", Activity_TX.this);
                }
                break;
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case USERINFO:
                    JSONObject OBJ = JSONUtils.StringToJSON(_str);
                    try {
                        JSONObject data = (JSONObject) JSONUtils.getJSONArray(OBJ, "data").get(0);
                        yE.setText("可用余额" + data.getString("srzhye") + "元");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case GETACCOUNT:

                    JSONObject ob = JSONUtils.StringToJSON(_get);

                    try {
                        if (ob.get("data").equals(null)) {
                            return;
                        }
                        if (((JSONArray) ob.get("data")).length() > 0) {

                            JSONObject ob_value = (JSONObject) ((JSONArray) ob.get("data")).get(((JSONArray) ob.get("data")).length() - 1);
                            account.setText(ob_value.getString("zh"));
                            xm = ob_value.getString("xm");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case SUCCESS:
                    Log.MyToast(ex.getMessageInfo(_success), Activity_TX.this);
                    finish();
                    break;
                case ERROR:

                    Log.MyToast(ex.getMessageInfo(_success), Activity_TX.this);

                    break;


            }
        }
    };

    private String xm = "";
    private final int USERINFO = 12222;
    private String _str = "";
    private final int GETACCOUNT = 235;
    private String _get = "";
    private final int SUCCESS = 200;
    private String _success = "";
    private final int ERROR = 404;

    @Override
    public void onMessage(String str, String cmd, int code) {


        if (cmd.equals("user.ApplyLiTransfer")) {
            if (code == 0) {
                _success = str;
                handler.sendEmptyMessage(SUCCESS);
            } else {
                _success = str;
                handler.sendEmptyMessage(ERROR);

            }
        }
        if (cmd.equals("user.getUserinfo")) {
            _str = str;
            handler.sendEmptyMessage(USERINFO);
        }
        if (cmd.equals("user.getAccount")) {
            _get = str;
            handler.sendEmptyMessage(GETACCOUNT);

        }
    }
}
