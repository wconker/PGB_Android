package com.android.pgb.Fragment.Account;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class MyTxList extends Fragment implements ExChange.CallBackForData {

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

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    void getData() {

        ex.getMyTxjl();



    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String value = "";
            JSONObject data;
            JSONArray array;
            switch (msg.what) {
                case 89:
                    value = msg.getData().getString("getMyTxjl");

                    data = JSONUtils.StringToJSON(value);
                    array = JSONUtils.getArrInJson(data, "data");
                    try {
                        list.clear();
                        for (int i = 0; i < array.length(); i++) {
                            HashMap<String, String> map = new HashMap<>();

                            map.put("je", JSONUtils.getString((JSONObject) array.get(i), "txje"));
                            map.put("sj", JSONUtils.getString((JSONObject) array.get(i), "txsj"));
                            map.put("lx", JSONUtils.getString((JSONObject) array.get(i), "txlx"));
                            map.put("ms", JSONUtils.getString((JSONObject) array.get(i), "xmmc"));
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
            viewHolder.setText(R.id.acc_time, hashMap.get("sj").toString())
                    .setText(R.id.acc_lb, "")
                    .setText(R.id.acc_ms, "提现")
                    .setText(R.id.acc_je,"-"+ hashMap.get("je").toString()+"元");
        }
    }


    @Override
    public void onMessage(String str, String cmd, int code) {
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        Log.e(str);
        switch (cmd) {
            case "user.getMyTxjl":
                message.what = 89;
                bundle.putString("getMyTxjl", str);
                message.setData(bundle);
                handler.sendMessage(message);
                break;

        }
    }
}
