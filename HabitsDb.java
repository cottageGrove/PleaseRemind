package com.example.android.pleaseremind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by mmous on 11/22/2016.
 */
public class HabitsDb {

    // instance members for accessing the database
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    long dbId;

    // constants for the database
    public static final String DB_NAME = "habits.db";
    public static final int DB_VERSION = 1;

    // constants for the table
    public static final String HABITS_TABLE = "Habits";

    public static final String ID = "_id";
    public static final int ID_COLUMN = 0;

    public static final String NAME = "name";
    public static final int NAME_COLUMN = 1;

    public static final String TIME = "time";
    public static final int TIME_COLUMN = 2;

    public static final String DATE = "date";
    public static final int DATE_COLUMN = 3;


    // DDL for creating the table. Careful with adding spaces!!
    public static final String CREATE_HABITS_TABLE =
            "CREATE TABLE " + HABITS_TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " TEXT, " +
                    DATE + " TEXT, " +
                    TIME + " TEXT)";

    public HabitsDb(Context context){
        openHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    //save habit
    public Habit saveHabit(Habit habit){
        database = openHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, habit.getHabit());
        values.put(TIME, habit.getTime());
        values.put(DATE, habit.getDate());

        dbId = database.insert(HABITS_TABLE, null, values);
        habit.setId(Long.toString(dbId));

        values.put(ID, Long.toString(dbId));

        database.close();
        return habit;
    }

    public int deleteAllHabits(){
        int numberDeleted = 0;

        database = openHelper.getWritableDatabase();
        numberDeleted = database.delete(HABITS_TABLE, null, null);

        database.close();
        return numberDeleted;

    }

    public int deleteHabit(Habit habit){
        int numberDeleted = 0;

        database = openHelper.getWritableDatabase();
        String id = habit.getId();

        String where = ID + "=?";
        String[] whereArgs = new String[]{id};

        numberDeleted = database.delete(HABITS_TABLE, where, whereArgs);
        database.close();

        return numberDeleted;
    }

    //load all habits
    public ArrayList<Habit> loadAllHabits(){
        ArrayList<Habit> habits = new ArrayList<>();

        database = openHelper.getReadableDatabase();

        Cursor result = database.query(HABITS_TABLE, null, null, null, null, null, NAME);

        while(result.moveToNext()){

            String dbId = result.getString(result.getColumnIndex(ID));
            String name = result.getString(result.getColumnIndex(NAME));
            String time = result.getString(result.getColumnIndex(TIME));
            String date = result.getString(result.getColumnIndex(DATE));

            habits.add(new Habit(name, time, date, dbId));
        }

        result.close();
        database.close();

        return habits;
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String dbName, SQLiteDatabase.CursorFactory o, int dbVersion){
            super(context, dbName, o, dbVersion);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_HABITS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + HABITS_TABLE);
            onCreate(db);
        }
    }
}
