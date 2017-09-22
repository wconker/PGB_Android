package com.android.pgb.Fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pgb.Activity.Activity_MainCenter;
import com.android.pgb.Activity.Activity_Resgister;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.HTTPSUtils;
import com.android.pgb.Utils.Log;
import com.android.pgb.Utils.SharedPreferences.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class loginByyzm extends Fragment implements View.OnClickListener, ExChange.CallBackForData {

    private Button login;
    private TextView newuser, register_getValid_b;
    private EditText phone, yzm;
    private ExChange ex;
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    public loginByyzm() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_byyzm, container, false);
        login = (Button) view.findViewById(R.id.login_bt);
        newuser = (TextView) view.findViewById(R.id.newuser);
        register_getValid_b = (TextView) view.findViewById(R.id.register_getValid_b);
        phone = (EditText) view.findViewById(R.id.phone);
        yzm = (EditText) view.findViewById(R.id.register_valid_et);

        ex = new ExChange(this);
        ex.client = HTTPSUtils.client;

        Drawable drawablephone = getResources().getDrawable(R.drawable.icon_phone);
        drawablephone.setBounds(0, 0, 44, 60);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        phone.setCompoundDrawables(drawablephone, null, null, null);//只放左边

        Drawable drawablereyzm = getResources().getDrawable(R.drawable.icon_yzm);
        drawablereyzm.setBounds(0, 0, 44, 60);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        yzm.setCompoundDrawables(drawablereyzm, null, null, null);//只放左边
        register_getValid_b.setOnClickListener(this);
        login.setOnClickListener(this);
        newuser.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.register_getValid_b:
                ex.getVertifyCode(phone.getText().toString());
                break;
            case R.id.login_bt:
                ex.getSyType("xj");
                ex.Login("", "", "", register_getValid_b.getText().toString(), phone.getText().toString());
                // 023399  startActivity(new Intent(getActivity(), Activity_MainCenter.class));
                break;
            case R.id.newuser:
                startActivity(new Intent(getActivity(), Activity_Resgister.class));
                break;
        }
    }
    @Override
    public void onMessage(String str, String cmd, int code) {
        Log.sys(str + cmd + code);
        JSONObject data = null;
        switch (cmd) {
            case "conn.getSyType":
                SharedPreferences.Editor sp = SharedPreferencesUtils.getEditor(getActivity());
                sp.putString("sysType", str);
                sp.commit();
                break;
            case "user.Login":
                if (code == 0) {
                    Looper.prepare();
                    Toast.makeText(getActivity(), "登录成功！", Toast.LENGTH_LONG).show();
                    try {
                        data = ex.getDatas(str);
                        SharedPreferences.Editor sp_ = SharedPreferencesUtils.getEditor(getActivity());
                        HTTPSUtils.USERID = Integer.parseInt(data.get("userid").toString());
                        sp_.putString("userphone", phone.getText().toString());
                        sp_.putString("yzm", register_getValid_b.getText().toString());
                        sp_.putString("fdcgjsbz", data.get("fdcgjsbz").toString());
                        sp_.putString("userid", data.get("userid").toString());
                        sp_.putString("tdgjsbz", data.get("tdgjsbz").toString());
                        sp_.putString("zcpgsbz", data.get("zcpgsbz").toString());
                        sp_.putString("nc", data.get("nc").toString());
                        sp_.putString("zhye", data.get("zhye").toString());
                        sp_.putString("hpl", data.get("hpl").toString());
                        sp_.putString("jf", data.get("jf").toString());
                        sp_.commit();
                        startActivity(new Intent(getActivity(), Activity_MainCenter.class));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), ex.getMessageInfo(str), Toast.LENGTH_LONG).show();
                }
                Looper.loop();
                break;
        }
    }
}
