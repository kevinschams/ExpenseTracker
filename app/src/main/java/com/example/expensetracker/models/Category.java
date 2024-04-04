package com.example.expensetracker.models;

import java.io.Serializable;

public class Category implements Serializable {
    private long id;
    private String name;

    // Constructors
    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name; // Return the category name instead of the object reference
    }
}
