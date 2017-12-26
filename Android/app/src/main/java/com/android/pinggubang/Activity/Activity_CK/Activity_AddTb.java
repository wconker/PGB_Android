package com.android.pinggubang.Activity.Activity_CK;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.pinggubang.Activity.Activity_Me.Activity_Auth;
import com.android.pinggubang.Activity.Activity_XJ.Activity_addBJ;
import com.android.pinggubang.Bean.TBBean;
import com.android.pinggubang.Bean.UserInfo;
import com.android.pinggubang.Bean.ZBBean;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.CheckUserInfo;
import com.android.pinggubang.Utils.Dialog;
import com.android.pinggubang.Utils.Image.Photo;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.Utils.SharedPreferences.SharedPreferencesUtils;
import com.android.pinggubang.View.CBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class Activity_AddTb extends Activity implements ExChange.CallBackForData, View.OnClickListener {
    private ExChange ex;
    private TextView tb_bdw, tb_zbj;
    private EditText tb_tbj, tb_bz_et;
    private Button add_btn;
    private TBBean tbBean;
    private ZBBean zbBean;
    private ArrayList<String> selectedPhotos;
    private Photo photo;

    private int ImageCount = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 500) {
                setView();
            }
            if (msg.what == IMAGE_UP) {
                if (ImageCount == selectedPhotos.size()) {
                    com.android.pinggubang.Utils.Log.MyToast("提交成功", Activity_AddTb.this);
                    finish();
                }
            }
            if (msg.what == ERROR) {
                com.android.pinggubang.Utils.Log.MyToast(ex.getMessageInfo(_error), Activity_AddTb.this);
            }
            if (msg.what == 520) {
                JSONObject data;
                try {
                    data = new JSONObject(_addtb);
                    //开始上传图片
                    if (data.getInt("code") == 0) {
                        JSONObject imgUp = data.getJSONObject("data");
                        if(selectedPhotos!=null) {
                            if(selectedPhotos.size()>0) {
                                for (int c = 0; c < selectedPhotos.size(); c++) {
                                    Map<String, String> map = new HashMap<>();
                                    map.put("userid", ex.userId.toString());
                                    map.put("imgtype", "投标附件");
                                    map.put("fjbs", imgUp.getString("fjbs"));
                                    map.put("tbid", imgUp.getString("tbid"));
                                    map.put("imgname", String.valueOf(c));
                                    ex.send(new File(selectedPhotos.get(c)), map);
                                }
                            }else {
                                com.android.pinggubang.Utils.Log.MyToast("提交成功", Activity_AddTb.this);
                                finish();
                            }
                        }else {
                            com.android.pinggubang.Utils.Log.MyToast("提交成功", Activity_AddTb.this);
                            finish();
                        }

                    } else {
                        com.android.pinggubang.Utils.Log.MyToast(ex.getMessageInfo(_addtb), Activity_AddTb.this);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add_tb);

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


        ex = new ExChange(this);
        int zbId = getIntent().getIntExtra("zbid", 0);
        ex.getZbInfo(zbId);
        initView();

    }


    private void setView() {
        tb_bdw.setText(zbBean.getBdw());
        tb_zbj.setText(zbBean.getBc().toString() + "");
    }


    private void initView() {

        photo = (Photo) this.findViewById(R.id.photo);
        photo.setActivity(this);
        tb_bdw = (TextView) this.findViewById(R.id.tb_bdw);
        add_btn = (Button) this.findViewById(R.id.add_btn);
        tb_zbj = (TextView) this.findViewById(R.id.tb_zbj);
        tb_bz_et = (EditText) this.findViewById(R.id.tb_bz_et);
        tb_tbj = (EditText) this.findViewById(R.id.tb_tbj);

        add_btn.setOnClickListener(this);

    }

    private final int IMAGE_UP = 333;
    private String _addtb = "", _error = "";
    private final int ERROR = 404;

    @Override
    public void onMessage(String str, String cmd, int code) {

        Message message = Message.obtain();

        com.android.pinggubang.Utils.Log.e(str);
        if (code != 0) {
            _error = str;
            handler.sendEmptyMessage(ERROR);
        }

        if (cmd.equals("business.getZbInfo")) {
            try {
                JSONObject o = (JSONObject) JSONUtils.getJSONArray(JSONUtils.StringToJSON(str), "data").get(0);
                zbBean = new ZBBean(o);
                message.what = 500;
                handler.sendMessage(message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (cmd.equals("business.addtb")) {
            message.what = 520;
            handler.sendMessage(message);
            _addtb = str;
            ImageCount = 0;

        }

        if (cmd.equals("upload_img")) {     //上传图片信息

            ImageCount++;
            handler.sendEmptyMessage(IMAGE_UP);

        }

    }

    private String _str = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {
            photo.setResult(data);
            selectedPhotos = photo.getPhoto();
        }
    }

    @Override
    public void onClick(View view) {
        int v = view.getId();
        switch (v) {

            case R.id.add_btn:
                UserInfo userInfo = (UserInfo) SharedPreferencesUtils.readObject(Activity_AddTb.this, "userinfo");
                CheckUserInfo.setUserInfo(userInfo);
                if (!CheckUserInfo.checkSmrz()) {
                    new Dialog(Activity_AddTb.this, "提示：", "实名认证后才可投标，是否前往认证？", new Dialog.dialogButton() {
                        @Override
                        public void ok(DialogInterface dialog, int which) {
                            Intent i = new Intent(Activity_AddTb.this, Activity_Auth.class);
                            i.putExtra("type", "sm");
                            startActivity(i);
                        }
                        @Override
                        public void cannot(DialogInterface dialog, int which) {


                        }
                    });

                    return;
                }
                if (!CheckUserInfo.checkFdcgjs()&&!CheckUserInfo.checkTdgjs()) {
                    new Dialog(Activity_AddTb.this, "提示：", "估价师认证后才可发布，是否前往认证？", new Dialog.dialogButton() {
                        @Override
                        public void ok(DialogInterface dialog, int which) {
                            Intent i = new Intent(Activity_AddTb.this, Activity_Auth.class);
                            i.putExtra("type", "fc");
                            startActivity(i);

                        }
                        @Override
                        public void cannot(DialogInterface dialog, int which) {
                        }
                    });
                    return;
                }
                try {
                    tbBean = new TBBean();
                    tbBean.setZbid(zbBean.getZbid());
                    tbBean.setTbbj(Double.parseDouble(tb_tbj.getText().toString().trim()));
                    tbBean.setTbsm(tb_bz_et.getText().toString().trim());
                    ex.addtb(tbBean);
                } catch (Exception e) {
                    com.android.pinggubang.Utils.Log.MyToast("请检查信息", Activity_AddTb.this);
                }
                break;
        }
    }
}
