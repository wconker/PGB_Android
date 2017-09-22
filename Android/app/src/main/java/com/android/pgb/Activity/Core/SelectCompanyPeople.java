package com.android.pgb.Activity.Core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.android.pgb.Bean.Group;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.CommonAdapter;
import com.android.pgb.Utils.CommonViewHolder;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.View.CBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectCompanyPeople extends Activity implements ExChange.CallBackForData, View.OnClickListener {
    private ExChange ex;
    private ListView listview;
    private List<JSONObject> data;
    private Button bt, selectAll, selectNone;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_company_people);
        new CBarView(this, new CBarView.OnClickListener() {
            @Override
            public void onLeftClick() {
                super.onLeftClick();
                finish();
            }
        }, null);
        listview = (ListView) this.findViewById(R.id.listview);
        bt = (Button) this.findViewById(R.id.bt);
        selectAll = (Button) this.findViewById(R.id.selectAll);
        selectNone = (Button) this.findViewById(R.id.selectNone);
        ex = new ExChange(this);
        data = new ArrayList<>();
        ex.getCompanyMemberList(ex.userId);
        selectAll.setOnClickListener(this);
        selectNone.setOnClickListener(this);
        bt.setOnClickListener(this);
    }

    private void initListView() {
        adapter = new ListAdapter(this, data, R.layout.listview_item);
        listview.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int vid = v.getId();
        switch (vid) {
            case R.id.bt:
                Group group;
                List<Group> list = new ArrayList<Group>();
                for (int c = 0; c < listview.getChildCount(); c++) {
                    View chilview = listview.getChildAt(c);
                    CheckBox checkBox = (CheckBox) chilview.findViewById(R.id.y);
                    if (checkBox.isChecked()) {
                        group = new Group();
                        group.setXm(JSONUtils.getString(data.get(c), "xm"));
                        group.setUserid(JSONUtils.getInt(data.get(c), "userid", 0));
                        list.add(group);
                    }
                }
                Intent i = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("value", (Serializable) list);
                i.putExtra("group", bundle);
                setResult(200, i);
                finish();
                break;
            case R.id.selectAll:
                for (int c = 0; c < listview.getChildCount(); c++) {
                    View chilview = listview.getChildAt(c);
                    CheckBox checkBox = (CheckBox) chilview.findViewById(R.id.y);
                    checkBox.setChecked(true);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.selectNone:
                for (int c = 0; c < listview.getChildCount(); c++) {
                    View chilview = listview.getChildAt(c);
                    CheckBox checkBox = (CheckBox) chilview.findViewById(R.id.y);
                    checkBox.setChecked(false);
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

    class ListAdapter extends CommonAdapter<JSONObject> {
        public ListAdapter(Context context, List list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void setViewContent(CommonViewHolder viewHolder, JSONObject jsonObject) {

            if (JSONUtils.getInt(jsonObject, "userid", 0) != ex.userId) {
                viewHolder.setText(R.id.name, JSONUtils.getString(jsonObject, "xm"))
                        .setText(R.id.bm, JSONUtils.getString(jsonObject, "bm"));
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                initListView();
            }
        }
    };

    @Override
    public void onMessage(String str, String cmd, int code) {
        Message message = Message.obtain();

        try {
            JSONObject object = new JSONObject(str);
            JSONArray array = JSONUtils.getJSONArray(object, "data");
            if (cmd.equals("user.getCompanyMemberList")) {
                if (array != null) {
                    for (int i = 0; i < array.length(); i++) {
                        data.add((JSONObject) array.get(i));
                    }
                }
            }
            handler.sendEmptyMessage(100);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
