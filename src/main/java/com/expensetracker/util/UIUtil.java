package com.expensetracker.util;

import javafx.scene.Scene;

public class UIUtil {

    public static void applyTheme(Scene scene) {
        var css = UIUtil.class.getResource(
                "/com/expensetracker/ui/style.css"
        );

        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        } else {
            System.out.println("⚠ style.css not found");
        }
    }
}
