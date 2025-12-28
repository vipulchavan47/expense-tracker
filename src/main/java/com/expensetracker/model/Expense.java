package com.expensetracker.model;

import java.time.LocalDate;

public class Expense {

    private int expenseId;
    private int userId;
    private int categoryId;
    private double amount;
    private String description;
    private LocalDate expenseDate;
    private String categoryName;

    // setters
    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }


    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // ===== getters =====
    public int getExpenseId() {
        return expenseId;
    }

    public int getUserId() {
        return userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

}
