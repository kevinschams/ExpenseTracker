package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetracker.dataaccess.ExpenseDataAccess;
import com.example.expensetracker.models.Expense;

import java.util.Date;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText editExpenseName, editExpenseAmount;
    private Button btnSaveExpense;
    private ExpenseDataAccess expenseDataAccess;

    private Expense editingExpense; // Variable to hold the expense being edited

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        editExpenseName = findViewById(R.id.editExpenseName);
        editExpenseAmount = findViewById(R.id.editExpenseAmount);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);
        expenseDataAccess = new ExpenseDataAccess(this);

        // Check if intent contains extra data (expense to edit)
        Intent intent = getIntent();
        if (intent.hasExtra("expenseToEdit")) {
            // Extract the expense data from the intent
            editingExpense = (Expense) intent.getSerializableExtra("expenseToEdit");

            // Set the fields with the data from the editing expense
            editExpenseName.setText(editingExpense.getName());
            editExpenseAmount.setText(String.valueOf(editingExpense.getAmount()));

            // Change the text of the button to indicate editing mode
            btnSaveExpense.setText("Update Expense");
        } else {
            // If no extra data, leave the fields blank (adding mode)
            editingExpense = null;
            btnSaveExpense.setText("Save Expense");
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
        String amountStr = editExpenseAmount.getText().toString().trim();

        if (name.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);
        Date date = new Date(); // You can customize this to allow user input for date
        long categoryId = 0; // You need to set the categoryId based on user selection or logic

        // Create a new expense or update the editing expense
        Expense expense;
        if (editingExpense != null) {
            // If editing an existing expense, update its fields
            expense = new Expense(editingExpense.getId(), name, amount, date, categoryId);
        } else {
            // If adding a new expense, create a new expense object
            expense = new Expense(0, name, amount, date, categoryId);
        }

        // Add or update the expense in the database
        if (editingExpense != null) {
            expenseDataAccess.updateExpense(expense);
        } else {
            expenseDataAccess.addExpense(expense);
        }

        Toast.makeText(this, "Expense " + (editingExpense != null ? "updated" : "added") + " successfully", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity after adding or updating expense
    }
}
