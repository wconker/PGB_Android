package com.android.pinggubang.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.pinggubang.Activity.T;

/**
 * Created by softsea on 17/8/4.
 */

public abstract class BaseHolder<M> extends RecyclerView.ViewHolder {

    public BaseHolder(View itemView) {
        super(itemView);
    }

    public abstract void InsertData(M t);
}
