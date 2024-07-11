package com.example.bansosapp.database;

public class DatabaseContract {
    private DatabaseContract() {}

    // Konstanta untuk nama tabel dan kolom dalam tabel Customer
    public static final class CustomerEntry {
        public static final String TABLE_NAME = "customer";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_NAME = "name";

        // Pernyataan SQL untuk membuat tabel Customer
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_USERNAME + " TEXT ," +
                        COLUMN_PASSWORD + " TEXT," +
                        COLUMN_EMAIL + " TEXT, "+
                        COLUMN_NAME + " TEXT )";

        // Pernyataan SQL untuk menghapus tabel Customer
        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }


}