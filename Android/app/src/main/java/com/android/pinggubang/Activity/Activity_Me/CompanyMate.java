package com.android.pinggubang.Activity.Activity_Me;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.pinggubang.Activity.Core.SelectBM;
import com.android.pinggubang.Bean.CompanyMateBean;
import com.android.pinggubang.Bean.UserInfo;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.CommonAdapter;
import com.android.pinggubang.Utils.CommonViewHolder;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.Utils.Log;
import com.android.pinggubang.Utils.SharedPreferences.SharedPreferencesUtils;
import com.android.pinggubang.View.CBarView;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.utils.L;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.List;

public class CompanyMate extends Activity implements ExChange.CallBackForData {


    private PullToRefreshListView pulltorefresh;
    List<CompanyMateBean> list;
    private CompanyMate.Adapter adapter;
    private int adminFlag = 0;
    private int memberUserid = 0;
    ExChange ex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_mate);
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
        list = new ArrayList<>();
        pulltorefresh = (PullToRefreshListView) this.findViewById(R.id.pulltorefresh);
        adapter = new CompanyMate.Adapter(this, list, R.layout.companymate);
        pulltorefresh.setAdapter(adapter);
        userInfo = (UserInfo) SharedPreferencesUtils.readObject(this, "userinfo");
        ex.getToMeApplyrz();

    }

    private UserInfo userInfo;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            JSONObject data;
            JSONArray array;
            switch (msg.what) {
                case 100:
                    data = JSONUtils.StringToJSON(_str);
                    array = JSONUtils.getArrInJson(data, "data");
                    try {
                        list.clear();
                        for (int i = 0; i < array.length(); i++) {
                            CompanyMateBean bean = new CompanyMateBean((JSONObject) array.get(i));
                            list.add(bean);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 101:
                    ex.getToMeApplyrz();

                    break;
                case 103:
                    ex.getToMeApplyrz();

                    break;
                case 104:
                    ex.getToMeApplyrz();

                    break;
            }
        }
    };
    private String _str;

    @Override
    public void onMessage(String str, String cmd, int code) {

        Message message = Message.obtain();
        if (cmd.equals("user.getToMeApplyrz")) {
            _str = str;
            message.what = 100;
            handler.sendMessage(message);
            Log.e(str + "ererer");
        }
        if (cmd.equals("user.agreeAddFriend")) {
            message.what = 101;
            handler.sendMessage(message);
        }
        if (cmd.equals("user.removeMember")) {
            message.what = 103;
            handler.sendMessage(message);
        }
        if (cmd.equals("user.setMemberInfo")) {
            message.what = 104;
            handler.sendMessage(message);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 400) {
            Log.e(data.getStringExtra("bm"));
            if (memberUserid != 0) {
                ex.setMemberInfo(memberUserid, adminFlag, data.getStringExtra("bm"));
            }

        }
    }

    private void showPopupMenu(View view, final int postion, final int uId) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.adminoption, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int vid = item.getItemId();
                switch (vid) {

                    case R.id.remove:
                        ex.removeMember(uId);
                        break;
                    case R.id.setBn:
                        setMemberIdAndAdminFlag(list.get(postion).getSquserid(), list.get(postion).getQygly());
                        Intent i = new Intent(CompanyMate.this, SelectBM.class);
                        startActivityForResult(i, 399);
                        break;
                }

                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {

            }
        });
        popupMenu.show();
    }


    void setMemberIdAndAdminFlag(int mId, int adminF) {
        this.adminFlag = adminF;
        this.memberUserid = mId;
    }

    class Adapter extends CommonAdapter<CompanyMateBean> {
        public Adapter(Context context, List<CompanyMateBean> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void setViewContent(final CommonViewHolder viewHolder, final CompanyMateBean companyMate) {
            ImageView imageView = viewHolder.getView(R.id.tx);
            ImageView admin = viewHolder.getView(R.id.admin);
            final Button button = viewHolder.getView(R.id.bt);
            if (companyMate.getQygly() == 1) {
                admin.setVisibility(View.VISIBLE);
            }
            if (userInfo.getQygly() == 1 && companyMate.getShbz() == 2) {
                button.setText("操作");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showPopupMenu(button, viewHolder.getPostion(), companyMate.getSquserid());
                    }
                });
            } else if (userInfo.getQygly() == 1 && companyMate.getShbz() == 1) {
                admin.setVisibility(View.GONE);
                button.setText("接受");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ex.agreeAddFriend(companyMate.getGsmc(), String.valueOf(companyMate.getSquserid()));
                    }
                });
            } else if (userInfo.getQygly() == 0 && companyMate.getQygly() == 0&&companyMate.getShbz() == 2) {
                admin.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
            } else if (companyMate.getShbz() == 2 && companyMate.getQygly() == 1) {
                admin.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
            } else {
                admin.setVisibility(View.GONE);
                button.setText("邀请");
            }
            Glide.with(CompanyMate.this).load(companyMate.getTx_img()).placeholder(R.drawable.defaultx).into(imageView);
            viewHolder.setText(R.id.xm, companyMate.getXm()).setText(R.id.bm, companyMate.getBm().equals("null") ? "未设置" : companyMate.getBm());


        }
    }
}
