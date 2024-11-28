package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        // Create a label for the title
        Label titleLabel = new Label("SKY BATTLE");
        titleLabel.setFont(Font.font("Arial", 48)); // Set title font size
        titleLabel.setTextFill(Color.WHITE); // Set title color

        // Create a label for the level aim
        Label aimLabel = new Label("Aim: " + levelAim);
        aimLabel.setFont(Font.font("Arial", 24)); // Set aim font size
        aimLabel.setTextFill(Color.WHITE); // Set aim color

        // Create a label for the blinking "Press SPACE to begin"
        Label startLabel = new Label("Press SPACE to begin");
        startLabel.setFont(Font.font("Arial", 24)); // Set font size
        startLabel.setTextFill(Color.YELLOW); // Set text color for visibility

        // Create a VBox layout to stack the labels vertically
        VBox layout = new VBox(20); // 20 pixels of spacing between elements
        layout.getChildren().addAll(titleLabel, aimLabel, startLabel); // Add labels to the layout
        layout.setStyle("-fx-background-color: #2C3E50;"); // Dark blue background
        layout.setPrefSize(stage.getWidth(), stage.getHeight()); // Set preferred size

        // Center the labels in the VBox
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        // Create a scene with the layout
        Scene scene = new Scene(layout, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Level Start Screen");
        stage.show();

        // Create a blinking animation for the "Press SPACE to begin" label
        Timeline blinkingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> startLabel.setVisible(true)),
                new KeyFrame(Duration.seconds(0.5), event -> startLabel.setVisible(false))
        );
        blinkingTimeline.setCycleCount(Timeline.INDEFINITE);
        blinkingTimeline.play();

        // Set the action for the space bar key press
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                blinkingTimeline.stop(); // Stop blinking when the game starts
                String levelName = levelManager.getNextLevelName();
                if (levelName != null) {
                    levelManager.goToNextLevel(levelName); // Call the method to go to the next level
                } else {
                    System.out.println("No more levels available.");
                }
            }
        });
    }
}