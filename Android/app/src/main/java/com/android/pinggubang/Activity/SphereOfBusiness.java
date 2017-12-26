package com.android.pinggubang.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.pinggubang.Bean.CollectBean;
import com.android.pinggubang.Bean.ProvinceBean;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.CommonAdapter;
import com.android.pinggubang.Utils.CommonViewHolder;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.View.CBarView;
import com.bigkoo.pickerview.OptionsPickerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SphereOfBusiness extends Activity implements ExChange.CallBackForData {


    private ExChange ex;
    private OptionsPickerView citypicview;
    private Button addCollect;
    private MyCollectAdapter adapter;
    private List<CollectBean> list;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sphere_of_business);
        new CBarView(this, new CBarView.OnClickListener() {
            @Override
            public void onLeftClick() {
                super.onLeftClick();
                finish();
            }

            @Override
            public void onRightClick() {
                super.onRightClick();
            }
        }, null);
        ex=new ExChange(this);
        //获取城市列表
        ex.getCityList("p", "0");

        addCollect = (Button) this.findViewById(R.id.addCollect);
        listView = (ListView) this.findViewById(R.id.mycollect);
              list = new ArrayList<>();
        list.add(new CollectBean("上海","3000"));
        list.add(new CollectBean("北京","3000"));
        list.add(new CollectBean("杭州","3000"));
        adapter = new MyCollectAdapter(this, list, R.layout.mycollect_item);
        listView.setAdapter(adapter);

        citypicview = new OptionsPickerView(this);
        addCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 citypicview.show();
            }
        });

    }
    private ArrayList<ProvinceBean> options2_;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<ProvinceBean>> options2Items = new ArrayList<>();

  private void setCitypicview()
    {
        //三级联动效果
        citypicview.setPicker(options1Items, options2Items, null, true);
        //设置选择的三级单位
        // pwOptions.setLabels("省", "市");
        citypicview.setTitle("选择城市");
        citypicview.setCyclic(false, true, false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        citypicview.setSelectOptions(1, 1, 1);
        citypicview.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                list.add(new CollectBean(options2Items.get(options1).get(option2).getName().toString(),"333"));
                adapter.notifyDataSetChanged();

            }
        });

    }



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    try {
                        String objectRcvd = msg.getData().getString("MyObject");
                        JSONObject obj = new JSONObject(objectRcvd);
                        JSONArray arr = JSONUtils.getJSONArray(obj, "data");

                        if (((JSONObject) arr.get(0)).get("fl").equals("P")) {
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jsonObj = (JSONObject) arr.get(i);
                                options1Items.add(new ProvinceBean(Long.parseLong(jsonObj.get("dqdm").toString()), jsonObj.get("dqmc").toString(), jsonObj.get("fl").toString(), ""));

                                JSONArray cityarr = JSONUtils.getJSONArray(jsonObj, "City");
                                if (cityarr.length() > 0) {
                                    options2_ = new ArrayList<>();
                                    for (int c = 0; c < cityarr.length(); c++) {
                                        JSONObject cityObj = (JSONObject) cityarr.get(c);
                                        options2_.add(new ProvinceBean(Long.parseLong(cityObj.get("dqdm").toString()), cityObj.get("dqmc").toString(), "", ""));
                                    }
                                    options2Items.add(options2_);

                                }
                            }
                        }
                        setCitypicview();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
            }

        }
    };

    @Override
    public void onMessage(String str, String cmd, int code) {
        if (cmd.equals("conn.getCityList")) {
            Message message = Message.obtain();
            Bundle b = new Bundle();
            b.putString("MyObject", str);
            message.setData(b);
            message.what = 100;
            handler.sendMessage(message);
        }
    }


    class MyCollectAdapter extends CommonAdapter<CollectBean> {


        public MyCollectAdapter(Context context, List list, int layoutId) {
            super(context, list, layoutId);
        }



        @Override
        public void setViewContent(CommonViewHolder viewHolder, CollectBean collectBean) {
            viewHolder.setText(R.id.collectcity,collectBean.getCityname());
        }
    }


}
