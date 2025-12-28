package com.concurrentfileprocessor.gui.window;

import static com.concurrentfileprocessor.ConcurrentFileProcessor.fileStats;
import com.concurrentfileprocessor.gui.window.components.EventCreator;
import com.concurrentfileprocessor.gui.window.components.OutputWindowComponents;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * displays processing results and statistics
 * shows file counts, character counts, line counts and word counts
 */
public class OutputWindow {
    private final Controller controller;
    private final Stage stage;

    /**
     * constructor
     * @param controller controller for window navigation
     * @param stage stage to display window
     */
    public OutputWindow(Controller controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;
    }
    
    /**
     * displays the output results window
     */
    public void show() {
        OutputWindowComponents components = OutputWindowComponents.createOutputWindowLabels();
        components = OutputWindowComponents.createOutputWindowButtons(components);

        // set up back button to return to main window and reset stats but keeps file details
        components.backMainButton.setOnAction(event -> {
            fileStats = fileStats.refreshFileStats();
            controller.showMainWindow(stage);
        });
        components.restartButton = EventCreator.addRestartEvent(components.restartButton, controller, stage);

        // create file stats display section
        VBox fileStatsBox = new VBox(25);
        fileStatsBox.setAlignment(Pos.CENTER);
        fileStatsBox.getChildren().addAll(components.numOfFilesLabel, components.lineCountLabel, components.characterCountLabel, components.wordCountLabel);

        // create navigation buttons section
        VBox navigationBox = new VBox(15);
        navigationBox.setAlignment(Pos.CENTER);
        navigationBox.getChildren().addAll(components.backMainButton, components.restartButton, components.quitButton);
        
        // create root layout and configure window
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f8fafc; -fx-padding: 30px;");
        root.setCenter(fileStatsBox);
        root.setBottom(navigationBox);

        Scene scene = new Scene(root, 960, 540);
        stage.setTitle("Output Results Window");
        stage.setScene(scene);
        
        // set window properties
        stage.setMinWidth(960);
        stage.setMinHeight(540);
        stage.setResizable(true);
        
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * creates formatted output labels with specified text
     * @param text text to display in the label
     * @return formatted label with Arial font and size 36
     */
    public static Label createOutputLabels(String text) {
        Label label = new Label(text);
        label.setFont(new Font("Arial", 36));
        return label;
    }
}
