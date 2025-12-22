package com.expensetracker.dao;

import com.expensetracker.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // REGISTER
    public boolean register(String name, String email, String password) throws Exception {

        String sql = """
            INSERT INTO users (name, email, password_hash)
            VALUES (?, ?, ?)
        """;

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, password); // plain text for now

        ps.executeUpdate();
        con.close();
        return true;
    }

    // LOGIN
    public ResultSet login(String email, String password) throws Exception {

        String sql = """
            SELECT user_id, name
            FROM users
            WHERE email = ? AND password_hash = ?
        """;

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, email);
        ps.setString(2, password);

        return ps.executeQuery();
    }
}
