package com.android.pinggubang.Activity.Core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.pinggubang.Bean.PGJG;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.CommonAdapter;
import com.android.pinggubang.Utils.CommonViewHolder;
import com.android.pinggubang.Utils.JSONUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectPGJG extends Activity implements ExChange.CallBackForData, View.OnClickListener {

    private ExChange ex;
    private ListView listView;
    private EditText etSearch;
    private Button btnSearch;
    private List<PGJG> listBean;
    private ListviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pgjg);
        ex = new ExChange(this);
        btnSearch = (Button) this.findViewById(R.id.btnSearch);
        etSearch = (EditText) this.findViewById(R.id.etSearch);
        btnSearch.setOnClickListener(this);
        listBean = new ArrayList<>();
        adapter = new ListviewAdapter(this, listBean, R.layout.new_items_list);
        listView = (ListView) this.findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.putExtra("JZBZ", String.valueOf(listBean.get(position).getPgzj()));
                i.putExtra("JGMC", listBean.get(position).getPgmc());
                setResult(260, i);
                finish();
            }
        });
    }

    @Override
    public void onMessage(String str, String cmd, int code) {
        Message msg = Message.obtain();
        if (cmd.equals("user.getPggsList")) {
            Bundle bundle = new Bundle();
            bundle.putString("getPggsList", str);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }


    }

    //数据处理中心
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String value = msg.getData().getString("getPggsList");
            JSONObject obj;
            try {
                obj = new JSONObject(value);
                JSONArray arr = JSONUtils.getJSONArray(obj, "data");
                if (arr != null) {
                    listBean.clear();
                    for (int c = 0; c < arr.length(); c++) {
                        PGJG pgjg = new PGJG((JSONObject) arr.get(c));
                        listBean.add(pgjg);
                    }
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    };


    class ListviewAdapter extends CommonAdapter<PGJG> {
        public ListviewAdapter(Context context, List<PGJG> list, int layoutId) {
            super(context, list, layoutId);
        }
        @Override
        public void setViewContent(CommonViewHolder viewHolder, PGJG pgjg) {
            viewHolder.setText(R.id.newsitem, (viewHolder.getPostion()+1) + " 、"+ pgjg.getPgmc());
            View info =viewHolder.getView(R.id.info);
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(SelectPGJG.this,PGJGInfo.class));
                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        int vid = v.getId();
        switch (vid) {
            case R.id.btnSearch:
                ex.getPggsList(etSearch.getText().toString().trim());
                break;
        }
    }
}
