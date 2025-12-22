package com.expensetracker.ui;

import com.expensetracker.dao.CategoryDAO;
import com.expensetracker.dao.ExpenseDAO;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.util.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class AddExpenseController {

    @FXML
    private ComboBox<Category> categoryComboBox;

    @FXML
    private TextField amountField;

    @FXML
    private TextField descriptionField;

    @FXML
    public void initialize() {
        try {
            CategoryDAO dao = new CategoryDAO();
            List<Category> categories =
                    dao.getCategoriesByUser(UserSession.getUserId());
            categoryComboBox.getItems().addAll(categories);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load categories");
        }
    }

    @FXML
    private void onSaveExpense() {
        try {
            Category category = categoryComboBox.getValue();
            String desc = descriptionField.getText();
            double amount = Double.parseDouble(amountField.getText());

            if (category == null || desc.isBlank()) {
                showAlert("Validation Error", "All fields are required");
                return;
            }

            Expense expense = new Expense();
            expense.setUserId(UserSession.getUserId());
            expense.setCategoryId(category.getCategoryId());
            expense.setAmount(amount);
            expense.setDescription(desc);
            expense.setExpenseDate(LocalDate.now());

            ExpenseDAO dao = new ExpenseDAO();
            dao.addExpense(expense);

            Stage stage = (Stage) amountField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Amount must be numeric");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save expense");
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
