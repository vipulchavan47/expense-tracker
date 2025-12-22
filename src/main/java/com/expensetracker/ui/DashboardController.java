package com.expensetracker.ui;

import com.expensetracker.util.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private Button addExpenseBtn;

    @FXML
    private void onAddExpenseClick() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/expensetracker/ui/add_expense.fxml")
            );

            Stage stage = new Stage();
            stage.setTitle("Add Expense");
            stage.setScene(new Scene(loader.load(), 300, 280));

            // 👇 refresh dashboard after window closes
            stage.setOnHidden(e -> refreshDashboard());

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshDashboard() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Refreshed");
        alert.setHeaderText(null);
        alert.setContentText("Dashboard refreshed (we will replace this later)");
        alert.showAndWait();
    }

    @FXML
    private void onViewExpensesClick() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/expensetracker/ui/view_expenses.fxml")
            );

            Stage stage = new Stage();
            stage.setTitle("My Expenses");
            stage.setScene(new Scene(loader.load(), 500, 350));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onChartsClick() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/expensetracker/ui/charts.fxml")
            );

            Stage stage = new Stage();
            stage.setTitle("Expense Charts");
            stage.setScene(new Scene(loader.load(), 600, 600));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onMonthlySummaryClick() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/expensetracker/ui/monthly_summary.fxml")
            );

            Stage stage = new Stage();
            stage.setTitle("Monthly Summary");
            stage.setScene(new Scene(loader.load(), 350, 300));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onLogout() {
        try {
            UserSession.logout();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/expensetracker/ui/login.fxml")
            );

            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(loader.load(), 350, 300));
            stage.show();

            // close dashboard
            Stage current = (Stage) addExpenseBtn.getScene().getWindow();
            current.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAddCategoryClick() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/expensetracker/ui/add_category.fxml")
            );

            Stage stage = new Stage();
            stage.setTitle("Add Category");
            stage.setScene(new Scene(loader.load(), 300, 200));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
