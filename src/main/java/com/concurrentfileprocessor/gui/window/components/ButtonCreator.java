package com.concurrentfileprocessor.gui.window.components;

import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

/**
 * factory class for creating and configuring buttons
 * provides consistent button styling and behavior across the application
 */
public class ButtonCreator {
    
    /**
     * creates a button with custom text and action
     * @param text button text to display
     * @param action action to execute when button is clicked
     * @return configured button
     */
    public static Button createButton(String text, Consumer<ActionEvent> action) {
        Button button = new Button(text);
        button.setOnAction(action::accept);
        button.setFont(Font.font("System", 16));
        button.setPrefSize(150, 40);
        button.setStyle(
            "-fx-background-color: #3b82f6;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 6;"
        );
        return button;
    }
    
    /**
     * creates a quit button that exits the application
     * @return configured quit button
     */
    public static Button createQuitButton() {
        Button quitButton = createButton("Quit", (event) -> Platform.exit());
        quitButton.setStyle(
            "-fx-background-color: #dc2626;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 6;"
        );
        return quitButton;
    }
    
    /**
     * creates a restart button that resets the application state
     * @return configured restart button
     */
    public static Button createRestartButton() {
        Button restartButton = createButton("Restart", (event) -> {});
        restartButton.setStyle(
            "-fx-background-color: #f59e0b;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 6;"
        );
        return restartButton;
    }
}
