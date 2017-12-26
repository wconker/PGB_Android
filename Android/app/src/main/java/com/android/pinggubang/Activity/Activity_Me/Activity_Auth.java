package com.android.pinggubang.Activity.Activity_Me;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.pinggubang.Bean.UserInfo;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.ImageUpload;
import com.android.pinggubang.Utils.SharedPreferences.SharedPreferencesUtils;
import com.android.pinggubang.View.CBarView;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class Activity_Auth extends Activity implements ExChange.CallBackForData, View.OnClickListener {

    private UserInfo userInfo;
    private ExChange ex;
    private ImageView sfzzm, sfzfm, fdc_img, td_img;
    private ViewStub viewStub;
    private Button add_btn;
    private EditText IdCard, fdc_username, username, td_username;
    private String picPath;
    private Uri uri;
    private String type;
    private String choose = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__auth);
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
        userInfo = (UserInfo) SharedPreferencesUtils.readObject(this, "userinfo");
        com.android.pinggubang.Utils.Log.e(userInfo + "");
        type = getIntent().getStringExtra("type");
        switch (type) {
            case "sm":
                viewStub = (ViewStub) this.findViewById(R.id.smrz_ViewSub);
                viewStub.inflate();
                IdCard = (EditText) this.findViewById(R.id.IdCard);
                username = (EditText) this.findViewById(R.id.username);
                username.setText(userInfo.getXm().equals("null") ? "" : userInfo.getXm());
                IdCard.setText(userInfo.getSfzh().equals("null") ? "" : userInfo.getSfzh());
                sfzfm = (ImageView) this.findViewById(R.id.sfzfm);
                sfzzm = (ImageView) this.findViewById(R.id.sfzzm);
                sfzfm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showPopupMenu(sfzfm);
                        choose = "sfzfm";
                    }
                });
                sfzzm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showPopupMenu(sfzzm);
                        choose = "sfzzm";
                    }
                });
                if (userInfo.getSfzfm_img().equals("")) {
                    Glide.with(this)
                            .load(R.drawable.sfz_modle_other)
                            .into(sfzfm);
                } else {
                    Glide.with(this).
                            load(userInfo.getSfzfm_img() + "&cid=" + System.currentTimeMillis() / 1000)
                            .placeholder(R.drawable.watting)
                            .into(sfzfm);
                }
                if (userInfo.getSfzzm_img().equals("")) {

                    Glide.with(this).
                            load(R.drawable.sfz_modle).into(sfzzm);
                } else {
                    Glide.with(this).
                            load(userInfo.getSfzzm_img() + "&cid=" + System.currentTimeMillis() / 1000)
                            .placeholder(R.drawable.watting)
                            .into(sfzzm);
                }


                break;
            case "fc":
                viewStub = (ViewStub) this.findViewById(R.id.fc_ViewSub);
                viewStub.inflate();
                fdc_img = (ImageView) this.findViewById(R.id.fdc_img);
                fdc_img.setOnClickListener(this);
                fdc_username = (EditText) this.findViewById(R.id.fdc_username);
                fdc_username.setText(userInfo.getFdcgjszch().equals("null") ? ""
                        : userInfo.getFdcgjszch());
                if (userInfo.getFc_img().equals("")) {
                    Glide.with(this).
                            load(R.drawable.fc).crossFade().
                            skipMemoryCache(true).
                            placeholder(R.drawable.watting).
                            into(fdc_img);

                } else {
                    Glide.with(this).
                            load(userInfo.getFc_img() + "&cid=" + System.currentTimeMillis() / 1000).
                            crossFade().
                            placeholder(R.drawable.watting).
                            skipMemoryCache(true).
                            into(fdc_img);
                }
                break;
            case "td":
                viewStub = (ViewStub) this.findViewById(R.id.t_ViewSub);
                viewStub.inflate();
                td_img = (ImageView) this.findViewById(R.id.td_img);
                td_img.setOnClickListener(this);
                td_username = (EditText) this.findViewById(R.id.td_username);
                td_username.setText(userInfo.getTdgjszch().equals("null") ? "" : userInfo.getTdgjszch());
                if (userInfo.getTd_img().equals("")) {
                    Glide.with(this).load(R.drawable.td).placeholder(R.drawable.watting).crossFade().skipMemoryCache(true).into(td_img);

                } else {
                    Glide.with(this).load(userInfo.getTd_img() + "&cid=" + System.currentTimeMillis() / 1000).placeholder(R.drawable.watting).into(td_img);
                }

                break;
        }
        add_btn = (Button) this.findViewById(R.id.add_btn);
        add_btn.setOnClickListener(this);

    }

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                //点击之后的弹出层，可现实更多信息以及更多内容
                int vid = item.getItemId();
                switch (vid) {
                    case R.id.action_local:
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 1);
                        break;
                    case R.id.action_crame:
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
//                        uri = Uri.fromFile(new File(Environment
//                                .getExternalStorageDirectory(), "image.jpg"));
                        //openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(openCameraIntent, 0);
                        break;
                }

                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                //   Toast.makeText(getApplicationContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }

    private String path;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 60:
                    com.android.pinggubang.Utils.Log.MyToast(ex.getMessageInfo(_str), Activity_Auth.this);
                    finish();
                    break;
                case 61:
                    com.android.pinggubang.Utils.Log.MyToast(ex.getMessageInfo(_str), Activity_Auth.this);
                    finish();
                    break;
                case Success:
                    com.android.pinggubang.Utils.Log.MyToast(ex.getMessageInfo(_str), Activity_Auth.this);
                    break;
                case FAIL:
                    com.android.pinggubang.Utils.Log.MyToast(ex.getMessageInfo(_str), Activity_Auth.this);
                    break;

            }
        }
    };
    private String _str;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Map<String, String> m = new HashMap<>();
        if (data == null) {
            return;
        }
        if (requestCode == 1) {
            uri = data.getData();// find uri
            path = ImageUpload.getImageAbsolutePath(this, uri);
            File file = new File(path);
            switch (choose) {
                case "sfzfm":
                    sfzfm.setImageURI(uri);
                    m.put("userid", ex.userId.toString());
                    m.put("imgtype", "身份证反面");
                    ex.send(file, m);
                    break;
                case "sfzzm":
                    sfzzm.setImageURI(uri);
                    m.put("userid", ex.userId.toString());
                    m.put("imgtype", "身份证正面");
                    ex.send(file, m);
                    break;
                case "fc":
                    fdc_img.setImageURI(uri);
                    m.put("userid", ex.userId.toString());
                    m.put("imgtype", "房地产估价师资格证");
                    ex.send(file, m);
                    break;
                case "td":
                    td_img.setImageURI(uri);
                    m.put("userid", ex.userId.toString());
                    m.put("imgtype", "土地估价师资格证");
                    ex.send(file, m);
                    break;
            }

        }
        if (requestCode == 0) { //拍照图片
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
            path = ImageUpload.getImageAbsolutePath(this, uri);
            File file = new File(path);
            switch (choose) {
                case "sfzfm":
                    sfzfm.setImageBitmap(bitmap);
                    m.put("userid", ex.userId.toString());
                    m.put("imgtype", "身份证反面");
                    ex.send(file, m);
                    break;
                case "sfzzm":
                    sfzzm.setImageBitmap(bitmap);
                    m.put("userid", ex.userId.toString());
                    m.put("imgtype", "身份证正面");
                    ex.send(file, m);
                    break;
                case "fc":
                    fdc_img.setImageBitmap(bitmap);
                    m.put("userid", ex.userId.toString());
                    m.put("imgtype", "房地产估价师资格证");
                    ex.send(file, m);
                    break;
                case "td":
                    fdc_img.setImageBitmap(bitmap);
                    m.put("userid", ex.userId.toString());
                    m.put("imgtype", "土地估价师资格证");
                    ex.send(file, m);
                    break;
            }

        }

        choose = "";

    }
    private void alert() {
        Dialog dialog = new AlertDialog.Builder(this).
                setTitle("提示")
                .setMessage("您选择的不是有效的图片")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        picPath = null;
                    }
                }).create();
        dialog.show();
    }
    private final int Success = 200;
    private final int FAIL = 206;
    @Override
    public void onMessage(String str, String cmd, int code) {
        Message message = Message.obtain();
        _str = str;
        Log.e("Coooo",str);
        if (!cmd.equals("upload_img")) {
            if (code == 0) {
                message.what = 60;
            } else {
                message.what = 61;
            }
        } else {
            if (code == 0) {
                message.what = Success;
            } else {
                message.what = FAIL;
            }
        }
        handler.sendMessage(message);
    }
    /***
     * 弹出框选择方式
     */
    protected void showChoosePicDialog() {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("选择方式");
        String[] items = {"从本地选择", "拍照"};
        builder.setNegativeButton("确定", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 1: //本地的
                        Intent intent1 = new Intent();
                        intent1.addCategory(Intent.CATEGORY_OPENABLE);
                        intent1.setType("image/*");
                        intent1.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent1, 1);
                        break;
                    case 0: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        uri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(openCameraIntent, 0);

                        break;
                }
            }
        });
        builder.create().show();
    }


    @Override
    public void onClick(View view) {

        int vid = view.getId();
        switch (vid) {
            case R.id.fdc_img:
                showPopupMenu(fdc_img); //菜单方式
                choose = "fc";
                break;
            case R.id.td_img:
                showPopupMenu(td_img); //菜单方式
                choose = "td";
                break;
            case R.id.add_btn:
                switch (type) {
                    case "sm":
                        ex.Certification(
                                username.getText().toString().trim(),
                                IdCard.getText().toString().trim(),
                                1);
                        break;
                    case "fc":
                        ex.Qualification_fc(fdc_username.getText().toString().trim());
                        break;
                    case "td":
                        ex.Qualification_td(td_username.getText().toString().trim());
                        break;
                }
                break;
        }

    }
}
