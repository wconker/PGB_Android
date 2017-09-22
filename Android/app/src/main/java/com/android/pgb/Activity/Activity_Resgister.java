package com.android.pgb.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pgb.Activity.Activity_Me.Activity_Userinstruction;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.BroadCast.Parse;
import com.android.pgb.R;
import com.android.pgb.Utils.HTTPSUtils;
import com.android.pgb.Utils.Log;
import com.android.pgb.Utils.VerificationUtil;
import com.android.pgb.View.CBarView;

import org.json.JSONException;

public class Activity_Resgister extends Activity implements View.OnClickListener, ExChange.CallBackForData {


    private TextView register_getValid_b;
    private EditText register_valid_et;
    private EditText register_password_et_sam;
    private EditText register_password_et;
    private EditText register_phone_et;
    private EditText register_account_et;
    private Button register_submit_b;
    private int time = 120;
    private ExChange ex;
    private TextView usercontrol;
    private String phone, pwd, repwd, nic, yzm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__resgister);
        new CBarView(this, null, null);
        //注册一个网络请求
        ex = new ExChange(this);
        ex.client = HTTPSUtils.client;
        initControl();
    }

    private void initControl() {
        register_submit_b = (Button) this.findViewById(R.id.register_submit_b);
        register_getValid_b = (TextView) this.findViewById(R.id.register_getValid_b);
        usercontrol = (TextView) this.findViewById(R.id.usercontrol);
        usercontrol.setOnClickListener(this);
        register_valid_et = (EditText) this.findViewById(R.id.register_valid_et);
        register_password_et_sam = (EditText) this.findViewById(R.id.register_password_et_sam);
        register_password_et = (EditText) this.findViewById(R.id.register_password_et);
        register_phone_et = (EditText) this.findViewById(R.id.register_phone_et);
        register_account_et = (EditText) this.findViewById(R.id.register_account_et);

        Drawable drawablewpd = getResources().getDrawable(R.drawable.icon_pwd);
        drawablewpd.setBounds(0, 0, 44, 60);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        register_password_et.setCompoundDrawables(drawablewpd, null, null, null);//只放左边

        Drawable drawablerepwd = getResources().getDrawable(R.drawable.icon_pwd);
        drawablerepwd.setBounds(0, 0, 44, 60);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        register_password_et_sam.setCompoundDrawables(drawablerepwd, null, null, null);//只放左边

        Drawable drawablephone = getResources().getDrawable(R.drawable.icon_phone);
        drawablephone.setBounds(0, 0, 44, 60);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        register_phone_et.setCompoundDrawables(drawablephone, null, null, null);//只放左边

        Drawable drawableyzm = getResources().getDrawable(R.drawable.icon_yzm);
        drawableyzm.setBounds(0, 0, 44, 60);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        register_valid_et.setCompoundDrawables(drawableyzm, null, null, null);//只放左边


        Drawable drawablenic = getResources().getDrawable(R.drawable.icon_nic);
        drawablenic.setBounds(0, 0, 44, 60);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        register_account_et.setCompoundDrawables(drawablenic, null, null, null);//只放左边


        register_getValid_b.setOnClickListener(this);
        register_submit_b.setOnClickListener(this);

    }

    // FIXME: 2017/1/6
    @Override
    public void onClick(View v) {
        Integer Id = v.getId();

        switch (Id) {
            case R.id.register_getValid_b:
                ex.getVertifyCode(register_phone_et.getText().toString());
                break;
            case R.id.register_submit_b:
                submit();
                break;
            case R.id.usercontrol:
                Intent to2 = new Intent(Activity_Resgister.this, Activity_Userinstruction.class);
                startActivity(to2);
                break;


        }
    }


    private void submit() {
        nic = register_account_et.getText().toString();
        phone = register_phone_et.getText().toString();
        yzm = register_valid_et.getText().toString();
        repwd = register_password_et_sam.getText().toString();
        pwd = register_password_et.getText().toString();


        if (!VerificationUtil.isValidTelNumber(phone)) {
            Log.MyToast("请检查手机号码", this);
            return;
        }
        if (!repwd.equals(pwd)) {
            Log.MyToast("两次密码不一致", this);
            return;
        }
        ex.Register(phone, nic, pwd, yzm);


    }

    private String _str = "";
    Handler handlerUi = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == 200) {
                Toast.makeText(Activity_Resgister.this,
                        ex.getMessageInfo(_str), Toast.LENGTH_SHORT).show();
            }
            if (what == 100) {
                Toast.makeText(Activity_Resgister.this,
                        ex.getMessageInfo(_str), Toast.LENGTH_SHORT).show();
                register_getValid_b.setEnabled(false);
                handler.postDelayed(runnable, 1000);
            }
            if (what == 300) {
                Toast.makeText(Activity_Resgister.this, ex.getMessageInfo(_str), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    };
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if (time < 1) {
                time = 120;
                register_getValid_b.setText("获取验证码");
                register_getValid_b.setEnabled(true);
                handler.removeCallbacks(runnable);
            } else {
                time--;
                register_getValid_b.setText(time + "秒");
            }
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onMessage(String str, String cmd, int code) {
        try {
            String message = Parse.parseForObj(str).get("message").toString();
            Log.sys(message);
            if (code != 0) {
                _str = str;
                handlerUi.sendEmptyMessage(200);
            } else {
                //开启倒计时
                _str = str;
                handlerUi.sendEmptyMessage(100);

            }
            if (cmd.equals("user.Register") && code == 0) {
                _str = str;
                handlerUi.sendEmptyMessage(300);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
