package com.example.android.pleaseremind;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.android.pleaseremind.R.id.add;

/**
 * Created by Billy Goat on 12/4/2016.
 */
public class HabitActivity extends AppCompatActivity{

  private EditText edtHabit, edtDate, edtTime;
  private int mYear, mMonth, mDay, mHour, mMinute;
  private Button addBtn;
  private AlarmManager am;
  private Calendar c1, c2;
  private String timeToNotify;
  private String formattedTime;
  String habitEntered, dateEntered, timeEntered;
  Habit habit;
  private SimpleDateFormat formatter;
  String toParse;
  private Date date;
  private long tsSet;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_habit);

    edtHabit = (EditText) findViewById(R.id.habit);
    edtTime = (EditText) findViewById(R.id.time);
    edtDate = (EditText) findViewById(R.id.date);
    addBtn = (Button) findViewById(R.id.add);



    edtTime.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        c1 = Calendar.getInstance();
        mHour = c1.get(Calendar.HOUR_OF_DAY);
        mMinute = c1.get(Calendar.MINUTE);


        //Create a new dialog  object
        TimePickerDialog timePickerDialog = new TimePickerDialog(HabitActivity.this,
          new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
              timeToNotify = hourOfDay + ":" + minute;
              formattedTime = FormatTime(hourOfDay, minute);
              edtTime.setText(formattedTime);
            }
          }, mHour, mMinute, false);
          timePickerDialog.show();
      }
    });

    edtDate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        c2 = Calendar.getInstance();
        mYear = c2.get(Calendar.YEAR);
        mMonth = c2.get(Calendar.MONTH);
        mDay = c2.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(HabitActivity.this,
          new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

              edtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

            }
          }, mYear, mMonth, mDay);

        datePickerDialog.show();

      }
    });


    addBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        //Save entered data to database
        habitEntered = edtHabit.getText().toString();
        dateEntered = edtDate.getText().toString();
        timeEntered = edtTime.getText().toString();

        habit = new Habit (habitEntered, timeEntered, dateEntered, null);
        HabitsDb database = new HabitsDb(getApplicationContext());
        database.saveHabit(habit);

        //set the alarm manager
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //Call function
        scheduleNotification(getApplicationContext(), 0);

        Toast.makeText(getBaseContext(), "Habit Reminder Set", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getBaseContext(), MainActivity.class));

      }
    });

  }


  public void scheduleNotification(Context context, int notificationId) {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
      .setSmallIcon(R.drawable.ic_alarm_white_24dp)
      .setAutoCancel(true)
      .setContentTitle("Habit Reminder")
      .setContentText(habit.getHabit());

      Intent intent = new Intent(context, HabitActivity.class);
      PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
      builder.setContentIntent(activity);

      Notification notification = builder.build();

    Intent notificationIntent = new Intent(context, TimeAlarm.class);
    notificationIntent.putExtra(TimeAlarm.NOTIFICATION_ID, notificationId);
    notificationIntent.putExtra(TimeAlarm.NOTIFICATION, notification);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

    try {
      //Convert the set date and time to timestamp
      toParse = dateEntered + " " + timeToNotify;
      formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
      date = formatter.parse(toParse);
      tsSet = date.getTime();
    } catch (ParseException e) {
      e.printStackTrace();
    }

    am.set(AlarmManager.RTC_WAKEUP, tsSet, pendingIntent);

  }

//  private void startAlarm() {
//    //Create a new intent
//    Intent intent = new Intent(this, TimeAlarm.class);
//    intent.putExtra("habit", habitEntered);
//    intent.putExtra("time", timeEntered);
//    intent.putExtra("date", dateEntered);
//
//    //Remains throughout the application
//    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//
//    try {
//      //Convert the set date and time to timestamp
//      toParse = dateEntered + " " + timeToNotify;
//      formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
//      date = formatter.parse(toParse);
//      tsSet = date.getTime();
//    } catch (ParseException e) {
//        e.printStackTrace();
//    }
//
//    am.set(AlarmManager.RTC_WAKEUP, tsSet, pendingIntent);
//
//  }

  private String FormatTime(int hourOfDay, int minute) {

    return null;
  }
}
