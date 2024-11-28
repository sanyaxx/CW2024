package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class LevelLostOverlay {
    private final StackPane overlay; // Use StackPane to layer the background and buttons
    private final Scene scene;
    private final RestartButton restartButton;
    private final StartPageButton startPageButton;
    private final Label completionMessage;
    private final Stage stage;

    public LevelLostOverlay(Scene scene) {
        this.scene = scene;
        this.stage = AppStage.getInstance().getPrimaryStage();
        this.restartButton = new RestartButton();
        this.startPageButton = new StartPageButton();

        // Create a full-screen semi-transparent background
        Rectangle background = new Rectangle();
        background.setFill(new Color(0, 0, 0, 0.7)); // Black with 70% opacity for a darker effect

        // Create a message indicating level completion
        completionMessage = new Label("Level Lost!");
        completionMessage.setTextFill(Color.WHITE);
        completionMessage.setStyle("-fx-font-size: 36; -fx-font-weight: bold;"); // Style the message

        // Set actions for the buttons
        restartButton.setOnRestartPageAction(this::restartLevel);
        startPageButton.setOnStartPageAction(this::goToStartPage);

        // Create an HBox to hold the buttons in a line
        HBox buttonContainer = new HBox(20, restartButton.getRestartButton(), startPageButton.getStartPageButton());
        buttonContainer.setAlignment(Pos.CENTER); // Center the buttons horizontally
        buttonContainer.setStyle("-fx-padding: 20;"); // Add some padding

        // Create a VBox to center the message and button container vertically
        VBox vbox = new VBox(20, completionMessage, buttonContainer);
        vbox.setAlignment(Pos.CENTER); // Center the content vertically
        vbox.setStyle("-fx-padding: 50;"); // Add some padding around the buttons

        // Create the overlay
        overlay = new StackPane();
        overlay.getChildren().addAll(background, vbox);
        overlay.setVisible(false); // Initially hidden

        // Add the overlay to the existing scene
        StackPane root = (StackPane) scene.getRoot();
        root.getChildren().add(overlay);
    }

    public StackPane getOverlay() {
        return overlay; // Return the overlay
    }

    public void showOverlay() {
        overlay.setVisible(true);
        overlay.toFront();
        GaussianBlur blur = new GaussianBlur(50); // Increase blur for a more pronounced effect
        scene.getRoot().setEffect(blur);
        System.out.println("Level Lost overlay is now visible."); // Debug statement
    }

    public void hideOverlay() {
        overlay.setVisible(false);
        scene.getRoot().setEffect(null);
    }

    private void restartLevel() {
        // Logic to go to the next level
        System.out.println("Going to the next level..."); // Debug statement
        hideOverlay();
        LevelManager levelManager = LevelManager.getInstance();
        levelManager.showLevelStartScreen(levelManager.getCurrentLevelNumber());
    }

    private void goToStartPage() {
        // Logic to go to the main menu
        System.out.println("Going to the main menu..."); // Debug statement
        hideOverlay();
        StartPage startPage = new StartPage(AppStage.getInstance().getPrimaryStage());
        startPage.show();
    }
}
