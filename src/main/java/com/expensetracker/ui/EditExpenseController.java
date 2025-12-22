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

import java.util.List;

public class EditExpenseController {

    @FXML
    private ComboBox<Category> categoryComboBox;

    @FXML
    private TextField amountField;

    @FXML
    private TextField descriptionField;

    private Expense expense;

    @FXML
    public void initialize() {
        try {
            CategoryDAO dao = new CategoryDAO();
            List<Category> categories =
                    dao.getCategoriesByUser(UserSession.getUserId());
            categoryComboBox.getItems().addAll(categories);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setExpense(Expense expense) {
        this.expense = expense;

        amountField.setText(String.valueOf(expense.getAmount()));
        descriptionField.setText(expense.getDescription());

        for (Category c : categoryComboBox.getItems()) {
            if (c.getCategoryName().equals(expense.getCategoryName())) {
                categoryComboBox.setValue(c);
                break;
            }
        }
    }

    @FXML
    private void onUpdateExpense() {
        try {
            Category category = categoryComboBox.getValue();
            double amount = Double.parseDouble(amountField.getText());
            String desc = descriptionField.getText();

            if (category == null || desc.isBlank()) {
                showAlert("Validation Error", "All fields are required");
                return;
            }

            ExpenseDAO dao = new ExpenseDAO();
            dao.updateExpense(
                    expense.getExpenseId(),
                    category.getCategoryId(),
                    amount,
                    desc
            );

            expense.setCategoryName(category.getCategoryName());
            expense.setAmount(amount);
            expense.setDescription(desc);

            Stage stage = (Stage) amountField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Amount must be numeric");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Update failed");
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
