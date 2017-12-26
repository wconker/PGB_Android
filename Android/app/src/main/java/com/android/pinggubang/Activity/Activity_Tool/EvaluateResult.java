package com.android.pinggubang.Activity.Activity_Tool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.pinggubang.R;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EvaluateResult extends BaseActivity {


    @Bind(R.id.community)
    TextView community;
    @Bind(R.id.totalPrice)
    TextView totalPrice;
    @Bind(R.id.calculate)
    Button calculate;
    @Bind(R.id.PriceSignle)
    TextView PriceSignle;

    @Override
    public void bindButterKnife() {
        super.bindButterKnife();
        ButterKnife.bind(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        String va1 = getIntent().getStringExtra("community");
        final String va2 = getIntent().getStringExtra("evaluatetotal")+"万元";
        final String va3 = getIntent().getStringExtra("evaluatesignal")+"元";

        community.setText(va1);
        totalPrice.setText(va2);
        PriceSignle.setText(va3);


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EvaluateResult.this, Calculate.class);
                i.putExtra("evaluatetotal", va2);
                i.putExtra("evaluatesignal", va3);
                startActivity(i);
            }
        });

    }

    @Override
    protected int GetLayout() {
        return R.layout.activity_activitytool_evaluateresult;
    }

    @Override
    public void onMessage(String str, String cmd, int code) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
