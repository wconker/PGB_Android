package com.android.pinggubang.View.ToolBar;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.pinggubang.R;

/**
 * Created by wu on 2017/4/18.
 */

public class MyToolBar extends Toolbar {
    private LayoutInflater Layoutinflater;

    private View myView;

    public MyToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        myView = Layoutinflater.from(getContext()).inflate(R.layout.mytoolbar, this);
        this.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


    }

    public void setTitle(String text) {
        TextView title = (TextView) myView.findViewById(R.id.mytitle);
        title.setText(text);
    }

}
