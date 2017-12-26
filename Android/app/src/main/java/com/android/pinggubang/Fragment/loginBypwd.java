package com.android.pinggubang.Fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pinggubang.Activity.Activity_ForgetPwd;
import com.android.pinggubang.Activity.Activity_Login;
import com.android.pinggubang.Activity.Activity_MainCenter;
import com.android.pinggubang.Activity.Activity_Resgister;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.HTTPSUtils;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.Utils.Log;
import com.android.pinggubang.Utils.SharedPreferences.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class loginBypwd extends Fragment implements ExChange.CallBackForData, View.OnClickListener {

    private EditText phone, pwd;
    private ExChange ex;
    private TextView newuser;
    private Button login_bt;
    private final int VERSIONCODE = 400;
    private String _vsersioncode = "";
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    private String version = "";
    private TextView forgetpwd;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ex = new ExChange(this);
        ex.client = HTTPSUtils.client;
        View view = inflater.inflate(R.layout.fragment_login_bypwd, container, false);
        phone = (EditText) view.findViewById(R.id.phone);
        pwd = (EditText) view.findViewById(R.id.pwd);
        login_bt = (Button) view.findViewById(R.id.login_bt);
        newuser = (TextView) view.findViewById(R.id.newuser);
        forgetpwd = (TextView) view.findViewById(R.id.forgetpwd);
        newuser.setOnClickListener(this);
        forgetpwd.setOnClickListener(this);
        login_bt.setOnClickListener(this);
        SharedPreferences sp = SharedPreferencesUtils.getSharedPreferences(getActivity());
        version = sp.getString("version", "");
        if (!version.equals("")) {
            HTTPSUtils.VersionCode = version;
        }

        ex.getVersion();

        //判断是否有这个账号的信息
        if (!sp.getString("userphone", "").equals("")) {
            pwd.setText(sp.getString("userpwd", ""));
            phone.setText(sp.getString("userphone", ""));
        }

        Drawable drawablephone = getResources().getDrawable(R.drawable.icon_phone);
        drawablephone.setBounds(0, 0, 44, 60);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        phone.setCompoundDrawables(drawablephone, null, null, null);//只放左边

        Drawable drawablereyzm = getResources().getDrawable(R.drawable.icon_pwd);
        drawablereyzm.setBounds(0, 0, 44, 60);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        pwd.setCompoundDrawables(drawablereyzm, null, null, null);//只放左边

        return view;

    }


    @Override
    public void onMessage(String str, String cmd, int code) {
        JSONObject data;
        Message message;
        if (cmd.equals("conn.getVersion")) {
            _vsersioncode = str;
            handler.sendEmptyMessage(VERSIONCODE);
        }

        switch (cmd) {
            case "conn.getSyType":
                SharedPreferences.Editor sp1 = SharedPreferencesUtils.getEditor(getActivity());
                sp1.putString("sysType", str);
                sp1.commit();

                break;
            case "":
                _str = str;
                message = Message.obtain();
                message.what = 14;
                handler.sendMessage(message);

                break;

            case "user.Login":
                if (code == 0) {
                    Looper.prepare();
                    Toast.makeText(getActivity(), "登录成功！", Toast.LENGTH_LONG).show();
                    try {
                        data = ex.getDatas(str);

                        HTTPSUtils.PWD = pwd.getText().toString();
                        HTTPSUtils.PHONE = phone.getText().toString();
                        String aa = data.get("sign").toString();
                        //   HTTPSUtils.USERID = Integer.parseInt(data.get("userid").toString());

                        SharedPreferences.Editor sp = SharedPreferencesUtils.getEditor(getActivity());
                        sp.putString("userphone", phone.getText().toString());
                        sp.putString("userpwd", pwd.getText().toString());
                        //这里注释掉是因为返回的东西都没有了
//                        sp.putString("fdcgjsbz", data.get("fdcgjsbz").toString());
//                        sp.putString("userid", data.get("userid").toString());
//                        sp.putString("tdgjsbz", data.get("tdgjsbz").toString());
//                        sp.putString("zcpgsbz", data.get("zcpgsbz").toString());
//                        sp.putString("nc", data.get("nc").toString());
//                        sp.putString("zhye", data.get("zhye").toString());
//                        sp.putString("hpl", data.get("hpl").toString());
//                        sp.putString("jf", data.get("jf").toString());
                        sp.commit();
                        startActivity(new Intent(getActivity(), Activity_MainCenter.class));
                        getActivity().finish();
                        Looper.loop();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                    _str = str;
                    message = Message.obtain();
                    message.what = 13;
                    handler.sendMessage(message);


                }
                break;
        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case VERSIONCODE:
                    JSONObject data;
                    try {
                        data = new JSONObject(_vsersioncode);
                        if (JSONUtils.getInt(data, "code", 100) == 0) {
                            JSONObject arr = (JSONObject) JSONUtils.getJSONArray(data, "data").get(0);
                            HTTPSUtils.VersionCode = arr.getString("bbh");
                            SharedPreferences.Editor sp = SharedPreferencesUtils.getEditor(getActivity());
                            sp.putString("version", arr.getString("bbh"));
                            sp.commit();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 13:
                    Toast.makeText(getActivity(), ex.getMessageInfo(_str), Toast.LENGTH_LONG).show();
                    break;
                case 14:
                    Toast.makeText(getActivity(), "账号不存在！", Toast.LENGTH_LONG).show();
                    break;


            }
        }
    };
    private String _str;

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.newuser:
                startActivity(new Intent(getActivity(), Activity_Resgister.class));
                break;
            case R.id.forgetpwd:
                startActivity(new Intent(getActivity(), Activity_ForgetPwd.class));
                break;
            case R.id.login_bt:
                ex.getSyType("xj");
                if (pwd.getText().toString().equals("") || phone.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "请输入正确的帐号或密码", Toast.LENGTH_SHORT).show();
                } else {
                    ex.Login("", "", pwd.getText().toString(), "", phone.getText().toString());
                }

                break;
        }


    }
}
