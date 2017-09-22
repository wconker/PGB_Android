package com.android.pgb.Activity.Guide;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import com.android.pgb.Activity.Activity_Login;
import com.android.pgb.Activity.Activity_MainCenter;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.HTTPSUtils;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.SharedPreferences.SharedPreferencesUtils;
import org.json.JSONObject;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;


public class Activity_Center extends Activity implements ExChange.CallBackForData {

    private ExChange ex = new ExChange(this, Activity_Center.this);

    private Observer<JSONObject> watcher;
    //三秒后跳转
    CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long l) {
        }
        @Override
        public void onFinish() {
            startActivity(new Intent(Activity_Center.this, Activity_MainCenter.class));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__center);
//
        watcher = new Observer<JSONObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(JSONObject o) {

                System.out.print(o);
                if (JSONUtils.getInt(o, "code", -1) == 0) {
                    countDownTimer.start();
                } else {
                    startActivity(new Intent(Activity_Center.this, Activity_Login.class));
                    finish();
                }

            }
        };
        SharedPreferences sp = SharedPreferencesUtils.getSharedPreferences(this);
        /*
        这里尝试登陆，如果能正常返回就直接跳，如果不能返回就跳到登录页面
         */
        String phone = sp.getString("userphone", "");
        String version = sp.getString("version", "");
        String pwd = sp.getString("userpwd", "");
        if (!version.equals("")) {
            HTTPSUtils.VersionCode = version;
            if (!phone.isEmpty() && !pwd.isEmpty()) {
                ex.Login("", "", pwd, "", phone);
            }
        }
    }


    @Override
    public void onMessage(final String str, String cmd, int code) {

        Observable.just(str)
                .map(new Func1<String, JSONObject>() {
                    @Override
                    public JSONObject call(String s) {
                        return JSONUtils.StringToJSON(str);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread()).subscribe(watcher);


    }
}
