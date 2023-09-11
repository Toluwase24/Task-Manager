package com.example.mystudy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountDatabase extends SQLiteOpenHelper {
    public static final String DatabaseName = "Login.db";

    public AccountDatabase(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase accountdb) {
        accountdb.execSQL("create Table accountdetails(emailaddress TEXT primary key, name TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase accountdb, int i, int i1) {
        accountdb.execSQL("drop Table if exists accountdetails");
    }

    public Boolean insertaccountdata(String emailaddress, String name, String password) {
        SQLiteDatabase accountdb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("emailaddress", emailaddress);
        contentValues.put("name", name);
        contentValues.put("password", password);
        long result = accountdb.insert("accountdetails", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkemailaddress(String emailaddress) {
        SQLiteDatabase accountdb = this.getWritableDatabase();
        Cursor cursor = accountdb.rawQuery("Select * from accountdetails where emailaddress = ?", new String[]{emailaddress});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean checkemailaddresspassword(String emailaddress, String password) {
        SQLiteDatabase accountdb = this.getWritableDatabase();
        Cursor cursor = accountdb.rawQuery("Select * from accountdetails where emailaddress = ? and password = ?", new String[]{emailaddress, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}


