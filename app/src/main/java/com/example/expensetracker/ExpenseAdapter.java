package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.expensetracker.dataaccess.ExpenseDataAccess;
import com.example.expensetracker.models.Expense;

import java.text.SimpleDateFormat;
import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    private List<Expense> expenses;
    private OnDeleteClickListener deleteClickListener;
    private OnEditClickListener editClickListener; // New interface for edit button click
    private ExpenseDataAccess expenseDataAccess;
    private boolean showButtons;

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public interface OnEditClickListener {
        void onEditClick(Expense expense);
    }

    public ExpenseAdapter(Context context, List<Expense> expenses, ExpenseDataAccess expenseDataAccess, boolean showButtons) {
        super(context, 0, expenses);
        this.expenses = expenses;
        this.expenseDataAccess = expenseDataAccess;
        this.showButtons = showButtons; // Initialize the flag
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    public void setOnEditClickListener(OnEditClickListener listener) { // Setter for edit button click listener
        this.editClickListener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Expense expense = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_expense, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.textExpenseName);
        TextView tvAmount = convertView.findViewById(R.id.textExpenseAmount);
        TextView tvDate = convertView.findViewById(R.id.textExpenseDate);
        Button btnDelete = convertView.findViewById(R.id.buttonDeleteExpense);
        Button btnEdit = convertView.findViewById(R.id.buttonEditExpense); // Edit button

        tvName.setText(expense.getName());
        tvAmount.setText(String.valueOf(expense.getAmount()));
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        tvDate.setText(sdf.format(expense.getDate()));
        // Hide buttons based on the flag
        if (!showButtons) {
            btnDelete.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
        } else {
            btnDelete.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteClickListener != null) {
                    deleteClickListener.onDeleteClick(position);
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() { // Set OnClickListener for edit button
            @Override
            public void onClick(View v) {
                if (editClickListener != null) {
                    editClickListener.onEditClick(expense); // Pass the selected expense to the listener
                }
            }
        });

        return convertView;
    }

    // Method to add a new expense to the list and update the dataset
    public void addExpense(Expense expense) {
        // Add the new expense to the list
        expenses.add(expense);
        // Update the dataset
        notifyDataSetChanged();
        // Update the expense in the database
        expenseDataAccess.updateExpense(expense);
    }
}

