package com.example.mystudy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TaskDatabase extends SQLiteOpenHelper {
    Context context;
    private static final String DatabaseName = "MyTaskReminder";
    private static final int DatabaseVersion = 1;

    public TaskDatabase(@Nullable Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        this.context =  context;
    }



    @Override
    public void onCreate(SQLiteDatabase taskdb) {
        taskdb.execSQL("create table table_taskreminder(title text primary key, description text, date text, time text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase taskdb, int i, int i1) {
        String query = "DROP TABLE IF EXISTS table_taskreminder" ;
        taskdb.execSQL(query);
        onCreate(taskdb);
    }

    String addtaskReminders(String title, String description, String date, String time){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("date", date);
        contentValues.put("time", time);

        long resultValue = sqLiteDatabase.insert("table_taskreminder", null, contentValues);

        if(resultValue ==  -1) {
            return "Failed";
        } else {
            return "success";
        }

    }

    public Cursor readAllData() {
        String query = "Select * from table_taskreminder";
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;
        if (database != null) {
            cursor = database.rawQuery(query, null);
        }
        return cursor;

    }

    public void deleteAllTasks() {
        SQLiteDatabase DB = this.getWritableDatabase();
        String query = String.valueOf(DB.rawQuery("DELETE FROM table_taskreminder", null));
        DB.execSQL(query);
    }

    public void deleteSingleTask(String recycler_title) {
        SQLiteDatabase DB = this.getWritableDatabase();
        long result = DB.delete("table_taskreminder", "title_tasklist=?", new String[]{recycler_title});
        if(result == -1) {

        }else {

        }
    }
}
