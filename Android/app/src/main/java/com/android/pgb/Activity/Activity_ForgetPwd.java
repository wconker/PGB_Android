package com.android.pgb.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.BroadCast.Parse;
import com.android.pgb.R;
import com.android.pgb.Utils.Log;
import com.android.pgb.Utils.VerificationUtil;
import com.android.pgb.View.CBarView;

import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Activity_ForgetPwd extends Activity implements ExChange.CallBackForData {

    @Bind(R.id.f_phone_et)
    EditText fPhoneEt;
    @Bind(R.id.f_pwd)
    EditText fPwd;
    @Bind(R.id.f_valid_et)
    EditText fValidEt;
    @Bind(R.id.f_getValid_b)
    TextView fGetValidB;
    @Bind(R.id.register_submit_b)
    Button registerSubmitB;

    private int count = 120;
    private CountDownTimer timer = new CountDownTimer(120000, 1000) {
        @Override
        public void onTick(long l) {
            int count2 = count--;
            fGetValidB.setText(count2 + "s");
            fGetValidB.setEnabled(false);
            fGetValidB.setClickable(false);
        }

        @Override
        public void onFinish() {
            fGetValidB.setText("获取验证码");

        }
    };

    private ExChange ex;

    @Override
    protected void onResume() {
        super.onResume();
        ex = new ExChange(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__forget_pwd);
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


        fGetValidB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ex.getVertifyCode(fPhoneEt.getText().toString());


            }
        });
        registerSubmitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!VerificationUtil.isValidTelNumber(fPhoneEt.getText().toString())) {
                    Log.MyToast("请检查手机号码", Activity_ForgetPwd.this);
                } else {
                    ex.UpdatePass(fPhoneEt.getText().toString().trim(), fPwd.getText().toString().trim(), fValidEt.getText().toString().trim());
                }
            }
        });


    }

    private Handler handlerUi = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (what == 200) {
                Toast.makeText(Activity_ForgetPwd.this,
                        ex.getMessageInfo(_str), Toast.LENGTH_SHORT).show();
            }
            if (what == 100) {
                Toast.makeText(Activity_ForgetPwd.this,
                        ex.getMessageInfo(_str), Toast.LENGTH_SHORT).show();
                timer.start();
            }

            if (what == 500) {
                Toast.makeText(Activity_ForgetPwd.this, ex.getMessageInfo(_str), Toast.LENGTH_SHORT).show();
            }
            if (what == 600) {
                Toast.makeText(Activity_ForgetPwd.this, ex.getMessageInfo(_str), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    };

    private String _str = "";

    @Override
    public void onMessage(String str, String cmd, int code) {
        _str = str;
        if (cmd.equals("conn.UpdatePass")) {
            if (code != 0) {
                handlerUi.sendEmptyMessage(500);
            } else {
                handlerUi.sendEmptyMessage(600);
            }
        } else {
            if (code != 0) {

                handlerUi.sendEmptyMessage(200);
            } else {
                //开启倒计时
                handlerUi.sendEmptyMessage(100);

            }
        }


    }
}
