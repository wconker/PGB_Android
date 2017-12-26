package com.android.pinggubang.News;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.pinggubang.R;

public class NewsWebview extends Activity {
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_webview);
        wv = (WebView) findViewById(R.id.webview);


        wv.getSettings().setJavaScriptEnabled(true);
// 设置可以支持缩放
        wv.getSettings().setSupportZoom(true);
// 设置出现缩放工具
        wv.getSettings().setBuiltInZoomControls(true);
//扩大比例的缩放
        wv.getSettings().setUseWideViewPort(true);
//自适应屏幕
        wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);// ����js����ֱ�Ӵ򿪴��ڣ���window.open()��Ĭ��Ϊfalse
        wv.getSettings().setJavaScriptEnabled(true);// �Ƿ�����ִ��js��Ĭ��Ϊfalse������trueʱ�������ѿ������XSS©��
        wv.getSettings().setSupportZoom(true);// �Ƿ�������ţ�Ĭ��true
        wv.getSettings().setBuiltInZoomControls(true);// �Ƿ���ʾ���Ű�ť��Ĭ��false
        wv.getSettings().setUseWideViewPort(true);// ���ô����ԣ�������������š�����ͼģʽ
        wv.getSettings().setLoadWithOverviewMode(true);// ��setUseWideViewPort(true)һ������ҳ����Ӧ����
        wv.getSettings().setAppCacheEnabled(true);// �Ƿ�ʹ�û���
        wv.getSettings().setDomStorageEnabled(true);// DOM Storage
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                }

                super.onProgressChanged(view, newProgress);
            }
        });
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wv.loadUrl("http://news.163.com/17/0119/23/CB68NINC00018AOQ.html");
    }
}