package com.example.demo;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class StartPage {
    private static final String BACKGROUND_IMAGE = "/com/example/demo/images/startPageBackground.png";
    private Stage stage; // Store the stage reference

    public StartPage(Stage stage) {
        this.stage = stage; // Initialize the stage reference
    }

    public void show() {
        // Set up the background image
        ImageView backgroundImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(BACKGROUND_IMAGE))));
        backgroundImageView.setFitWidth(stage.getWidth());
        backgroundImageView.setFitHeight(stage.getHeight());
        backgroundImageView.setPreserveRatio(false);

        // Create an ImageView for the game title
        ImageView titleImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo/images/gameTitle1.png"))));
        titleImageView.setFitWidth(400);
        titleImageView.setPreserveRatio(true);
        titleImageView.setLayoutX((stage.getWidth() - titleImageView.getFitWidth()) / 2); // Center the title
        titleImageView.setLayoutY(20); // Set Y position for title

        // Create buttons
        StartButton startButton = new StartButton();
        startButton.setOnAction(e -> startButton.handleStartButtonClick());
        startButton.getButton().setLayoutX(stage.getWidth()/2 - 10); // Set X position for start button
        startButton.getButton().setLayoutY(stage.getHeight() - 167); // Set Y position for start button

        QuitButton quitButton = new QuitButton();
        quitButton.setOnAction(e -> System.exit(0));
        quitButton.getButton().setLayoutX(stage.getWidth()/2 - 278); // Set X position for quit button
        quitButton.getButton().setLayoutY(stage.getHeight() - 167); // Set Y position for quit button

        // Create a root pane and add the background and layout
        Group root = new Group();
        root.getChildren().addAll(backgroundImageView, titleImageView, startButton.getButton(), quitButton.getButton());

        // Set the scene
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Game Start Page");

        stage.show();
    }
}