package com.example.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmService extends Service {

    private AlarmManager manager;
    private PendingIntent pendingIntent;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Retrieve a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(this, AlramReciver.class);
        alarmIntent.putExtra("message","You have meeting in next 1 hour");
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        Log.d("debugger","Alarm-service created");
//        cancelAlarm();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startAlarm();
        Log.d("debugger","Alarm-service started");
      //  cancelAlarm();
        return super.onStartCommand(intent, flags, startId);
    }

    public void startAlarm()
    {
        manager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE,14);
        calendar.set(Calendar.SECOND,00);
        calendar.set(Calendar.MILLISECOND,00);
//      manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60*1000, pendingIntent);
        manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
        Log.d("debugger","Alarm-alarm set");
    }

    public void cancelAlarm() {
        if (manager != null) {
            manager.cancel(pendingIntent);
            Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d("debugger","Alarm-service removed");
    }
}
