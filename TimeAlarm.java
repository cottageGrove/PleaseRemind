package com.example.android.pleaseremind;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Billy Goat on 12/5/2016.
 */

public class TimeAlarm extends BroadcastReceiver {

  private NotificationManager nm;
  private String habit;
  private Notification notification;
  public static String NOTIFICATION_ID = "notification_id";
  public static String NOTIFICATION = "notification";

  @Override
  public void onReceive(Context context, Intent intent) {
    Bundle extras = intent.getExtras();
    habit = extras.getString("habit");

    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
      .setSmallIcon(R.drawable.ic_alarm_white_24dp)
      .setAutoCancel(true)
      .setContentTitle("Habit Reminder")
      .setContentText(habit);

    nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    nm.notify(0, notificationBuilder.build());

  }
}
