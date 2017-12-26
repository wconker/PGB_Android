package com.android.pinggubang.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by kanghui on 2017/1/6.
 */

public class Log {

    public static void sys(String message)
    {

        System.out.println("wkh"+"\n========>"+message);

    }
    public static void e(String message)
    {

       android.util.Log.e("wkh","==========>\n"+message);

    }
    public static void v(String message)
    {

       android.util.Log.v("wkh","========>\n"+message);

    }
    public  static  void  MyToast(String msg, Context context)
    {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }



}
