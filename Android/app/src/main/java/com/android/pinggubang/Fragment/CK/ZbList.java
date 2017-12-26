package com.android.pinggubang.Fragment.CK;


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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.pinggubang.Activity.Activity_CK.Activity_ZbInfo;
import com.android.pinggubang.Activity.Activity_CK.CreateKC;
import com.android.pinggubang.Bean.ZBBean;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.CommonAdapter;
import com.android.pinggubang.Utils.CommonViewHolder;
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
public class ZbList extends BaseFragment implements ExChange.CallBackForData {

    private List<ZBBean> listBean;
    private ExChange ex;
    private int pullFlag = 100;
    private ZBAdapter adapter;
    private int CurrentPage = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zb_list, container, false);

        final CustomFAB floatButton = (CustomFAB) view.findViewById(R.id.floatButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateKC.class);
                // ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), floatButton, "share");
                getActivity().startActivity(intent);
            }
        });
        initView(view);

        ex = new ExChange(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        com.android.pinggubang.Utils.Log.e("这是招标列表的 onResume()方法");
    }

    @Override
    public void onPause() {
        super.onPause();
        com.android.pinggubang.Utils.Log.e("这是招标列表的 onPause()方法");
    }

    private void initView(View view) {
        listBean = new ArrayList<>();
        adapter = new ZBAdapter(getActivity(),
                listBean,
                R.layout.fragment_zblist_item);
        pullListView = (PullToRefreshListView) view.findViewById(R.id.pulltorefresh);
        pullListView.setAdapter(adapter);
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
                ex.getZbList(CurrentPage);
                pullFlag = 0;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pullFlag = 1;
                CurrentPage++;
                ex.getZbList(CurrentPage);
            }
        });

        pullListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), Activity_ZbInfo.class);
                int zbId = listBean.get(i - 1).getZbid();
                intent.putExtra("zbid", zbId);
                getActivity().startActivity(intent);
            }
        });

        firstLoad();
    }

    @Override
    public void onMessage(String str, String cmd, int code) {
        Message msg = Message.obtain();
        switch (cmd) {
            case "business.getZbList":
                Bundle bundle = new Bundle();
                bundle.putString("getZbList", str);
                msg.setData(bundle);
                handler.sendMessage(msg);
                break;
            case "user.Login":
                handler.sendEmptyMessage(LOGIN);
                break;
        }

    }

    private class ZBAdapter extends CommonAdapter<ZBBean> {

        ZBAdapter(Context context, List<ZBBean> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void setViewContent(CommonViewHolder viewHolder, ZBBean zbBean) {
            android.util.Log.e("KING"," i am setViewContent in Me");
            LinearLayout isnew = viewHolder.getView(R.id.IsNew);
            if (zbBean.getIsnew().equals("new")) {
                isnew.setVisibility(View.VISIBLE);
            } else {
                isnew.setVisibility(View.GONE);
            }

            viewHolder.setText(R.id.bdw, zbBean.getBdw())
                    .setText(R.id.ztms, zbBean.getZtms())
                    .setText(R.id.zbfw, zbBean.getZbfw())
                    .setText(R.id.zblbmc, zbBean.getZblbmc())
                    .setText(R.id.zb_time, zbBean.getZbsj())
                    .setText(R.id.info_readCount, zbBean.getYdcs())
                    .setText(R.id.info_bc, "￥" + zbBean.getBc() + "元");
        }
    }

    private final int LOGIN = 9999;

    // FIXME: 2017/3/14 数据处理中心
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == LOGIN) {
                pullListView.onRefreshComplete();
            } else {
                String value = msg.getData().getString("getZbList");
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
                    ZBBean zbBean;
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject temp = (JSONObject) arr.get(i);
                        zbBean = new ZBBean(temp);
                        listBean.add(zbBean);
                    }
                    pullListView.onRefreshComplete();
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };


}