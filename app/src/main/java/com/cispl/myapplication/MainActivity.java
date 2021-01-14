package com.cispl.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Intent serviceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
       BroadcastReceiver mReceiver = new CloseSystemDialogsIntentReceiver();
        registerReceiver(mReceiver, filter);
    }


    @Override
    protected void onPause() {
        // when the screen is about to turn off
        if (CloseSystemDialogsIntentReceiver.wasScreenOn) {
            // this is the case when onPause() is called by the system due to a screen state change
            System.out.println("SCREEN TURNED OFF");

        } else {
            // this is when onPause() is called when the screen state has not changed
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        // only when screen turns on
        if (!CloseSystemDialogsIntentReceiver.wasScreenOn) {
            // this is when onResume() is called due to a screen state change
            System.out.println("SCREEN TURNED ON");
        } else {
            // this is when onResume() is called when the screen state has not changed
        }
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_POWER) {
            // Do something here...
            Log.d("ONKEYDOWN", "ONKEYDOWN");
            event.startTracking(); // Needed to track long presses
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_POWER) {
            // Do something here...
            Log.d("onKeyLongPress", "ONKEYDOWN");
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_POWER) {
            Log.d("dispatchKeyEvent", "ONKEYDOWN");

            return true;
        }

        return super.dispatchKeyEvent(event);
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }
}