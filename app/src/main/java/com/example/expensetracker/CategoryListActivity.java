package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expensetracker.dataaccess.CategoryDataAccess;
import com.example.expensetracker.models.Category;

import java.util.List;

public class CategoryListActivity extends AppCompatActivity implements CategoryAdapter.OnDeleteClickListener, CategoryAdapter.OnViewClickListener{

    private EditText editCategoryName;
    private Button btnSaveCategory;
    private CategoryDataAccess categoryDataAccess;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        editCategoryName = findViewById(R.id.editCategoryName);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);
        categoryDataAccess = new CategoryDataAccess(this);


        btnSaveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCategory();
            }
        });

    }


    private void saveCategory() {
        String name = editCategoryName.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show();
            return;
        }
        // Create a new Category object
        Category category = new Category(name);

        // Add the new category to the database
        categoryDataAccess.addCategory(category);

        // Go back to the main activity where the categories button is clicked
        Intent intent = new Intent(CategoryListActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onViewClick(long categoryId) {
        Intent intent = new Intent(CategoryListActivity.this, CategoryExpensesActivity.class);
        intent.putExtra("category_id", categoryId);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {

    }
}
