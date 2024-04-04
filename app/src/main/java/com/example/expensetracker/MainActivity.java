package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.expensetracker.dataaccess.CategoryDataAccess;
import com.example.expensetracker.dataaccess.ExpenseDataAccess;
import com.example.expensetracker.models.Category;
import com.example.expensetracker.models.Expense;
import com.example.expensetracker.AddExpenseActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnAddExpense;
    private Button btnExpenses; // Button for expenses
    private Button btnCategories; // Button for categories
    private ListView listViewExpenses;
    private ListView listViewCategories;
    private ExpenseAdapter expenseAdapter;
    private CategoryAdapter categoryAdapter;
    private ExpenseDataAccess expenseDataAccess;
    private Button btnAddCategory; // Add category button
    private CategoryDataAccess categoryDataAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnExpenses = findViewById(R.id.btnExpenses); // Initialize expenses button
        btnCategories = findViewById(R.id.btnCategories); // Initialize categories button
        listViewExpenses = findViewById(R.id.listExpenses);
        listViewCategories = findViewById(R.id.listCategories); // Initialize categories ListView
        btnAddCategory = findViewById(R.id.btnAddCategory); // Initialize add category button

        // Hide the categories ListView and Add Category button by default
        listViewCategories.setVisibility(View.GONE);
        btnAddCategory.setVisibility(View.GONE);

        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AddExpenseActivity
                Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
                startActivity(intent);
            }
        });
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AddCategoryActivity
                Intent intent = new Intent(MainActivity.this, CategoryListActivity.class);
                startActivity(intent);
            }
        });

        btnExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show expenses ListView and hide categories ListView
                listViewExpenses.setVisibility(View.VISIBLE);
                listViewCategories.setVisibility(View.GONE);
                btnAddCategory.setVisibility(View.GONE);

                // Load expenses
                loadExpenses();
            }
        });


        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show categories ListView and hide expenses ListView
                listViewExpenses.setVisibility(View.GONE);
                listViewCategories.setVisibility(View.VISIBLE);
                btnAddCategory.setVisibility(View.VISIBLE);

                // Load categories
                loadCategories();
                // Set delete functionality for category items
                categoryAdapter.setOnDeleteClickListener(new CategoryAdapter.OnDeleteClickListener() {
                    @Override
                    public void onDeleteClick(int position) {
                        // Get the category at the clicked position
                        Category categoryToDelete = categoryAdapter.getItem(position);

                        // Delete the category from the database
                        categoryDataAccess.deleteCategory(categoryToDelete.getId());

                        // Remove the category from the list and update the ListView
                        categoryAdapter.remove(categoryToDelete);

                        categoryAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        expenseDataAccess = new ExpenseDataAccess(this);
        final List<Expense> expenses = expenseDataAccess.getAllExpenses();

        expenseAdapter = new ExpenseAdapter(this, expenses, expenseDataAccess);
        listViewExpenses.setAdapter(expenseAdapter);

        // Set OnClickListener for delete button in each row of the ListView
        expenseAdapter.setOnDeleteClickListener(new ExpenseAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                // Get the expense at the clicked position
                Expense expenseToDelete = expenses.get(position);

                // Delete the expense from the database
                expenseDataAccess.deleteExpense(expenseToDelete.getId());

                // Remove the expense from the list and update the ListView
                expenses.remove(position);
                expenseAdapter.notifyDataSetChanged();
            }
        });




        // Set OnEditClickListener for edit button in each row of the ListView
        expenseAdapter.setOnEditClickListener(new ExpenseAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(Expense expense) {
                // Start AddExpenseActivity with the selected expense for editing
                Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
//                intent.putExtra("expenseToEdit", expense);
                intent.putExtra("expense_id", expense.getId());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh the expense list when the activity resumes
        refreshExpenseList();
    }

    private void refreshExpenseList() {
        List<Expense> expenses = expenseDataAccess.getAllExpenses();

        // Update the adapter with the new list of expenses
        expenseAdapter.clear();
        expenseAdapter.addAll(expenses);
        expenseAdapter.notifyDataSetChanged();
    }

    private void loadExpenses() {
        // Fetch expenses from the database
        List<Expense> expenses = expenseDataAccess.getAllExpenses();

        // Initialize ExpenseAdapter if not already initialized
        if (expenseAdapter == null) {
            expenseAdapter = new ExpenseAdapter(MainActivity.this, expenses, expenseDataAccess);
            listViewExpenses.setAdapter(expenseAdapter);
        } else {
            // Update existing adapter with new data
            expenseAdapter.clear();
            expenseAdapter.addAll(expenses);
            expenseAdapter.notifyDataSetChanged();
        }
    }

    // Method to load categories
    private void loadCategories() {
        // Initialize CategoryDataAccess if not already initialized
        if (categoryDataAccess == null) {
            categoryDataAccess = new CategoryDataAccess(MainActivity.this);
        }

        // Fetch categories from the database
        List<Category> categories = categoryDataAccess.getAllCategories();

        // Initialize CategoryAdapter if not already initialized
        if (categoryAdapter == null) {
            categoryAdapter = new CategoryAdapter(MainActivity.this, categories, categoryDataAccess);
            listViewCategories.setAdapter(categoryAdapter);
        } else {
            // Update existing adapter with new data
            categoryAdapter.clear();
            categoryAdapter.addAll(categories);
            categoryAdapter.notifyDataSetChanged();
        }
    }
}
