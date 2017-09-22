package com.android.pgb.Activity.Activity_XJ.XJ.Holder;

import android.view.View;
import android.widget.TextView;

import com.android.pgb.Activity.Activity_XJ.XJ.Bean.xjBean;
import com.android.pgb.Activity.T;
import com.android.pgb.R;
import com.android.pgb.base.BaseHolder;

import java.util.List;

/**
 * Created by softsea on 17/8/7.
 */

public class titleDate extends BaseHolder<xjBean.DataBean> {
    private TextView tv;

    public titleDate(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.titleDate);

    }

    @Override
    public void InsertData(xjBean.DataBean t) {

        tv.setText(t.getXjsj());
    }


}
