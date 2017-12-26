package com.android.pinggubang.Fragment.XJ;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
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
import com.android.pinggubang.Bean.XJBean;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.Interface.ParentsButtonLisener;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.Dialog;
import com.android.pinggubang.Utils.HTTPSUtils;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.Utils.Log;
import com.android.pinggubang.View.InnerListview.adapt.ParentAdapt;
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
public class MyXJList extends BaseFragment implements ExChange.CallBackForData, ParentsButtonLisener<XJBean> {


    private List<XJBean> listBean;
    private ExChange ex;
    //内部list
    private List<List<XJBean>> mapList;
    private ParentAdapt mParentAdapt;
    private List<XJBean> listems;
    private int pullFlag = 100;
    private int CurrentPage = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_xjlist, container, false);

        pullListView = (PullToRefreshListView) view.findViewById(R.id.pulltorefresh);
        listBean = new ArrayList<>();
        mapList = new ArrayList<>();
        final CustomFAB floatButton = (CustomFAB) view.findViewById(R.id.floatButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateXJ.class);
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), floatButton, "share");
                getActivity().startActivity(intent, activityOptionsCompat.toBundle());
            }
        });

        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ex = new ExChange(this);
        Log.e("我进入OnResume里了");
    }


    private void initView() {
        mParentAdapt = new ParentAdapt(getActivity(), listBean, mapList, this);
        pullListView.setAdapter(mParentAdapt);
        pullListView.setOnItemClickListener(new AdaptItemClick());
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
                pullFlag = 0;
                CurrentPage = 1;
                ex.getMyxjList(CurrentPage);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pullFlag = 1;
                CurrentPage++;
                ex.getMyxjList(CurrentPage);
            }
        });
        firstLoad();

    }

    private class AdaptItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            Intent intent = new Intent(getActivity(), Activity_XjInfo.class);
            int xjId = listBean.get(position - 1).getXjid();
            intent.putExtra("my", false);
            intent.putExtra("xjid", xjId);
            startActivity(intent);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String value;
            String MessageInfo;
            switch (msg.what) {
                case 103:
                    ex.getMyxjList(CurrentPage);
                    value = msg.getData().getString("endXj");
                    MessageInfo = ex.getMessageInfo(value);
                    new Dialog(getActivity(), "提示", MessageInfo);
                    break;
                case 106:
                    ex.getMyxjList(CurrentPage);
                    value = msg.getData().getString("closeXj");
                    MessageInfo = ex.getMessageInfo(value);
                    new Dialog(getActivity(), "提示", MessageInfo);
                    break;
                case 101:
                    value = msg.getData().getString("getMyxjList");
                    JSONObject obj;
                    JSONObject temp;
                    XJBean XJBean;
                    try {
                        obj = new JSONObject(value);
                        JSONArray arr = JSONUtils.getJSONArray(obj, "data");
                        if (pullFlag == 0) {
                            CurrentPage = 1;
                        }
                        if (pullFlag != 1) {
                            listBean.clear();
                            mapList.clear();
                        }
                        for (int i = 0; i < arr.length(); i++) {
                            temp = (JSONObject) arr.get(i);
                            listems = new ArrayList<XJBean>();
                            JSONArray bjList = (JSONArray) temp.get("bjinfo");
                            for (int c = 0; c < bjList.length(); c++) {
                                JSONObject bjobj = (JSONObject) bjList.get(c);
                                XJBean EB = new XJBean(bjobj);
                                listems.add(EB);
                            }
                            mapList.add(listems);
                            XJBean = new XJBean(temp);
                            listBean.add(XJBean);
                        }
                        pullListView.onRefreshComplete();
                        mParentAdapt.notifyDataSetChanged();
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
    public void leftbtnClick(int vid, int posttion, XJBean bean) {
        Intent intent = new Intent(getActivity(), CreateXJ.class);
        int xjId = listBean.get(posttion).getXjid();
        intent.putExtra("xjId", xjId);
        startActivity(intent);

    }

    @Override
    public void rightbtnClick(int vid, final int posttion, XJBean bean) {


        if (bean.getZt() == 1) {
            new Dialog(getActivity(), "提示：", "确定结案？", new Dialog.dialogButton() {
                @Override
                public void ok(DialogInterface dialog, int which) {
                    ex.closeXj(listBean.get(posttion).getXjid());
                }

                @Override
                public void cannot(DialogInterface dialog, int which) {

                }
            });
        } else {

            new Dialog(getActivity(), "提示：", "确定终止询价？", new Dialog.dialogButton() {
                @Override
                public void ok(DialogInterface dialog, int which) {
                    ex.endXj(listBean.get(posttion).getXjid());
                }

                @Override
                public void cannot(DialogInterface dialog, int which) {

                }
            });
        }
    }


    private final int LOGIN = 9999;

    @Override
    public void onMessage(String str, String cmd, int code) {
        Message msg = Message.obtain();
        Bundle bundle;
        switch (cmd) {
            case "business.getMyxjList":
                msg.what = 101;
                bundle = new Bundle();
                bundle.putString("getMyxjList", str);
                msg.setData(bundle);
                handler.sendMessage(msg);
                break;
            case "business.endXj":
                msg.what = 103;
                bundle = new Bundle();
                bundle.putString("endXj", str);
                msg.setData(bundle);
                handler.sendMessage(msg);
                break;
            case "business.closeXj":
                msg.what = 106;
                bundle = new Bundle();
                bundle.putString("closeXj", str);
                msg.setData(bundle);
                handler.sendMessage(msg);
                break;
            case "user.Login":

                handler.sendEmptyMessage(LOGIN);
                break;
        }
    }
}
