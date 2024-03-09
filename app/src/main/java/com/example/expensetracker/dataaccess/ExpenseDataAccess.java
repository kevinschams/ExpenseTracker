package com.example.expensetracker.dataaccess;

import com.example.expensetracker.AddExpenseActivity;
import com.example.expensetracker.models.Expense;

import java.util.List;

public class ExpenseDataAccess {

    public ExpenseDataAccess(AddExpenseActivity addExpenseActivity) {
    }

    // Methods for CRUD operations on expenses
    public void addExpense(Expense expense) {}
    public void updateExpense(Expense expense) {}
    public void deleteExpense(long id) {}
    public Expense getExpenseById(long id) {
        return null;
    }
    public List<Expense> getAllExpenses() {
        return null;
    }
}

