package com.android.pgb.Activity.Activity_Tool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pgb.Activity.Activity_CK.Activity_Record;
import com.android.pgb.Bean.ProvinceBean;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.Log;
import com.android.pgb.View.CBarView;
import com.android.pgb.alipay.PayResult;
import com.bigkoo.pickerview.OptionsPickerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddKc extends Activity implements ExChange.CallBackForData, View.OnClickListener {


    @Bind(R.id.zblb_tv)
    TextView zblbTv;
    @Bind(R.id.zblb)
    LinearLayout zblb;
    @Bind(R.id.bdw_et)
    EditText bdwEt;
    @Bind(R.id.bdw)
    LinearLayout bdw;
    @Bind(R.id.kcb_tv)
    TextView kcbTv;
    @Bind(R.id.kcb)
    LinearLayout kcb;
    @Bind(R.id.bz_tv)
    EditText bzTv;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    private final int SUCCESS = 200;
    private String _str = "";
    private final int ERROR = 404;
    private ArrayList<ProvinceBean> pickviewdata = new ArrayList<>();
    private ExChange ex;
    private OptionsPickerView optionsPickerView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SUCCESS) {
                Log.MyToast(ex.getMessageInfo(_str), AddKc.this);
                Intent i = new Intent(AddKc.this, Activity_Record.class);
                i.putExtra("zbid", Integer.valueOf(TempZbid));
                i.putExtra("from", "to");
                startActivity(i);
                finish();
            } else if (msg.what == ERROR) {
                Log.MyToast(ex.getMessageInfo(_str), AddKc.this);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kc);
        ButterKnife.bind(this);
        new CBarView(this, new CBarView.OnClickListener() {
            @Override
            public void onLeftClick() {
                super.onLeftClick();
                finish();
            }
        }, null);
        ex = new ExChange(this);
        ex.getSyType("zb");
        kcb.setOnClickListener(this);
        zblb.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        optionsPickerView = new OptionsPickerView(this);
    }

    /***
     * 得到勘察表
     */
    private void getKcb() {
        pickviewdata.clear();
        if (array != null) {
            JSONArray Jarr = null;
            try {
                Jarr = JSONUtils.getJSONArray((JSONObject) array.get(0), "kcbmb");

                for (int i = 0; i < Jarr.length(); i++) {
                    pickviewdata.add(new ProvinceBean(
                            JSONUtils.getLong((JSONObject) Jarr.get(i), "MBBH"),
                            JSONUtils.getString((JSONObject) Jarr.get(i), "MBMC"),
                            "",
                            ""));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    // FIXME: 2017/3/21  pickview
    private void setPickerView(final String keyWord) {
        //三级联动效果
        optionsPickerView.setPicker(pickviewdata, null, null, false);
        optionsPickerView.setCyclic(false, false, false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        optionsPickerView.setSelectOptions(1, 1, 1);
        optionsPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置

                switch (keyWord) {
                    case "choose_zblx":
                        zblbTv.setText(pickviewdata.get(options1).getName());
                        zblbTv.setTextColor(Color.BLACK);
                        zblbTvId = (int) pickviewdata.get(options1).getId();
                        break;
                    case "choose_kcb":
                        kcbTv.setText(pickviewdata.get(options1).getName());
                        kcbTv.setTextColor(Color.BLACK);
                        kcbmb = (int) pickviewdata.get(options1).getId();
                        break;

                }


            }
        });

    }


    private int zblbTvId = 0;
    private int kcbmb = 0;
    private String TempZbid = "";
    JSONArray array;

    @Override
    public void onMessage(String str, String cmd, int code) {
        JSONObject object = JSONUtils.StringToJSON(str);
        if (cmd.equals("conn.getSyType")) {

            array = JSONUtils.getJSONArray(object, "data");
        }
        if (cmd.equals("business.addCkb")) {
            _str = str;
            if (code == 0) {

                try {
                    JSONObject addKCResult = (JSONObject) object.get("data");
                    TempZbid = JSONUtils.getString(addKCResult, "ckid");
                    handler.sendEmptyMessage(SUCCESS);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                handler.sendEmptyMessage(ERROR);
            }
        }
    }

    /***
     * 得到物业类型数据列表
     */
    private void getWylxList() throws JSONException {
        pickviewdata.clear();
        if (array != null) {
            JSONArray Jarr = JSONUtils.getJSONArray((JSONObject) array.get(0), "zblb");
            for (int i = 0; i < Jarr.length(); i++) {
                pickviewdata.add(
                        new ProvinceBean(JSONUtils.getLong((JSONObject) Jarr.get(i), "value"),
                                JSONUtils.getString((JSONObject) Jarr.get(i), "name"),
                                "",
                                ""));

            }


        }
    }

    @Override
    public void onClick(View view) {
        try {
            int vid = view.getId();
            switch (vid) {
                case R.id.kcb:
                    getKcb();
                    setPickerView("choose_kcb");
                    optionsPickerView.show();
                    break;
                case R.id.zblb:
                    getWylxList();
                    setPickerView("choose_zblx");
                    optionsPickerView.show();
                    break;
                case R.id.btn_submit:
                    ex.addCkb(String.valueOf(zblbTvId),
                            bdwEt.getText().toString().trim(),
                            String.valueOf(kcbmb),
                            bzTv.getText().toString().trim());
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
