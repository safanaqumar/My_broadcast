package com.cispl.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class CloseSystemDialogsIntentReceiver extends BroadcastReceiver {
    public static boolean wasScreenOn = true;
    static long prevTime=0;
    static long currTime=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent background = new Intent(context, BackgroundService.class);
        context.startService(background);

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            // do whatever you need to do here
            Log.d("CHECK IN RECIVER WHEN ON","CHECK IN RECIVER WHEN ON");
            wasScreenOn = false;
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            // and do whatever you need to do here
            Log.d("CHECK IN RECIVER WHEN ON","CHECK IN RECIVER WHEN OFF");
            wasScreenOn = true;
        }

        if (prevTime == 0) {
// power button first time pressed or after you double-pressed
            prevTime = System.currentTimeMillis();
        } else if (((currTime = System.currentTimeMillis()) - prevTime) < 1000 ) {
// second press under 1s(double-pressed), reset prevTime
            Toast.makeText(context, "double Clicked power button",
                    Toast.LENGTH_LONG).show();
            Log.e("eciver ", "double Clicked power button");
            prevTime = 0;
        } else {
// second press over 1s, considered as first press for next checking
            prevTime = currTime;
        }
}
}
