package com.expensetracker.ui;

import com.expensetracker.dao.UserDAO;
import com.expensetracker.util.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    @FXML
    private void onLogin() {
        try {
            UserDAO dao = new UserDAO();
            ResultSet rs = dao.login(
                    emailField.getText(),
                    passwordField.getText()
            );

            if (rs.next()) {
                UserSession.login(
                        rs.getInt("user_id"),
                        rs.getString("name")
                );
                openDashboard();
            } else {
                statusLabel.setText("Invalid email or password");
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Login failed");
        }
    }

    @FXML
    private void onRegister() {
        try {
            if (nameField.getText().isBlank() ||
                    emailField.getText().isBlank() ||
                    passwordField.getText().isBlank()) {

                statusLabel.setText("All fields required for registration");
                return;
            }

            UserDAO dao = new UserDAO();
            dao.register(
                    nameField.getText(),
                    emailField.getText(),
                    passwordField.getText()
            );

            statusLabel.setText("Registered successfully. Login now.");

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Registration failed (email may exist)");
        }
    }

    private void openDashboard() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/expensetracker/ui/dashboard.fxml")
        );

        Stage stage = new Stage();
        stage.setTitle("Dashboard");
        stage.setScene(new Scene(loader.load(), 400, 350));
        stage.show();



        Stage current = (Stage) emailField.getScene().getWindow();
        current.close();
    }
}
