package com.android.pinggubang.Fragment.XJ;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.pinggubang.Activity.Activity_XJ.Activity_XjInfo;
import com.android.pinggubang.Activity.Activity_XJ.CreateXJ;
import com.android.pinggubang.Adapter.CommAdapter;
import com.android.pinggubang.Bean.XJBean;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.HTTPSUtils;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.Utils.Log;
import com.android.pinggubang.View.floatbutton.CustomFAB;
import com.android.pinggubang.base.BaseFragment;
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
public class XJList extends BaseFragment implements ExChange.CallBackForData {

    private List<XJBean> listBean;
    private ExChange ex;
    private CommAdapter adapter;
    private int pullFlag = 100;
    private int CurrentPage = 1;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xjlist, container, false);

        listBean = new ArrayList<>();
        pullListView = (PullToRefreshListView) view.findViewById(R.id.pulltorefresh);
        adapter = new CommAdapter(getActivity(), listBean, R.layout.enquiry_item);
        pullListView.setAdapter(adapter);

        pullListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Activity_XjInfo.class);
                int xjid = listBean.get(position - 1).getXjid();
                intent.putExtra("xjid", xjid);
                getActivity().startActivity(intent);
            }
        });
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
                CurrentPage = 1;
                ex.getxjList(CurrentPage);
                pullFlag = 0;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                CurrentPage++;
                ex.getxjList(CurrentPage);
                pullFlag = 1;
            }
        });
        final CustomFAB floatButton = (CustomFAB) view.findViewById(R.id.floatButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateXJ.class);
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), floatButton, "share");
                getActivity().startActivity(intent, activityOptionsCompat.toBundle());
            }
        });
        ex = new ExChange(this);
        firstLoad();
        return view;
    }
    Handler timeHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            adapter.notifyDataSetChanged();
            handler.postDelayed(this, 1000);
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        Log.e("我进入询价列表的OnResume里了");
    }

    // FIXME: 2017/3/14 数据处理中心
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == LOGIN) {

                pullListView.onRefreshComplete();

            } else {

                String value = msg.getData().getString("getxjList");
                JSONObject data;
                if (pullFlag == 0) {
                    CurrentPage = 1;
                }
                if (pullFlag != 1) {
                    listBean.clear();
                }
                try {
                    data = new JSONObject(value);
                    JSONArray arr = JSONUtils.getJSONArray(data, "data");
                    XJBean xjBean;
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject temp = (JSONObject) arr.get(i);
                        xjBean = new XJBean(temp);
                        listBean.add(xjBean);
                    }
                    pullListView.onRefreshComplete();
                    adapter.notifyDataSetChanged();
                    timeHandler.post(runnable);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }
    };

    private int Count = 0;

    private final int LOGIN = 9999;

    @Override
    public void onMessage(String str, String cmd, int code) {
        Message msg = Message.obtain();
        Log.e("xjlist" + str);
        switch (cmd) {
            case "business.getxjList":
                Bundle bundle = new Bundle();
                bundle.putString("getxjList", str);
                msg.setData(bundle);
                handler.sendMessage(msg);
                break;
            case "user.Login":

                handler.sendEmptyMessage(LOGIN);
                break;
        }
    }
}
