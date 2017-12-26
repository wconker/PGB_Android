package com.android.pinggubang.News;

import android.support.v4.app.Fragment;

/**
 * Created by moon.zhong on 2015/3/9.
 */
public class PagerItem {
    /*item 的信息*/
    private String mMsg ;
    /*item的 title*/
    private String mTitle ;

    public PagerItem(String mTitle, String mMsg) {
        this.mMsg = mMsg;
        this.mTitle = mTitle;
    }

    Fragment createFragment(){
        return ContentFragment.instance(mMsg) ;
    }

    public String getTitle() {
        return mTitle;
    }
}
