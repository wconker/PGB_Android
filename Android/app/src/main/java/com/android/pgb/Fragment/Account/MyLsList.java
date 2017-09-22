package com.android.pgb.Fragment.Account;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.pgb.Activity.Activity_Me.Activity_Account;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.CommonAdapter;
import com.android.pgb.Utils.CommonViewHolder;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.Log;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyLsList extends Fragment implements ExChange.CallBackForData {
    private PullToRefreshListView pulltorefresh;
    List<HashMap> list;
    private Adapter adapter;
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
        pulltorefresh.setAdapter(adapter);
        getData();
        return view;
    }



    @Override
    public void onResume() {
        super.onResume();

    }

    void getData() {

            ex.getXjlsList();



    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String value = "";
            JSONObject data;
            JSONArray array;
            switch (msg.what) {
                case 88:
                    value = msg.getData().getString("getXjlsList");
                    data = JSONUtils.StringToJSON(value);
                    array = JSONUtils.getArrInJson(data, "data");
                    try {
                        list.clear();
                        for (int i = 0; i < array.length(); i++) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("je", JSONUtils.getString((JSONObject) array.get(i), "je"));
                            map.put("sj", JSONUtils.getString((JSONObject) array.get(i), "sj"));
                            map.put("lx", JSONUtils.getString((JSONObject) array.get(i), "lx"));
                            map.put("ms", JSONUtils.getString((JSONObject) array.get(i), "sm"));
                            list.add(map);
                        }
                        adapter.notifyDataSetChanged();
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
        public void setViewContent(CommonViewHolder viewHolder, HashMap hashMap) {
            Log.e(hashMap.get("ms").toString());

            String type=hashMap.get("ms").toString().equals("xj")?"询价":(hashMap.get("ms").toString().equals("tb")?"投标":(hashMap.get("ms").toString().equals("bj")?"报价":"招标"));
            String sub=hashMap.get("lx").toString().equals("1")?"+":"-";
            String je = sub + hashMap.get("je").toString()+"元";
            viewHolder.setText(R.id.acc_time, hashMap.get("sj").toString())
                    .setText(R.id.acc_lb, type)
                    .setText(R.id.acc_ms, hashMap.get("ms").toString())
                    .setText(R.id.acc_je,je);
        }
    }


    @Override
    public void onMessage(String str, String cmd, int code) {
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        Log.e(str);
        switch (cmd) {

            case "user.getXjlsList":
                message.what = 88;
                bundle.putString("getXjlsList", str);
                message.setData(bundle);
                handler.sendMessage(message);
                break;
        }
    }
}