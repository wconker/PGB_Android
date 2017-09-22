package com.android.pgb.Activity.Activity_CK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pgb.Activity.Activity_Login;
import com.android.pgb.Activity.Activity_XJ.Activity_XjInfo;
import com.android.pgb.Activity.Core.Select_DX;
import com.android.pgb.Activity.Core.Select_DXMore;
import com.android.pgb.Bean.Record;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.Interface.PhotoClick;
import com.android.pgb.R;
import com.android.pgb.Utils.Dialog;
import com.android.pgb.Utils.Image.Photo;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.Net.network;
import com.android.pgb.View.CBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class Activity_Record extends Activity implements ExChange.CallBackForData, View.OnClickListener {

    private ExChange ex;
    private List<Record> listBean;
    private LinearLayout box;
    private int CurrentImage = 0;
    private int ImageId = 0;
    private int zbId = 0;
    private android.app.Dialog dialog;
    private ArrayList<String> selectedPhotos;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String value = "";
            JSONObject stringToJSON;
            switch (msg.what) {
                case 110:
                    dialog.cancel();
                    value = msg.getData().getString("getKcjlList");
                    stringToJSON = JSONUtils.StringToJSON(value);
                    JSONArray jsonArray = JSONUtils.getJSONArray(stringToJSON, "data");
                    listBean = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            Record record = new Record(jsonObject);
                            listBean.add(record);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ConstructBuildView(listBean);
                    break;
                case 201:
                    value = msg.getData().getString("updateKcjl");
                    stringToJSON = JSONUtils.StringToJSON(value);
                    if (JSONUtils.getInt(stringToJSON, "code", 0) != 0)
                        Toast.makeText(Activity_Record.this, JSONUtils.getString(stringToJSON, "message"), Toast.LENGTH_SHORT).show();
                    break;
            }


        }
    };

    //防止无限加载
    CountDownTimer countDownTimer = new CountDownTimer(5 * 1000, 1000) {
        @Override
        public void onTick(long l) {
        }

        @Override
        public void onFinish() {

            if (dialog.isShowing()) {
                network.CheckNetWork(Activity_Record.this);
                dialog.setCanceledOnTouchOutside(true);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__record);
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
    }
    @Override
    protected void onResume() {
        super.onResume();
        dialog = Dialog.showWaitDialog(Activity_Record.this, "加载中...", false, false);
        countDownTimer.start();
        InitView();
    }

    private String from = "";

    void InitView() {
        ex = new ExChange(this);
        from = getIntent().getStringExtra("from");
        box = (LinearLayout) this.findViewById(R.id.box);
        zbId = getIntent().getIntExtra("zbid", 0);
        if (from.equals("to")) {
            ex.getKcjlList_to(zbId);
        } else {
            ex.getKcjlList(zbId);
        }
    }

    @Override
    public void onMessage(String str, String cmd, int code) {
        Message msg = Message.obtain();
        Bundle bundle;
        Log.e("Conker", str);
        switch (cmd) {
            case "business.getKcjlList":
                bundle = new Bundle();
                bundle.putString("getKcjlList", str);
                msg.what = 110;
                msg.setData(bundle);
                handler.sendMessage(msg);
                break;
            case "business.updateKcjl":
                bundle = new Bundle();
                bundle.putString("updateKcjl", str);
                msg.what = 201;
                msg.setData(bundle);
                handler.sendMessage(msg);
                break;
            case "business.getKcjlList_to":
                bundle = new Bundle();
                bundle.putString("getKcjlList", str);
                msg.what = 110;
                msg.setData(bundle);
                handler.sendMessage(msg);
                break;
            case "business.updateKcjl_to":
                bundle = new Bundle();
                bundle.putString("updateKcjl", str);
                msg.what = 201;
                msg.setData(bundle);
                handler.sendMessage(msg);
                break;
        }
    }

    @Override
    public void onClick(View view) {

        int vid = view.getId();
        switch (vid) {
        }
    }

    //描述
    View getMs(String tit) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_ms, null);
        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(tit);
        return view;
    }

    //单选
    View getDxk(String tit, String value, final DxClick dxClick, int pos) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_dxk, null);
        TextView textView = (TextView) view.findViewById(R.id.dxk_title);
        TextView textView_value = (TextView) view.findViewById(R.id.dxk_value);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dxk_layout);
        textView_value.setText(pos + "");
        textView.setText(tit);
        textView_value.setText(value);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dxClick.selectValue();
            }
        });
        view.setTag(pos);
        return view;
    }

    interface DxClick {
        void selectValue();
    }

    //单行输入
    View getDx(final String title, final String value, String hin, int pos) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_dx, null);
        final EditText editText = (EditText) view.findViewById(R.id.txt_value);
        editText.setHint(hin);
        editText.setText(value);
        TextView tv = (TextView) view.findViewById(R.id.txt_title);
        tv.setText(title);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {

                    if (from.equals("to")) {
                        ex.updateKcjl_to(zbId, title, editText.getText().toString().trim());
                    } else {

                        ex.updateKcjl(zbId, title, editText.getText().toString().trim());
                    }

                }
            }
        });
        view.setTag(pos);
        return view;
    }

    View getDxByNumber(final String title, final String value, String hin, int pos) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_dx, null);
        EditText editText = (EditText) view.findViewById(R.id.txt_value);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint(hin);
        editText.setText(value);
        TextView tv = (TextView) view.findViewById(R.id.txt_title);
        tv.setText(title);
        view.setTag(pos);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {

                    if (from.equals("to")) {
                        ex.updateKcjl_to(zbId, title, value);
                    } else {

                        ex.updateKcjl(zbId, title, value);
                    }

                }
            }
        });

        return view;
    }

    //图片控件
    View getImageList(String img_title, String ms, String title_fjbs, final int pos, JSONArray list) {
        View view = LayoutInflater.from(this).inflate(R.layout.layou_img, null);
        Photo p = (Photo) view.findViewById(R.id.photo);
        final List<String> idList = new ArrayList<>();
        ArrayList<String> pList = new ArrayList<>();
        if (list != null) {

            for (int c = 0; c < list.length(); c++) {
                //加载网络中获取的图片地址
                try {
                    idList.add(JSONUtils.getString((JSONObject) list.get(c), "id").toString());
                    pList.add(JSONUtils.getString((JSONObject) list.get(c), "img_url").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            selectedPhotos = pList;
            p.setUrl(selectedPhotos);

        }
        TextView tv = (TextView) view.findViewById(R.id.img_title);
        TextView tit_ms = (TextView) view.findViewById(R.id.img_ms);
        TextView fjBs = (TextView) view.findViewById(R.id.fjbs);
        fjBs.setText(title_fjbs);
        tv.setText(img_title);
        tit_ms.setText(ms);
        p.setActivity(Activity_Record.this);
        p.IfDele = true;
        p.setPhotoClick(new PhotoClick() {
            @Override
            public void imgClick(int _pos) {
                CurrentImage = pos;
            }

            @Override
            public void delImage(int _pos) {
                com.android.pgb.Utils.Log.MyToast(idList.get(_pos) + "", Activity_Record.this);
                ex.del_ftpimg(Integer.parseInt(idList.get(_pos)));
            }
        });
        view.setTag(pos);
        return view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        for (int child = 0; child < box.getChildCount(); child++) {
            if (box.getChildAt(child).getTag() != null) {
                //单选多选返回
                if ((int) box.getChildAt(child).getTag() == resultCode) {
                    TextView t = (TextView) box.getChildAt(child).findViewById(R.id.dxk_value);
                    TextView tit_tit = (TextView) box.getChildAt(child).findViewById(R.id.dxk_title);
                    Bundle b = data.getExtras(); //data为B中回传的Intent
                    String str = b.getString("StrValue");//str即为回传的值
                    t.setText(str + "");
                    if (from.equals("to")) {
                        ex.updateKcjl_to(zbId, tit_tit.getText().toString().trim(), str);

                    } else {
                        ex.updateKcjl(zbId, tit_tit.getText().toString().trim(), str);
                    }

                }
                //图片上传
                if (resultCode == RESULT_OK &&
                        (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {
                    if (CurrentImage == (int) box.getChildAt(child).getTag()) {
                        Photo mp = (Photo) box.getChildAt(child).findViewById(R.id.photo);
                        TextView fjBs = (TextView) box.getChildAt(child).findViewById(R.id.fjbs);
                        mp.setResult(data);
                        if (selectedPhotos != null) {
                            selectedPhotos.addAll(mp.getPhoto());
                        } else {
                            selectedPhotos = mp.getPhoto();
                        }
                        if (from.equals("to")) {
                            for (int c = 0; c < selectedPhotos.size(); c++) {
                                Map<String, String> map = new HashMap<>();
                                map.put("userid", ex.userId.toString());
                                map.put("imgtype", "查勘记录");
                                map.put("fjbs", fjBs.getText().toString().trim());
                                map.put("ckid", String.valueOf(zbId));
                                map.put("imgname", "");
                                ex.send(new File(selectedPhotos.get(c)), map);
                            }

                        } else {
                            for (int c = 0; c < selectedPhotos.size(); c++) {
                                Map<String, String> map = new HashMap<>();
                                map.put("userid", ex.userId.toString());
                                map.put("imgtype", "查勘记录");
                                map.put("fjbs", fjBs.getText().toString().trim());
                                map.put("zbid", String.valueOf(zbId));
                                map.put("imgname", "");
                                ex.send(new File(selectedPhotos.get(c)), map);
                            }
                        }

                    }
                }
            }
        }
    }

    void ConstructBuildView(final List<Record> list) {
        for (int con = 0; con < list.size(); con++) {
            switch (list.get(con).getLx()) {
                case "说明文字":
                    box.addView(getMs(list.get(con).getBt()));
                    break;
                case "单行输入框":
                    box.addView(getDx(list.get(con).getBt(), list.get(con).getSjxz(), list.get(con).getTswz(), con));
                    break;
                case "单选框":
                    final int finalCon = con;
                    box.addView(getDxk(list.get(con).getBt(), list.get(con).getSjxz(), new DxClick() {
                        @Override
                        public void selectValue() {
                            Intent i = new Intent(Activity_Record.this, Select_DX.class);
                            i.putExtra("value", list.get(finalCon).getXx());
                            i.putExtra("index", finalCon);
                            startActivityForResult(i, finalCon);
                        }
                    }, con));
                    break;
                case "多选框":
                    final int finalCon_More = con;
                    box.addView(getDxk(list.get(con).getBt(), list.get(con).getSjxz(), new DxClick() {
                        @Override
                        public void selectValue() {
                            Intent i = new Intent(Activity_Record.this, Select_DXMore.class);
                            i.putExtra("value", list.get(finalCon_More).getXx());
                            i.putExtra("index", finalCon_More);
                            startActivityForResult(i, finalCon_More);
                        }
                    }, con));
                    break;
                case "图片":
                    box.addView(getImageList(list.get(con).getBt(), list.get(con).getTswz(), list.get(con).getSjxz(), con, list.get(con).getFiles()));
                    break;
                case "数字输入框":
                    box.addView(getDxByNumber(list.get(con).getBt(), list.get(con).getSjxz(), list.get(con).getTswz(), con));
                    break;

            }


        }

    }

}
