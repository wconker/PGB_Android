package com.android.pinggubang.Activity.Activity_Job;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pinggubang.Bean.GW_Bean;
import com.android.pinggubang.Bean.QZ_Bean;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.Dialog;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.Utils.Log;
import com.android.pinggubang.View.CBarView;
import com.android.pinggubang.View.floatbutton.CustomFAB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Home extends Activity implements ExChange.CallBackForData {
    @Bind(R.id.all)
    Button all;
    @Bind(R.id.myzp)
    Button myzp;
    @Bind(R.id.zp_List)
    LinearLayout zpList;
    private ExChange ex;
    private ViewStub v;
    private RecyclerView list;
    private GWAdapter gwAdapter;
    private GWAdapter2 gwAdapter2;
    private List<QZ_Bean> QZList = new ArrayList<>();
    private CBarView bar;
    private int type = 0;
    private SwipeRefreshLayout SRH;
    private CustomFAB fab;
    private int tabIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", 0);
        bar = new CBarView(this, new CBarView.OnClickListener() {
            @Override
            public void onLeftClick() {
                super.onLeftClick();
                finish();
            }

            @Override
            public void onRightClick() {
                super.onRightClick();

                if (type == 2)//求职
                {
                    Intent qz = new Intent(Activity_Home.this, Activity_AddQZ.class);
                    startActivity(qz);
                }
                if (type == 1) //招聘
                {
                    Intent zp = new Intent(Activity_Home.this, Activity_AddZP.class);
                    startActivity(zp);
                }
            }
        }, null);


        if (type == 2) //求职
        {

            bar.setRightText("我要求职");
            v = (ViewStub) this.findViewById(R.id.job_want);
            v.inflate();
            list = (RecyclerView) this.findViewById(R.id.list_view);
            ex = new ExChange(this);
            ex.getRcList();
            gwAdapter2 = new GWAdapter2(this, QZList);
            list.setLayoutManager(new LinearLayoutManager(this));
            list.setAdapter(gwAdapter2);
        }
        if (type == 1)//招聘
        {

            bar.setRightText("我要招聘");
            v = (ViewStub) this.findViewById(R.id.job_apply);
            v.inflate();
            list = (RecyclerView) this.findViewById(R.id.list_view);
            ex = new ExChange(this);
            ex.getGwList();
            gwAdapter = new GWAdapter(this, GW_List);
            list.setLayoutManager(new LinearLayoutManager(this));
            list.setAdapter(gwAdapter);
            fab = (CustomFAB) this.findViewById(R.id.floatButton);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tabIndex == 1) {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.my));
                        tabIndex = 0;
                        ex.getMyGwList();
                    } else {
                        tabIndex = 1;
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.all));
                        ex.getGwList();
                    }
                }
            });
        }


        SRH = (SwipeRefreshLayout) this.findViewById(R.id.srh);
        SRH.setColorSchemeColors(getResources().getColor(R.color.wkh_blue), getResources().getColor(R.color.reddown));
        SRH.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (type == 1) {
                    if (tabIndex == 0) {
                        ex.getMyGwList();
                    } else {
                        ex.getGwList();
                    }

                } else {
                    ex.getRcList();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (type == 2) //求职
        {
            ex.getRcList();
        }
        if (type == 1) {

            ex.getWzList();
        }
    }

    private List<GW_Bean> GW_List = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String value = "";
            switch (msg.what) {
                case 1000:
                    SRH.setRefreshing(false);
                    break;
                case GWLIST: //招聘
                    try {
                        value = _getGwList;
                        JSONObject data = new JSONObject(value);
                        JSONArray arr = JSONUtils.getJSONArray(data, "data");
                        GW_Bean gwBean;
                        GW_List.clear();
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject temp = (JSONObject) arr.get(i);
                            gwBean = new GW_Bean();
                            gwBean.setBz(temp.getString("gsmc"));
                            gwBean.setGw(temp.getString("gw"));
                            gwBean.setUserid(JSONUtils.getInt(temp, "userid", 0));
                            gwBean.setGwid(temp.getInt("gwid"));
                            gwBean.setFbsj(temp.getString("fbsj"));
                            gwBean.setGzxz(temp.getString("gzxz"));
                            GW_List.add(gwBean);
                        }
                        SRH.setRefreshing(false);
                        gwAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case QZ: //求职
                    try {
                        value = _qz;
                        JSONObject data = new JSONObject(value);
                        JSONArray arr = JSONUtils.getJSONArray(data, "data");
                        QZ_Bean qz_bean;
                        QZList.clear();
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject temp = (JSONObject) arr.get(i);
                            qz_bean = new QZ_Bean(temp);
                            if (temp.getInt("rcid") == ex.userId) {
                                QZList.add(0, qz_bean);
                            } else {
                                QZList.add(qz_bean);
                            }
                        }
                        SRH.setRefreshing(false);
                        gwAdapter2.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case RM:

                    Log.MyToast(ex.getMessageInfo(_Rm), Activity_Home.this);
                    break;
                case GW:
                    Log.MyToast(ex.getMessageInfo(_GW), Activity_Home.this);


                    break;
            }
        }
    };

    private final int GWLIST = 123;
    private final int QZ = 124;
    private final int GW = 403;
    private final int RM = 404;
    private String _qz = "";
    private String _getGwList = "";
    private String _Rm = "";
    private String _GW = "";

    @Override
    public void onMessage(String str, String cmd, int code) {

        switch (cmd) {
            case "business.getGwList":
                _getGwList = str;
                handler.sendEmptyMessage(GWLIST);
                break;
            case "business.getRcList":
                _qz = str;
                handler.sendEmptyMessage(QZ);
                break;
            case "business.delRc":
                _Rm = str;
                handler.sendEmptyMessage(RM);
                break;
            case "business.delGw":
                _GW = str;
                handler.sendEmptyMessage(GW);
                break;
            case "business.getMyGwList":
                _getGwList = str;
                handler.sendEmptyMessage(GWLIST);
                break;
        }

    }

    @OnClick({R.id.all, R.id.myzp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all:
                ex.getGwList();
                break;
            case R.id.myzp:
                ex.getMyGwList();
                break;
        }
    }


    class GWAdapter extends RecyclerView.Adapter<GWAdapter.MyAdapter> {
        private List<GW_Bean> gw_List;
        private LayoutInflater inflater;

        public GWAdapter(Context context, List<GW_Bean> arg1) {
            inflater = LayoutInflater.from(context);
            gw_List = arg1;
        }


        @Override
        public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.jobapply_item, parent, false);

            MyAdapter myAdapter = new MyAdapter(view);
            return myAdapter;
        }

        @Override
        public void onBindViewHolder(MyAdapter holder, final int position) {

            holder.bz.setText(gw_List.get(position).getBz());
            holder.gw.setText(gw_List.get(position).getGw());
            holder.sj.setText(gw_List.get(position).getFbsj());
            holder.gwxz.setText(gw_List.get(position).getGzxz());
            if (gw_List.get(position).getUserid() == ex.userId) {
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new Dialog(Activity_Home.this, "提示：", "是否撤销?", new Dialog.dialogButton() {
                            @Override
                            public void ok(DialogInterface dialog, int which) {

                                ex.delGw(gw_List.get(position).getGwid());
                            }

                            @Override
                            public void cannot(DialogInterface dialog, int which) {

                            }
                        });

                    }
                });
            }
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent zp = new Intent(Activity_Home.this, Activity_jobInfo.class);
                    zp.putExtra("MyId", gw_List.get(position).getGwid());
                    zp.putExtra("type", 1);
                    startActivity(zp);
                }
            });


        }

        @Override
        public int getItemCount() {

            return gw_List.size();

        }

        class MyAdapter extends RecyclerView.ViewHolder {

            private TextView bz;
            private TextView gw;
            private TextView gwxz;
            private TextView sj;
            private ImageView imageView;
            private View view;

            MyAdapter(View itemView) {
                super(itemView);

                view = itemView;
                bz = (TextView) itemView.findViewById(R.id.bz);
                gw = (TextView) itemView.findViewById(R.id.gw);
                gwxz = (TextView) itemView.findViewById(R.id.gwxz);
                sj = (TextView) itemView.findViewById(R.id.sj);
                imageView = (ImageView) itemView.findViewById(R.id.undo);
            }
        }
    }

    class GWAdapter2 extends RecyclerView.Adapter<GWAdapter2.MyAdapter> {
        private List<QZ_Bean> QZList;
        private LayoutInflater inflater;

        public GWAdapter2(Context context, List<QZ_Bean> arg1) {

            inflater = LayoutInflater.from(context);
            QZList = arg1;
        }


        @Override
        public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.jobwant_item, parent, false);
            MyAdapter myAdapter = new MyAdapter(view);
            return myAdapter;
        }

        @Override
        public void onBindViewHolder(MyAdapter holder, final int position) {
            holder.bz.setText(QZList.get(position).getXm());
            holder.gw.setText(QZList.get(position).getGw());
            holder.sj.setText(QZList.get(position).getFbsj());
            holder.gwxz.setText(QZList.get(position).getGzxz());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent qz = new Intent(Activity_Home.this, Activity_jobInfo.class);
                    qz.putExtra("type", 2);
                    qz.putExtra("MyId", QZList.get(position).getRcid());
                    startActivity(qz);
                }
            });
            if (QZList.get(position).getRcid() == ex.userId) {
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new Dialog(Activity_Home.this, "提示：", "是否撤销?", new Dialog.dialogButton() {
                            @Override
                            public void ok(DialogInterface dialog, int which) {

                                ex.delRc();
                            }

                            @Override
                            public void cannot(DialogInterface dialog, int which) {

                            }
                        });

                    }
                });
            }
        }


        @Override
        public int getItemCount() {

            return QZList.size();

        }

        class MyAdapter extends RecyclerView.ViewHolder {

            private TextView bz;
            private TextView gw;
            private TextView gwxz;
            private TextView sj;
            private ImageView imageView;
            private View view;

            public MyAdapter(View itemView) {
                super(itemView);
                view = itemView;
                bz = (TextView) itemView.findViewById(R.id.bz);
                gw = (TextView) itemView.findViewById(R.id.gw);
                gwxz = (TextView) itemView.findViewById(R.id.gwxz);
                sj = (TextView) itemView.findViewById(R.id.sj);
                imageView = (ImageView) itemView.findViewById(R.id.undo);

            }
        }
    }


}