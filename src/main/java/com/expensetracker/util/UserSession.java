package com.expensetracker.util;

public class UserSession {

    private static int userId;
    private static String username;

    public static void login(int id, String name) {
        userId = id;
        username = name;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getUsername() {
        return username;
    }

    public static void logout() {
        userId = 0;
        username = null;
    }
}
