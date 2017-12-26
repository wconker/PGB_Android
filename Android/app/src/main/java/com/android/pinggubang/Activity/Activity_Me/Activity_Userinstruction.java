package com.android.pinggubang.Activity.Activity_Me;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.android.pinggubang.R;
import com.android.pinggubang.View.CBarView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Activity_Userinstruction extends Activity {

    @Bind(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__userinstruction);
        ButterKnife.bind(this);
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
        webview.loadUrl("http://www.yiqiyun.org/filepgb/state.html");

    }
}
