package com.android.pgb.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.print.PageRange;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.android.pgb.Bean.UserInfo;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.Fragment.loginBypwd;
import com.android.pgb.Fragment.loginByyzm;
import com.android.pgb.R;
import com.android.pgb.Utils.Dialog;
import com.android.pgb.Utils.HTTPSUtils;
import com.android.pgb.Utils.Image.Photo;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.Net.network;
import com.android.pgb.Utils.SharedPreferences.SharedPreferencesUtils;
import com.android.pgb.View.CBarView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


public class Activity_Login extends FragmentActivity implements ExChange.CallBackForData {

    private Photo photo;
    private RadioButton left;
    private RadioButton right;
    private Fragment loginByyzm;
    private Fragment loginBypwd;
    public ExChange ex;
    private ImageView tx;
    private android.app.Dialog dialog;
    private UserInfo userInfo;
    private String phone;
    private String pwd;
    private String version;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);
        new CBarView(this, null, null);
        //倒计时任务
        timerTask = new TimerTask() {
            @Override
            public void run() {
                t--;
                if (t == 0) {
                    dialog.setCanceledOnTouchOutside(true);
                }
            }
        };
        userInfo = (UserInfo) SharedPreferencesUtils.readObject(Activity_Login.this, "userinfo");
        timer = new Timer();
        //检查网络情况
        network.CheckNetWork(this);
        ex = new ExChange(this);
        //从本地中读取出帐号和密码信息以及版本信息
        //然后判断版本号是否对应
        SharedPreferences sp = SharedPreferencesUtils.getSharedPreferences(this);
        phone = sp.getString("userphone", "");
        pwd = sp.getString("userpwd", "");
        version = sp.getString("version", "");
        if (!version.equals("")) {
            HTTPSUtils.VersionCode = version;
            ExcLogin();
        }
        initView();
    }

    private int t = 5;

    void ExcLogin() {
        if (!phone.isEmpty() && !pwd.isEmpty()) {
            HTTPSUtils.PWD = pwd;
            HTTPSUtils.PHONE = phone;
            ex.Login("", "", pwd, "", phone);
            dialog = Dialog.showWaitDialog(Activity_Login.this, "加载中...", false, false);
            timer.schedule(timerTask, 1000, 1000);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //初始化控件
    //这里包括切换帐号登录和验证码登录两种tab的方法
    //目前已注释掉验证码登录
    private void initView() {
        left = (RadioButton) findViewById(R.id.rb_left);
        right = (RadioButton) findViewById(R.id.rb_right);
        tx = (ImageView) findViewById(R.id.tx);
        left.setOnCheckedChangeListener(lis_check);
        right.setOnCheckedChangeListener(lis_check);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        loginBypwd = new loginBypwd();
        loginByyzm = new loginByyzm();
        ft.add(R.id.fl_content, loginByyzm, loginByyzm.getClass().getSimpleName());
        ft.add(R.id.fl_content, loginBypwd, loginBypwd.getClass().getSimpleName());
        right.isChecked();
        ft.hide(loginByyzm);
        ft.commit();
        if (userInfo != null) {
            if (userInfo.getTx_img().equals("")) {
                Glide.with(this).load(R.drawable.user).asBitmap().into(tx);
            } else {
                Glide.with(this).load(userInfo.getTx_img()).asBitmap().into(tx);
            }
        }
    }

    CompoundButton.OnCheckedChangeListener lis_check = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.rb_left:
                    // host(isChecked);
                    break;
                case R.id.rb_right:
                    //  start(isChecked);
                    break;
                default:
                    break;
            }
        }

    };

    private void start(boolean isChecked) {

        if (isChecked) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.show(loginBypwd);
            ft.hide(loginByyzm);
            ft.commit();
        }
    }

    private void host(boolean isChecked) {
        if (isChecked) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.show(loginByyzm);
            ft.hide(loginBypwd);
            ft.commit();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 9999:
                    dialog.cancel();
                    com.android.pgb.Utils.Log.MyToast("账户不存在，请前往注册", Activity_Login.this);
                    break;
                case 99995:
                    com.android.pgb.Utils.Log.MyToast(ex.getMessageInfo(_str), Activity_Login.this);
                    dialog.cancel();
                    startActivity(new Intent(Activity_Login.this, Activity_MainCenter.class));
                    finish();
                    break;
            }
        }
    };

    String _str = "";

    @Override
    public void onMessage(String str, String cmd, int code) {
        Log.e("Conker",str);
        if (code == 9999) {
            handler.sendEmptyMessage(9999);
        }
        if (cmd.equals("user.Login")) {
            if (code == 0) {
                _str = str;
                handler.sendEmptyMessage(99995);
            }
        }


    }
}
