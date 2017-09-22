package com.android.pgb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.pgb.R;

/**
 * Created by conker on 2017/1/30.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {

    Context context;
    String[] items = new String[]{};
    Spinner spinner;

    public SpinnerAdapter(final Context context,
                          final int textViewResourceId, final String[] objects, final Spinner spinner) {
        super(context, textViewResourceId, objects);
        this.items = objects;
        this.context = context;
        this.spinner = spinner;
    }

    @Override

    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    R.layout.a_spinner_item_layout, parent, false);
        }
        TextView label = (TextView) convertView.findViewById(R.id.spinner_item_label);
        label.setText(items[position]);
        if (spinner.getSelectedItemPosition() == position) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.wkh_blue));

        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.green));

        }
        return convertView;
    }


}