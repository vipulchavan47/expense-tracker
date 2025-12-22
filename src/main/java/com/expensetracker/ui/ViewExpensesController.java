package com.expensetracker.ui;

import com.expensetracker.dao.ExpenseDAO;
import com.expensetracker.model.Expense;
import com.expensetracker.util.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ViewExpensesController {

    @FXML
    private TableView<Expense> expenseTable;

    @FXML
    private TableColumn<Expense, String> dateCol;

    @FXML
    private TableColumn<Expense, String> categoryCol;

    @FXML
    private TableColumn<Expense, Double> amountCol;

    @FXML
    private TableColumn<Expense, String> descCol;

    @FXML
    private TableColumn<Expense, Void> actionCol;

    @FXML
    public void initialize() {
        try {
            dateCol.setCellValueFactory(new PropertyValueFactory<>("expenseDate"));
            categoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
            amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
            descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

            ExpenseDAO dao = new ExpenseDAO();
            expenseTable.getItems().addAll(
                    dao.getExpensesByUser(UserSession.getUserId())
            );

            addActionButtons();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addActionButtons() {
        actionCol.setCellFactory(col -> new TableCell<>() {

            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox box = new HBox(10, editBtn, deleteBtn);

            {
                editBtn.setOnAction(e -> {
                    Expense exp = getTableView().getItems().get(getIndex());
                    handleEdit(exp);
                });

                deleteBtn.setOnAction(e -> {
                    Expense exp = getTableView().getItems().get(getIndex());
                    handleDelete(exp);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });
    }

    private void handleDelete(Expense expense) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setContentText("Delete this expense?");
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    ExpenseDAO dao = new ExpenseDAO();
                    dao.deleteExpense(expense.getExpenseId());
                    expenseTable.getItems().remove(expense);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleEdit(Expense expense) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/expensetracker/ui/edit_expense.fxml")
            );

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 300, 250));

            EditExpenseController controller = loader.getController();
            controller.setExpense(expense);

            stage.showAndWait();
            expenseTable.refresh();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
