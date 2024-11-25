package com.example.demo;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StartPageButton {
    private final Button button;

    // Predefined image path for the main menu button
    private static final String IMAGE_PATH = "/com/example/demo/images/menuButton.png";

    public StartPageButton() {
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

    public Button getStartPageButton() {
        return button;
    }

    // Method to set the action for the button
    public void setOnStartPageAction(Runnable action) {
        button.setOnAction(event -> action.run());
    }
}