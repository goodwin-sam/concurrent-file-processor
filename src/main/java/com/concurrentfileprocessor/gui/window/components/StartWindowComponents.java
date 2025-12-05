package com.concurrentfileprocessor.gui.window.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * component factory class for start window UI elements
 * creates and configures all labels and buttons for the start window
 */
public class StartWindowComponents {
    // UI component fields for start window
    public Label welcomeLabel;
    public Label instructionLabel;
    public Label descriptionLabel;
    public Button startButton;
    public Button quitButton;

    /**
     * constructor for start window components
     * @param welcomeLabel welcome message label
     * @param instructionLabel instruction text label
     * @param descriptionLabel description text label
     * @param startButton start application button
     * @param quitButton quit application button
     */
    public StartWindowComponents(Label welcomeLabel, Label instructionLabel, Label descriptionLabel, Button startButton, Button quitButton) {
        this.welcomeLabel = welcomeLabel;
        this.instructionLabel = instructionLabel;
        this.descriptionLabel = descriptionLabel;
        this.startButton = startButton;
        this.quitButton = quitButton;
    }

    /**
     * creates all UI components for the start window
     * configures labels and buttons with appropriate styling and text
     * @return StartWindowComponents object with all components configured
     */
    public static StartWindowComponents createStartWindowComponents() {
        // create welcome label
        Label welcomeLabel = new Label("Welcome to Concurrent File Processor");
        welcomeLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        welcomeLabel.setStyle("-fx-text-fill: #1e40af;");

        // create instruction label
        Label instructionLabel = new Label("Click the Start button to begin processing text files");
        instructionLabel.setFont(Font.font("System", 18));
        instructionLabel.setStyle("-fx-text-fill: #6b7280;");

        // create description label
        Label descriptionLabel = new Label("This application processes multiple text files concurrently to analyze word frequency, character counts, and line counts");
        descriptionLabel.setFont(Font.font("System", 16));
        descriptionLabel.setStyle("-fx-text-fill: #6b7280;");
        descriptionLabel.setWrapText(true);

        // create start button
        Button startButton = ButtonCreator.createButton("Start", (event) -> {});
        startButton.setFont(Font.font("System", 20));
        startButton.setPrefSize(200, 50);
        startButton.setStyle(
            "-fx-background-color: #059669;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 10;"
        );

        // create quit button
        Button quitButton = ButtonCreator.createQuitButton();

        return new StartWindowComponents(welcomeLabel, instructionLabel, descriptionLabel, startButton, quitButton);
    }
}
