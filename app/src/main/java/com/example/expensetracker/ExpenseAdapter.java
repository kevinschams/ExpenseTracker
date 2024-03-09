package com.example.expensetracker;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.expensetracker.models.Expense;

import java.text.SimpleDateFormat;
import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    private List<Expense> expenses;

    public ExpenseAdapter(Context context, List<Expense> expenses) {
        super(context, 0, expenses);
        this.expenses = expenses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Expense expense = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_expense, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.textExpenseName);
        TextView tvAmount = convertView.findViewById(R.id.textExpenseAmount);
        TextView tvDate = convertView.findViewById(R.id.textExpenseDate);

        tvName.setText(expense.getName());
        tvAmount.setText(String.valueOf(expense.getAmount()));
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        tvDate.setText(sdf.format(expense.getDate()));

        return convertView;
    }
}
