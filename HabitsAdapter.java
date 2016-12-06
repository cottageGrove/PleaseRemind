package com.example.android.pleaseremind;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Billy Goat on 12/4/2016.
 */

//This class is the adapter for Habit
public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.ViewHolder> {

  //Declare a list of habits
  private ArrayList<Habit> habits;
  //Declare a single habit
  private Habit habit;
  private Context context;

  public HabitsAdapter(ArrayList<Habit> habits, Context context) {
    this.habits = habits;
    this.context = context;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    private TextView habitText;
    private TextView timeAndDateText;
    private LinearLayout listing;

    //This constructor is initializing all the widgets on listings_row.xml
    public ViewHolder(View view) {
      super(view);
      habitText = (TextView) view.findViewById(R.id.habitTxt);
      timeAndDateText = (TextView) view.findViewById(R.id.time_and_date);
      listing = (LinearLayout) view.findViewById(R.id.listing);
    }
  }

  //View group is the base class for layouts and holds all the views
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View itemView = inflater.inflate(R.layout.listings_row, parent, false);


    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {

    //delete select habit from database
    habit = habits.get(position);

    //Setting each viewholder attribute to habit field variables
    holder.habitText.setText(habit.getHabit());
    holder.timeAndDateText.setText("At " + habit.getTime() + " On " + habit.getDate());




    //to delete from database
    holder.listing.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {

        HabitsDb habitsDb = new HabitsDb(context);
        habitsDb.deleteHabit(habits.get(position));

        habits.remove(habits.get(position));
        notifyDataSetChanged();
        return false;
      }
    });
  }

  @Override
  public int getItemCount() {return habits.size();}

}
