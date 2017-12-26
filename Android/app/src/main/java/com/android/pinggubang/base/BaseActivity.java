package com.android.pinggubang.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.View.CBarView;

import butterknife.ButterKnife;

/**
 * Created by softsea on 17/7/24.
 */

public abstract class BaseActivity extends AppCompatActivity implements ExChange.CallBackForData {

    protected ExChange ex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(GetLayout());
        bindButterKnife();
        setTopBar();
        initView(savedInstanceState);
        ex = new ExChange(this);
    }

    public void bindButterKnife(){}

    protected void setTopBar() {
        new CBarView(this, new CBarView.OnClickListener() {
            @Override
            public void onLeftClick() {
                super.onLeftClick();
                finish();
            }

            @Override
            public void onRightClick() {
                super.onRightClick();

            }
        }, null);
    }

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int GetLayout();
    public void showLoading(){

    }
    public void hidLoading()
    {
    }
}
