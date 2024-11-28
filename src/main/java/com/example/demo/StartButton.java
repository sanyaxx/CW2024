package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class StartButton {
    private final Button button;
    private LevelManager levelManager;

    public StartButton() {
        button = new Button();
        Image playImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo/images/startButton.png")));
        ImageView playImageView = new ImageView(playImage);
        playImageView.setFitWidth(250);
        playImageView.setFitHeight(120);
        playImageView.setPreserveRatio(true);
        levelManager = LevelManager.getInstance();

        button.setGraphic(playImageView);
        button.setStyle("-fx-background-color: transparent;"); // Make the button background transparent
    }

    public Button getButton() {
        return button; // Return the button instance
    }

    public void setOnAction(EventHandler<ActionEvent> eventHandler) {
        button.setOnAction(eventHandler); // Set the action for the button
    }

    // New method to handle the start button click
    public void handleStartButtonClick() {
        int levelNumber = levelManager.getCurrentLevelNumber();
        System.out.println("currentLevelNumber: " + levelNumber);
        if (levelNumber != -1) {
            levelManager.showLevelStartScreen(levelNumber); // Call goToNextLevel with the next level name
        } else {
            System.out.println("No more levels available.");
        }
    }
}