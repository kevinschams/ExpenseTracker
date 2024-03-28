package com.example.expensetracker.dataaccess;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expensetracker.models.Category;
import com.example.expensetracker.sqlite.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryDataAccess {

    // Database constants
    private static final String DATABASE_NAME = "expense_tracker.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CATEGORIES = "categories";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    // Database helper
    private MySQLiteHelper dbHelper;

    public CategoryDataAccess(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    // Database helper class
    private static class MySQLiteHelper extends SQLiteOpenHelper {
        MySQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Create categories table
            String createTable = "CREATE TABLE " + TABLE_CATEGORIES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT)";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Handle database upgrades (if needed)
        }
    }

    // Add category to database
    public void addCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, category.getName());
        db.insert(TABLE_CATEGORIES, null, values);
        db.close();
    }

    // Get all categories from database
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORIES, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                Category category = new Category(id, name);
                categories.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }




    public void updateCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, category.getName());

        // Define the WHERE clause to identify the category to be updated
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(category.getId()) };

        // Update the category in the database
        db.update(TABLE_CATEGORIES, values, selection, selectionArgs);
        db.close();
    }
    public void deleteCategory(long id) {}
    public Category getCategoryById(long id) {
        return null;
    }



}
