package com.concurrentfileprocessor.gui.window.components;

import static com.concurrentfileprocessor.ConcurrentFileProcessor.fileStats;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * component factory class for output window UI elements
 * creates and configures all labels and buttons for the output window
 */
public class OutputWindowComponents {
    // UI component fields for output window
    public Label numOfFilesLabel;
    public Label lineCountLabel;
    public Label characterCountLabel;
    public Label wordCountLabel;
    public Button backMainButton;
    public Button restartButton;
    public Button quitButton;

    /**
     * constructor for output window components
     */
    public OutputWindowComponents(Label numOfFilesLabel, Label lineCountLabel, Label characterCountLabel, Label wordCountLabel, Button backMainButton, Button restartButton, Button quitButton) {
        this.numOfFilesLabel = numOfFilesLabel;
        this.lineCountLabel = lineCountLabel;
        this.characterCountLabel = characterCountLabel;
        this.wordCountLabel = wordCountLabel;
        this.backMainButton = backMainButton;
        this.restartButton = restartButton;
        this.quitButton = quitButton;
    }

    /**
     * creates all label components for the output window
     * @return OutputWindowComponents object with all labels configured
     */
    public static OutputWindowComponents createOutputWindowLabels() {
        // create file count label
        Label numOfFilesLabel = new Label("Number of files: " + fileStats.numberOfFiles);
        numOfFilesLabel.setFont(new Font("Arial", 36));
        numOfFilesLabel.setStyle("-fx-text-fill: #1e40af;");

        // create line count label
        Label lineCountLabel = new Label("Total line count: " + fileStats.lineCount.get());
        lineCountLabel.setFont(new Font("Arial", 36));
        lineCountLabel.setStyle("-fx-text-fill: #059669;");

        // create character count label
        Label characterCountLabel = new Label("Total character count: " + fileStats.characterCount.get());
        characterCountLabel.setFont(new Font("Arial", 36));
        characterCountLabel.setStyle("-fx-text-fill: #dc2626;");

        // create word count label
        Label wordCountLabel = new Label("Total word count: " + fileStats.wordCount.size());
        wordCountLabel.setFont(new Font("Arial", 36));
        wordCountLabel.setStyle("-fx-text-fill: #7c3aed;");

        return new OutputWindowComponents(numOfFilesLabel, lineCountLabel, characterCountLabel, wordCountLabel, null, null, null);
    }

    /**
     * creates all button components for the output window
     * @param components existing components object to update
     * @return updated components with all buttons configured
     */
    public static OutputWindowComponents createOutputWindowButtons(OutputWindowComponents components) {
        // create back to main window button
        Button backMainButton = ButtonCreator.createButton("Back to Main Window", (event) -> {});
        backMainButton.setFont(new Font("System", 18));
        backMainButton.setPrefSize(250, 50);
        backMainButton.setStyle(
            "-fx-background-color: #3b82f6;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;"
        );

        // create restart button
        Button restartButton = ButtonCreator.createRestartButton();
        restartButton.setFont(new Font("System", 18));
        restartButton.setPrefSize(200, 50);

        // create quit button
        Button quitButton = ButtonCreator.createQuitButton();
        quitButton.setFont(new Font("System", 18));
        quitButton.setPrefSize(150, 50);

        // assign all buttons to components
        components.backMainButton = backMainButton;
        components.restartButton = restartButton;
        components.quitButton = quitButton;

        return components;
    }
}
