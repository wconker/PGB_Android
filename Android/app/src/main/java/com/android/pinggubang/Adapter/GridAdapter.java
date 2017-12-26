package com.android.pinggubang.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pinggubang.R;

import java.util.List;

/**
 * Created by kanghui on 2017/1/5.
 */

public class GridAdapter extends BaseAdapter {

    private Context context;
    private List<Drawable> _imgageList;
    private String[] _textList;
    private int[] icon = {
            R.drawable.xunjia_active,
            R.drawable.icon_task,
            R.drawable.kancha_active,
            R.drawable.lookfor_active,
            R.drawable.job_active,
            R.drawable.tools,
            R.drawable.library,
            R.drawable.counter,
            R.drawable.icon_more};


    public GridAdapter(Context context, String[] textList, List<Drawable> imgageList) {
        this.context = context;
        this._textList = textList;
        this._imgageList = imgageList;
    }


    @Override
    public int getCount() {
        return _textList.length;
    }

    @Override
    public Object getItem(int position) {
        return _textList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.index_grid_item, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) convertView
                    .findViewById(R.id.text);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTextView.setText(_textList[position]);
        viewHolder.iv.setImageResource(icon[position]);
        return convertView;
    }

    private final class ViewHolder {
        TextView mTextView;
        ImageView iv;
    }


}