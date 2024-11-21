package com.example.demo;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox; // Import VBox for vertical layout
import javafx.scene.layout.StackPane; // Import StackPane
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.Objects;
import java.util.function.Consumer;

public class StartPage {
    private static final String BACKGROUND_IMAGE = "/com/example/demo/images/startPageBackground.jpg";
    private Consumer<Void> onPlayButtonClicked; // Callback for play button click
    private final Stage stage; // Store the stage reference

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
        titleImageView.setFitWidth(400); // Set desired width for the title (increase size)
        titleImageView.setPreserveRatio(true); // Preserve aspect ratio

        // Create buttons
        Button playButton = createPlayButton();
        Button quitButton = createQuitButton();

        // Layout the title and buttons in a VBox with spacing
        VBox layout = new VBox(20); // 20 pixels of spacing
        layout.getChildren().addAll(titleImageView, playButton, quitButton); // Add title above buttons
        layout.setTranslateY(50); // Adjust vertical position of the entire layout
        layout.setTranslateX((stage.getWidth() - 400) / 2); // Center horizontally based on title width

        // Create a root pane and add the background and layout
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, layout); // Add layout to the root

        // Set the scene
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Game Start Page");
        stage.show();
    }

    private Button createPlayButton() {
        Button playButton = new Button(); // Create a button without text

        // Load the image
        Image playImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo/images/startGameButton.png")));
        ImageView playImageView = new ImageView(playImage);
        playImageView.setFitWidth(250); // Set desired width (increase size)
        playImageView.setFitHeight(120); // Set desired height (increase size)
        playImageView.setPreserveRatio(true); // Preserve aspect ratio

        // Set the image to the button
        playButton.setGraphic(playImageView);
        playButton.setStyle("-fx-background-color: transparent;"); // Make the button background transparent

        playButton.setOnAction(e -> {
            if (onPlayButtonClicked != null) {
                onPlayButtonClicked.accept(null); // Notify the controller
            }
        });

        return playButton;
    }

    private Button createQuitButton() {
        Button quitButton = new Button(); // Create a button without text

        // Load the image
        Image quitImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo/images/quitButton.png")));
        ImageView quitImageView = new ImageView(quitImage);
        quitImageView.setFitWidth(250); // Set desired width (increase size)
        quitImageView.setFitHeight(120); // Set desired height (increase size)
        quitImageView.setPreserveRatio(true); // Preserve aspect ratio

        // Set the image to the button
        quitButton.setGraphic(quitImageView);
        quitButton.setStyle("-fx-background-color: transparent;"); // Make the button background transparent

        quitButton.setOnAction(e -> {
            System.exit(0); // Close the application
        });

        return quitButton;
    }

    public void setOnPlayButtonClicked(Consumer<Void> onPlayButtonClicked) {
        this.onPlayButtonClicked = onPlayButtonClicked; // Set the callback
    }
}