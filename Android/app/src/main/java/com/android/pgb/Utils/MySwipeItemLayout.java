package com.android.pgb.Utils;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.LinkedHashMap;

/**
 * Created by softsea on 17/9/14.
 */

public class MySwipeItemLayout extends FrameLayout {

    public static final String TAG = "swipeItemLayout";
    private ViewDragHelper mDragHelper;  //拖动控件
    private int vVelocity;
    private float mDownX;
    private float mDownY;
    private boolean mIsDragged;
    private boolean mSwipeEnable = true;
    private int CurrentMenu;
    private LinkedHashMap<Integer, View> mMenus = new LinkedHashMap<>();

    public MySwipeItemLayout(@NonNull Context context) {
        super(context);
    }
    public MySwipeItemLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MySwipeItemLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MySwipeItemLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


}
