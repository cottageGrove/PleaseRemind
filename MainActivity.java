package com.example.android.pleaseremind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private HabitsAdapter adapter;
  private ArrayList <Habit> list;
  private RecyclerView rvHabits;
  private HabitsDb habitsDb;
  private LinearLayoutManager mLayoutManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_habits_listing);

    rvHabits = (RecyclerView) findViewById(R.id.habits);

    //Create a new list of Habits
    list = new ArrayList<Habit>();

    habitsDb = new HabitsDb(this);

    //Read the values saved in the database
    habitsDb.loadAllHabits();

    list = habitsDb.loadAllHabits();

    //getBaseContext() refers to activity.this
    //Second parameter refers to the current activity we are in
    adapter = new HabitsAdapter(list, getBaseContext());

    //Setting the recycler view to the custom Habits Adapter created
    rvHabits.setAdapter(adapter);

    //a layout manager is needed for the RecyclerView to function
    mLayoutManager = new LinearLayoutManager(this);
    rvHabits.setLayoutManager(mLayoutManager);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_home, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    int id = item.getItemId();

    if (id == R.id.menuAdd) {
      startActivity(new Intent(this, HabitActivity.class));
      return true;
    }


    return super.onOptionsItemSelected(item);
  }
}
