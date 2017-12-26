package com.android.pinggubang.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.pinggubang.Activity.Activity_Login;

import com.android.pinggubang.Activity.Activity_Me.Activity_Account;
import com.android.pinggubang.Activity.Activity_Me.Activity_Auth;
import com.android.pinggubang.Activity.Activity_Me.Activity_BindInfo;
import com.android.pinggubang.Activity.Activity_Me.Activity_FastAuth;
import com.android.pinggubang.Activity.Activity_Me.Activity_TX;
import com.android.pinggubang.Activity.Activity_Me.Activity_Userinstruction;
import com.android.pinggubang.Activity.Activity_Me.CompanyMate;
import com.android.pinggubang.Activity.PayConfirm;
import com.android.pinggubang.Activity.T;
import com.android.pinggubang.Bean.UserInfo;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.News.Test;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.CheckUserInfo;
import com.android.pinggubang.Utils.CommonAdapter;
import com.android.pinggubang.Utils.CommonViewHolder;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.Utils.SharedPreferences.SharedPreferencesUtils;
import com.android.pinggubang.Utils.download.DownloadApk;
import com.android.pinggubang.alipay.AuthResult;
import com.android.pinggubang.alipay.PayDemoActivity;
import com.android.pinggubang.alipay.PayResult;
import com.android.pinggubang.alipay.util.OrderInfoUtil2_0;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kyleduo.switchbutton.SwitchButton;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFOptions;
import com.qiyukf.unicorn.api.YSFUserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * A simple {@link Fragment} subclass.
 */

public class me extends Fragment implements ExChange.CallBackForData, View.OnClickListener {
    private LinearLayout smrz, wdbj, CompanyMate, fc, email_bind, td, mycomp_bind, phone_bind, fast_auth, yhxy;
    private GridView gv;
    private Button login_out;
    private TextView zhye, jf, zw, name, dzyx, sjhm, gsmc, fcrzyj, tdrzyj, smrzyj;
    private ImageView tx_img;
    private me.GvAdapter adapter;
    private UserInfo userInfo;
    private ExChange ex;
    private TextView ts_icon;
    private List<Double> list;
    private LinearLayout updateapp;
    private ImageView gs_icon, sj_icon, yx_icon, sm_icon, fc_icon, td_icon;
    private View view;
    private TextView newApp;
    private SwitchButton switchbutton;
    private ImageView Gaosi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ex = new ExChange(this);
        this.view = view;
        //1.注册下载广播接收器
        DownloadApk.registerBroadcast(getActivity());
        //2.删除已存在的Apk
        DownloadApk.removeFile(getActivity());
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ex.getUserinfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DownloadApk.unregisterBroadcast(getActivity());
    }

    void setBdIcon(ImageView view, int bz) {
        if (bz == 1) {
            view.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.success));
        }
        if (bz == -1) {
            view.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.warn));
        }
    }

    void setRzIcon(ImageView view, int bz) {
        if (bz == 1) {
            view.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.wait));
        }
        if (bz == -1) {
            view.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.warn));
        }
        if (bz == 2) {
            view.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.success));
        }
    }

    private void initView(View view) {
        userInfo = (UserInfo) SharedPreferencesUtils.readObject(getActivity(), "userinfo");
        if (userInfo == null) return;
        /*
        UserInfo认证信息
        */
        CheckUserInfo.setUserInfo(userInfo);

        list = new ArrayList<>();
        updateapp = (LinearLayout) view.findViewById(R.id.updateApp);
        list.add(Double.parseDouble(userInfo.getZhye()));
        list.add(Double.parseDouble(userInfo.getSrzhye()));
        list.add((double) (userInfo.getJf()));
        list.add(00.0);
        list.add(00.0);
        list.add(00.0);
        newApp = (TextView) view.findViewById(R.id.newApp);
        ts_icon = (TextView) view.findViewById(R.id.ts_icon);

        if (userInfo.getSq_num() > 0 && userInfo.getAdminSign() == 1) {
            ts_icon.setText(userInfo.getSq_num());
        }
        zw = (TextView) view.findViewById(R.id.zw);
        fcrzyj = (TextView) view.findViewById(R.id.fcrzyj);
        smrzyj = (TextView) view.findViewById(R.id.smrzyj);
        tdrzyj = (TextView) view.findViewById(R.id.tdrzyj);

        fcrzyj.setText(userInfo.getFgrzyj().equals("null") ? "" : userInfo.getFgrzyj());
        smrzyj.setText(userInfo.getSmrzyj().equals("null") ? "" : userInfo.getSmrzyj());
        tdrzyj.setText(userInfo.getTgrzyj().equals("null") ? "" : userInfo.getTgrzyj());
        Gaosi = (ImageView) view.findViewById(R.id.TrainImg);
        switchbutton = (SwitchButton) view.findViewById(R.id.switchbutton);
        CompanyMate = (LinearLayout) view.findViewById(R.id.CompanyMate);
        fast_auth = (LinearLayout) view.findViewById(R.id.fast_auth);
        fast_auth.setOnClickListener(this);
        email_bind = (LinearLayout) view.findViewById(R.id.email_bind);
        yhxy = (LinearLayout) view.findViewById(R.id.yhxy);
        yhxy.setOnClickListener(this);
        email_bind.setOnClickListener(this);
        phone_bind = (LinearLayout) view.findViewById(R.id.phone_bind);
        phone_bind.setOnClickListener(this);
        mycomp_bind = (LinearLayout) view.findViewById(R.id.mycomp_bind);
        mycomp_bind.setOnClickListener(this);
        name = (TextView) view.findViewById(R.id.name);
        gv = (GridView) view.findViewById(R.id.gv);
        adapter = new me.GvAdapter(getActivity(), list, R.layout.gv_layout);

        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                    case 1:
                        Intent it = new Intent(getActivity(), Activity_Account.class);
                        it.putExtra("index", i);
                        startActivity(it);
                        break;
                    case 2:
                        com.android.pinggubang.Utils.Log.MyToast("尽情期待", getActivity());
                    case 3:
                        break;
                    case 4:
                        Intent it2 = new Intent(getActivity(), Activity_TX.class);
                        startActivity(it2);
                        break;
                    case 5:
                        Custom();
                        break;
                }
            }
        });
        switchbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ex.isShow(1);
                } else {
                    ex.isShow(0);
                }
            }
        });
        smrz = (LinearLayout) view.findViewById(R.id.smrz);
        tx_img = (ImageView) view.findViewById(R.id.tx_img);
        tx_img.setOnClickListener(this);
        fc = (LinearLayout) view.findViewById(R.id.fc);
        td = (LinearLayout) view.findViewById(R.id.td);
        login_out = (Button) view.findViewById(R.id.login_out);
        login_out.setOnClickListener(this);
        dzyx = (TextView) view.findViewById(R.id.dzyx);
        gsmc = (TextView) view.findViewById(R.id.gsmc);
        sjhm = (TextView) view.findViewById(R.id.sjhm);
        sjhm.setText(userInfo.getSjhm());
        gsmc.setText(userInfo.getGsmc());
        dzyx.setText(userInfo.getDzyx());
        gs_icon = (ImageView) view.findViewById(R.id.gs_icon);
        setBdIcon(gs_icon, userInfo.getGsmcbdbz());
        yx_icon = (ImageView) view.findViewById(R.id.yx_icon);
        setBdIcon(yx_icon, userInfo.getDzyxjhbz());
        fc_icon = (ImageView) view.findViewById(R.id.fdc_icon);
        setRzIcon(fc_icon, userInfo.getFdcgjsbz());
        sm_icon = (ImageView) view.findViewById(R.id.sm_icon);
        setRzIcon(sm_icon, userInfo.getScrzbz());
        td_icon = (ImageView) view.findViewById(R.id.td_icon);
        setRzIcon(td_icon, userInfo.getTdgjsbz());
        sj_icon = (ImageView) view.findViewById(R.id.sj_icon);
        setBdIcon(sj_icon, userInfo.getSjhmbdbz());
        name.setText(userInfo.getNc());
        smrz.setOnClickListener(this);
        fc.setOnClickListener(this);
        td.setOnClickListener(this);
        zw.setText(userInfo.getTitle());
        CompanyMate.setOnClickListener(this);

        /*头像信息*/
        if (userInfo.getTx_img().equals("null")) {
            Glide.with(this).load(R.drawable.user).asBitmap().into(tx_img);
        } else {
            /*
            把头像路径保存到本地，下次刷新时跟本次对比，如果不同测加载
            */
            if (tx_img.getTag(R.id.tag_tx_img) == null || !tx_img.getTag(R.id.tag_tx_img).equals(userInfo.getTx_img())) {
//                Glide.with(this).load(R.drawable.user).bitmapTransform(new BlurTransformation(getActivity(),23,4)).into(Gaosi); 设置高斯模糊
                Glide.with(getActivity()).load(userInfo.getTx_img()).asBitmap().skipMemoryCache(false).into(tx_img);
                tx_img.setTag(R.id.tag_tx_img, userInfo.getTx_img());
            }
        }
        checkUpdate();
    }

    private void Custom() {
        String title = "评估帮客服窗口";
        /**
         * 设置访客来源，标识访客是从哪个页面发起咨询的，
         * 用于客服了解用户是从什么页面进入三个参数分别为
         * 来源页面的url，来源页面标题，来源页面额外信息（可自由定义）。
         * 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
         */
        ConsultSource source = new ConsultSource("https://sumx.qiyukf.com/", "客服", "这里是评估小娜");
        /**
         * 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable()，
         * 如果返回为false，该接口不会有任何动作
         *
         * @param context 上下文
         * @param title   聊天窗口的标题
         * @param source  咨询的发起来源，包括发起咨询的url，title，描述信息等
         */
        Unicorn.openServiceActivity(getActivity(), title, source);
        YSFOptions ysfOptions = new YSFOptions();
        UICustomization customization = new UICustomization();
        customization.rightAvatar = userInfo.getTx_img();
        customization.rightAvatar = userInfo.getTx_img();
        customization.titleBackgroundResId = R.color.colorPrimary;
        customization.topTipBarTextColor = R.color.white;
        ysfOptions.uiCustomization = customization;
        YSFUserInfo ysfuserInfo = new YSFUserInfo();
        ysfuserInfo.userId = ex.userId + "";
        ysfuserInfo.data = "[{\"key\":\"real_name\", \"value\":\"" + userInfo.getNc() + "\"}, {\"key\":\"mobile_phone\", \"value\":\"" + userInfo.getSjhm() + "\"}," +
                " {\"key\":\"email\", \"value\":\"" + userInfo.getDzyx() + "\"}]";
        Unicorn.setUserInfo(ysfuserInfo);
        Unicorn.updateOptions(ysfOptions);
    }

    /**
     * 软件版本升级
     * 根据version判断是否需要升级版本，
     * 这里为显示在个人中心页面的升级提示信息
     */
    void checkUpdate() {
        SharedPreferences sp = SharedPreferencesUtils.getSharedPreferences(getActivity());
        String version = sp.getString("version", "");
        if (getVersionName(getActivity()).equals(version.substring(version.indexOf("-") + 1, version.length()))) {
            newApp.setText("当前版本" + getVersionName(getActivity()));
        } else {
            newApp.setText("新版本!点击下载！");
            newApp.setTextColor(Color.RED);
            updateapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DownloadApk.removeFile(getActivity());
                    //下载apk
                    DownloadApk.downloadApk(getActivity().getApplicationContext(),
                            "http://imtt.dd.qq.com/16891/E5FB6A45C1F58F5EB06FC94BBD8803A0.apk?fsname=com.android.pinggubang_1.0_1.apk&csr=1bbd",
                            "评估帮更新",
                            "评估帮");
                }
            });
        }
    }



    /*
    升级包设置
    */

    private PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    //版本名
    public String getVersionName(Context context) {

        String VersionName = "";
        try {
            VersionName = getActivity().getPackageManager().getPackageInfo(
                    "com.android.pinggubang", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return VersionName;
    }

    //版本号
    public int getVersionCode(Context context) {

        return getPackageInfo(context).versionCode;
    }

    class GvAdapter extends CommonAdapter<Double> {

        public GvAdapter(Context context, List list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void setViewContent(CommonViewHolder viewHolder, Double str) {

            switch (viewHolder.getPostion()) {
                case 0:
                    viewHolder.getView(R.id.imagetitle).setVisibility(View.GONE);
                    viewHolder.getView(R.id.title).setVisibility(View.VISIBLE);
                    viewHolder.setText(R.id.title, "账户余额").setText(R.id.value, str + "元");
                    break;
                case 1:
                    viewHolder.getView(R.id.imagetitle).setVisibility(View.GONE);
                    viewHolder.getView(R.id.title).setVisibility(View.VISIBLE);
                    viewHolder.setText(R.id.title, "收入账户余额").setText(R.id.value, str + "元");
                    break;
                case 2:
                    viewHolder.getView(R.id.imagetitle).setVisibility(View.GONE);
                    viewHolder.getView(R.id.title).setVisibility(View.VISIBLE);
                    viewHolder.setText(R.id.title, "用户积分").setText(R.id.value, str + "元");
                    break;
                case 3:
                    viewHolder.getView(R.id.title).setVisibility(View.GONE);
                    viewHolder.getView(R.id.imagetitle).setVisibility(View.VISIBLE);
                    viewHolder.setImageResource(R.id.imagetitle, R.drawable.icon_set).setText(R.id.value, "设置");
                    break;
                case 4:
                    viewHolder.getView(R.id.title).setVisibility(View.GONE);
                    viewHolder.getView(R.id.imagetitle).setVisibility(View.VISIBLE);
                    viewHolder.setImageResource(R.id.imagetitle, R.drawable.icon_cash).setText(R.id.value, "提现");
                    break;
                case 5:
                    viewHolder.getView(R.id.title).setVisibility(View.GONE);
                    viewHolder.getView(R.id.imagetitle).setVisibility(View.VISIBLE);
                    viewHolder.setImageResource(R.id.imagetitle, R.drawable.apptalk).setText(R.id.value, "联系客服");
                    break;
            }

        }
    }

    @Override
    public void onClick(View v) {
        int vid = v.getId();
        Intent i = new Intent(getActivity(), Activity_Auth.class);
        Intent ii = new Intent(getActivity(), Activity_BindInfo.class);
        switch (vid) {
            case R.id.tx_img:
                Intent tx = new Intent(getActivity(), Activity_FastAuth.class);
                startActivity(tx);
                break;
            case R.id.smrz:
                if (userInfo.getScrzbz() < 2) {
                    i.putExtra("type", "sm");
                    startActivity(i);
                }
                break;
            case R.id.fc:
                if (userInfo.getFdcgjsbz() < 2) {
                    i.putExtra("type", "fc");
                    startActivity(i);
                }
                break;
            case R.id.td:
                if (userInfo.getTdgjsbz() < 2) {
                    i.putExtra("type", "td");
                    startActivity(i);
                }
                break;
            case R.id.login_out:
                SharedPreferences.Editor sp_ = SharedPreferencesUtils.getEditor(getActivity());
                sp_.putString("userphone", "");
                sp_.putString("yzm", "");
                sp_.remove("userinfo");
                sp_.commit();
                getActivity().startActivity(new Intent(getActivity(), Activity_Login.class));
                getActivity().finish();
                break;
            case R.id.email_bind:
                ii.putExtra("type", "email");
                getActivity().startActivity(ii);
                break;
            case R.id.phone_bind:
                ii.putExtra("type", "phone");
                getActivity().startActivity(ii);
                break;
            case R.id.mycomp_bind:
                ii.putExtra("type", "company");
                getActivity().startActivity(ii);
                break;
            case R.id.fast_auth:
                Intent c = new Intent(getActivity(), Activity_FastAuth.class);
                startActivity(c);
                break;
            case R.id.CompanyMate:
                Intent to = new Intent(getActivity(), CompanyMate.class);
                startActivity(to);
                break;
            case R.id.yhxy:
                Intent to2 = new Intent(getActivity(), Activity_Userinstruction.class);
                startActivity(to2);
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    JSONArray object = JSONUtils.getJSONArray(JSONUtils.StringToJSON(_str), "data");
                    try {
                        UserInfo userInfo = new UserInfo((JSONObject) object.get(0));
                        SharedPreferencesUtils.saveObject(getActivity(), "userinfo", userInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    initView(view);
                    break;
                case 300:
                    break;
                case 202:
                    String Mes = JSONUtils.getString(JSONUtils.StringToJSON(_str), "message");
                    Snackbar.make(switchbutton, Mes, Snackbar.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    private String _str = "";
    private String _pay = "";

    @Override
    public void onMessage(String str, String cmd, int code) {
        com.android.pinggubang.Utils.Log.e(str);
        _str = str;
        if (cmd.equals("user.getUserinfo")) {
            if (code == 0) {

                handler.sendEmptyMessage(200);
            }
        }

        if (cmd.equals("user.isShow")) {
            handler.sendEmptyMessage(202);
        }

    }
}
