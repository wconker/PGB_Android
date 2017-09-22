package com.android.pgb.Activity.Core;

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
import android.widget.GridView;
import android.widget.ListView;

import com.android.pgb.Bean.City;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.CommonAdapter;
import com.android.pgb.Utils.CommonViewHolder;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectCity extends Activity implements ExChange.CallBackForData, View.OnClickListener {
    private ExChange ex;
    private ListView listView;
    private GridView hotCity;
    private EditText etSearch;
    private Button btnSearch;
    private List<City> listBean, AllData;
    private List<City> HotList;
    private ListviewAdapter adapter;
    private ListviewAdapter adapterHot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        ex = new ExChange(this);
        ex.getCityList();
        btnSearch = (Button) this.findViewById(R.id.btnSearch);
        etSearch = (EditText) this.findViewById(R.id.etSearch);
        btnSearch.setOnClickListener(this);
        listBean = new ArrayList<>();
        AllData = new ArrayList<>();
        HotList = new ArrayList<>();
        adapter = new ListviewAdapter(this, listBean, R.layout.new_items_list);
        adapterHot = new ListviewAdapter(this, HotList, R.layout.city_items);
        listView = (ListView) this.findViewById(R.id.listview);
        hotCity = (GridView) this.findViewById(R.id.hotCity);
        listView.setAdapter(adapter);
        hotCity.setAdapter(adapterHot);
        hotCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i1 = new Intent();
                i1.putExtra("dqdm", HotList.get(i).getDqdm());
                i1.putExtra("dqmc", HotList.get(i).getDqmc());

                setResult(230, i1);
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i2 = new Intent();
                i2.putExtra("dqdm", listBean.get(position).getDqdm());
                i2.putExtra("dqmc", listBean.get(position).getDqmc());

                setResult(230, i2);
                finish();
            }
        });
    }

    @Override
    public void onMessage(String str, String cmd, int code) {

        Message msg = Message.obtain();
        Log.e(str + "getCityList");
        if (cmd.equals("business.getCityList")) {
            Bundle bundle = new Bundle();
            bundle.putString("getCityList", str);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    }

    //数据处理中心
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String value = msg.getData().getString("getCityList");
            JSONObject obj;

            try {
                obj = new JSONObject(value);
                JSONObject jb = (JSONObject) obj.get("data");
                JSONArray arr = JSONUtils.getJSONArray(jb, "all");
                JSONArray hot = JSONUtils.getJSONArray(jb, "hot");
                if (arr != null) {
                    for (int c = 0; c < arr.length(); c++) {
                        City city = new City((JSONObject) arr.get(c));
                        listBean.add(city);
                    }
                    AllData.clear();
                    AllData.addAll(listBean);
                    adapter.notifyDataSetChanged();
                }
                if (hot != null) {
                    for (int c = 0; c < hot.length(); c++) {
                        City city = new City((JSONObject) hot.get(c));
                        HotList.add(city);
                    }
                    adapterHot.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    class ListviewAdapter extends CommonAdapter<City> {

        public ListviewAdapter(Context context, List<City> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void setViewContent(CommonViewHolder viewHolder, City city) {
            viewHolder.setText(R.id.newsitem, "  " + city.getDqmc());
        }
    }

    @Override
    public void onClick(View v) {

        int vid = v.getId();
        switch (vid) {
            case R.id.btnSearch:


                List<City> tempList = new ArrayList<>();

                for (int i = 0; i < AllData.size(); i++) {


                    if (AllData.get(i).getDqmc().length() < etSearch.getText().toString().trim().length()) {
                        continue;
                    }
                    String str = AllData.get(i).getDqmc().substring(0, etSearch.getText().toString().trim().length());

                    if (etSearch.getText().toString().trim().equals(str)) {
                        tempList.add(AllData.get(i));
                    }


                }
                listBean.clear();
                listBean.addAll(tempList);
                adapter.notifyDataSetChanged();

                break;
        }
    }
}
