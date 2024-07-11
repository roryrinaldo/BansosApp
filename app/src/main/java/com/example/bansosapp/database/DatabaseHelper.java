package com.example.bansosapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bansosapp.datautil.DefaultDataUtil;
import com.example.bansosapp.model.Customer;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bansos_app.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.CustomerEntry.SQL_CREATE_TABLE);

        // Tambahkan pernyataan CREATE TABLE untuk tabel lainnya jika diperlukan

        DefaultDataUtil.insertDefaultData(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.CustomerEntry.SQL_DROP_TABLE);

        // Tambahkan pernyataan DROP TABLE untuk tabel lainnya jika diperlukan

        onCreate(db);
    }

    public Customer getCustomerById(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                DatabaseContract.CustomerEntry.COLUMN_ID,
                DatabaseContract.CustomerEntry.COLUMN_USERNAME,
                DatabaseContract.CustomerEntry.COLUMN_PASSWORD,
                DatabaseContract.CustomerEntry.COLUMN_EMAIL,
                DatabaseContract.CustomerEntry.COLUMN_NAME
        };

        String selection = DatabaseContract.CustomerEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(customerId)};

        Cursor cursor = db.query(
                DatabaseContract.CustomerEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Customer customer = null;

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.CustomerEntry.COLUMN_ID));
            String username = cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerEntry.COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerEntry.COLUMN_PASSWORD));
            String email = cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerEntry.COLUMN_EMAIL));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseContract.CustomerEntry.COLUMN_NAME));

            customer = new Customer(id, username, password, email, name);
            cursor.close();
        }

        db.close();

        return customer;
    }

    public long addCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.CustomerEntry.COLUMN_USERNAME, customer.getUsername());
        values.put(DatabaseContract.CustomerEntry.COLUMN_PASSWORD, customer.getPassword());
        values.put(DatabaseContract.CustomerEntry.COLUMN_EMAIL, customer.getEmail());
        values.put(DatabaseContract.CustomerEntry.COLUMN_NAME, customer.getName());

        long result = db.insert(DatabaseContract.CustomerEntry.TABLE_NAME, null, values);
        db.close();

        return result;
    }

    public boolean updateCustomer(int customerId,  String newName, String newEmail,String newUsername, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.CustomerEntry.COLUMN_USERNAME, newUsername);
        values.put(DatabaseContract.CustomerEntry.COLUMN_PASSWORD, newPassword);
        values.put(DatabaseContract.CustomerEntry.COLUMN_NAME, newName);
        values.put(DatabaseContract.CustomerEntry.COLUMN_EMAIL, newEmail);

        int result = db.update(DatabaseContract.CustomerEntry.TABLE_NAME, values,
                DatabaseContract.CustomerEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(customerId)});

        db.close();
        return result != -1;
    }



}