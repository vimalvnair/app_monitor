package com.example.appmonitor;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AppMonitorService extends Service {
    public AppMonitorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
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
