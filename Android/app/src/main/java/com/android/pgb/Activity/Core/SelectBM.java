package com.android.pgb.Activity.Core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;

public class SelectBM extends Activity {

    private LinearLayout layout;
    private Button btnSearch;
    private TextView ywb, pgb;
    private EditText etSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bm);

        layout = (LinearLayout) this.findViewById(R.id.bm);
        etSearch = (EditText) this.findViewById(R.id.etSearch);
        ywb = (TextView) this.findViewById(R.id.ywb);
        pgb = (TextView) this.findViewById(R.id.pgb);
        btnSearch = (Button) this.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BacktoActivity(etSearch.getText().toString().trim());
            }
        });
        ywb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BacktoActivity(ywb.getText().toString().trim());
            }
        });
        pgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BacktoActivity(pgb.getText().toString().trim());
            }
        });


    }


    void BacktoActivity(String s) {



        Intent intent = new Intent();
        intent.putExtra("bm", s);
        setResult(400, intent);
        finish();

    }


}
