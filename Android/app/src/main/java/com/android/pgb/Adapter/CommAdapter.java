package com.android.pgb.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.android.pgb.Bean.XJBean;
import com.android.pgb.R;
import com.android.pgb.Utils.CommonAdapter;
import com.android.pgb.Utils.CommonViewHolder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by kanghui on 2017/1/11.
 */

public class CommAdapter extends CommonAdapter<XJBean> {

    public CommAdapter(Context context, List<XJBean> list, int layoutId) {
        super(context, list, layoutId);
    }
    @Override
    public void setViewContent(CommonViewHolder viewHolder, XJBean xjBean) {
        String timeDown = "";

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String date = df.format(new java.util.Date());
            Date d2 = df.parse(date);
            Date d1 = df.parse(xjBean.getXjyxsjtime()+":0");
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
            long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
            LinearLayout isnew = viewHolder.getView(R.id.IsNew);
            if(xjBean.getXjnew().equals("new"))
            {
                isnew.setVisibility(View.VISIBLE);
            }else {
                isnew.setVisibility(View.GONE);
            }
             if(minutes>0) {
                 timeDown = "" + days + "天" + hours + "小时" + minutes + "分";
             }else {
                 timeDown ="已结束";
             }
        } catch (Exception e) {
        }
        viewHolder.setText(R.id.e_address, xjBean.getXjbdw())
                .setText(R.id.xjfs, xjBean.getXjfw())
                .setText(R.id.info_bc, "￥" + xjBean.getBc())
                .setText(R.id.info_jsbjtj, xjBean.getJsbjtj() + "条")
                .setText(R.id.info_zt, xjBean.getZtms())
                .setText(R.id.info_lastTime, xjBean.getXjsj())
                .setText(R.id.downTime, timeDown)
                .setText(R.id.xjlb,xjBean.getWylx())

                .setText(R.id.info_readCount, "" + xjBean.getYdcs());
    }
}
