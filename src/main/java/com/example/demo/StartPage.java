package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.function.Consumer;

public class StartPage {
    private static final String BACKGROUND_IMAGE = "/com/example/demo/images/startPageBackground.jpg";
    private Consumer<Void> onPlayButtonClicked; // Callback for play button click
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

        // Create buttons
        StartButton startButton = new StartButton();
        startButton.setOnAction(e -> startButton.handleStartButtonClick());

        QuitButton quitButton = new QuitButton();
        quitButton.setOnAction(e -> System.exit(0));

        // Layout the title and buttons in a VBox
        VBox layout = new VBox(20);
        layout.getChildren().addAll(titleImageView, startButton.getButton(), quitButton.getButton());
        layout.setTranslateY(50);
        layout.setTranslateX((stage.getWidth() - 400) / 2);

        // Create a root pane and add the background and layout
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, layout);

        // Set the scene
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Game Start Page");
        stage.show();
    }
}