package com.android.pinggubang.View.InnerListview.adapt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pinggubang.Activity.Activity_XJ.Activity_BjInfo;

import com.android.pinggubang.Bean.XJBean;
import com.android.pinggubang.Interface.ParentsButtonLisener;
import com.android.pinggubang.R;
import com.android.pinggubang.View.InnerListview.base.BaseObjectListAdapter;


/**
 * ��listview������
 *
 * @author mmsx
 */
public class ParentAdapt extends BaseObjectListAdapter {

    private List<XJBean> mChildList;
    public static int mParentItem = -1;
    public static boolean mbShowChild = false;
    private ParentsButtonLisener parentsButtonLisener;
    private List<List<XJBean>> getmChildList = null;
    private Context context;

    public ParentAdapt(Context context, List<XJBean> datas) {
        super(context, datas);
        this.context = context;
    }

    public ParentAdapt(Context context, List<XJBean> datas, List<List<XJBean>> childrenDatas, ParentsButtonLisener parentsButtonLisener) {
        super(context, datas);
        getmChildList = childrenDatas;
        this.context = context;
        this.parentsButtonLisener = parentsButtonLisener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder;


        if (convertView == null) {
            vHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.activity_main_list_item, null);
            vHolder.bc = (TextView) convertView.findViewById(R.id.info_bc);
            vHolder.xjlb = (TextView) convertView.findViewById(R.id.xjlb);
            vHolder.bjts = (TextView) convertView.findViewById(R.id.info_jsbjtj);
            vHolder.info = (TextView) convertView.findViewById(R.id.info);
            vHolder.readtime = (TextView) convertView.findViewById(R.id.info_readCount);
            vHolder.address = (TextView) convertView.findViewById(R.id.e_address);
            vHolder.bjzt = (TextView) convertView.findViewById(R.id.info_zt);
            vHolder.sysj = (TextView) convertView.findViewById(R.id.info_lastTime);
            vHolder.resubmit = (Button) convertView.findViewById(R.id.resubmit);
            vHolder.stop = (Button) convertView.findViewById(R.id.stop);
            vHolder.xjfs = (TextView) convertView.findViewById(R.id.xjfs);
            vHolder.listViewItem = (ListView) convertView.findViewById(R.id.info_listview);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }



        XJBean tempEntity = mDatas.get(position);
        vHolder.address.setText(tempEntity.getXjbdw());
        vHolder.readtime.setText(tempEntity.getYdcs() + "次");
        vHolder.bjzt.setText(getBJZT(String.valueOf(tempEntity.getZt())) + "");
        ChildAdapt tempAdapt = new ChildAdapt(mContext, getmChildList.get(position));
        if (getmChildList.get(position).size() > 0) {
            vHolder.info.setVisibility(View.GONE);
            vHolder.listViewItem.setVisibility(View.VISIBLE);

        }

        vHolder.listViewItem.setAdapter(tempAdapt);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = "";
        try {
            Date d2 = df.parse(tempEntity.getXjyxsjtime()); //设置
            Date d1 = df.parse(df.format(new Date())); //当前
            long diff = d2.getTime() - d1.getTime();
            time = (diff / (1000 * 60)) > 0 ? time + "分" : "时间到";
        } catch (Exception e) {
        }
        vHolder.xjfs.setText(tempEntity.getXjfw());
        vHolder.sysj.setText(tempEntity.getXjsj());
        vHolder.post = position;
        vHolder.xjlb.setText(tempEntity.getWylx());
        vHolder.xjid = tempEntity.getXjid();
        vHolder.bc.setText(tempEntity.getBc() + "");
        vHolder.bjts.setText(tempEntity.getJsbjtj() + "");
        final ViewHolder finalVHolder = vHolder;
        //内部list 点击事件
        vHolder.listViewItem.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(context, Activity_BjInfo.class);
                XJBean bean = getmChildList.get(finalVHolder.post).get(position);
                int bjId = bean.getBjid();

                i.putExtra("type","info");
                i.putExtra("bjid", bjId);
                context.startActivity(i);
            }
        });

        if (tempEntity.getZt() == 0) {
            vHolder.stop.setVisibility(View.VISIBLE);
            vHolder.stop.setOnClickListener(new ParentButtonLisener());
            vHolder.stop.setTag(position);
        }else  if(tempEntity.getZt()==1) {
            vHolder.stop.setText("结案");
            vHolder.stop.setVisibility(View.VISIBLE);
            vHolder.stop.setTag(position);
        } else
        {
            vHolder.stop.setVisibility(View.GONE);
        }
        vHolder.resubmit.setOnClickListener(new ParentButtonLisener());
        vHolder.resubmit.setTag(position);
        return convertView;
    }

    private String getBJZT(String zt) {
        String temp = "";
        switch (zt) {
            case "-2":
                temp = "终止询价";
                break;
            case "-1":
                temp = "未付款";
                break;
            case "0":
                temp = "询价中";
                break;
            case "1":
                temp = "结束询价";
                break;
            case "2":
                temp = "已完成";
                break;
            case "3":
                temp = "过期处理";
                break;
        }
        return temp;
    }

    class ViewHolder {
        TextView address;
        TextView info;
        TextView sysj;
        TextView readtime;
        ListView listViewItem;
        Button resubmit;
        Button stop;
        TextView xjfs;
        TextView bjzt;
        Integer post;
        Integer xjid;
        TextView bc;
        TextView bjts;
        TextView xjlb;
    }


    private class ParentButtonLisener implements OnClickListener {
        @Override
        public void onClick(View v) {

            int vid = v.getId();
            Integer tid = (Integer) v.getTag();

            switch (vid) {
                case R.id.resubmit:
                    parentsButtonLisener.leftbtnClick(vid, tid, mDatas.get(tid));
                    break;
                case R.id.stop:
                    parentsButtonLisener.rightbtnClick(vid, tid, mDatas.get(tid));
                    break;
            }

        }
    }
}
