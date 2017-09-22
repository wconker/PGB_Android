package com.android.pgb.Adapter.innerAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pgb.Activity.Activity_CK.Activity_TbInfo;
import com.android.pgb.Activity.Activity_XJ.Activity_BjInfo;
import com.android.pgb.Bean.ZBBean;
import com.android.pgb.Interface.ParentsButtonLisener;
import com.android.pgb.R;
import com.android.pgb.Utils.Log;
import com.baidu.mapapi.map.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghui on 2017/1/18.
 */

public class outsideAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ZBBean> mChildList;
    public static int mParentItem = -1;
    public static boolean mbShowChild = false;
    private ParentsButtonLisener parentsButtonLisener;
    private List<List<ZBBean>> getmChildList = null;
    private List<ZBBean> mDatas = new ArrayList<ZBBean>();

    protected Context mContext;
    private Context context;

    public outsideAdapter(Context context, List<ZBBean> datas) {

        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public outsideAdapter(Context context,
                          List<ZBBean> datas,
                          List<List<ZBBean>> childrenDatas,
                          ParentsButtonLisener parentsButtonLisener) {

        getmChildList = childrenDatas;
        this.context = context;
        this.parentsButtonLisener = parentsButtonLisener;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
    }

    private int ppap;

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder;


        if (convertView == null) {
            vHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.activity_myzb_list_item, null);
            vHolder.stop = (Button) convertView.findViewById(R.id.stop);
            vHolder.listView = (ListView) convertView.findViewById(R.id.info_listview);
            vHolder.bdw = (TextView) convertView.findViewById(R.id.bdw);
            vHolder.zbsj = (TextView) convertView.findViewById(R.id.zbsj);
            vHolder.zbyqsj = (TextView) convertView.findViewById(R.id.zbyqsj);
            vHolder.ztms = (TextView) convertView.findViewById(R.id.ztms);
            vHolder.zbfw = (TextView) convertView.findViewById(R.id.zbfw);
            vHolder.zblbmc = (TextView) convertView.findViewById(R.id.zblbmc);
            vHolder.ydcs = (TextView) convertView.findViewById(R.id.info_readCount);
            vHolder.bc = (TextView) convertView.findViewById(R.id.info_bc);
            vHolder.ckfj = (Button) convertView.findViewById(R.id.ckfj);
            vHolder.fsyx = (Button) convertView.findViewById(R.id.fsyx);
            vHolder.ysLin = (LinearLayout) convertView.findViewById(R.id.ysLin);

            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }
        innerAdatper innerAdatper = new innerAdatper(getmChildList.get(position), context);
        vHolder.listView.setAdapter(innerAdatper);
        ZBBean zbBean = mDatas.get(position);
        vHolder.ztms.setText(zbBean.getZtms());
        vHolder.zbsj.setText(zbBean.getZbsj());
        vHolder.pos = position;
        vHolder.zbyqsj.setText(zbBean.getZbyxsjtime());
        vHolder.zbfw.setText(zbBean.getZbfw());
        vHolder.zblbmc.setText(zbBean.getZblbmc());
        vHolder.ydcs.setText(zbBean.getYdcs());
        vHolder.bc.setText("￥" + zbBean.getBc() + "元");
        vHolder.bdw.setText(zbBean.getBdw());
        if (zbBean.getZt() >= 3) {
            vHolder.ysLin.setVisibility(View.VISIBLE);
            vHolder.fsyx.setOnClickListener(new ParentButtonLisener());
            vHolder.fsyx.setTag(position);
            vHolder.ckfj.setOnClickListener(new ParentButtonLisener());
            vHolder.ckfj.setTag(position);
        } else {
            vHolder.ysLin.setVisibility(View.GONE);
        }
        final ViewHolder finalVHolder = vHolder;
        //内部list 点击事件
        vHolder.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(context, Activity_TbInfo.class);
                ZBBean bean = getmChildList.get(finalVHolder.pos).get(position);
                int tbId = bean.getTbid();
                i.putExtra("tbid", tbId);
                context.startActivity(i);
            }
        });

        if (zbBean.getZt() <= 3) {
            vHolder.stop.setOnClickListener(new ParentButtonLisener());
            vHolder.stop.setTag(position);
        } else {
            vHolder.stop.setVisibility(View.GONE);
        }

        return convertView;
    }


    private class ViewHolder {

        private ListView listView;
        private TextView bdw, zbfw, zblbmc, bc, ztms, ydcs, zbsj, zbyqsj;
        Button stop;
        int pos;
        private LinearLayout ysLin;
        Button ckfj, fsyx;

    }


    private class ParentButtonLisener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            int vid = v.getId();
            Integer tid = (Integer) v.getTag();

            switch (vid) {

                case R.id.ckfj:
                    Log.e(R.id.ckfj + ":");
                    parentsButtonLisener.leftbtnClick(vid, tid, mDatas.get(tid));
                    break;
                case R.id.fsyx:
                    Log.e(R.id.fsyx + "++");
                    parentsButtonLisener.leftbtnClick(vid, tid, mDatas.get(tid));
                    break;
                case R.id.stop:
                    parentsButtonLisener.rightbtnClick(vid, tid, mDatas.get(tid));
                    break;
            }

        }
    }
}
