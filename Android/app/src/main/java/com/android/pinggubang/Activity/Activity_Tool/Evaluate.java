package com.android.pinggubang.Activity.Activity_Tool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.pinggubang.Bean.TempBean;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.CommonAdapter;
import com.android.pinggubang.Utils.CommonViewHolder;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.base.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class Evaluate extends BaseActivity implements TextWatcher {


    @Bind(R.id.search_et_input)
    EditText searchEtInput;
    @Bind(R.id.search_btn_clear)
    Button searchBtnClear;
    @Bind(R.id.BuildingList)
    ListView BuildingList;
    @Bind(R.id.house_decoration)
    Spinner houseDecoration;
    @Bind(R.id.house_special)
    Spinner houseSpecial;
    @Bind(R.id.HouseArea)
    TextView HouseArea;
    @Bind(R.id.Start_evaluate)
    Button StartEvaluate;

    private List<TempBean> data = new ArrayList<>();
    private Observer observer;
    private JSONArray arr;
    BaseAdapter adapter;
    private int SelectIndex = 0;
    private String xq, zh, dy, lc;
    private String searchText = "";
    private String hbh = "";
    private int TAG_house_decoration = 0, TAG_house_special = 0;

    @Override
    public void bindButterKnife() {
        super.bindButterKnife();
        ButterKnife.bind(this);

    }

    //注册各种监听事件
    void ResEvent() {
        StartEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ex.evaluate(hbh, HouseArea.getText().toString(), String.valueOf(TAG_house_decoration), String.valueOf(TAG_house_special));
            }
        });
        houseDecoration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TAG_house_decoration = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        houseSpecial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                TAG_house_special = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        searchBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BuildingList.setVisibility(View.VISIBLE);
                searchEtInput.setText("");
                searchEtInput.setEnabled(true);
                searchText = "";
                SelectIndex = 0;
                searchEtInput.addTextChangedListener(Evaluate.this);
            }
        });
        BuildingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (data.get(i).Data2.equals("hbh")) {
                    HouseArea.setText(data.get(i).Data1 == null ? "暂无数据" : data.get(i).Data1);
                    hbh = data.get(i).Value;
                    BuildingList.setVisibility(View.GONE);
                }
                searchEtInput.setEnabled(false);
                searchEtInput.removeTextChangedListener(Evaluate.this);
                searchText += data.get(i).Key;
                searchEtInput.setText(searchText);
                String TextValue = data.get(i).Value;
                ChangeInfo(TextValue);


            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        searchEtInput.addTextChangedListener(this);
        adapter = new BaseAdapter(this,
                data,
                R.layout.listview_items);//该listview_items 布局为临时用的通配布局
        BuildingList.setAdapter(adapter);
        ResEvent();
        initObserver();
    }

    void ChangeInfo(String v) {

        switch (SelectIndex) {
            case 0:
                ex.selectCommunity("conn.selectBuildingNo", v);
                SelectIndex = 1;
                xq = v;
                break;
            case 1:
                ex.selectCommunity("conn.selectUnitNo", xq, v);
                zh = v;
                SelectIndex = 2;
                break;

            case 2:
                ex.selectCommunity("conn.selectFloor", xq, zh, v);
                dy = v;
                SelectIndex = 3;
                break;
            case 3:
                ex.selectCommunity("conn.selectDoorNo", xq, zh, dy, v);
                SelectIndex = 4;
                lc = v;
                break;
            default:
                data.clear();
                adapter.notifyDataSetChanged();
                break;
        }
    }

    void initObserver() {
        observer = new Observer<JSONObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("99999", e.getMessage());
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                DataCenter(jsonObject);
            }
        };
    }


    private void DataCenter(JSONObject obj) {

        if (JSONUtils.getInt(obj, "code", 20) == 0) {
            if (JSONUtils.getString(obj, "cmd").equals("conn.evaluate")) {
                Intent i = new Intent(Evaluate.this, EvaluateResult.class);
                try {
                    JSONObject ConverData = (JSONObject) JSONUtils.getArrInJson(obj, "data").get(0);
                    i.putExtra("community", JSONUtils.getString(ConverData, "pgnr"));
                    i.putExtra("evaluatetotal", JSONUtils.getString(ConverData, "pgzj"));
                    i.putExtra("evaluatesignal", JSONUtils.getString(ConverData, "pgdj"));
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                arr = JSONUtils.getJSONArray(obj, "data");
                data.clear();
                for (int i = 0; i < arr.length(); i++) {
                    try {
                        JSONObject signleData = (JSONObject) arr.get(i);
                        TempBean tempBean = new TempBean();
                        switch (SelectIndex) {
                            case 0:
                                tempBean.Value = JSONUtils.getString(signleData, "xqbh");
                                tempBean.Key = JSONUtils.getString(signleData, "xqmc");
                                break;
                            case 1:
                                tempBean.Value = JSONUtils.getString(signleData, "zh");
                                tempBean.Key = JSONUtils.getString(signleData, "zm");
                                break;
                            case 2:
                                tempBean.Value = JSONUtils.getString(signleData, "dy");
                                tempBean.Key = JSONUtils.getString(signleData, "dym");
                                break;
                            case 3:
                                tempBean.Value = JSONUtils.getString(signleData, "lcs");
                                tempBean.Key = JSONUtils.getString(signleData, "lcm");
                                tempBean.Data2 = "lc";
                                break;
                            case 4:
                                tempBean.Value = JSONUtils.getString(signleData, "hbh");
                                tempBean.Key = JSONUtils.getString(signleData, "hh");
                                tempBean.Data1 = JSONUtils.getString(signleData, "jzmj");
                                tempBean.Data2 = "hbh";

                                break;
                            default:

                                break;
                        }
                        data.add(tempBean);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        } else {
            Snackbar.make(searchEtInput, JSONUtils.getString(obj, "message"), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected int GetLayout() {
        return R.layout.activity_evaluate;
    }

    @Override
    public void onMessage(String str, String cmd, int code) {
        Log.e("Conkerererer", str);
        Observable.just(str)
                .map(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        return JSONUtils.StringToJSON(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hidLoading() {
        super.hidLoading();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        ex.selectCommunity("conn.selectCommunity", searchEtInput.getText().toString().trim());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    class BaseAdapter extends CommonAdapter<TempBean> {

        BaseAdapter(Context context, List<TempBean> list, int layoutId) {
            super(context, list, layoutId);

        }

        @Override
        public void setViewContent(CommonViewHolder viewHolder, TempBean tempBean) {
            TextView tv = viewHolder.getView(R.id.listview_text);
            String lcsStr = tempBean.Key;
            if (tempBean.Data2.equals("lc")) {
                lcsStr = tempBean.Key + "层";

            } else if (tempBean.Data2.equals("hbh")) {
                lcsStr = tempBean.Key + "室";
            }
            tv.setText(lcsStr);
            tv.setTextSize(19);
            tv.setTypeface(Typeface.DEFAULT_BOLD);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv.getLayoutParams();
            params.setMargins(15, 10, 0, 10);
            tv.setLayoutParams(params);
        }

    }


}
