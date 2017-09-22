package com.android.pgb.Fragment.CK;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.pgb.Activity.Activity_CK.Activity_ZbInfo;
import com.android.pgb.Activity.Activity_CK.CreateKC;
import com.android.pgb.Bean.TBBean;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.CommonAdapter;
import com.android.pgb.Utils.CommonViewHolder;
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
public class MyTbList extends BaseFragment implements ExChange.CallBackForData {


    private List<TBBean> listBean;
    private ExChange ex;
    private int pullFlag = 100;
    private MyTbAdapter adapter;
    private int CurrentPage = 1;
    private final int LOGIN = 9999;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_tb_list, container, false);

        final CustomFAB floatButton = (CustomFAB) view.findViewById(R.id.floatButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateKC.class);
                getActivity().startActivity(intent);
            }
        });
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ex = new ExChange(this);
        ex.getMytbList(CurrentPage);
        com.android.pgb.Utils.Log.e("这是我都投标列表 的 onResume()方法");
    }

    @Override
    public void onPause() {
        super.onPause();
        com.android.pgb.Utils.Log.e("这是我都投标列表 的 onPause()方法");
    }

    private void initView(View view) {
        listBean = new ArrayList<>();
        adapter = new MyTbAdapter(getActivity(), listBean, R.layout.fragment_mytblist_item);
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
                CurrentPage=1;
                ex.getMytbList(CurrentPage);
                pullFlag = 0;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pullFlag = 1;
                CurrentPage++;
                ex.getMytbList(CurrentPage);
            }
        });

        pullListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Activity_ZbInfo.class);
                int zbId = listBean.get(position-1).getZbid();
                intent.putExtra("zbid", zbId);
                getActivity().startActivity(intent);
            }
        });

        firstLoad();
    }

    // FIXME: 2017/3/14 数据处理中心
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==LOGIN){
                pullListView.onRefreshComplete();
            }else {

                String value = msg.getData().getString("getMytbList");
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
                    TBBean tbBean;
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject temp = (JSONObject) arr.get(i);
                        tbBean = new TBBean(temp);
                        listBean.add(tbBean);
                    }
                    pullListView.onRefreshComplete();
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    class MyTbAdapter extends CommonAdapter<TBBean> {
         MyTbAdapter(Context context, List<TBBean> list, int layoutId) {
            super(context, list, layoutId);

        }

        @Override
        public void setViewContent(CommonViewHolder viewHolder, TBBean tbBean) {
            String text1=tbBean.getTbj()+ "元";
            SpannableStringBuilder style = new SpannableStringBuilder(text1);

            style.setSpan(new ForegroundColorSpan(Color.RED),
                    0,
                    text1.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            viewHolder.setText(R.id.bdw, tbBean.getBdw() + "").
                    setText(R.id.zbj, "招标价：" + tbBean.getZbbj() + "元").
                    setText(R.id.tbj, "投标价：" +style).
                    setText(R.id.zt, tbBean.getZtms()).
                    setText(R.id.zbsj, tbBean.getTbsj()).
                    setText(R.id.bz, tbBean.getXxsm().equals("")?"无信息":tbBean.getXxsm());
        }
    }

    @Override
    public void onMessage(String str, String cmd, int code) {
        Message msg = Message.obtain();
        switch (cmd) {
            case "business.getMytbList":
                Bundle bundle = new Bundle();
                bundle.putString("getMytbList", str);
                msg.setData(bundle);
                handler.sendMessage(msg);

                break;
            case "user.Login":
                handler.sendEmptyMessage(LOGIN);
                break;
        }
    }
}
