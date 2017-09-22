package com.android.pgb.News;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.View.CBarView;
import com.android.pgb.alipay.PayResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewInfo extends Activity implements ExChange.CallBackForData {

    @Bind(R.id._title)
    TextView _title;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.readcount)
    TextView readcount;
    @Bind(R.id.content)
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_info);
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

        int newId = getIntent().getIntExtra("newId", 0);
        ExChange ex = new ExChange(this);
        ex.getNewsInfo(newId);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == NEWINFO) {
                JSONObject ob = JSONUtils.StringToJSON(_newinfo);
                try {
                    JSONObject ob_value = (JSONObject) ((JSONArray) ob.get("data")).get(0);
                    if (ob_value != null) {

                        _title.setText(ob_value.getString("title"));
                        time.setText(ob_value.getString("fbsj"));
                        content.setText(ob_value.getString("content"));
                        readcount.setText("阅读量："+ob_value.getString("readtimes"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private final int NEWINFO = 100;
    private String _newinfo = "";

    @Override
    public void onMessage(String str, String cmd, int code) {


        if (cmd.equals("conn.getNewsInfo")) {
            _newinfo = str;
            handler.sendEmptyMessage(NEWINFO);

        }
    }
}
