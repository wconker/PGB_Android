package com.android.pgb.Fragment.XJ;


import android.content.Context;
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

import com.android.pgb.Activity.Activity_XJ.Activity_BjInfo;
import com.android.pgb.Activity.Activity_XJ.CreateXJ;
import com.android.pgb.Bean.BJBean;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.CommonAdapter;
import com.android.pgb.Utils.CommonViewHolder;
import com.android.pgb.Utils.HTTPSUtils;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.Log;
import com.android.pgb.View.floatbutton.CustomFAB;
import com.android.pgb.base.BaseFragment;
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
public class MyBJList extends BaseFragment implements ExChange.CallBackForData {
    private List<BJBean> listBean;
    private ExChange ex;
    private BJAdapter adapter;
    private int pullFlag = 100;
    private int CurrentPage = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bjlist, container, false);
        final CustomFAB floatButton = (CustomFAB) view.findViewById(R.id.floatButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateXJ.class);
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), floatButton, "share");
                getActivity().startActivity(intent, activityOptionsCompat.toBundle());
            }
        });
        pullListView = (PullToRefreshListView) view.findViewById(R.id.pulltorefresh);
        listBean = new ArrayList<>();
        adapter = new BJAdapter(getActivity(), listBean, R.layout.fragment_mybjlist_item);
        pullListView.setAdapter(adapter);
        setPullListView();
        ex = new ExChange(this);
        return view;
    }

    private void setPullListView() {
        pullListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Activity_BjInfo.class);
                int bjId = listBean.get(position - 1).getBjid();
                intent.putExtra("type", "my");
                intent.putExtra("bjid", bjId);
                startActivity(intent);
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
                pullFlag = 0;
                ex.getMybjList(CurrentPage);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pullFlag = 1;
                CurrentPage++;
                ex.getMybjList(CurrentPage);
            }
        });
        firstLoad();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private final int LOGIN = 9999;

    //数据处理中心
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    String value = msg.getData().getString("getMybjList");
                    BJBean bjBean;
                    if (pullFlag == 0) {
                        CurrentPage = 1;
                    } else {
                        listBean.clear();
                    }
                    try {
                        JSONObject obj = new JSONObject(value);
                        JSONArray arr = JSONUtils.getJSONArray(obj, "data");
                        for (int c = 0; c < arr.length(); c++) {
                            bjBean = new BJBean((JSONObject) arr.get(c));
                            listBean.add(bjBean);
                        }
                        pullListView.onRefreshComplete();
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case LOGIN:
                    pullListView.onRefreshComplete();
                    break;

            }


        }
    };


    @Override
    public void onMessage(String str, String cmd, int code) {

        Message msg = Message.obtain();
        switch (cmd) {
            case "business.getMybjList":
                Bundle bundle = new Bundle();
                bundle.putString("getMybjList", str);
                msg.what = 100;
                msg.setData(bundle);
                handler.sendMessage(msg);
                break;
            case "user.Login":
                handler.sendEmptyMessage(LOGIN);
                break;
        }


    }


    class BJAdapter extends CommonAdapter<BJBean> {

        public BJAdapter(Context context, List<BJBean> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void setViewContent(CommonViewHolder viewHolder, BJBean bjBean) {


            viewHolder.setText(R.id.e_address, bjBean.getXjbdw())
                    .setText(R.id.mybj_info_dj, "单价:" + bjBean.getDj() + "元/m²")
                    .setText(R.id.mybj_info_bz, bjBean.getBz().equals("") ? "暂无信息！" : bjBean.getBz())
                    .setText(R.id.mybj_info_zj, "总价:" + bjBean.getZj() + "万元")
                    .setText(R.id.info_lastTime, bjBean.getBjsj())
                    .setText(R.id.info_bc, "￥" + bjBean.getBc())
                    .setText(R.id.info_zt, bjBean.getZt() == "0" ? "询价中" : bjBean.getZt() == "1" ? "已终止" : "已完成");


        }
    }

}
