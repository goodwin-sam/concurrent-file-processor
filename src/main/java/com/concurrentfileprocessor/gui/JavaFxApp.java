package com.concurrentfileprocessor.gui;

import com.concurrentfileprocessor.gui.window.Controller;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * main JavaFX application class
 * initializes the GUI and shows the start window
 */
public class JavaFxApp extends Application{
    /**
     * entry point for JavaFX application
     * creates controller and displays start window
     * @param primaryStage main application window
     */
    @Override
    public void start(Stage primaryStage) {
        Controller controller = new Controller(primaryStage);
        controller.showStartWindow(primaryStage);
    }
}
