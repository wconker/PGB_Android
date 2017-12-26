package com.android.pinggubang.Fragment.CK;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.pinggubang.Activity.Activity_CK.Activity_MyTask;
import com.android.pinggubang.Activity.Activity_CK.Activity_ZbInfo;
import com.android.pinggubang.Activity.Activity_CK.CreateKC;
import com.android.pinggubang.Bean.TaskBean;
import com.android.pinggubang.Bean.ZBBean;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.CommonAdapter;
import com.android.pinggubang.Utils.CommonViewHolder;
import com.android.pinggubang.Utils.JSONUtils;
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
public class MyTask extends Fragment implements ExChange.CallBackForData {
    private PullToRefreshListView pullListView;
    private ExChange ex;
    private int CurrentPage = 1;
    private MyTask.TaskAdapter adapter;
    private List<TaskBean> listBean;
    private int pullFlag = 100;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == LOGIN) {
                pullListView.onRefreshComplete();
            } else {
                String value = msg.getData().getString("getMyTaskList");
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
                    TaskBean taskBean;
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject temp = (JSONObject) arr.get(i);
                        taskBean = new TaskBean(temp);
                        listBean.add(taskBean);
                    }
                    pullListView.onRefreshComplete();
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    class TaskAdapter extends CommonAdapter<TaskBean> {

        public TaskAdapter(Context context, List<TaskBean> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void setViewContent(CommonViewHolder viewHolder, TaskBean taskBean) {

            viewHolder.setText(R.id.bdw, taskBean.getBdw() + "").
                    setText(R.id.zbj, "招标价:" + taskBean.getZbj() + "元").
                    setText(R.id.tbj, "投标价:" + taskBean.getTbj() + "元").
                    setText(R.id.zt, taskBean.getZtms()).
                    setText(R.id.zbsj, taskBean.getTbsj()).
                    setText(R.id.bz, taskBean.getXxsm().equals("") ? "无信息" : taskBean.getXxsm());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_task, container, false);

        final CustomFAB floatButton = (CustomFAB) view.findViewById(R.id.floatButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateKC.class);
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), floatButton, "share");
                getActivity().startActivity(intent, activityOptionsCompat.toBundle());
            }
        });
        initView(view);
        return view;
    }

    private void initView(View view) {
        listBean = new ArrayList<>();
        adapter = new MyTask.TaskAdapter(getActivity(), listBean, R.layout.fragment_mytblist_item);
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
                ex.getMyTaskList(CurrentPage);
                pullFlag = 0;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pullFlag = 1;
                CurrentPage++;
                ex.getMyTaskList(CurrentPage);
            }
        });
        pullListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), Activity_MyTask.class);
                int zbId = listBean.get(i - 1).getZbid();
                intent.putExtra("zbid", zbId);
                getActivity().startActivity(intent);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ex = new ExChange(this);
        ex.getMyTaskList(1);
        com.android.pinggubang.Utils.Log.e("这是MyTask 的 onResume()方法");
    }

    @Override
    public void onPause() {
        super.onPause();

        com.android.pinggubang.Utils.Log.e("这是MyTask 的 onPause()方法");

    }

    private final int LOGIN = 9999;

    @Override
    public void onMessage(String str, String cmd, int code) {
        com.android.pinggubang.Utils.Log.e(str);
        Message msg = Message.obtain();
        if (cmd.equals("business.getMyTaskList")) {
            Bundle bundle = new Bundle();
            bundle.putString("getMyTaskList", str);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
        if (cmd.equals("user.Login")) {
            handler.sendEmptyMessage(LOGIN);
        }
    }
}
