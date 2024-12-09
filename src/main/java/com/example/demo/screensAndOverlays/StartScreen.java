package com.example.demo.screensAndOverlays;

import com.example.demo.activityManagers.ButtonFactory;
import com.example.demo.activityManagers.LevelManager;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class StartScreen {
    private static final String BACKGROUND_IMAGE = "/com/example/demo/images/startPageBackground.png";
    private Stage stage; // Store the stage reference
    private Button startButton;
    private Button quitButton;

    private static final String START_BUTTON_IMAGE = "/com/example/demo/images/startButton.png";
    private static final String QUIT_BUTTON_IMAGE = "/com/example/demo/images/quitButton.png";

    public StartScreen(Stage stage) {
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

        // Create buttons dynamically using ButtonFactory
        startButton = ButtonFactory.createImageButton(START_BUTTON_IMAGE, 350, 400);
        startButton.setLayoutX(stage.getWidth()/2 - 30); // Set X position for start button
        startButton.setLayoutY(stage.getHeight() - 200); // Set Y position for start button

        quitButton = ButtonFactory.createImageButton(QUIT_BUTTON_IMAGE, 350, 400);
        quitButton.setLayoutX(stage.getWidth()/2 - 360); // Set X position for quit button
        quitButton.setLayoutY(stage.getHeight() - 200); // Set Y position for quit button

        // Set actions for the buttons
        startButton.setOnAction(event -> startGame());
        quitButton.setOnAction(event -> quitGame());

        // Create a root pane and add the background and layout
        Group root = new Group();
        root.getChildren().addAll(backgroundImageView, titleImageView, startButton, quitButton);

        // Set the scene
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Game Start Page");

        stage.setMaximized(true);
        stage.show();
    }

    public void startGame() {
        LevelManager levelManager = LevelManager.getInstance();
//        levelManager.showLevelStartScreen(1);
        levelManager.showLevelStartScreen(levelManager.getCurrentLevelNumber());// Call goToNextLevel with the next level name
    }

    public void quitGame() {
        System.exit(0);
    }
}