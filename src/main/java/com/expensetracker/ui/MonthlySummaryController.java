package com.expensetracker.ui;

import com.expensetracker.dao.ExpenseDAO;
import com.expensetracker.util.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MonthlySummaryController {

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private TextField yearField;

    @FXML
    private Label resultLabel;

    @FXML
    public void initialize() {
        monthComboBox.getItems().addAll(
                "January","February","March","April","May","June",
                "July","August","September","October","November","December"
        );
    }

    @FXML
    private void onViewSummary() {
        try {
            int month = monthComboBox.getSelectionModel().getSelectedIndex() + 1;
            int year = Integer.parseInt(yearField.getText());

            ExpenseDAO dao = new ExpenseDAO();
            double total = dao.getMonthlyTotal(
                    UserSession.getUserId(), month, year);

            resultLabel.setText("Total: ₹" + total);

        } catch (Exception e) {
            resultLabel.setText("Invalid input");
        }
    }
}
