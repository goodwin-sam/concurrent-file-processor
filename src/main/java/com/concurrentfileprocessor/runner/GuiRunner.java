package com.concurrentfileprocessor.runner;

import com.concurrentfileprocessor.gui.JavaFxApp;

import javafx.application.Application;

/**
 * launcher class for GUI mode
 */
public class GuiRunner {
    /**
     * launches the JavaFX GUI application
     */
    public static void launch() {
        System.out.println("Running in GUI mode");
        Application.launch(JavaFxApp.class);
    }
}
