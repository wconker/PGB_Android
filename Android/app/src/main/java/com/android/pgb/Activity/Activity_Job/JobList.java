package com.android.pgb.Activity.Activity_Job;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.android.pgb.R;

public class JobList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);
        LinearLayout zp = (LinearLayout) this.findViewById(R.id.zp);
        zp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(JobList.this, Activity_Home.class);
                intent2.putExtra("type", 2);
                startActivity(intent2);
            }
        });



        LinearLayout qz = (LinearLayout) this.findViewById(R.id.qz);
        qz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(JobList.this, Activity_Home.class);
                intent2.putExtra("type", 1);
                startActivity(intent2);
            }
        });

    }
}
