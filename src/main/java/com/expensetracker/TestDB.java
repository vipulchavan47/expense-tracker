package com.expensetracker;

import com.expensetracker.util.DBConnection;

public class TestDB {
    public static void main(String[] args) {
        System.out.println(DBConnection.getConnection());
    }
}

