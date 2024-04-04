package com.example.expensetracker.dataaccess;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.expensetracker.models.Category;
import com.example.expensetracker.sqlite.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryDataAccess {

    private Context context;
    private MySQLiteHelper dbHelper;
    private SQLiteDatabase database;
    public CategoryDataAccess(Context context) {
        this.context = context;
        this.dbHelper = new MySQLiteHelper(context);
        this.database = this.dbHelper.getWritableDatabase();
    }

    // Database constants
    private static final String DATABASE_NAME = "expense_tracker.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CATEGORIES = "categories";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

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
        try {
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);

                do {
                    long id = cursor.getLong(idIndex);
                    String name = cursor.getString(nameIndex);
                    Category category = new Category(id, name);
                    categories.add(category);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return categories;
    }





    public void updateCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, category.getName());

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(category.getId()) };

        db.update(TABLE_CATEGORIES, values, selection, selectionArgs);
        db.close();
    }
    public void deleteCategory(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_CATEGORIES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Category getCategoryById(long id) {
        return null;
    }



}
