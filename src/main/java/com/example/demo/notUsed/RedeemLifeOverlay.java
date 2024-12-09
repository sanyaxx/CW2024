package com.example.demo.notUsed;

import com.example.demo.activityManagers.ButtonFactory;
import com.example.demo.controller.AppStage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.function.Consumer;

public class RedeemLifeOverlay {

    private static RedeemLifeOverlay instance; // Singleton instance
    private final StackPane overlay;
    private final Scene scene;
    private final Stage stage;
    private final Text countdownText;
    private boolean userClicked = false;
    private final Timeline countdownTimeline;

    // Button image path
    private static final String HEART_BUTTON_IMAGE = "/com/example/demo/images/heart.png";

    // Private constructor to enforce singleton
    private RedeemLifeOverlay(Scene scene) {
        this.scene = scene;
        this.stage = AppStage.getInstance().getPrimaryStage();

        // Create a full-screen semi-transparent background
        Rectangle background = new Rectangle();
        background.widthProperty().bind(stage.widthProperty());
        background.heightProperty().bind(stage.heightProperty());
        background.setFill(new Color(0, 0, 0, 0.7)); // Black with 70% opacity

        // Create a countdown text
        countdownText = new Text("5");
        countdownText.setStyle("-fx-font-size: 48; -fx-fill: white; -fx-font-weight: bold;");

        // Create a heart button dynamically using ButtonFactory
        Button redeemButton = ButtonFactory.createImageButton(HEART_BUTTON_IMAGE, 100, 100);
        redeemButton.setOnAction(event -> handleRedeemButtonClick());

        // Center the countdown text and button vertically
        VBox contentBox = new VBox(20, countdownText, redeemButton);
        contentBox.setAlignment(Pos.CENTER);

        overlay = new StackPane();
        overlay.getChildren().addAll(background, contentBox);
        overlay.setVisible(false); // Initially hidden

        // Add the overlay to the existing scene
        if (scene.getRoot() instanceof StackPane root) {
            root.getChildren().add(overlay);
        } else {
            throw new IllegalStateException("Scene root must be a StackPane");
        }

        // Initialize the countdown
        countdownTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    int currentTime = Integer.parseInt(countdownText.getText());
                    if (currentTime > 1) {
                        countdownText.setText(String.valueOf(currentTime - 1));
                    } else {
                        closeOverlay();
                    }
                })
        );
        countdownTimeline.setCycleCount(5); // Countdown from 5
    }

    /**
     * Get the singleton instance of RedeemLifeOverlay.
     *
     * @param scene The scene in which the overlay is used.
     * @return The singleton instance.
     */
    public static RedeemLifeOverlay getInstance(Scene scene) {
        if (instance == null) {
            instance = new RedeemLifeOverlay(scene);
        }
        return instance;
    }

    /**
     * Show the overlay and handle the result asynchronously using a callback.
     *
     * @param onComplete Callback to handle whether the user clicked the button or not.
     */
    public void showOverlay(Consumer<Boolean> onComplete) {
        userClicked = false;
        overlay.setVisible(true);
        overlay.toFront();

        // Blur the root node
        GaussianBlur blur = new GaussianBlur(50);
        scene.getRoot().setEffect(blur);

        // Reset countdown and start it
        countdownText.setText("5");
        countdownTimeline.play();

        // Handle overlay close after countdown ends
        countdownTimeline.setOnFinished(event -> {
            if (!userClicked) {
                closeOverlay();
            }
            onComplete.accept(userClicked);
        });
    }

    private void closeOverlay() {
        overlay.setVisible(false);
        scene.getRoot().setEffect(null); // Remove blur effect from root
        countdownTimeline.stop();
    }

    private void handleRedeemButtonClick() {
        userClicked = true;
        closeOverlay();
    }
}
