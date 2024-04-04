package com.example.expensetracker;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.expensetracker.dataaccess.CategoryDataAccess;
import com.example.expensetracker.models.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {

    private List<Category> categories;
    private OnDeleteClickListener deleteClickListener;
    private OnViewClickListener viewClickListener; // Add view click listener
    private CategoryDataAccess categoryDataAccess;

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public interface OnViewClickListener {
        void onViewClick(long categoryId); // Pass the category ID
    }

    public CategoryAdapter(Context context, List<Category> categories, CategoryDataAccess categoryDataAccess) {
        super(context, 0, categories);
        this.categories = categories;
        this.categoryDataAccess = categoryDataAccess;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    public void setOnViewClickListener(OnViewClickListener listener) {
        this.viewClickListener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Category category = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_category, parent, false);
        }

        TextView categoryNameTextView = convertView.findViewById(R.id.textCategoryName);
        Button deleteButton = convertView.findViewById(R.id.buttonDeleteCategory);
        Button viewButton = convertView.findViewById(R.id.buttonViewCategory); // Get the view button

        categoryNameTextView.setText(category.getName());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteClickListener != null) {
                    deleteClickListener.onDeleteClick(position);
                }
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch CategoryExpensesActivity and pass category ID
                Intent intent = new Intent(getContext(), CategoryExpensesActivity.class);
                intent.putExtra("category_id", category.getId());
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    // Method to add a new category to the list and update the dataset
    public void addCategory(Category category) {
        // Add the new category to the list
        categories.add(category);
        // Update the dataset
        notifyDataSetChanged();
        categoryDataAccess.updateCategory(category);
    }
}

