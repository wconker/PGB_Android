package com.android.pgb.Utils.Net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Checkable;
import android.widget.Toast;

import com.android.pgb.Utils.Log;

/**
 * Created by wu on 2017/3/31.
 */

public  class network {

    private static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static void CheckNetWork(Context context) {
        if (!isNetworkConnected(context)) {
            Log.MyToast("请检查当前网络状态", context);
        }
    }

}
