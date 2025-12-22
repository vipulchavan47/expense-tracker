package com.expensetracker.ui;

import com.expensetracker.dao.CategoryDAO;
import com.expensetracker.model.Category;
import com.expensetracker.util.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCategoryController {

    @FXML
    private TextField categoryField;

    @FXML
    private Label statusLabel;


    @FXML
    private void onAddCategory() {
        try {
            String name = categoryField.getText();

            if (name == null || name.isBlank()) {
                statusLabel.setText("Category name required");
                return;
            }

            Category category = new Category();
            category.setUserId(UserSession.getUserId());
            category.setCategoryName(name);

            CategoryDAO dao = new CategoryDAO();
            dao.addCategory(category);

            Stage stage = (Stage) categoryField.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Failed to add category");
        }
    }

}
