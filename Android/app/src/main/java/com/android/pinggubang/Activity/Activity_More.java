package com.android.pinggubang.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.android.pinggubang.Activity.Activity_Job.Activity_Home;
import com.android.pinggubang.Activity.Activity_Me.Activity_Account;
import com.android.pinggubang.Activity.Activity_Me.Activity_Auth;
import com.android.pinggubang.Activity.Activity_Me.Activity_BindInfo;
import com.android.pinggubang.Activity.Activity_Me.Activity_FastAuth;
import com.android.pinggubang.Activity.Activity_Me.Activity_TX;
import com.android.pinggubang.Bean.UserInfo;
import com.android.pinggubang.Fragment.me;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.CommonAdapter;
import com.android.pinggubang.Utils.CommonViewHolder;
import com.android.pinggubang.Utils.SharedPreferences.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Activity_More extends AppCompatActivity {




    @Bind(R.id.mytitle)
    TextView mytitle;
    @Bind(R.id.gv)
    GridView gv;
    private List<String> list;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__more);
        ButterKnife.bind(this);
        list = new ArrayList<>();

        list.add("求职");
        list.add("招聘");
        list.add("实名认证");
        list.add("房估认证");
        list.add("土估认证");
        list.add("手机绑定");
        list.add("邮箱绑定");
        list.add("公司绑定");
        list.add("快速认证");
        list.add("提现");

        mytitle.setText("更多");
        userInfo = (UserInfo) SharedPreferencesUtils.readObject(this, "userinfo");

        Activity_More.GvAdapter adapter = new Activity_More.GvAdapter(this, list, R.layout.gv_layout);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent go;
                switch (i) {
                    case 0:
                        go = new Intent(Activity_More.this, Activity_Home.class);
                        go.putExtra("type", 2);
                        startActivity(go);
                        break;
                    case 1:
                        go = new Intent(Activity_More.this, Activity_Home.class);
                        go.putExtra("type", 1);
                        startActivity(go);
                        break;
                    case 2:
                        go = new Intent(Activity_More.this, Activity_Auth.class);
                        if (userInfo.getScrzbz() < 2) {
                            go.putExtra("type", "sm");
                            startActivity(go);
                        }
                        break;
                    case 3:
                        go = new Intent(Activity_More.this, Activity_Auth.class);
                        if (userInfo.getFdcgjsbz() < 2) {
                            go.putExtra("type", "fc");
                            startActivity(go);
                        }
                        break;
                    case 4:
                        go = new Intent(Activity_More.this, Activity_Auth.class);
                        if (userInfo.getTdgjsbz() < 2) {
                            go.putExtra("type", "td");
                            startActivity(go);
                        }
                        break;
                    case 5:
                        go = new Intent(Activity_More.this, Activity_BindInfo.class);
                        go.putExtra("type", "phone");
                        startActivity(go);
                        break;
                    case 6:
                        go = new Intent(Activity_More.this, Activity_BindInfo.class);
                        go.putExtra("type", "email");
                        startActivity(go);
                        break;
                    case 7:
                        go = new Intent(Activity_More.this, Activity_BindInfo.class);
                        go.putExtra("type", "company");
                        startActivity(go);
                        break;
                    case 8:
                        Intent tx = new Intent(Activity_More.this, Activity_FastAuth.class);
                        startActivity(tx);
                        break;
                    case 9:
                        Intent it2 = new Intent(Activity_More.this, Activity_TX.class);
                        startActivity(it2);
                        break;
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    class GvAdapter extends CommonAdapter<String> {

        public GvAdapter(Context context, List list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void setViewContent(CommonViewHolder viewHolder, String str) {


            switch (viewHolder.getPostion()) {
                case 0:
                    viewHolder.getView(R.id.title).setVisibility(View.GONE);
                    viewHolder.getView(R.id.imagetitle).setVisibility(View.VISIBLE);
                    viewHolder.setImageResource(R.id.imagetitle, R.drawable.lookfor_active).setText(R.id.value, str);
                    break;
                case 1:
                    viewHolder.getView(R.id.title).setVisibility(View.GONE);
                    viewHolder.getView(R.id.imagetitle).setVisibility(View.VISIBLE);
                    viewHolder.setImageResource(R.id.imagetitle, R.drawable.job_active).setText(R.id.value, str);
                    break;
                case 2:
                    viewHolder.getView(R.id.title).setVisibility(View.GONE);
                    viewHolder.getView(R.id.imagetitle).setVisibility(View.VISIBLE);
                    viewHolder.setImageResource(R.id.imagetitle, R.drawable.icon_smrz).setText(R.id.value, str);
                    break;
                case 3:
                    viewHolder.getView(R.id.title).setVisibility(View.GONE);
                    viewHolder.getView(R.id.imagetitle).setVisibility(View.VISIBLE);
                    viewHolder.setImageResource(R.id.imagetitle, R.drawable.icon_fcgjsrz).setText(R.id.value, str);
                    break;
                case 4:
                    viewHolder.getView(R.id.title).setVisibility(View.GONE);
                    viewHolder.getView(R.id.imagetitle).setVisibility(View.VISIBLE);
                    viewHolder.setImageResource(R.id.imagetitle, R.drawable.icon_tdgjsrz).setText(R.id.value, str);
                    break;
                case 5:
                    viewHolder.getView(R.id.title).setVisibility(View.GONE);
                    viewHolder.getView(R.id.imagetitle).setVisibility(View.VISIBLE);
                    viewHolder.setImageResource(R.id.imagetitle, R.drawable.icon_sjhm).setText(R.id.value, str);
                    break;
                case 6:
                    viewHolder.getView(R.id.title).setVisibility(View.GONE);
                    viewHolder.getView(R.id.imagetitle).setVisibility(View.VISIBLE);
                    viewHolder.setImageResource(R.id.imagetitle, R.drawable.icon_email).setText(R.id.value, str);
                    break;
                case 7:
                    viewHolder.getView(R.id.title).setVisibility(View.GONE);
                    viewHolder.getView(R.id.imagetitle).setVisibility(View.VISIBLE);
                    viewHolder.setImageResource(R.id.imagetitle, R.drawable.icon_news).setText(R.id.value, str);
                    break;
                case 8:
                    viewHolder.getView(R.id.title).setVisibility(View.GONE);
                    viewHolder.getView(R.id.imagetitle).setVisibility(View.VISIBLE);
                    viewHolder.setImageResource(R.id.imagetitle, R.drawable.icon_set).setText(R.id.value, str);
                    break;
                case 9:
                    viewHolder.getView(R.id.title).setVisibility(View.GONE);
                    viewHolder.getView(R.id.imagetitle).setVisibility(View.VISIBLE);
                    viewHolder.setImageResource(R.id.imagetitle, R.drawable.icon_cash).setText(R.id.value, str);
                    break;
            }
        }
    }

}
