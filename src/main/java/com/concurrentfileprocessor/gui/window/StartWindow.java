package com.concurrentfileprocessor.gui.window;

import com.concurrentfileprocessor.gui.window.components.StartWindowComponents;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * displays welcome message and navigation buttons to start or quit
 */
public class StartWindow {
    private final Controller controller;        
    private final Stage stage;

    /**
     * constructor
     * @param controller controller for window navigation
     * @param stage stage to display window
     */
    public StartWindow(Controller controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;
    }

    /**
     * displays the start window with welcome interface
     */
    public void show() {
        // create main layout container
        VBox startLayout = new VBox(25);
        startLayout.setAlignment(Pos.CENTER);
        startLayout.setPrefSize(600, 450);
        startLayout.setStyle(
            "-fx-background-color: #f8fafc;" +
            "-fx-padding: 30px;"
        );

        // create UI components and set up button actions
        StartWindowComponents components = StartWindowComponents.createStartWindowComponents();
        components.startButton.setOnAction(e -> controller.showMainWindow(stage));
        
        // add all components to the layout container
        startLayout.getChildren().addAll(
            components.welcomeLabel,
            components.instructionLabel,
            components.descriptionLabel,
            components.startButton,
            components.quitButton
        );

        // create scene and configure stage properties
        Scene startScene = new Scene(startLayout, 600, 450);
        stage.setScene(startScene);
        stage.setTitle("Concurrent File Processor");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}
