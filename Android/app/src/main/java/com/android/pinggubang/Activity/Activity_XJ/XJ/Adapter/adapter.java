package com.android.pinggubang.Activity.Activity_XJ.XJ.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.pinggubang.Activity.Activity_XJ.XJ.Bean.xjBean;
import com.android.pinggubang.Activity.Activity_XJ.XJ.Holder.ContainerBox;
import com.android.pinggubang.Activity.Activity_XJ.XJ.Holder.titleDate;
import com.android.pinggubang.Activity.T;
import com.android.pinggubang.R;
import com.android.pinggubang.base.BaseHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by softsea on 17/8/7.
 */

public class adapter extends RecyclerView.Adapter {
    private List<xjBean.DataBean> listData;
    private Context mContext;
    public adapter(Context context) {
        this.mContext = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new titleDate(LayoutInflater.from(mContext).inflate(R.layout.activity_xj_holder_date, null));
            case 2:
                return new ContainerBox(LayoutInflater.from(mContext).inflate(R.layout.activit_xj_container, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseHolder<xjBean.DataBean>) holder).InsertData(listData.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        return listData.size();
    }

    private int TimeCompare(String data1) {
        long days = 0;
        //格式化时间
        SimpleDateFormat CurrentTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            String date = CurrentTime.format(new java.util.Date());
            Date d2 = CurrentTime.parse(date);
            Date d1 = CurrentTime.parse(data1 + ":0");
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (days % -2 == -1) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return 1;
        } else {
            return TimeCompare(listData.get(position - 1).getXjsj());
        }
    }

    public void setData(List<xjBean.DataBean> ListBean) {
        this.listData = ListBean;
    }
}
