package com.example.demo;

import javafx.animation.ScaleTransition;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class KillCountDisplay {
    private static final double DISPLAY_X_POSITION = 1150;  // Position from the left
    private static final double DISPLAY_Y_POSITION = 25;   // Position from the top
    private int killCount;
    private final Text killCountText;
    private final StackPane container;

    public KillCountDisplay() {
        this.killCount = 0;

        // Create styled text
        this.killCountText = new Text("Kills: " + killCount);
        this.killCountText.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 36)); // Larger and bolder font
        this.killCountText.setFill(Color.GOLD);  // Set a bright and attractive color
        this.killCountText.setStroke(Color.BLACK); // Add a black outline to make text stand out
        this.killCountText.setStrokeWidth(2);

        // Initialize the container
        this.container = new StackPane();
        this.container.getChildren().add(killCountText);
        this.container.setTranslateX(DISPLAY_X_POSITION);
        this.container.setTranslateY(DISPLAY_Y_POSITION);
    }

    // Method to increment the kill count and update the display
    public void incrementKillCount() {
        killCount++;
        killCountText.setText("Kills: " + killCount);

        // Create a scale animation for the text
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), killCountText);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(1.5); // Increase scale for a brief zoom effect
        scaleTransition.setToY(1.5);
        scaleTransition.setCycleCount(2); // Scale up and back down
        scaleTransition.setAutoReverse(true);

        // Play the animation
        scaleTransition.play();
    }

    // Getter for the display container
    public StackPane getContainer() {
        return container;
    }

    // Getter for the kill count value (in case you need it elsewhere)
    public int getKillCount() {
        return killCount;
    }
}
