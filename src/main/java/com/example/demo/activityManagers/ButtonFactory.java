package com.example.demo.activityManagers;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ButtonFactory {
    public static Button createImageButton(String imagePath, double width, double height) {
        Button button = new Button();
        Image image = new Image(Objects.requireNonNull(ButtonFactory.class.getResourceAsStream(imagePath)));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);

        button.setGraphic(imageView);
        button.setStyle("-fx-background-color: transparent;");

        return button;
    }
}