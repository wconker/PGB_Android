package com.android.pinggubang.Fragment.XJ;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.pinggubang.Activity.SphereOfBusiness;
import com.android.pinggubang.Bean.EnquiryBean;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.HTTPSUtils;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.Utils.Log;
import com.android.pinggubang.View.floatbutton.CustomFAB;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class enquiry_house extends Fragment implements ExChange.CallBackForData {

    private PullToRefreshListView pullListView;

    private CustomFAB floatButton;
    private List<EnquiryBean> listBean;
    private ExChange ex;

    public enquiry_house() {

    }


    @Override
    public void onResume() {
        super.onResume();
        ex.getxjList(1);
        Log.sys("我进入OnResume里了");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.enquiry_house, container, false);

        ex = new ExChange(this);
        ex.client = HTTPSUtils.client;
        Log.sys(this.getClass().getName() + "发送了getxjList");
        listBean = new ArrayList<>();
        floatButton = (CustomFAB) view.findViewById(R.id.floatButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SphereOfBusiness.class));
            }
        });
        pullListView = (PullToRefreshListView) view.findViewById(R.id.pulltorefresh);
        pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {


            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(
                        getActivity(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);

                ex.getxjList(1);
                listBean.clear();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pullListView.onRefreshComplete();
            }


        });
        pullListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });
        return view;
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String value = msg.getData().getString("getxjList");
            Log.sys(value);
            JSONObject obj = null;
            JSONObject temp = null;
            EnquiryBean enquiryBean = null;
            try {
                obj = new JSONObject(value);
                JSONArray arr = JSONUtils.getJSONArray(obj, "data");
                listBean.clear();


                for (int i = 0; i < arr.length(); i++) {
                    temp = (JSONObject) arr.get(i);
                    enquiryBean = new EnquiryBean();
                    enquiryBean.setBc(temp.get("bc").toString());
                    enquiryBean.setE_address(temp.get("xjbdw").toString());
                    enquiryBean.setXjyxsj(temp.get("xjyxsj").toString());
                    enquiryBean.setE_readtime(temp.get("ydcs").toString());
                    enquiryBean.setXjid(temp.get("xjid").toString());
                    listBean.add(enquiryBean);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };


    // FIXME: 2017/1/18  回调信息
    @Override
    public void onMessage(String str, String cmd, int code) {


        Log.sys(cmd);

        switch (cmd) {
            case "business.getxjList":
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("getxjList", str);
                msg.setData(bundle);
                handler.sendMessage(msg);
                break;
        }


    }
}
