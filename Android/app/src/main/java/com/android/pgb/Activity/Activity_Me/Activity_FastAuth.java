package com.android.pgb.Activity.Activity_Me;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.pgb.Activity.Core.SelectPGJG;
import com.android.pgb.Bean.UserInfo;
import com.android.pgb.R;
import com.android.pgb.Utils.ImageUpload;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.SharedPreferences.SharedPreferencesUtils;
import com.android.pgb.View.RoundImageView;
import com.android.pgb.base.BaseActivity;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class Activity_FastAuth extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.person_sign)
    EditText personSign;
    @Bind(R.id.xm)
    EditText xm;
    @Bind(R.id.person_phone)
    EditText personPhone;
    @Bind(R.id.person_company)
    EditText personCompany;
    @Bind(R.id.tx_img)
    RoundImageView txImg;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    @Bind(R.id.person_companyLayout)
    LinearLayout person_companyLayout;
    @Bind(R.id.upSignature)
    Button upSignature;
    UserInfo user;
    private Context me = this;
    private int GoForCompany = 1520;
    private Observer observer;
    private Uri uri;
    private String path;

    @Override
    public void onClick(View view) {
        int vid = view.getId();
        switch (vid) {
            case R.id.btn_submit:
                ex.Certification(xm.getText().toString(),
                        personCompany.getText().toString().trim());
                break;
            case R.id.person_companyLayout:
                startActivityForResult(
                        new Intent(Activity_FastAuth.this,
                        SelectPGJG.class),
                        GoForCompany);
                break;
            case R.id.tx_img:
                showPopupMenu(txImg);
                break;
            case R.id.upSignature:
                ex.updateSign(personSign.getText().toString().trim());
                break;

        }

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        btnSubmit.setOnClickListener(this);

        person_companyLayout.setOnClickListener(this);

        txImg.setOnClickListener(this);

        upSignature.setOnClickListener(this);

        //注册观察者
        dealObervable();

        user = (UserInfo) SharedPreferencesUtils.readObject(me, "userinfo");

        if (user != null) {
            xm.setText(user.getXm());
            personCompany.setText(user.getGsmc());
            Glide.with(this).load(user.getTx_img()).asBitmap().into(txImg);
            personSign.setText(user.getQm());
        }
    }

    @Override
    protected int GetLayout() {
        return R.layout.activity__fast_auth;
    }

    void dealObervable() {
        observer = new Observer<JSONObject>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(JSONObject o) {

                MessageCenter(
                        JSONUtils.getInt(o, "code", 404),
                        JSONUtils.getString(o, "message")
                );
            }
        };
    }

    private void showPopupMenu(View view) {

        PopupMenu popupMenu = new PopupMenu(this, view); // View当前PopupMenu显示的相对View的位置

        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());   // menu布局

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // menu的item点击事件
            @Override
            public boolean onMenuItemClick(MenuItem item) {

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
                        //    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
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

            }
        });
        popupMenu.show();
    }

    //用于处理返回的事件
    private void MessageCenter(int CmdCode, String Message) {
        switch (CmdCode) {
            case 0:
                Toast.makeText(me, Message, Toast.LENGTH_SHORT).show();
                break;
            case -1:
                Toast.makeText(me, Message, Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(me, Message, Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        if (requestCode == 1) {// 从本地相册找
            uri = data.getData();
            path = ImageUpload.getImageAbsolutePath(this, uri);
            txImg.setImageURI(uri);
        }
        if (requestCode == 0) { //拍照图片
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
            txImg.setImageBitmap(bitmap);
            path = ImageUpload.getImageAbsolutePath(this, uri);
        }

        if (requestCode == GoForCompany) {
            personCompany.setText(data.getStringExtra("JGMC"));
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("userid", ex.userId.toString());
            map.put("imgtype", "头像");

            if (!path.equals(""))
                ex.send(new File(path), map);
        }
    }

    private final int IMG_UP = 333;
    private String _str = "";

    @Override
    public void onMessage(final String str, String cmd, int code) {

        Observable.just(str).map(new Func1<String, JSONObject>() {
            @Override
            public JSONObject call(String s) {
                return JSONUtils.StringToJSON(s);
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        if (cmd.equals("user.setUserinfo")) {
            _str = str;
        }
    }

    @Override
    public void bindButterKnife() {
        super.bindButterKnife();
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
