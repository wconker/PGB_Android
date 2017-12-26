package com.android.pinggubang.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.BroadCast.ExChange.CallBackForData;
import com.android.pinggubang.R;
import com.android.pinggubang.View.CBarView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebView extends Activity implements CallBackForData {

  private   ExChange ex;
    @Bind(R.id.file)
    android.webkit.WebView file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
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
        ButterKnife.bind(this);

        ex = new ExChange(this);
        ex.Test(getIntent().getStringExtra("file"));


    }
   private String _Url = "";

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==100)
            {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(_Url);
                intent.setData(content_url);
                startActivity(intent);
            }
        }
    };
    @Override
    public void onMessage(String str, String cmd, int code) {

        _Url = str;
        handler.sendEmptyMessage(100);



    }
}
