package com.expensetracker.ui;

import com.expensetracker.dao.ExpenseDAO;
import com.expensetracker.model.Expense;
import com.expensetracker.util.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

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

    // export the monthly report in (csv)
    @FXML
    private void exportMonthlyCsv() {

        try {
            int userId = UserSession.getUserId();

            // Month from ComboBox index (1–12)
            int month = monthComboBox.getSelectionModel().getSelectedIndex() + 1;
            int year = Integer.parseInt(yearField.getText());

            ExpenseDAO expenseDAO = new ExpenseDAO();
            List<Expense> expenses =
                    expenseDAO.getExpensesByMonth(userId, month, year);

            FileChooser chooser = new FileChooser();
            chooser.setTitle("Save Monthly Report");
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv")
            );

            File file = chooser.showSaveDialog(null);
            if (file == null) return;

            try (FileWriter writer = new FileWriter(file)) {

                writer.write("Date,Category,Description,Amount\n");

                double total = 0;

                for (Expense e : expenses) {
                    writer.write(String.format(
                            "%s,%s,\"%s\",%.2f\n",
                            e.getExpenseDate(),
                            e.getCategoryName(),
                            e.getDescription(),
                            e.getAmount()
                    ));
                    total += e.getAmount();
                }

                writer.write(String.format("TOTAL,,,%.2f\n", total));
            }

            resultLabel.setText("CSV exported successfully");

        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid year");
        } catch (Exception e) {
            e.printStackTrace();
            resultLabel.setText("Error exporting CSV");
        }
    }

}
