package com.android.pgb.Activity.Activity_Me;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.print.PageRange;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pgb.Activity.Core.SelectPGJG;
import com.android.pgb.Bean.TBBean;
import com.android.pgb.Bean.UserInfo;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.CommonAdapter;
import com.android.pgb.Utils.CommonViewHolder;
import com.android.pgb.Utils.Dialog;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.Log;
import com.android.pgb.Utils.SharedPreferences.SharedPreferencesUtils;
import com.android.pgb.View.CBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Activity_BindInfo extends Activity implements ExChange.CallBackForData {

    private ExChange ex;
    private LinearLayout gsmc_l;
    private TextView gsmc;
    private ListView mayaccord;
    private ViewStub viewStub;
    private Button add_gs;
    private Button add_btn, add_email, add_company;
    private TextView register_getValid_b;
    private EditText phone, register_valid_et, mail, sqxx;
    private int time = 0;
    private int sqbz = -100;
    private UserInfo userInfo;
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {

            time--;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    register_getValid_b.setText(time + "s");
                    if (time <= 0) {
                        setEnable(true);
                        register_getValid_b.setText("获取验证码");
                    }
                }
            });
            if (time <= 0) {
                timerTask.cancel();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__bind_info);
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
        ex = new ExChange(this);
        userInfo = (UserInfo) SharedPreferencesUtils.readObject(this, "userinfo");

        initView();
    }

    void setEnable(boolean ifTrue) {

        register_getValid_b.setEnabled(ifTrue);
        register_getValid_b.setClickable(ifTrue);
    }

    private void initView() {
        String str = getIntent().getStringExtra("type");
        switch (str) {
            case "phone":
                viewStub = (ViewStub) this.findViewById(R.id.phone_view);
                viewStub.inflate();
                register_getValid_b = (TextView) this.findViewById(R.id.register_getValid_b);
                phone = (EditText) this.findViewById(R.id.phone);
                phone.setText(userInfo.getSjhm());
                register_valid_et = (EditText) this.findViewById(R.id.register_valid_et);
                add_btn = (Button) this.findViewById(R.id.add_btn);
                add_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ex.bindSjhm(phone.getText().toString().trim(), register_valid_et.getText().toString().trim());

                    }
                });
                register_getValid_b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        time = 120;
                        setEnable(false);
                        timer.schedule(timerTask, 1000, 1000);
                        ex.getVertifyCode(phone.getText().toString().trim());

                    }
                });
                break;
            case "email":
                viewStub = (ViewStub) this.findViewById(R.id.email_view);
                viewStub.inflate();
                mail = (EditText) this.findViewById(R.id.mail);
                register_getValid_b = (TextView) this.findViewById(R.id.register_getValid_b);
                register_valid_et = (EditText) this.findViewById(R.id.register_valid_et);
                register_getValid_b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        time = 60;
                        setEnable(false);
                        timer.schedule(timerTask, 1000, 1000);
                        ex.getEmailCode(mail.getText().toString().trim());
                    }
                });

                mail.setText(userInfo.getDzyx());
                add_email = (Button) this.findViewById(R.id.add_email);
                add_email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ex.bindEmail(mail.getText().toString().trim(), register_valid_et.getText().toString().trim());

                    }
                });
                break;
            case "company":
                viewStub = (ViewStub) this.findViewById(R.id.company_view);
                viewStub.inflate();
                shbz = new ArrayList<>();
                list = new ArrayList<>();
                gsmc_l = (LinearLayout) this.findViewById(R.id.gsmc_l);
                mayaccord = (ListView) this.findViewById(R.id.mayaccord);
                adapter = new Activity_BindInfo.ListViewAdapter(this, list, R.layout.new_items_list);
                mayaccord.setAdapter(adapter);

                gsmc = (TextView) this.findViewById(R.id.gsmc);
                sqxx = (EditText) this.findViewById(R.id.sqxx);
                gsmc.setText(userInfo.getGsmc());
                add_gs = (Button) this.findViewById(R.id.add_gs);
                gsmc_l.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Activity_BindInfo.this, SelectPGJG.class);
                        startActivityForResult(intent, 200);
                    }
                });
                mayaccord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        gsmc.setText(list.get(i));
                        sqbz = shbz.get(i);
                    }
                });
                add_gs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (userInfo.getGsmcbdbz() == 1) {
                            Log.MyToast("请勿重复绑定", Activity_BindInfo.this);
                            return;
                        }

                        if (sqbz ==1) {
                            ex.bindCompany(gsmc.getText().toString().trim(), sqbz);
                        }
                        else if (userInfo.getGsmcbdbz() == 2) {
                            Log.MyToast("该公司正在申请中", Activity_BindInfo.this);
                            return;
                        }
                        else {

                          new Dialog(Activity_BindInfo.this, "提示：", "当前公司未入住，是否前往审核入住？?", new Dialog.dialogButton() {
                                @Override
                                public void ok(DialogInterface dialog, int which) {

                                    Intent apply = new Intent(Activity_BindInfo.this, GSRZ.class);
                                    if (!gsmc.getText().toString().trim().equals(""))
                                        apply.putExtra("jgmc", gsmc.getText().toString().trim());
                                    startActivity(apply);
                                }

                                @Override
                                public void cannot(DialogInterface dialog, int which) {

                                }
                            });
                        }
                    }

                });

                ex.getPggsTjList();

                break;
        }

    }


    private Activity_BindInfo.ListViewAdapter adapter;
    private List<String> list;
    private List<Integer> shbz;

    class ListViewAdapter extends CommonAdapter<String> {

        public ListViewAdapter(Context context, List<String> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void setViewContent(CommonViewHolder viewHolder, String s) {

            viewHolder.setText(R.id.newsitem, s);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 260) {
            gsmc.setText(data.getStringExtra("JGMC"));
            sqbz = Integer.parseInt(data.getStringExtra("JZBZ"));
        }
    }

    private final int UserbindCompanySuccess=5603;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what==UserbindCompanySuccess)
            {
                finish();
            }

            if(msg.what==ERROR)
            {

                Toast.makeText(Activity_BindInfo.this, ex.getMessageInfo(_error), Toast.LENGTH_SHORT).show();

            }
            if(msg.what==SUCCESS)
            {
                Toast.makeText(Activity_BindInfo.this, ex.getMessageInfo(_OK), Toast.LENGTH_SHORT).show();

            }
            if(msg.what==TJLIST) {

                String value = msg.getData().getString("tjlist");
                JSONObject obj;
                try {
                    obj = new JSONObject(value);
                    if (JSONUtils.getJSONArray(obj, "data") == null) {
                        return;
                    }
                    JSONObject arr = (JSONObject) JSONUtils.getJSONArray(obj, "data").get(0);
                    Log.MyToast(arr.length() + "", Activity_BindInfo.this);
                    if (arr!= null) {
                        for (int i = 0; i < arr.length(); i++) {
                            list.add((String) arr.get("jgmc"));
                            shbz.add((Integer) arr.get("shbz"));
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private Timer timer = new Timer();

    private String _error="",_OK="";
    private final int ERROR = 404;
    private final int SUCCESS=200;
    private final int TJLIST = 290;
    @Override
    public void onMessage(String str, String cmd, int code) {
        if (code != 0) {
            _error = str;
           handler.sendEmptyMessage(ERROR);
        }
        else {
            if(!cmd.equals("user.getPggsTjList")) {
                _OK = str;
                handler.sendEmptyMessage(SUCCESS);
            }
        }
        switch (cmd) {
            case "conn.getEmailCode":
                Log.e(str);
                break;
            case "user.bindCompany":
                handler.sendEmptyMessage(UserbindCompanySuccess);
                break;
            case "user.getPggsTjList":
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                message.what=TJLIST;
                bundle.putString("tjlist", str);
                message.setData(bundle);
                handler.sendMessage(message);
                break;
        }

    }
}
