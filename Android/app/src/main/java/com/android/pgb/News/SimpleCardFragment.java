package com.android.pgb.News;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.pgb.Bean.NewBean;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.CommonAdapter;
import com.android.pgb.Utils.CommonViewHolder;
import com.android.pgb.Utils.JSONUtils;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;


@SuppressLint("ValidFragment")
public class SimpleCardFragment extends Fragment implements ExChange.CallBackForData {
    private String mTitle;
    private Observer<String> newsDeal;
    private PullToRefreshListView pullToRefreshListView;
    private SimpleCardFragment.MyNewsAdapter newAdapter;
    private List<NewBean.DataBean> list;
    private ExChange ex;

    public static SimpleCardFragment getInstance(String title) {
        SimpleCardFragment sf = new SimpleCardFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_simple_card, null);
        list = new ArrayList<>();
        ex = new ExChange(this);
        ex.getNewsList(50);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.id_text_msg);
        newAdapter = new SimpleCardFragment.MyNewsAdapter(getActivity(), list, R.layout.new_items_list);
        pullToRefreshListView.setAdapter(newAdapter);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getActivity().startActivity(new Intent(getActivity(), NewsWebview.class));
            }
        });
        return view;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String objectRcvd = msg.getData().getString("news");
            Gson gson = new Gson();
            Type type = new TypeToken<NewBean>() {
            }.getType();
            NewBean bean = gson.fromJson(objectRcvd, type);
            list.clear();
            list.addAll(bean.getData());
            newAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onMessage(String str, String cmd, int code) {
        Message message = Message.obtain();
        Bundle b = new Bundle();
        b.putString("news", str);
        message.setData(b);
        handler.sendMessage(message);
    }



    class MyNewsAdapter extends CommonAdapter<NewBean.DataBean>{
        public MyNewsAdapter(Context context, List<NewBean.DataBean> list, int layoutId) {
            super(context, list, layoutId);
            Log.e("Conker",list.size()+"列表里的数量");
        }

        @Override
        public void setViewContent(CommonViewHolder viewHolder, NewBean.DataBean dataBean) {
            viewHolder.setText(R.id.newsitem,dataBean.getTitle())
                    .setText(R.id.new_fbsj,dataBean.getFbsj());
        }
    }


}
