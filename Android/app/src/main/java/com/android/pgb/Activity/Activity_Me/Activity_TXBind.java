package com.android.pgb.Activity.Activity_Me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.Log;
import com.android.pgb.View.CBarView;
import com.android.pgb.View.CustomBar;
import com.android.pgb.alipay.PayResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_TXBind extends Activity  implements ExChange.CallBackForData{

    @Bind(R.id.realName)
    EditText realName;
    @Bind(R.id.account)
    EditText account;

    @Bind(R.id.submit)
    Button submit;

    private ExChange ex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__txbind);
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

        ex = new ExChange(this);
        ex.getAccount();

    }

    @OnClick({ R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.canel:
//
//                if(AccountID>0)
//                ex.deleteAccount(AccountID);
//
//
//                account.setText("");
//                realName.setText("");
//                break;
            case R.id.submit:
                ex.addAccount(account.getText().toString().trim(),realName.getText().toString().trim(),3);
                break;
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==ADDACCOUNT)
            {
                Intent result=new Intent();
                result.putExtra("realName", realName.getText().toString());
                result.putExtra("account", account.getText().toString());
                setResult(333,result);
                finish();

            }
            if(msg.what==DELACCOUNT)
            {
               account.setText("");
                realName.setText("");

            }
            if(msg.what==GETACCOUNT)
            {

                JSONObject ob = JSONUtils.StringToJSON(_get);
                try {
                    if(ob.get("data").equals(null))
                    {
                        return;
                    }
                    if (((JSONArray) ob.get("data")).length() > 0) {
                        JSONObject ob_value = (JSONObject) ((JSONArray) ob.get("data")).get(((JSONArray) ob.get("data")).length() - 1);
                        if (ob_value != null) {
                            AccountID = ob_value.getInt("id");
                            realName.setText(ob_value.getString("xm"));
                            account.setText(ob_value.getString("zh"));
                        }

                }
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }
        }
    };

    private int AccountID=0;
    private final int ADDACCOUNT=234;
    private String _add="";
    private final int GETACCOUNT=235;
    private String _get="";
    private final int DELACCOUNT=235;
    private String _del="";
    @Override
    public void onMessage(String str, String cmd, int code) {
        Log.e("dfdf"+str);

        if(cmd.equals("user.addAccount"))
        {
            _add=str;
            handler.sendEmptyMessage(ADDACCOUNT);

        }
        if(cmd.equals("user.getAccount"))
        {
            _get=str;
            handler.sendEmptyMessage(GETACCOUNT);

        }
        if(cmd.equals("user.deleteAccount"))
        {
            _del=str;
            handler.sendEmptyMessage(DELACCOUNT);

        }
    }
}
