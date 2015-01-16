package com.example.appmonitor;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AppMonitorService extends Service {
    private static int INTERVAL_IN_SECONDS = 5;
    HashMap<String, Integer> usageData = new HashMap<String, Integer>();
    DatabaseHelper databaseHelper = null;
    public AppMonitorService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter screenIntentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        screenIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        BroadcastReceiver screenStatus = new ScreenStatus();
        registerReceiver(screenStatus, screenIntentFilter);
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
                boolean locked = keyguardManager.inKeyguardRestrictedInputMode();
                if(ScreenStatus.SCREEN_ON && !locked){
                    logAppUsage();
                }
            }
        }, 1, INTERVAL_IN_SECONDS, TimeUnit.SECONDS);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void logAppUsage() {
        ActivityManager am;
        am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
        String foregroundTaskPackageName = foregroundTaskInfo.topActivity.getPackageName();


        if(usageData.containsKey(foregroundTaskPackageName)) {
            usageData.put(foregroundTaskPackageName, usageData.get(foregroundTaskPackageName) + 1);
        } else {
            usageData.put(foregroundTaskPackageName, 1);
        }
//        SQLiteDatabase sqLiteDatabase = databaseHelper.getSqLiteDatabase();
//
//        for(Map.Entry<String, Integer> entry: usageData.entrySet()){
//            String packageName = entry.getKey();
//            String foregroundTaskAppName = getAppName(packageName);
//            Cursor cursor;
//            cursor = sqLiteDatabase.rawQuery("select * from " + DatabaseHelper.TABLE_NAME + " where " + DatabaseHelper.PACKAGE_NAME + " = ? and " + DatabaseHelper.REPORT_DATE + " = ?" , new String[]{packageName});
//            if(cursor.getCount() == 0){
//
//            }
//            Log.d("appmonit", "key: "+ entry.getKey() + " value: "+ entry.getValue());
//        }

        Log.d("appmonit", usageData.toString() + "package: " + foregroundTaskPackageName);
    }

    private String getAppName(String foregroundTaskPackageName) {
        PackageManager pm = this.getPackageManager();
        PackageInfo foregroundAppPackageInfo = null;
        try {
            foregroundAppPackageInfo = pm.getPackageInfo(foregroundTaskPackageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return foregroundAppPackageInfo.applicationInfo.loadLabel(pm).toString();
    }

    @Override
    @SuppressLint("NewApi")
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
         Notification notification = new Notification.Builder(this)
                .setContentTitle("App Monitor")
                .setContentText("App Monitor Service is running")
                .setSmallIcon(R.drawable.ic_launcher)
                .build();
        startForeground(400, notification);
        return START_STICKY;
    }
}
