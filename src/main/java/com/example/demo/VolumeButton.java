package com.example.demo;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VolumeButton {
    private final Button button;

    // Predefined image path for the volume button
    private static final String IMAGE_PATH = "/com/example/demo/images/volumeButton.png";

    public VolumeButton() {
        button = createButton();
    }

    private Button createButton() {
        Button button = new Button();
        Image image = new Image(getClass().getResourceAsStream(IMAGE_PATH));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100); // Set desired button size
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true); // Preserve aspect ratio

        button.setGraphic(imageView);
        button.setStyle("-fx-background-color: transparent;"); // Make the button background transparent

        return button;
    }

    public Button getVolumeButton() {
        return button;
    }

    // Method to set the action for the button
    public void setOnVolumeAction(Runnable action) {
        button.setOnAction(event -> action.run());
    }
}