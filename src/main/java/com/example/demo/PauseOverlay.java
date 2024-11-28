package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;

public class PauseOverlay {
    private final StackPane overlay; // Use StackPane to layer the background and buttons
    private final Scene scene;
    private final ResumeButton resumeButton;
    private final StartPageButton startPageButton;
    private final VolumeButton volumeButton;
    private final Stage stage;
    private StartPage startPage;

    public PauseOverlay(Scene scene) {
        this.scene = scene;
        this.stage = AppStage.getInstance().getPrimaryStage();
        this.resumeButton = new ResumeButton();
        this.startPageButton = new StartPageButton();
        this.volumeButton = new VolumeButton();
        this.startPage = new StartPage(stage);

        // Create a full-screen semi-transparent background
        Rectangle background = new Rectangle(stage.getWidth(), stage.getHeight());
        background.setFill(new Color(0, 0, 0, 0.7)); // Black with 70% opacity for a darker effect

        // Set actions for the buttons
        resumeButton.setOnResumeAction(this::hideOverlay);
        startPageButton.setOnStartPageAction(this::goToStartPage);
        volumeButton.setOnVolumeAction(this::hideOverlay);

        // Create an HBox to hold the buttons in a line
        HBox buttonContainer = new HBox(20, resumeButton.getResumeButton(), startPageButton.getStartPageButton(), volumeButton.getVolumeButton());
        buttonContainer.setAlignment(Pos.CENTER); // Center the buttons horizontally
        buttonContainer.setStyle("-fx-padding: 20;"); // Add some padding

        // Create a VBox to center the button container vertically
        VBox vbox = new VBox(buttonContainer);
        vbox.setAlignment(Pos.CENTER); // Center the buttons vertically
        vbox.setStyle("-fx-padding: 50;"); // Add some padding around the buttons

        // Create the overlay
        overlay = new StackPane();
        overlay.getChildren().addAll(background, vbox);
        overlay.setVisible(false); // Initially hidden

        // Add the overlay to the existing scene
        StackPane root = (StackPane) scene.getRoot();
        root.getChildren().add(overlay);
    }


    public void showOverlay() {
        overlay.setVisible(true);
        overlay.toFront();
        GaussianBlur blur = new GaussianBlur(50); // Increase blur for a more pronounced effect
        scene.getRoot().setEffect(blur);
        System.out.println("Overlay is now visible."); // Debug statement
    }

    public void hideOverlay() {
        overlay.setVisible(false);
        scene.getRoot().setEffect(null);
    }

    public StackPane getOverlay() {
        return overlay; // Return the overlay
    }

    public ResumeButton getResumeButton() {
        return resumeButton; // Return the instance of ResumeButton
    }

    private void goToStartPage() {
        // Logic to go to the main menu
        System.out.println("Going to the main menu..."); // Debug statement
        hideOverlay();
        startPage.show();
    }

    private void adjustVolume() {
        // Logic to adjust volume
        System.out.println("Adjusting volume..."); // Debug statement
    }
}