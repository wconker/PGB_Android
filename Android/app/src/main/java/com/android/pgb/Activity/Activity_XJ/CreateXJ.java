package com.android.pgb.Activity.Activity_XJ;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pgb.Activity.Activity_CK.CreateKC;
import com.android.pgb.Activity.Activity_Login;
import com.android.pgb.Activity.Activity_Me.Activity_Auth;
import com.android.pgb.Activity.Activity_Tool.Maptools;
import com.android.pgb.Activity.Core.SelectCity;
import com.android.pgb.Activity.Core.SelectCompanyPeople;
import com.android.pgb.Activity.PayConfirm;
import com.android.pgb.Bean.Group;
import com.android.pgb.Bean.ProvinceBean;
import com.android.pgb.Bean.UserInfo;
import com.android.pgb.Bean.XJBean;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.CheckUserInfo;
import com.android.pgb.Utils.Dialog;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.Log;
import com.android.pgb.Utils.PhotoPicker.PhotoAdapter;
import com.android.pgb.Utils.PhotoPicker.RecyclerItemClickListener;
import com.android.pgb.Utils.SharedPreferences.SharedPreferencesUtils;
import com.android.pgb.View.CBarView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.model.IPickerViewData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;


public class CreateXJ extends Activity implements View.OnClickListener, ExChange.CallBackForData {
    private android.app.Dialog dialog;
    private ExChange ex;
    private LinearLayout choose_wylx, choosecity, choosexjfs, chooseyxsj, choosegroup;
    private EditText xjbdw_et, bz_et, dtbj_et, xqrs_et, mj_et;
    private TextView wylx_tv, city_tv, xjfs_tv, yxsj_tv, group_tv;
    private OptionsPickerView optionsPickerView, cityPickerView;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<ProvinceBean>> options2Items = new ArrayList<>();
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private PhotoAdapter photoAdapter;
    private XJBean Info_xjbean;
    private Button btn_submit;
    private View group_view;
    private int DQDM = 0;
    private int xjfsIndex = -1;
    private JSONArray xjdx;
    private RecyclerView recyclerView;
    private ArrayList<ProvinceBean> options2_;
    private int XJID = 0;
    private int YXSJ = 0;
    private UserInfo userInfo;
    private Button chooseformap;
    private final int MAPCODE=198;

    private void initControl() {
        optionsPickerView = new OptionsPickerView(this);
        cityPickerView = new OptionsPickerView(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);
        photoAdapter.setClick(new PhotoAdapter.ClickOnList() {
            @Override
            public void Box(int pos) {
                if (photoAdapter.getItemViewType(pos) == 1) {
                    PhotoPicker.builder()
                            .setPhotoCount(9)
                            .setShowCamera(true)
                            .setPreviewEnabled(false)
                            .setSelected(selectedPhotos)
                            .start(CreateXJ.this);
                } else {
                    PhotoPreview.builder()
                            .setPhotos(selectedPhotos)
                            .setCurrentItem(pos)
                            .start(CreateXJ.this);
                }
            }

            @Override
            public void Close(int pos) {
                selectedPhotos.remove(pos);
                photoAdapter.notifyDataSetChanged();
            }
        });
        mj_et = (EditText) this.findViewById(R.id.mj_et);
        xjbdw_et = (EditText) this.findViewById(R.id.xjbdw_et);
        bz_et = (EditText) this.findViewById(R.id.bz_et);
        dtbj_et = (EditText) this.findViewById(R.id.dtbj_et);
        xqrs_et = (EditText) this.findViewById(R.id.xqrs_et);

        group_view = this.findViewById(R.id.group_view);
        choose_wylx = (LinearLayout) this.findViewById(R.id.choose_wylx);
        choosexjfs = (LinearLayout) this.findViewById(R.id.choosexjfs);
        choosecity = (LinearLayout) this.findViewById(R.id.choosecity);
        chooseyxsj = (LinearLayout) this.findViewById(R.id.chooseyxsj);
        choosegroup = (LinearLayout) this.findViewById(R.id.choosegroup);

        wylx_tv = (TextView) this.findViewById(R.id.wylx_tv);
        xjfs_tv = (TextView) this.findViewById(R.id.xjfs_tv);
        city_tv = (TextView) this.findViewById(R.id.city_tv);
        xjfs_tv = (TextView) this.findViewById(R.id.xjfs_tv);
        yxsj_tv = (TextView) this.findViewById(R.id.yxsj_tv);
        group_tv = (TextView) this.findViewById(R.id.group_tv);
        choose_wylx.setOnClickListener(this);
        choosecity.setOnClickListener(this);
        choosexjfs.setOnClickListener(this);
        chooseyxsj.setOnClickListener(this);
        choosegroup.setOnClickListener(this);
        btn_submit = (Button) this.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        chooseformap = (Button) this.findViewById(R.id.chooseformap);
        chooseformap.setOnClickListener(this);

        //能获取到数据提前赋值
        userInfo = (UserInfo) SharedPreferencesUtils.readObject(this, "userinfo");
        SharedPreferences sp = SharedPreferencesUtils.getSharedPreferences(this);

        if (sp.getInt("dqdm", 0) > 0 && !sp.getString("dqmc", "").equals("")) {
            DQDM = sp.getInt("dqdm", 0);
            city_tv.setText(sp.getString("dqmc", ""));
        }


    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_xj);
        new CBarView(this, new CBarView.OnClickListener() {
            @Override
            public void onLeftClick() {
                super.onLeftClick();
                new Dialog(CreateXJ.this, "提示：", "放弃编辑并退出？", new Dialog.dialogButton() {
                    @Override
                    public void ok(DialogInterface dialog, int which) {
                        finish();
                    }

                    @Override
                    public void cannot(DialogInterface dialog, int which) {

                    }
                });

            }
        }, null);
        ex = new ExChange(this);
        //获取城市列表 ,现在改成跳转式
        // ex.getCityList("p", "0");

        XJID = getIntent().getIntExtra("xjId", 0);
        if (XJID > 0) {
            ex.getxjInfo(0, XJID);
        }
        initControl();
    }

    /***
     * 系统参数
     */
    private JSONArray InitSysType(String name) {
        JSONArray J = null;
        try {
            SharedPreferences sp = SharedPreferencesUtils.getSharedPreferences(CreateXJ.this);
            JSONObject jsonObject = (JSONObject) JSONUtils.getJSONArrayFromStr(sp.getString("sysType", "")).get(0);
            J = (JSONArray) jsonObject.get(name);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return J;
    }

    private ArrayList<ProvinceBean> pickviewdata = new ArrayList<>();

    /***
     * 城市列表
     */
    private void setCityView() {

        //三级联动效果
        cityPickerView.setPicker(options1Items, options2Items, null, true);
        //设置选择的三级单位
        // pwOptions.setLabels("省", "市");
        cityPickerView.setTitle("选择城市");
        cityPickerView.setCyclic(false, true, false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        cityPickerView.setSelectOptions(1, 1, 1);
        cityPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2).getPickerViewText();
                city_tv.setText(options2Items.get(options1).get(option2).getPickerViewText());
                DQDM = (int) options2Items.get(options1).get(option2).getId();

            }
        });

    }

    /***
     * 得到物业类型数据列表
     */
    private void getWylxList() {
        optionsPickerView.setTitle("物业类型");
        JSONArray jarr = InitSysType("wylx");
        pickviewdata.clear();
        for (int j = 0; j < jarr.length(); j++) {
            JSONObject jobject;
            try {
                jobject = (JSONObject) jarr.get(j);
                pickviewdata.add(
                        new ProvinceBean(Long.parseLong(jobject.get("value").toString()),
                                jobject.get("name").toString(), "",
                                ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    /***
     * 有效时间物业类型数据列表
     */
    private void getYxsjList() {
        optionsPickerView.setTitle("有效时间");
        JSONArray jarr = InitSysType("yxsj");
        pickviewdata.clear();
        for (int j = 0; j < jarr.length(); j++) {
            JSONObject jobject;
            try {
                jobject = (JSONObject) jarr.get(j);
                pickviewdata.add(new ProvinceBean(Long.parseLong(jobject.get("value").toString()), jobject.get("name").toString(), "", ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * 得到询价方式数据列表
     */
    private void getXjfsList() {
        optionsPickerView.setTitle("询价方式");
        pickviewdata.clear();
        pickviewdata.add(new ProvinceBean(0, "外部询价", "", ""));
        pickviewdata.add(new ProvinceBean(1, "内部询价", "", ""));
    }


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
                    case "choose_wylx":
                        wylx_tv.setText(pickviewdata.get(options1).getName());
                        wylx_tv.setTextColor(Color.BLACK);
                        break;
                    case "choosexjfs":
                        //处理内部询价和外部询价
                        xjfsIndex = (int) pickviewdata.get(options1).getId();
                        if (pickviewdata.get(options1).getId() == 1) {   //外部询价
                            choosegroup.setVisibility(View.VISIBLE);
                            group_view.setVisibility(View.VISIBLE);
                        } else {                                         //内部询价
                            choosegroup.setVisibility(View.GONE);
                            group_view.setVisibility(View.GONE);
                            group_tv.setText("");
                            //为防止切换询价方式时数据保留，需要清空字段值
                            putXjdx = "";
                            GroupDx = "";


                        }
                        group_tv.setTextColor(Color.BLACK);
                        xjfs_tv.setText(pickviewdata.get(options1).getName());
                        xjfs_tv.setTextColor(Color.BLACK);
                        break;
                    case "chooseyxsj":
                        yxsj_tv.setText(pickviewdata.get(options1).getName());
                        yxsj_tv.setTextColor(Color.BLACK);
                        YXSJ = (int) pickviewdata.get(options1).getId();
                        break;
                }

            }
        });

    }

    @Override
    public void onClick(View v) {

        int cid = v.getId();
        switch (cid) {
            case R.id.chooseformap:
                Intent intentForMap= new Intent(CreateXJ.this, Maptools.class);
                startActivityForResult(intentForMap,MAPCODE);
                break;
            case R.id.choose_wylx:
                getWylxList();
                setPickerView("choose_wylx");
                optionsPickerView.show();
                break;
            case R.id.choosecity:
                Intent intent2 = new Intent(CreateXJ.this, SelectCity.class);
                startActivityForResult(intent2, 201);
                break;
            case R.id.choosexjfs:
                getXjfsList();
                setPickerView("choosexjfs");
                optionsPickerView.show();
                break;
            case R.id.chooseyxsj:
                getYxsjList();
                setPickerView("chooseyxsj");
                optionsPickerView.show();
                break;
            case R.id.choosegroup:
                Intent intent = new Intent(CreateXJ.this, SelectCompanyPeople.class);
                startActivityForResult(intent, 200);
                break;
            case R.id.btn_submit:
                CheckUserInfo.setUserInfo(userInfo);
                if (!CheckUserInfo.checkSmrz()) {

                    new Dialog(CreateXJ.this, "提示：",
                            "实名认证后才可发布，是否前往认证？",
                            new Dialog.dialogButton() {
                                @Override
                                public void ok(DialogInterface dialog, int which) {
                                    Intent i = new Intent(CreateXJ.this, Activity_Auth.class);
                                    i.putExtra("type", "sm");
                                    startActivity(i);
                                }

                                @Override
                                public void cannot(DialogInterface dialog, int which) {
                                }
                            });

                    return;
                }

                DataCneter();
                break;

        }
    }

    private void setControl() {
        wylx_tv.setText(Info_xjbean.getWylx());
        xjbdw_et.setText(Info_xjbean.getXjbdw());
        //报酬
        dtbj_et.setText(Info_xjbean.getBc() / 0.85 + "");
        bz_et.setText(Info_xjbean.getBz());
        xjfs_tv.setText(Info_xjbean.getXjlb() == 0 ? "外部询价" : "内部询价");
        mj_et.setText(Info_xjbean.getMj());
        city_tv.setText(Info_xjbean.getSzscmc());
        DQDM = Integer.parseInt(Info_xjbean.getSzcs());
        xjfsIndex = Info_xjbean.getNbxj();
        yxsj_tv.setText("" + Info_xjbean.getXjyxsj() / 60 + "小时");
        xqrs_et.setText(Info_xjbean.getJsbjtj() + "");
        YXSJ = Info_xjbean.getXjyxsj();
        if (Info_xjbean.getNb_list() != null) {
            String str = "";
            GroupDx = "";
            putXjdx = "";
            xjdx = new JSONArray();
            choosegroup.setVisibility(View.VISIBLE);
            group_view.setVisibility(View.VISIBLE);
            for (int p = 0; p < Info_xjbean.getNb_list().length(); p++) {
                try {
                    str += ((JSONObject) ((JSONArray) Info_xjbean.getNb_list().get(p)).get(0)).get("xm") + "、";
                    group_tv.setText(str);
                    putXjdx += ((JSONObject) ((JSONArray) Info_xjbean.getNb_list().get(p)).get(0)).get("userid") + "|";
                    GroupDx += ((JSONObject) ((JSONArray) Info_xjbean.getNb_list().get(p)).get(0)).get("xm") + "、";
                    xjdx.put(((JSONObject) ((JSONArray) Info_xjbean.getNb_list().get(p)).get(0)).get("userid"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private XJBean xjBean;

    // FIXME: 2017/3/22  设置bean并提交
    private void DataCneter() {
        xjBean = new XJBean();
        Intent pay_to = new Intent(CreateXJ.this, PayConfirm.class);

        try {

            //这里有些冗余，xjBean 赋值的时候可以做统一处理

            if (XJID > 0) {
                xjBean.setXjbdw(xjbdw_et.getText().toString());
                xjBean.setBc(Double.parseDouble(dtbj_et.getText().toString()));
                xjBean.setJsbjtj(Integer.parseInt(xqrs_et.getText().toString()));
                xjBean.setYxbjs(Integer.parseInt(xqrs_et.getText().toString()));
                xjBean.setFwxm(xjfs_tv.getText().toString());
                xjBean.setBz(bz_et.getText().toString());
                xjBean.setWylx(wylx_tv.getText().toString());
                xjBean.setXjdx(xjdx);
                xjBean.setSzscmc(city_tv.getText().toString());
                xjBean.setXjyxsj(YXSJ);
                xjBean.setZhlx("充值账户");
                xjBean.setMj(mj_et.getText().toString());
                xjBean.setNbxj(xjfsIndex);
                xjBean.setSzcs(String.valueOf(DQDM));
                xjBean.setXjlb(Info_xjbean.getXjlb());
                if (Double.parseDouble(dtbj_et.getText().toString()) * Integer.parseInt(xqrs_et.getText().toString()) <= 0) {
                    ex.endXj(xjBean.getXjid());
                    ex.addxj(xjBean);
                } else {
                    pay_to.putExtra("re", 100);
                    pay_to.putExtra("type", "询价");
                    pay_to.putExtra("group", GroupDx);
                    pay_to.putStringArrayListExtra("img", selectedPhotos);
                    pay_to.putExtra("putxjdx", putXjdx);
                    pay_to.putExtra("xjbean", xjBean);
                    startActivityForResult(pay_to, 100001);
                }
            } else {
                xjBean.setXjbdw(xjbdw_et.getText().toString());
                xjBean.setBc(Double.parseDouble(dtbj_et.getText().toString()));
                xjBean.setJsbjtj(Integer.parseInt(xqrs_et.getText().toString()));
                xjBean.setYxbjs(Integer.parseInt(xqrs_et.getText().toString()));
                xjBean.setFwxm(xjfs_tv.getText().toString());
                xjBean.setBz(bz_et.getText().toString());
                xjBean.setWylx(wylx_tv.getText().toString());
                xjBean.setXjdx(xjdx);
                xjBean.setXjyxsj(YXSJ);
                xjBean.setSzscmc(city_tv.getText().toString());
                xjBean.setZhlx("充值账户");
                xjBean.setMj(mj_et.getText().toString());
                xjBean.setNbxj(xjfsIndex);
                xjBean.setSzcs(String.valueOf(DQDM));
                xjBean.setXjlb(xjfsIndex);

                if (Double.parseDouble(dtbj_et.getText().toString()) * Integer.parseInt(xqrs_et.getText().toString()) <= 0) {
                    //设置try 防异常
                    try {
                        ex.addxj(xjBean);
                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    pay_to.putExtra("re", 10);
                    pay_to.putExtra("type", "询价");
                    pay_to.putExtra("group", GroupDx);
                    pay_to.putStringArrayListExtra("img", selectedPhotos);
                    pay_to.putExtra("putxjdx", putXjdx);
                    pay_to.putExtra("xjbean", xjBean);
                    startActivityForResult(pay_to, 100001);
                }

            }
        } catch (Exception e) {
            Toast.makeText(this, "请检查信息完整性！", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;


        if(requestCode==MAPCODE)
        {
            android.util.Log.e("resultCode" + requestCode + "", "requestCode" + requestCode);

            xjbdw_et.setText(data.getStringExtra("addressText"));
        }

        if (resultCode == 9000 && requestCode == 100001) {
            finish();
        }
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {
            List<String> photos;
            photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
        if (resultCode == 230) {
            city_tv.setText(data.getStringExtra("dqmc"));
            DQDM = data.getIntExtra("dqdm", 0);
        }
        if (resultCode == 200) {
            List<Group> g = (List<Group>) data.getBundleExtra("group").getSerializable("value");
            //赋值之前先初始化，避免数据残留
            putXjdx = "";
            GroupDx = "";
            xjdx = new JSONArray();
            for (int u = 0; u < g.size(); u++) {
                xjdx.put(g.get(u).getUserid());
                putXjdx += g.get(u).getUserid() + "|";
                GroupDx += g.get(u).getXm() + "、";
            }
            xqrs_et.setText(String.valueOf(xjdx.length()));
            xqrs_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    if (!editable.toString().equals("")) {
                        if (xjdx.length() < Integer.parseInt(editable.toString())) {
                            Log.MyToast("需求条数不可大于选择人数！",
                                    CreateXJ.this);
                            xqrs_et.setText("");
                        }
                    }
                }
            });
            group_tv.setText(GroupDx);
        }
    }

    String GroupDx = "";
    String putXjdx = "";
    private int ImageCount = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 101:
                    setControl();
                    break;
                case 3333:
                    Toast.makeText(CreateXJ.this,
                            ex.getMessageInfo(_UpdateXj),
                            Toast.LENGTH_LONG).show();
                    break;
                case 3334:     //采用弹出式的城市选择，现在改成跳转式
                    try {
                        JSONObject obj;
                        obj = new JSONObject(_getCityList);
                        JSONArray arr = JSONUtils.getJSONArray(obj, "data");
                        if (((JSONObject) arr.get(0)).get("fl").equals("P")) {
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jsonObj = (JSONObject) arr.get(i);
                                options1Items.add(new ProvinceBean(Long.parseLong(jsonObj.get("dqdm").toString()),
                                        jsonObj.get("dqmc").toString(),
                                        jsonObj.get("fl").toString(), ""));
                                JSONArray cityarr = JSONUtils.getJSONArray(jsonObj, "City");
                                if (cityarr.length() > 0) {
                                    options2_ = new ArrayList<>();
                                    for (int c = 0; c < cityarr.length(); c++) {
                                        JSONObject cityObj = (JSONObject) cityarr.get(c);
                                        options2_.add(new ProvinceBean(Long.parseLong(cityObj.get("dqdm").toString()),
                                                cityObj.get("dqmc").toString(),
                                                "",
                                                ""));
                                    }
                                    options2Items.add(options2_);
                                }
                            }
                            setCityView();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 233:  //错误提示
                    Toast.makeText(CreateXJ.this, ex.getMessageInfo(_str), Toast.LENGTH_LONG).show();
                    break;
                case 3335: //图片也上传成功了执行
                    ImageCount++;
                    if (ImageCount == selectedPhotos.size()) {
                        dialog.cancel();
                        if (xjfsIndex == 0) {
                            Intent pay_to = new Intent(CreateXJ.this, PayConfirm.class);
                            pay_to.putExtra("type", "询价");
                            pay_to.putExtra("xjid", Pay_xjid);
                            startActivityForResult(pay_to, 100001);
                        } else {
                            finish();
                        }
                    }
                    break;
                case 201: //提交询价之后提交图片
                    JSONObject object = JSONUtils.StringToJSON(_addxj);
                    try {
                        JSONObject data = (JSONObject) object.get("data");
                        Pay_xjid = (int) data.get("xjid");
                        dialog = Dialog.showWaitDialog(CreateXJ.this, "加载中...", false, false);
                        //处理没有图片上传的时候
                        if (selectedPhotos.size() <= 0) {
                            dialog.cancel();
                            if (xjfsIndex == 0) {
                                Intent pay_to = new Intent(CreateXJ.this, PayConfirm.class);
                                pay_to.putExtra("type", "询价");
                                pay_to.putExtra("xjid", Pay_xjid);
                                startActivityForResult(pay_to, 100001);
                            } else {
                                finish();
                            }
                        } else {
                            for (int c = 0; c < selectedPhotos.size(); c++) {
                                Map<String, String> map = new HashMap<>();
                                map.put("userid", ex.userId.toString());
                                map.put("imgtype", "产权证");
                                map.put("fjbs", data.get("fjbs").toString());
                                map.put("xjid", data.get("xjid").toString());
                                map.put("imgname", String.valueOf(c));
                                ex.send(new File(selectedPhotos.get(c)), map);
                            }
                        }
                        ImageCount = 0;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

            }

        }
    };


    private int Pay_xjid = 0;

    //打断返回按钮的事件防止误触
    @Override
    public void onBackPressed() {

        new Dialog(CreateXJ.this, "提示：",
                "放弃编辑并退出？",
                new Dialog.dialogButton() {
                    @Override
                    public void ok(DialogInterface dialog, int which) {
                        finish();
                    }

                    @Override
                    public void cannot(DialogInterface dialog, int which) {

                    }
                });
    }

    private String _str = "",
            _getCityList = "",
            _UpdateXj = "";

    private String _addxj = "";

    @Override
    public void onMessage(String str, String cmd, int code) {
        JSONObject obj;
        Message message = Message.obtain();
        _str = str;
        Log.e(str);

        //统一错误的返回
        if (code != 0) {
            _str = str;
            handler.sendEmptyMessage(233);
        }

        //上传图片信息
        if (cmd.equals("upload_img")) {
            handler.sendEmptyMessage(3335);
        }
        //添加询价成功
        if (cmd.equals("business.addxj")) {
            if (code == 0) {
                _addxj = str;
                handler.sendEmptyMessage(201);
            } else {
                _str = str;
                handler.sendEmptyMessage(233);
            }

        }

        //用于重新询价时获取信息
        if (cmd.equals("business.getxjInfo")) {

            obj = JSONUtils.StringToJSON(str);
            try {
                JSONObject xjObj = JSONUtils.getJSONArray(obj, "data").getJSONObject(0);

                Info_xjbean = new XJBean(xjObj);
                if (XJID > 0) {
                    message.what = 101;
                    handler.sendMessage(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        //重发功能返回，更新询价
        if (cmd.equals("business.UpdateXj")) {
            _UpdateXj = str;
            handler.sendEmptyMessage(3333);
        }
        if (cmd.equals("conn.getCityList")) {
            _getCityList = str;
            handler.sendEmptyMessage(3334);

        }
        if (cmd.equals("business.endXj")) {
            ex.addxj(xjBean);
        }


    }
}
