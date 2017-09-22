package com.android.pgb.News;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.CommonAdapter;
import com.android.pgb.Utils.CommonViewHolder;
import com.android.pgb.Utils.JSONUtils;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moon.zhong on 2015/3/9.
 */
public class ContentFragment extends Fragment implements ExChange.CallBackForData {

    public static Fragment instance(String msg) {
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("msg", msg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pager_item, null);
    }

    private PullToRefreshListView pullToRefreshListView;
    private NewAdapter newAdapter;
    private List<String> list;

    private ExChange ex;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String msg = bundle.getString("msg");
        list = new ArrayList<>();
        ex = new ExChange(this);
        ex.Test("http://line.56pt.cn/pinggubang/GetList.php?cid=5");
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.id_text_msg);
        list.add("新闻1");
        list.add("新闻1");
        list.add("新闻1");
        newAdapter = new NewAdapter(getActivity(), list, R.layout.new_items_list);
        pullToRefreshListView.setAdapter(newAdapter);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getActivity().startActivity(new Intent(getActivity(), NewsWebview.class));
            }
        });

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String objectRcvd = msg.getData().getString("news");
            try {
                JSONObject j = new JSONObject(objectRcvd);


                for (int i = 1; i < 3; i++) {
                    JSONObject arr = (JSONObject) j.get(String.valueOf(i));

                    list.add(JSONUtils.getString(arr, "title"));

                }
                newAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    class NewAdapter extends CommonAdapter<String> {

        public NewAdapter(Context context, List list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void setViewContent(CommonViewHolder viewHolder, String s) {
            viewHolder.setText(R.id.newsitem, s);
        }
    }


}
