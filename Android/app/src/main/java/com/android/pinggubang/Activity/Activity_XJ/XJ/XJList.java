package com.android.pinggubang.Activity.Activity_XJ.XJ;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.pinggubang.Activity.Activity_XJ.XJ.Adapter.adapter;
import com.android.pinggubang.Activity.Activity_XJ.XJ.Bean.xjBean;
import com.android.pinggubang.R;
import com.android.pinggubang.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class XJList extends BaseActivity {


    private List<xjBean.DataBean> list = new ArrayList<>();
    @Bind(R.id.xjListRecyc)
    RecyclerView xjListRecyc;
    private Observer observer;

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        for (int i = 1; i < 2; i++) {
            xjBean.DataBean d = new xjBean.DataBean();
            d.setXjsj("2017/08/02 14:21:20");
            d.setXjbdw("北京" + i);
            list.add(d);
        }
        for (int o = 1; o < 4; o++) {
            xjBean.DataBean d = new xjBean.DataBean();
            d.setXjsj("2017/08/03 14:21:20");
            d.setXjbdw("北京" + o);
            list.add(d);
        }
        for (int t = 1; t< 4; t++) {
            xjBean.DataBean d = new xjBean.DataBean();
            d.setXjsj("2017/08/05 14:21:20");
            d.setXjbdw("北京" + t);
            list.add(d);
        }
        adapter adapter = new adapter(this);
        adapter.setData(list);
        xjListRecyc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        xjListRecyc.setAdapter(adapter);

    }

    @Override
    protected int GetLayout() {
        return R.layout.activity_xjlist;
    }

    @Override
    public void onMessage(String str, String cmd, int code) {

        Observable.just(str).subscribeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }


}
