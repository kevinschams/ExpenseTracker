package com.example.expensetracker;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetracker.dataaccess.ExpenseDataAccess;
import com.example.expensetracker.models.Expense;

import java.util.List;

public class CategoryExpensesActivity extends AppCompatActivity {

    private ListView listViewExpenses;
    private ExpenseAdapter expenseAdapter;
    private ExpenseDataAccess expenseDataAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_expenses);

        listViewExpenses = findViewById(R.id.listCategoryExpenses);

        // Get the category ID passed from the previous activity
        long categoryId = getIntent().getLongExtra("category_id", -1);

        // Initialize ExpenseDataAccess
        expenseDataAccess = new ExpenseDataAccess(this);

        // Fetch expenses associated with the selected category
        List<Expense> categoryExpenses = expenseDataAccess.getExpensesByCategory(categoryId);

        // Initialize and set up the ExpenseAdapter
        expenseAdapter = new ExpenseAdapter(this, categoryExpenses, expenseDataAccess, false);
        listViewExpenses.setAdapter(expenseAdapter);
    }
}
