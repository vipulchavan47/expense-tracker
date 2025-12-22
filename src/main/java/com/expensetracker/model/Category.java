package com.expensetracker.model;

public class Category {

    private int categoryId;
    private int userId;
    private String categoryName;

    // getters
    public int getCategoryId() {
        return categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    // setters
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return categoryName;
    }

}
