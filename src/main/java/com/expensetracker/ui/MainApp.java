package com.expensetracker.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/expensetracker/ui/login.fxml")
        );


        Scene scene = new Scene(loader.load(), 400, 300);
        scene.getStylesheets().add(
                getClass().getResource("/com/expensetracker/ui/style.css").toExternalForm()
        );


        stage.setTitle("Expense Tracker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/expensetracker/ui/dashboard.fxml")
    );

}
