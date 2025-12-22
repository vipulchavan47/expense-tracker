package com.expensetracker.dao;

import com.expensetracker.model.Category;
import com.expensetracker.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public List<Category> getCategoriesByUser(int userId) throws Exception {

        List<Category> list = new ArrayList<>();

        String sql = "SELECT category_id, category_name FROM categories WHERE user_id = ?";

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Category c = new Category();
            c.setCategoryId(rs.getInt("category_id"));
            c.setCategoryName(rs.getString("category_name"));
            list.add(c);
        }

        con.close();
        return list;
    }

    public void addCategory(int userId, String categoryName) throws Exception {

        String sql = """
        INSERT INTO categories (user_id, category_name)
        VALUES (?, ?)
    """;

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, userId);
        ps.setString(2, categoryName);

        ps.executeUpdate();
        con.close();
    }

    public void addCategory(Category category) throws Exception {

        String sql = """
        INSERT INTO categories (user_id, category_name)
        VALUES (?, ?)
    """;

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, category.getUserId());
        ps.setString(2, category.getCategoryName());

        ps.executeUpdate();
        con.close();
    }

}
