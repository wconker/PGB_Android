package com.android.pgb.Activity.Activity_XJ.XJ.Holder;

import android.view.View;
import android.widget.TextView;

import com.android.pgb.Activity.Activity_XJ.XJ.Bean.xjBean;
import com.android.pgb.R;
import com.android.pgb.base.BaseHolder;

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
