package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.expensetracker.dataaccess.ExpenseDataAccess;
import com.example.expensetracker.models.Expense;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnAddExpense;
    private ListView listViewExpenses;
    private ExpenseAdapter expenseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddExpense = findViewById(R.id.btnAddExpense);
        listViewExpenses = findViewById(R.id.listExpenses);
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AddExpenseActivity
                Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
                startActivity(intent);
            }
        });

        ExpenseDataAccess expenseDataAccess = new ExpenseDataAccess(this);
        List<Expense> expenses = expenseDataAccess.getAllExpenses();

        expenseAdapter = new ExpenseAdapter(this, expenses);
        listViewExpenses.setAdapter(expenseAdapter);
    }
}