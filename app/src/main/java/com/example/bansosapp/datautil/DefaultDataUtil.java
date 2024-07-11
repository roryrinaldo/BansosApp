package com.example.bansosapp.datautil;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.bansosapp.database.DatabaseContract;

public class DefaultDataUtil {

    public static void insertDefaultData(SQLiteDatabase db) {
        insertDefaultCustomers(db);
    }

    private static void insertDefaultCustomers(SQLiteDatabase db) {
        ContentValues values1 = new ContentValues();
        values1.put(DatabaseContract.CustomerEntry.COLUMN_USERNAME, "user1");
        values1.put(DatabaseContract.CustomerEntry.COLUMN_PASSWORD, "user1");
        values1.put(DatabaseContract.CustomerEntry.COLUMN_EMAIL, "user1@gmail.com");
        values1.put(DatabaseContract.CustomerEntry.COLUMN_NAME, "User 1");
        db.insert(DatabaseContract.CustomerEntry.TABLE_NAME, null, values1);
    }
}
