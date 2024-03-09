package com.example.expensetracker.models;

import java.util.Date;

public class Expense {
    private long id;
    private String name;
    private double amount;
    private Date date;
    private long categoryId;

    // Constructor
    public Expense(long id, String name, double amount, Date date, long categoryId) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.categoryId = categoryId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
