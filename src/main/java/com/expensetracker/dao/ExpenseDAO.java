package com.expensetracker.dao;
//DAO = Data Access Object

import com.expensetracker.model.Expense;
import com.expensetracker.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExpenseDAO {

    // This method ONLY inserts expense
    public void addExpense(Expense expense) throws Exception {

        String sql = """
            INSERT INTO expenses
            (user_id, category_id, amount, description, expense_date)
            VALUES (?, ?, ?, ?, ?)
        """;

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, expense.getUserId());
        ps.setInt(2, expense.getCategoryId());
        ps.setDouble(3, expense.getAmount());
        ps.setString(4, expense.getDescription());
        ps.setDate(5, java.sql.Date.valueOf(expense.getExpenseDate()));

        ps.executeUpdate();

        con.close();
    }

    public List<Expense> getExpensesByUser(int userId) throws Exception {

        List<Expense> expenses = new ArrayList<>();

        String sql = """
    SELECT e.expense_id,
           e.amount,
           e.description,
           e.expense_date,
           c.category_name
    FROM expenses e
    JOIN categories c ON e.category_id = c.category_id
    WHERE e.user_id = ?
    ORDER BY e.expense_date DESC
""";

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Expense e = new Expense();

            e.setExpenseId(rs.getInt("expense_id"));
            e.setAmount(rs.getDouble("amount"));
            e.setDescription(rs.getString("description"));
            e.setExpenseDate(rs.getDate("expense_date").toLocalDate());
            e.setCategoryName(rs.getString("category_name")); // 🔥 key line


            expenses.add(e);
        }

        con.close();
        return expenses;
    }

    public void updateExpense(int expenseId, int categoryId,
                              double amount, String description) throws Exception {

        String sql = """
        UPDATE expenses
        SET category_id = ?, amount = ?, description = ?
        WHERE expense_id = ?
    """;

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, categoryId);
        ps.setDouble(2, amount);
        ps.setString(3, description);
        ps.setInt(4, expenseId);

        ps.executeUpdate();
        con.close();
    }

    public void updateExpense(int expenseId,
                              double amount, String description) throws Exception {

        String sql = """
        UPDATE expenses
        SET amount = ?, description = ?
        WHERE expense_id = ?
    """;

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setDouble(1, amount);
        ps.setString(2, description);
        ps.setInt(3, expenseId);

        ps.executeUpdate();
        con.close();
    }


    public void deleteExpense(int expenseId) throws Exception {

        String sql = "DELETE FROM expenses WHERE expense_id = ?";

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, expenseId);
        ps.executeUpdate();

        con.close();
    }

    public double getMonthlyTotal(int userId, int month, int year) throws Exception {

        String sql = """
        SELECT IFNULL(SUM(amount), 0) AS total
        FROM expenses
        WHERE user_id = ?
          AND MONTH(expense_date) = ?
          AND YEAR(expense_date) = ?
    """;

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, userId);
        ps.setInt(2, month);
        ps.setInt(3, year);

        ResultSet rs = ps.executeQuery();

        double total = 0;
        if (rs.next()) {
            total = rs.getDouble("total");
        }

        con.close();
        return total;
    }

    public Map<String, Double> getMonthlyTotals(int userId) throws Exception {

        String sql = """
        SELECT DATE_FORMAT(expense_date, '%Y-%m') AS month,
               SUM(amount) total
        FROM expenses
        WHERE user_id = ?
        GROUP BY month
        ORDER BY month
    """;

        Map<String, Double> map = new LinkedHashMap<>();

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            map.put(rs.getString("month"), rs.getDouble("total"));
        }

        con.close();
        return map;
    }

    public Map<String, Double> getCategoryTotals(int userId) throws Exception {

        String sql = """
        SELECT c.category_name, SUM(e.amount) total
        FROM expenses e
        JOIN categories c ON e.category_id = c.category_id
        WHERE e.user_id = ?
        GROUP BY c.category_name
    """;

        Map<String, Double> map = new LinkedHashMap<>();

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            map.put(rs.getString("category_name"), rs.getDouble("total"));
        }

        con.close();
        return map;
    }

    public Map<String, Double> getCategoryWiseMonthlyTotal(int userId, int month, int year)
            throws Exception {

        String sql = """
        SELECT c.category_name, SUM(e.amount) AS total
        FROM expenses e
        JOIN categories c ON e.category_id = c.category_id
        WHERE e.user_id = ?
          AND MONTH(e.expense_date) = ?
          AND YEAR(e.expense_date) = ?
        GROUP BY c.category_name
    """;

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, userId);
        ps.setInt(2, month);
        ps.setInt(3, year);

        ResultSet rs = ps.executeQuery();

        Map<String, Double> result = new LinkedHashMap<>();
        while (rs.next()) {
            result.put(
                    rs.getString("category_name"),
                    rs.getDouble("total")
            );
        }

        con.close();
        return result;
    }



}
