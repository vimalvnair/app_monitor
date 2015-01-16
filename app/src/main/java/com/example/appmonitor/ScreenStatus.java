package com.example.appmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenStatus extends BroadcastReceiver {
    public static boolean SCREEN_ON = true;

    public ScreenStatus() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            SCREEN_ON = true;
        }else if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            SCREEN_ON = false;
        }
    }
}
