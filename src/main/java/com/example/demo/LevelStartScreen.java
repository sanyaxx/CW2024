package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LevelStartScreen {
    private final Stage stage; // Reference to the stage
    private final int levelNumber; // Level number to display
    private final LevelManager levelManager; // Reference to LevelManager
    private String levelAim; // Variable to hold the aim for the level

    public LevelStartScreen(int levelNumber) {
        this.stage = AppStage.getInstance().getPrimaryStage(); // Initialize the stage reference
        this.levelNumber = levelNumber; // Set the level number
        this.levelManager = LevelManager.getInstance(); // Get the LevelManager instance
        setLevelAim(); // Set the aim based on the level number
    }

    private void setLevelAim() {
        // Set the aim based on the level number using a switch-case statement
        switch (levelNumber) {
            case 1:
                levelAim = "Kill 10 enemy planes while dodging enemy missiles and the planes.";
                break;
            case 2:
                levelAim = "Defeat the boss!";
                break;
            case 3:
                levelAim = "Reach the finish line!";
                break;
            default:
                levelAim = "Unknown level aim!";
                break;
        }
    }

    public void show() {
        // Create a label to display the level information
        Label levelInfoLabel = new Label("Level " + levelNumber + "\nAim: " + levelAim + "\nPress SPACE to begin");
        levelInfoLabel.setStyle("-fx-font-size: 24; -fx-text-fill: white;"); // Style the label

        // Create a layout for the label
        StackPane layout = new StackPane();
        layout.getChildren().add(levelInfoLabel); // Add the label to the layout

        // Create a scene with the layout
        Scene scene = new Scene(layout, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Level Start Screen");
        stage.show();

        // Set the action for the space bar key press
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                String nextLevelName = levelManager.getNextLevelName();
                if (nextLevelName != null) {
                    levelManager.goToNextLevel(nextLevelName); // Call the method to go to the next level
                } else {
                    System.out.println("No more levels available.");
                }
            }
        });
    }
}