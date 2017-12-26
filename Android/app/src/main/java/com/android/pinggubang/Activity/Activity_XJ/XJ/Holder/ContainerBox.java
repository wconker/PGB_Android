package com.android.pinggubang.Activity.Activity_XJ.XJ.Holder;

import android.view.View;
import android.widget.TextView;

import com.android.pinggubang.Activity.Activity_XJ.XJ.Bean.xjBean;
import com.android.pinggubang.R;
import com.android.pinggubang.base.BaseHolder;

/**
 * Created by softsea on 17/8/7.
 */

public class ContainerBox extends BaseHolder<xjBean.DataBean> {
    private TextView tv ;
    public ContainerBox(View itemView) {
        super(itemView);

        tv = (TextView) itemView.findViewById(R.id.MainBox);

    }

    @Override
    public void InsertData(xjBean.DataBean t) {


        tv.setText(t.getXjbdw());
    }
}
