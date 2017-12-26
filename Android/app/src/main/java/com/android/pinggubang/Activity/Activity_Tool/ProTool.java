package com.android.pinggubang.Activity.Activity_Tool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.android.pinggubang.Activity.Core.SelectPGJG;
import com.android.pinggubang.News.NewMain;
import com.android.pinggubang.R;
import com.android.pinggubang.View.CBarView;
import com.android.pinggubang.View.CustomBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by softsea on 17/9/18.
 */

public class ProTool extends Activity {


    @Bind(R.id.znpg)
    LinearLayout znpg;
    @Bind(R.id.czsf)
    LinearLayout czsf;
    @Bind(R.id.pgjg)
    LinearLayout pgjg;
    @Bind(R.id.hytt)
    LinearLayout hytt;
    @Bind(R.id.bar)
    CustomBar bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_pro_tool);
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
        znpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(ProTool.this, Evaluate.class);
                startActivity(intent3);

            }
        });
        czsf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent7 = new Intent(ProTool.this, Calculate.class);
                startActivity(intent7);
            }
        });
        hytt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProTool.this, NewMain.class);
                startActivity(intent);
            }
        });
        pgjg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProTool.this, SelectPGJG.class);
                startActivity(intent);
            }
        });

    }
}
