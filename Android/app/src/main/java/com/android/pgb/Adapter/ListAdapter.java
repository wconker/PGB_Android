package com.android.pgb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.pgb.Bean.NewBean;
import com.android.pgb.R;
import com.android.pgb.Utils.Log;

import java.util.List;

/**
 * Created by kanghui on 2017/1/5.
 */

public class ListAdapter extends BaseAdapter {

    private Context mcontext;
    private List<NewBean> mNews;

    public ListAdapter(Context context, List<NewBean> news) {
        this.mcontext = context;
        mNews = news;
    }



    @Override
    public int getCount() {
        return mNews.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.listview_items, null); //加载item
            viewHolder.info = (TextView) convertView.findViewById(R.id.listview_text);  //找到控件
            viewHolder.date = (TextView) convertView.findViewById(R.id.listview_date);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag(); //
        }
//        viewHolder.info.setText(mNews.get(position).getTitle()); //赋值
//        viewHolder.date.setText(mNews.get(position).getTime());
        return convertView;
    }

    private class ViewHolder {
        TextView info;
        TextView date;

    }
}
