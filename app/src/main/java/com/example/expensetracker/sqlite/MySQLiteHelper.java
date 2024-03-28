package com.example.expensetracker.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expense_tracker.db";
    private static final int DATABASE_VERSION = 1;

    // Define table creation SQL queries
    private static final String CREATE_EXPENSE_TABLE = "CREATE TABLE expenses " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, amount REAL, date INTEGER, categoryId INTEGER)";

    private static final String CREATE_CATEGORY_TABLE = "CREATE TABLE categories " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";

    // Constructor
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(CREATE_EXPENSE_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades (if needed)
    }
}

