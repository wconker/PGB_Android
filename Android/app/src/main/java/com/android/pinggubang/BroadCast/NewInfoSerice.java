package com.android.pinggubang.BroadCast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.pinggubang.Activity.Activity_MainCenter;
import com.android.pinggubang.Activity.Guide.MainActivity;
import com.android.pinggubang.R;

public class NewInfoSerice extends Service implements ExChange.CallBackForData {

    private ExChange ex;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ex = new ExChange(this);
        ex.WebSocket();


        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private final int id = 7250;

    void ShowNotification() {
        NotificationManager nm = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(getApplication(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        PendingIntent contentIntent = PendingIntent.getBroadcast(this.getApplicationContext(), id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 获取Notification对象之前,设置相关属性.
        Notification notification = new NotificationCompat.Builder(getApplication())
                // 设置通知文本标题
                .setContentTitle("信息通知")
                // 设置通知文本内容
                .setContentText("有新的询价发布！")
                // 通知被创建的时间,以毫秒为单位.
                .setWhen(System.currentTimeMillis())

                .setContentIntent(contentIntent)
                // 设置小图标,也就是在状态栏上显示那个小的图标.
                .setSmallIcon(R.mipmap.ic_launcher)
                // 设置大图标,将状态栏展开,看到的通知图标.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo144_144))
                // 设置自动取消.否则状态的图标一直存在,也就是说通知没有被取消
                .setAutoCancel(true)
                // 构建出Notification对象.
                .build();

        notification.number = 1;
        nm.notify(id, notification);


    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == OK) {
                ShowNotification();
            }

        }
    };
    private final int OK = 100;

    @Override
    public void onMessage(String str, String cmd, int code) {

        //这里的websocket 驻留在后台
        com.android.pinggubang.Utils.Log.e("Conker"+"========="+""+str);
        handler.sendEmptyMessage(OK);
    }
}
