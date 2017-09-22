package com.android.pgb.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * 这是一个SharePreferences的帮助类，
 * 隐藏了SP的库名和打开模式
 * @author cuimingqiang at 2014年10月6日
 *
 */
public class SharedPreferencesUtils{
	public static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences(Constants.SHARE_SP, Context.MODE_PRIVATE);
	}
	public static Editor getEditor(Context context){
		return getSharedPreferences(context).edit();
	}
}
