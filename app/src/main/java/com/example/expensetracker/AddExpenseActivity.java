package com.example.expensetracker;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetracker.dataaccess.ExpenseDataAccess;
import com.example.expensetracker.models.Expense;

import java.util.Date;
import java.util.List;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText editExpenseName, editExpenseAmount;
    private Button btnSaveExpense;
    private ExpenseDataAccess expenseDataAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        editExpenseName = findViewById(R.id.editExpenseName);
        editExpenseAmount = findViewById(R.id.editExpenseAmount);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);
        expenseDataAccess = new ExpenseDataAccess(this);

        btnSaveExpense.setOnClickListener(v -> saveExpense());
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
        // Create Expense object with the provided constructor
        Expense expense = new Expense(0, name, amount, date, categoryId);

        expenseDataAccess.addExpense(expense);
        Toast.makeText(this, "Expense added successfully", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity after adding expense
    }

}
