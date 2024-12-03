package com.example.demo.screensAndOverlays;

import com.example.demo.activityManagers.LevelManager;
import com.example.demo.gameConfig.AppStage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
                levelAim = "Kill 10 enemy planes while dodging enemy missiles.";
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
        // Create a background for the scene
        Group backgroundPane = new Group();
        backgroundPane.setStyle("-fx-background-image: url('/com/example/demo/images/background1.jpg');" +
                "-fx-background-size: cover; -fx-background-position: center;");

        // Create a label for the title
        Label titleLabel = new Label("LEVEL " + levelNumber);
        titleLabel.setFont(Font.font("Arial", 72)); // Set title font size
        titleLabel.setTextFill(Color.YELLOW); // Set title color
        titleLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.5), 10, 0.0, 0, 0);");

        // Create a label for the level aim
        Label aimLabel = new Label("Aim: " + levelAim);
        aimLabel.setFont(Font.font("Arial", 36)); // Set aim font size
        aimLabel.setTextFill(Color.WHITE); // Set aim color
        aimLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0.0, 0, 0);");

        // Create a label for the blinking "Press SPACE to begin"
        Label startLabel = new Label("Press SPACE to begin");
        startLabel.setFont(Font.font("Arial", 24)); // Set font size
        startLabel.setTextFill(Color.GREEN); // Set text color for visibility

        // Create a blinking animation for the "Press SPACE to begin" label
        Timeline blinkingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> startLabel.setVisible(true)),
                new KeyFrame(Duration.seconds(0.5), event -> startLabel.setVisible(false))
        );
        blinkingTimeline.setCycleCount(Timeline.INDEFINITE);
        blinkingTimeline.play();

        // Create a VBox to stack the labels vertically
        VBox layout = new VBox(20); // Spacing of 20 pixels between elements
        layout.getChildren().addAll(titleLabel, aimLabel, startLabel);
        layout.setAlignment(Pos.CENTER); // Center align the VBox content
        layout.setStyle("-fx-background-color: rgba(44, 62, 80, 0.8);"); // Semi-transparent background

        // Create a scene with the layout
        Scene scene = new Scene(backgroundPane, stage.getWidth(), stage.getHeight());
        backgroundPane.getChildren().add(layout); // Add layout to the background pane
        stage.setScene(scene);
        stage.setTitle("Level Start Screen");
        stage.show();

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