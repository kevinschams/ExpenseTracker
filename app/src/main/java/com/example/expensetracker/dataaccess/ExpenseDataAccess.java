package com.example.expensetracker.dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expensetracker.AddExpenseActivity;
import com.example.expensetracker.models.Expense;
import com.example.expensetracker.sqlite.MySQLiteHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseDataAccess {

        // Database constants
        private static final String DATABASE_NAME = "expense_tracker.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_EXPENSES = "expenses";
        private static final String COLUMN_ID = "id";
        private static final String COLUMN_NAME = "name";
        private static final String COLUMN_AMOUNT = "amount";
        private static final String COLUMN_DATE = "date";
        private static final String COLUMN_CATEGORY_ID = "categoryId";

        // Database helper
        private MySQLiteHelper dbHelper;

        public ExpenseDataAccess(Context context) {
                dbHelper = new MySQLiteHelper(context);
        }

        // Database helper class
        private static class MySQLiteHelper extends SQLiteOpenHelper {
                MySQLiteHelper(Context context) {
                        super(context, DATABASE_NAME, null, DATABASE_VERSION);
                }

                @Override
                public void onCreate(SQLiteDatabase db) {
                        // Create expenses table
                        String createTable = "CREATE TABLE " + TABLE_EXPENSES + " (" +
                                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                COLUMN_NAME + " TEXT, " +
                                COLUMN_AMOUNT + " REAL, " +
                                COLUMN_DATE + " INTEGER, " +
                                COLUMN_CATEGORY_ID + " INTEGER)";
                        db.execSQL(createTable);
                }

                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                        // Handle database upgrades (if needed)
                }
        }

        // Add expense to database
        public void addExpense(Expense expense) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME, expense.getName());
                values.put(COLUMN_AMOUNT, expense.getAmount());
                values.put(COLUMN_DATE, expense.getDate().getTime());
                values.put(COLUMN_CATEGORY_ID, expense.getCategoryId());
                db.insert(TABLE_EXPENSES, null, values);
                db.close();
        }

        // Get all expenses from database
        public List<Expense> getAllExpenses() {
                List<Expense> expenses = new ArrayList<>();
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query(TABLE_EXPENSES, null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                        do {
                                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                                double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT));
                                long dateInMillis = cursor.getLong(cursor.getColumnIndex(COLUMN_DATE));
                                long categoryId = cursor.getLong(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
                                Expense expense = new Expense(id, name, amount, new Date(dateInMillis), categoryId);
                                expenses.add(expense);
                        } while (cursor.moveToNext());
                }
                cursor.close();
                db.close();
                return expenses;
        }


        public void updateExpense(Expense expense) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME, expense.getName());
                values.put(COLUMN_AMOUNT, expense.getAmount());
                values.put(COLUMN_DATE, expense.getDate().getTime());
                values.put(COLUMN_CATEGORY_ID, expense.getCategoryId());

                // Define the WHERE clause to identify the expense to be updated
                String selection = COLUMN_ID + " = ?";
                String[] selectionArgs = { String.valueOf(expense.getId()) };

                // Update the expense in the database
                db.update(TABLE_EXPENSES, values, selection, selectionArgs);
                db.close();
        }
    public void deleteExpense(long id) {}
    public Expense getExpenseById(long id) {
        return null;
    }

}

