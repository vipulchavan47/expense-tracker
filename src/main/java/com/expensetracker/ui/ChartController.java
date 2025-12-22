package com.expensetracker.ui;

import com.expensetracker.dao.ExpenseDAO;
import com.expensetracker.util.UserSession;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.Map;

public class ChartController {

    @FXML
    private BarChart<String, Number> monthlyChart;

    @FXML
    private PieChart categoryChart;

    @FXML
    public void initialize() {
        loadMonthlyChart();
        loadCategoryChart();
    }

    private void loadMonthlyChart() {
        try {
            ExpenseDAO dao = new ExpenseDAO();
            Map<String, Double> data =
                    dao.getMonthlyTotals(UserSession.getUserId());

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            data.forEach((k, v) ->
                    series.getData().add(new XYChart.Data<>(k, v)));

            monthlyChart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCategoryChart() {
        try {
            ExpenseDAO dao = new ExpenseDAO();
            Map<String, Double> data =
                    dao.getCategoryTotals(UserSession.getUserId());

            data.forEach((k, v) ->
                    categoryChart.getData().add(
                            new PieChart.Data(k, v)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
