package com.concurrentfileprocessor.gui.window.components;

import java.io.File;
import java.util.List;

import static com.concurrentfileprocessor.ConcurrentFileProcessor.inputFiles;
import static com.concurrentfileprocessor.ConcurrentFileProcessor.outputFilePath;
import static com.concurrentfileprocessor.ConcurrentFileProcessor.outputFilename;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * component factory class for main window UI elements
 * creates and configures all labels, buttons, and input fields for the main window
 */
public class MainWindowComponents {
    // UI component fields for main window
    public Label windowPurposeLabel;
    public Label descriptionLabel;
    public Label inputLabel;
    public Label outputLabel;
    public Label outputFilenameLabel;
    public Label outputDirectoryLabel;
    public Button quitButton;
    public Button restartButton;
    public Button inputFileButton;
    public Button outputDirectoryButton;
    public Button runButton;
    public Button filenameButton;
    public TextField filenameField;

    /**
     * constructor for main window components
     */
    public MainWindowComponents(Label windowPurposeLabel, Label descriptionLabel, Label inputLabel, Label outputLabel, Label outputFilenameLabel, Label outputDirectoryLabel, Button quitButton, Button restartButton, Button inputFileButton, Button outputDirectoryButton, Button runButton, Button filenameButton, TextField filenameField) {
        this.windowPurposeLabel = windowPurposeLabel;
        this.descriptionLabel = descriptionLabel;
        this.inputLabel = inputLabel;
        this.outputLabel = outputLabel;
        this.outputFilenameLabel = outputFilenameLabel;
        this.outputDirectoryLabel = outputDirectoryLabel;
        this.quitButton = quitButton;
        this.restartButton = restartButton;
        this.inputFileButton = inputFileButton;
        this.filenameButton = filenameButton;
        this.outputDirectoryButton = outputDirectoryButton;
        this.runButton = runButton;
        this.filenameField = filenameField;
    }

    /**
     * creates all label components for the main window
     * @return MainWindowComponents object with all labels configured
     */
    public static MainWindowComponents createMainWindowLabels(){
        // create window purpose label
        Label windowPurposeLabel = new Label("File Processing Setup");
        windowPurposeLabel.setFont(Font.font("System", 24));
        windowPurposeLabel.setStyle("-fx-text-fill: #1e40af; -fx-font-weight: bold;");

        // create description label
        Label descriptionLabel = new Label("Select your input files (.txt files only) and configure output settings, then click the Process Files button to begin concurrent analysis");
        descriptionLabel.setFont(Font.font("System", 18));
        descriptionLabel.setAlignment(Pos.CENTER);
        descriptionLabel.setStyle("-fx-text-fill: #6b7280; -fx-text-alignment: center;");
        descriptionLabel.setWrapText(true);

        // create input label
        Label inputLabel = new Label("Click button below to select input files");
        if (inputFiles != null && !inputFiles.isEmpty()) {
            inputLabel.setText(buildFilenamesList(inputFiles));
        }
        inputLabel.setWrapText(true);
        inputLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        // create output configuration label
        Label outputLabel = new Label("Modify filename and choose output directory (default: Downloads)");
        outputLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        outputLabel.setWrapText(true);
        outputLabel.setAlignment(Pos.CENTER);
        outputLabel.setStyle(outputLabel.getStyle() + "; -fx-text-alignment: center;");
        outputLabel.setPadding(new javafx.geometry.Insets(0, 0, 15, 0));

        // create output filename display label
        Label outputFilenameLabel = new Label("Output Filename: " + outputFilename);
        outputFilenameLabel.setFont(Font.font("System", 16));
        outputFilenameLabel.setStyle("-fx-text-fill: #374151;");

        // create output directory display label
        Label outputDirectoryLabel = new Label("Output directory: " + outputFilePath);
        outputDirectoryLabel.setFont(Font.font("System", 16));
        outputDirectoryLabel.setStyle("-fx-text-fill: #374151;");

        return new MainWindowComponents(windowPurposeLabel, descriptionLabel, inputLabel, outputLabel, outputFilenameLabel, outputDirectoryLabel, null, null, null, null, null, null, null);
    }

    /**
     * creates all field components for the main window
     */
    public static MainWindowComponents createMainWindowFields(MainWindowComponents components){
        TextField filenameField = new TextField();
        filenameField.setFont(Font.font("System", 16));
        filenameField.setText(outputFilename);
        filenameField.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #6b7280;" +
            "-fx-border-width: 2;" +
            "-fx-background-radius: 4;" +
            "-fx-padding: 8px;"
        );
        
        Platform.runLater(() -> filenameField.requestFocus());
        
        components.filenameField = filenameField;
        return components;
    }

    /**
     * creates all button components for the main window
     */
    public static MainWindowComponents createMainWindowButtons(MainWindowComponents components, Stage primaryStage){
        Button quitButton = ButtonCreator.createQuitButton();

        // create input file button
        Button inputFileButton = ButtonCreator.createButton("Select Input Files", (event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Input Files");
            List<File> files = fileChooser.showOpenMultipleDialog(primaryStage);
            if (files != null) {
                components.inputLabel.setText(buildFilenamesList(files));
                components.inputLabel.setWrapText(true);
                components.inputLabel.setFont(Font.font("System", 16));
                components.inputLabel.setStyle("-fx-text-fill: #374151;");
                inputFiles = files;
            }
        });
        inputFileButton.setFont(Font.font("System", 18));
        inputFileButton.setPrefSize(200, 45);
        inputFileButton.setStyle(
            "-fx-background-color: #3b82f6;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;"
        );

        // create filename button
        Button filenameButton = ButtonCreator.createButton("Set Filename", (event) -> {
            outputFilename = components.filenameField.getText();
            components.outputFilenameLabel.setText("Output filename: " + outputFilename);
        });
        filenameButton.setFont(Font.font("System", 14));
        filenameButton.setPrefSize(120, 45);
        filenameButton.setStyle(
            "-fx-background-color: #3b82f6;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;"
        );
        
        // create output directory button
        Button outputDirectoryButton = ButtonCreator.createButton("Select Output Directory", (event) -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Output Directory");
            File directory = directoryChooser.showDialog(primaryStage);
            if (directory != null) {
                outputFilePath = directory.getAbsolutePath();
                outputFilename = components.filenameField.getText();
                components.outputDirectoryLabel.setText("Output file path: " + outputFilePath);
                components.outputFilenameLabel.setText("Output filename: " + outputFilename);
            }
        });
        outputDirectoryButton.setFont(Font.font("System", 18));
        outputDirectoryButton.setPrefSize(250, 45);
        outputDirectoryButton.setStyle(
            "-fx-background-color: #3b82f6;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;"
        );

        // create run button
        Button runButton = ButtonCreator.createButton("Process Files", (event) -> {});
        runButton.setFont(Font.font("System", 22));
        runButton.setPrefSize(250, 55);
        runButton.setMinSize(250, 55);
        runButton.setMaxSize(250, 55);
        runButton.setStyle(
            "-fx-background-color: #059669;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 10;"
        );

        Button restartButton = ButtonCreator.createRestartButton();

        // assign all buttons to components
        components.quitButton = quitButton;
        components.restartButton = restartButton;
        components.inputFileButton = inputFileButton;
        components.filenameButton = filenameButton;
        components.outputDirectoryButton = outputDirectoryButton;
        components.runButton = runButton;

        return components;
    }
    
    /**
     * builds a formatted list of filenames for display
     */
    private static String buildFilenamesList(List<File> files) {
        StringBuilder builder = new StringBuilder();
        for (File file : files) {
            builder.append("- ").append(file.getName()).append("\n");
        }
        return builder.toString();
    }
}
