package com.concurrentfileprocessor.gui.window.components;

import com.concurrentfileprocessor.gui.window.Controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * factory class for creating event handlers
 * provides consistent event handling across the application
 */
public class EventCreator {
    
    /**
     * adds restart event to a button
     * @param button button to add restart event to
     * @param controller controller for window navigation
     * @param stage stage for window management
     * @return button with restart event added
     */
    public static Button addRestartEvent(Button button, Controller controller, Stage stage) {
        button.setOnAction(event -> {
            controller.showStartWindow(stage);
        });
        return button;
    }
}
