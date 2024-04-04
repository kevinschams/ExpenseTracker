package com.example.expensetracker.dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//import com.example.expensetracker.AddExpenseActivity;
import com.example.expensetracker.models.Expense;
import com.example.expensetracker.sqlite.MySQLiteHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseDataAccess {
        private Context context;
        private MySQLiteHelper dbHelper;
        private SQLiteDatabase database;

        public ExpenseDataAccess(Context context){
                this.context = context;
                this.dbHelper = new MySQLiteHelper(context);
                this.database = this.dbHelper.getWritableDatabase();
        }

        private static final String DATABASE_NAME = "expense_tracker.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_EXPENSES = "expenses";
        private static final String COLUMN_ID = "id";
        private static final String COLUMN_NAME = "name";
        private static final String COLUMN_AMOUNT = "amount";
        private static final String COLUMN_DATE = "date";
        private static final String COLUMN_CATEGORY_ID = "categoryId";


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
                try {
                        if (cursor != null) {
                                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                                int amountIndex = cursor.getColumnIndex(COLUMN_AMOUNT);
                                int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
                                int categoryIdIndex = cursor.getColumnIndex(COLUMN_CATEGORY_ID);

                                while (cursor.moveToNext()) {
                                        long id = cursor.getLong(idIndex);
                                        String name = cursor.getString(nameIndex);
                                        double amount = cursor.getDouble(amountIndex);
                                        long dateInMillis = cursor.getLong(dateIndex);
                                        long categoryId = cursor.getLong(categoryIdIndex);
                                        Expense expense = new Expense(id, name, amount, new Date(dateInMillis), categoryId);
                                        expenses.add(expense);
                                }
                        }
                } finally {
                        if (cursor != null) {
                                cursor.close();
                        }
                        db.close();
                }
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
        public void deleteExpense(long id) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Define the WHERE clause to identify the expense to be deleted
                String selection = COLUMN_ID + " = ?";
                String[] selectionArgs = { String.valueOf(id) };

                // Delete the expense from the database
                db.delete(TABLE_EXPENSES, selection, selectionArgs);

                db.close();
        }
        public Expense getExpenseById(long id) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Expense expense = null;

                // Define the WHERE clause to select the expense by its ID
                String selection = COLUMN_ID + " = ?";
                String[] selectionArgs = { String.valueOf(id) };

                Cursor cursor = db.query(
                        TABLE_EXPENSES,
                        null,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );

                try {
                        if (cursor != null && cursor.moveToFirst()) {
                                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                                int amountIndex = cursor.getColumnIndex(COLUMN_AMOUNT);
                                int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
                                int categoryIdIndex = cursor.getColumnIndex(COLUMN_CATEGORY_ID);

                                long expenseId = cursor.getLong(idIndex);
                                String name = cursor.getString(nameIndex);
                                double amount = cursor.getDouble(amountIndex);
                                long dateInMillis = cursor.getLong(dateIndex);
                                long categoryId = cursor.getLong(categoryIdIndex);

                                expense = new Expense(expenseId, name, amount, new Date(dateInMillis), categoryId);
                        }
                } finally {
                        if (cursor != null) {
                                cursor.close();
                        }
                        db.close();
                }

                return expense;
        }

        public List<Expense> getExpensesByCategory(long categoryId) {
                List<Expense> expenses = new ArrayList<>();
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                // Define the WHERE clause to select expenses by category ID
                String selection = COLUMN_CATEGORY_ID + " = ?";
                String[] selectionArgs = { String.valueOf(categoryId) };

                Cursor cursor = db.query(
                        TABLE_EXPENSES,
                        null,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );

                try {
                        if (cursor != null) {
                                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                                int amountIndex = cursor.getColumnIndex(COLUMN_AMOUNT);
                                int dateIndex = cursor.getColumnIndex(COLUMN_DATE);

                                while (cursor.moveToNext()) {
                                        long id = cursor.getLong(idIndex);
                                        String name = cursor.getString(nameIndex);
                                        double amount = cursor.getDouble(amountIndex);
                                        long dateInMillis = cursor.getLong(dateIndex);

                                        // Since we're querying by category, we already have the category ID
                                        // No need to retrieve it from the cursor
                                        Expense expense = new Expense(id, name, amount, new Date(dateInMillis), categoryId);
                                        expenses.add(expense);
                                }
                        }
                } finally {
                        if (cursor != null) {
                                cursor.close();
                        }
                        db.close();
                }

                return expenses;
        }

}

