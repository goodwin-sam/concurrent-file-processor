package com.concurrentfileprocessor.gui.window;

import javafx.stage.Stage;

/**
 * central controller for managing window navigation
 * handles transitions between different application windows
 */
public class Controller {

    /**
     * constructor
     */
    public Controller(Stage primaryStage) {}

    /**
     * shows the start window
     * @param primaryStage stage to display start window
     */
    public void showStartWindow(Stage primaryStage) {
        StartWindow startWindow = new StartWindow(this, primaryStage);
        startWindow.show();
    }

    /**
     * shows the main processing window
     * @param primaryStage stage to display main window
     */
    public void showMainWindow(Stage primaryStage) {
        MainWindow mainWindow = new MainWindow(this, primaryStage);
        mainWindow.show();
    }

    /**
     * shows the output results window
     * @param primaryStage stage to display output window
     */
    public void showOutputWindow(Stage primaryStage) {
        OutputWindow outputWindow = new OutputWindow(this, primaryStage);
        outputWindow.show();
    }
}
