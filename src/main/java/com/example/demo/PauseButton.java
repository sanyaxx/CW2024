package com.example.demo;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PauseButton {
    private final Button button;

    // Predefined image path for the pause button
    private static final String IMAGE_PATH = "/com/example/demo/images/pauseButton.png";

    public PauseButton() {
        button = createButton();
        button.setLayoutX(1180);
        button.setLayoutY(15);
    }

    private Button createButton() {
        Button button = new Button();
        Image image = new Image(getClass().getResourceAsStream(IMAGE_PATH));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(75); // Set desired button size
        imageView.setFitHeight(75);
        imageView.setPreserveRatio(true); // Preserve aspect ratio

        button.setGraphic(imageView);
        button.setStyle("-fx-background-color: transparent;"); // Make the button background transparent

        return button;
    }

    public Button getPauseButton() {
        return button; // Expose the button for visibility control
    }

    // Method to set the action for the button
    public void setOnPauseAction(Runnable action) {
        button.setOnAction(event -> action.run());
    }
}