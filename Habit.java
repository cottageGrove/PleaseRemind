package com.example.android.pleaseremind;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Billy Goat on 12/4/2016.
 */

public class Habit implements Parcelable{

  private String habit, time, date, timestamp;
  private String id;

  public Habit(String habit, String time, String date, String id)
  {
    this.id = id;
    this.habit = habit;
    this.time = time;
    this.date = date;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(id);
    dest.writeString(habit);
    dest.writeString(time);
    dest.writeString(date);
  }

  protected Habit(Parcel in) {
    id = in.readString();
    habit = in.readString();
    time = in.readString();
    date = in.readString();
  }

  public static final Creator<Habit> CREATOR = new Creator<Habit>() {
    @Override
    public Habit createFromParcel(Parcel in) {
      return new Habit(in);
    }

    @Override
    public Habit[] newArray(int size) {
      return new Habit[size];
    }
  };

  public String getHabit() {
    return habit;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public void setHabit(String habit) {
    this.habit = habit;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
