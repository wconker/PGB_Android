package com.android.pinggubang.Activity.Activity_Tool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.pinggubang.Bean.KCBean;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.CommonAdapter;
import com.android.pinggubang.Utils.CommonViewHolder;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.View.CBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Activity_KCGJ extends AppCompatActivity implements ExChange.CallBackForData {


    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.srh)
    SwipeRefreshLayout srh;
    private ExChange ex;
    private List<KCBean> list;
    private Activity_KCGJ.ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcgj);
        ButterKnife.bind(this);
        ex = new ExChange(this);
        new CBarView(this, new CBarView.OnClickListener() {
            @Override
            public void onLeftClick() {
                super.onLeftClick();
                finish();
            }
            @Override
            public void onRightClick() {
                super.onRightClick();
                Intent intent = new Intent(Activity_KCGJ.this, AddKc.class);
                startActivity(intent);
            }
        }, null);

        list = new ArrayList<>();
        adapter = new Activity_KCGJ.ListAdapter(this, list, R.layout.kclist_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(Activity_KCGJ.this,KcbInfo.class);
                intent.putExtra("kcid",list.get(i).getId());
                startActivity(intent);
            }
        });
        srh.setColorSchemeColors(getResources().getColor(R.color.wkh_blue), getResources().getColor(R.color.reddown));
        srh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ex.getCkbList();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        srh.setRefreshing(true);
        ex.getCkbList();
    }
    class ListAdapter extends CommonAdapter<KCBean> {
        public ListAdapter(Context context, List<KCBean> list, int layoutId) {
            super(context, list, layoutId);
        }
        @Override
        public void setViewContent(CommonViewHolder viewHolder, KCBean kcBean) {
            viewHolder.setText(R.id.title, kcBean.getCkdz())
                    .setText(R.id.bz, kcBean.getBz())
                    .setText(R.id.kclb, kcBean.getCklb_name())
                    .setText(R.id.sj, kcBean.getCkrq());
        }
    }
    private final int KCLIST = 1002;
    private String _str = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            KCBean kcBean;
            try {
                list.clear();
                JSONObject obj = new JSONObject(_str);
                JSONArray arr = JSONUtils.getJSONArray(obj, "data");
                for (int c = 0; c < arr.length(); c++) {
                    kcBean = new KCBean((JSONObject) arr.get(c));
                    list.add(kcBean);
                }
                srh.setRefreshing(false);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    public void onMessage(String str, String cmd, int code) {

        if (cmd.equals("business.getCkbList")) {
            _str = str;
            handler.sendEmptyMessage(KCLIST);
        }
    }
}
