package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class QuitButton {
    private final Button button;

    public QuitButton() {
        button = new Button();
        Image quitImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo/images/quitButton.png")));
        ImageView quitImageView = new ImageView(quitImage);
        quitImageView.setFitWidth(260);
        quitImageView.setFitHeight(225);
        quitImageView.setPreserveRatio(true);

        button.setGraphic(quitImageView);
        button.setStyle("-fx-background-color: transparent;"); // Make the button background transparent
    }

    public Button getButton() {
        return button; // Return the button instance
    }

    public void setOnAction(EventHandler<ActionEvent> eventHandler) {
        button.setOnAction(eventHandler); // Set the action for the button
    }
}