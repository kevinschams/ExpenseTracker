package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetracker.dataaccess.CategoryDataAccess;
import com.example.expensetracker.dataaccess.ExpenseDataAccess;
import com.example.expensetracker.models.Category;
import com.example.expensetracker.models.Expense;

import java.util.Date;
import java.util.List;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText editExpenseName, editExpenseAmount;
    private Spinner categorySpinner;
    private Button btnSaveExpense;
    private ExpenseDataAccess expenseDataAccess;
    private CategoryDataAccess categoryDataAccess;
    private List<Category> categories;
    private ArrayAdapter<Category> categoryAdapter;

    private Expense editingExpense; // Variable to hold the expense being edited
    private long expenseId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        editExpenseName = findViewById(R.id.editExpenseName);
        editExpenseAmount = findViewById(R.id.editExpenseAmount);
        categorySpinner = findViewById(R.id.spinnerCategories);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);

        expenseDataAccess = new ExpenseDataAccess(this);
        categoryDataAccess = new CategoryDataAccess(this);

        // Get categories from the database
        categories = categoryDataAccess.getAllCategories();

        // Populate the spinner with categories
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // If editing expense, pre-fill the fields
        expenseId = getIntent().getLongExtra("expense_id", -1);
        if (expenseId != -1) {
            Expense expense = expenseDataAccess.getExpenseById(expenseId);
            if (expense != null) {
                editingExpense = expense;
                editExpenseName.setText(expense.getName());
                editExpenseAmount.setText(String.valueOf(expense.getAmount()));

                // Set the selected category in the spinner
                int categoryIndex = getCategoryIndexById(expense.getCategoryId());
                categorySpinner.setSelection(categoryIndex);
            }
        }

        btnSaveExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExpense();
            }
        });
    }

    private void saveExpense() {
        String name = editExpenseName.getText().toString().trim();
        String amountString = editExpenseAmount.getText().toString().trim();

        if (name.isEmpty() || amountString.isEmpty()) {
            // Handle empty fields
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountString);
        Date date = new Date(); // You can customize this to allow user input for date

        // Get the selected category from the spinner
        Category selectedCategory = (Category) categorySpinner.getSelectedItem();
        long categoryId = selectedCategory.getId();

        // Create a new Expense or update the editing Expense
        Expense expense;
        if (editingExpense != null) {
            // If editing an existing Expense, update its fields
            expense = new Expense(expenseId, name, amount, date, categoryId);
        } else {
            // If adding a new Expense, create a new Expense object
            expense = new Expense(0, name, amount, date, categoryId);
        }

        // Add or update the Expense in the database
        if (editingExpense != null) {
            expenseDataAccess.updateExpense(expense);
        } else {
            expenseDataAccess.addExpense(expense);
        }

        Toast.makeText(this, "Expense " + (editingExpense != null ? "updated" : "added") + " successfully", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity after adding or updating Expense
    }

    // Helper method to get the index of a Category by its ID
    private int getCategoryIndexById(long categoryId) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == categoryId) {
                return i;
            }
        }
        return -1;
    }
}
