package com.android.pinggubang.Activity.MyApplication;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.Utils.Net.network;
import com.android.pinggubang.Utils.config.SystemParams;
import com.baidu.mapapi.SDKInitializer;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnicornImageLoader;
import com.qiyukf.unicorn.api.YSFOptions;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by wu on 2017/4/4.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();



        SDKInitializer.initialize(this);
        CrashReport.initCrashReport(getApplicationContext(), "7c7419100c", false);
        SystemParams.init(this);
        //客服
        Unicorn.init(this, "41e27d0b41bb64555f513e40747bb2f0", options(), new UnicornImageLoader() {
            @Nullable
            @Override
            public Bitmap loadImageSync(String uri, int width, int height) {
                return null;
            }
            @Override
            public void loadImage(String uri, int width, int height, ImageLoaderListener listener) {

            }
        });
      new ExChange(this,"app");
    }

    // 如果返回值为null，则全部使用默认参数。
    private YSFOptions options() {
        YSFOptions options = new YSFOptions();
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        options.savePowerConfig = new SavePowerConfig();
        return options;
    }
}
