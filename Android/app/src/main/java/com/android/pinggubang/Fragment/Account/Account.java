package com.android.pinggubang.Fragment.Account;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.view.menu.MenuAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pinggubang.Activity.Activity_Me.Activity_Account;
import com.android.pinggubang.Activity.Activity_Me.Activity_TX;
import com.android.pinggubang.Bean.BJBean;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.CommonAdapter;
import com.android.pinggubang.Utils.CommonViewHolder;
import com.android.pinggubang.Utils.Constants;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.Utils.Log;
import com.baidu.platform.comapi.map.D;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

import static com.android.pinggubang.R.id.bar;
import static com.android.pinggubang.R.id.pulltorefresh;
import static com.android.pinggubang.R.id.zt;

/**
 * A simple {@link Fragment} subclass.
 */
public class Account extends Fragment implements ExChange.CallBackForData {

    private IWXAPI api;
    private PullToRefreshListView pulltorefresh;
    List<HashMap> list;
    private Adapter adapter;
    private AdapterTk AdapterTk;
    ExChange ex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ex = new ExChange(this);
        list = new ArrayList<>();
        pulltorefresh = (PullToRefreshListView) view.findViewById(R.id.pulltorefresh);
        adapter = new Adapter(getActivity(), list, R.layout.account_items);
        AdapterTk = new AdapterTk(getActivity(), list, R.layout.account_items);
        pulltorefresh.setAdapter(AdapterTk);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    void getData() {
        ex.getMyCzjl();
        ex.getTklist();

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String value ;
            JSONObject data;
            JSONArray array;
            switch (msg.what) {
                case 89:
                    value = msg.getData().getString("getMyCzjl");
                    data = JSONUtils.StringToJSON(value);
                    array = JSONUtils.getArrInJson(data, "data");
                    try {
                        list.clear();
                        for (int i = 0; i < array.length(); i++) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("je", JSONUtils.getString((JSONObject) array.get(i), "czje"));
                            map.put("zt", JSONUtils.getString((JSONObject) array.get(i), "zt"));
                            map.put("sj", JSONUtils.getString((JSONObject) array.get(i), "czsj"));
                            map.put("lx", JSONUtils.getString((JSONObject) array.get(i), "czlx").equals("null") ? "" : JSONUtils.getString((JSONObject) array.get(i), "czlx"));
                            map.put("ms", JSONUtils.getString((JSONObject) array.get(i), "xmmc"));
                            list.add(map);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 119:
                    value = msg.getData().getString("applyWxPay");
                    try {
                        data = (JSONObject) JSONUtils.StringToJSON(value).get("data");
                        PayReq req = new PayReq();
                        req.appId = data.getString("appId");
                        req.sign = data.getString("sign");
                        req.timeStamp = data.getString("timeStamp");
                        req.extData = "app data"; // optional
                        req.packageValue = data.getString("package");
                        req.partnerId = data.getString("partnerid");
                        req.prepayId = data.getString("prepay_id");
                        req.nonceStr = data.getString("nonceStr");
                       api.registerApp(data.getString("appId"));
                      // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                        api.sendReq(req);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.MyToast(e.getMessage(),getActivity());
                    }
                    break;
                case 120:
                    value = msg.getData().getString("getTklist");
                    try {
                        data = JSONUtils.StringToJSON(value);
                        array = JSONUtils.getArrInJson(data, "data");
                            list.clear();
                            for (int i = 0; i < array.length(); i++) {
                                HashMap<String, String> map = new HashMap<>();
                                map.put("je", JSONUtils.getString((JSONObject) array.get(i), "tkje"));
                                map.put("zt", JSONUtils.getString((JSONObject) array.get(i), "zt"));
                                map.put("sj", JSONUtils.getString((JSONObject) array.get(i), "tksj"));
                               // map.put("lx", JSONUtils.getString((JSONObject) array.get(i), "czlx").equals("null") ? "" : JSONUtils.getString((JSONObject) array.get(i), "czlx"));
                                map.put("ms", JSONUtils.getString((JSONObject) array.get(i), "tksm"));
                                list.add(map);
                            }
                        AdapterTk.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    break;

            }
        }
    };

    class Adapter extends CommonAdapter<HashMap> {


        public Adapter(Context context, List<HashMap> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void setViewContent(final CommonViewHolder viewHolder, HashMap hashMap) {
            String je = hashMap.get("je").toString() + "元";
            viewHolder.setText(R.id.acc_time, hashMap.get("sj").toString())
                    .setText(R.id.acc_lb, hashMap.get("lx").toString())
                    .setText(R.id.acc_ms, hashMap.get("ms").toString())
                    .setText(R.id.acc_je, "+" + je);
            TextView textView = viewHolder.getView(R.id.tx);
            textView.setVisibility(View.VISIBLE);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 startActivity(new Intent(getActivity(), Activity_TX.class));
                }
            });
            if (hashMap.get("je").toString().equals("-1")) {

            }
        }
    }
    class AdapterTk extends CommonAdapter<HashMap> {

        public AdapterTk(Context context, List<HashMap> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void setViewContent(final CommonViewHolder viewHolder, HashMap hashMap) {

            String je = hashMap.get("je").toString() + "元";

            viewHolder.setText(R.id.acc_time, hashMap.get("sj").toString())
                  //  .setText(R.id.acc_lb, hashMap.get("lx").toString())
                    .setText(R.id.acc_ms, hashMap.get("ms").toString())
                    .setText(R.id.acc_je, "+" + je);

            TextView textView = viewHolder.getView(R.id.tx);

            if (hashMap.get("je").toString().equals("-1"))
            {
                textView.setVisibility(View.VISIBLE);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(), Activity_TX.class));
                    }
                });
            }
        }
    }
    @Override
    public void onMessage(String str, String cmd, int code) {
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        Log.e(str);
        switch (cmd) {
            case "user.getMyCzjl":
                message.what = 89;
                bundle.putString("getMyCzjl", str);
                message.setData(bundle);
                handler.sendMessage(message);
                break;
            case "user.applyWxPay":
                message.what = 119;
                bundle.putString("applyWxPay", str);
                message.setData(bundle);
                handler.sendMessage(message);
                break;
            case "user.getTklist":
                message.what = 120;
                bundle.putString("getTklist", str);
                message.setData(bundle);
                handler.sendMessage(message);
                break;
        }
    }
}
