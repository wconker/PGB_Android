package com.android.pinggubang.Interface;

/**
 * Created by kanghui on 2017/1/18.
 */


/***
 * 抽象出来的泛型接口，分别用来装载招标和询价model
 * @param <T>
 */
public interface ParentsButtonLisener<T> {

    void leftbtnClick(int vid, int posttion, T bean);

    void rightbtnClick(int vid, int posttion, T bean);

}
