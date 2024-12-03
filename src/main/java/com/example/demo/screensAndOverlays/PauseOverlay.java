package com.example.demo.screensAndOverlays;

import com.example.demo.activityManagers.ButtonFactory;
import com.example.demo.gameConfig.AppStage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private final Stage stage;
    private StartScreen startScreen;
    Button resumeButton;

    // Button image paths
    private static final String RESUME_BUTTON_IMAGE = "/com/example/demo/images/resumeButton.png";
    private static final String START_PAGE_BUTTON_IMAGE = "/com/example/demo/images/menuButton.png";
    private static final String VOLUME_BUTTON_IMAGE = "/com/example/demo/images/volumeButton.png";

    public PauseOverlay(Scene scene) {
        this.scene = scene;
        this.stage = AppStage.getInstance().getPrimaryStage();
        this.startScreen = new StartScreen(stage);

        // Create a full-screen semi-transparent background
        Rectangle background = new Rectangle(stage.getWidth(), stage.getHeight());
        background.setFill(new Color(0, 0, 0, 0.7)); // Black with 70% opacity for a darker effect

        // Create buttons dynamically using ButtonFactory
        resumeButton = ButtonFactory.createImageButton(RESUME_BUTTON_IMAGE, 100, 100);
        Button startPageButton = ButtonFactory.createImageButton(START_PAGE_BUTTON_IMAGE, 100, 100);
        Button volumeButton = ButtonFactory.createImageButton(VOLUME_BUTTON_IMAGE, 100, 100);

        // Set actions for the buttons
        resumeButton.setOnAction(event -> hideOverlay());
        startPageButton.setOnAction(event -> goToStartPage());
        volumeButton.setOnAction(event -> adjustVolume());

        // Create an HBox to hold the buttons in a line
        HBox buttonContainer = new HBox(20, resumeButton, startPageButton, volumeButton);
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

    public Button getResumeButton() {
        return resumeButton; // Return the instance of ResumeButton
    }

    private void goToStartPage() {
        // Logic to go to the main menu
        System.out.println("Going to the main menu..."); // Debug statement
        hideOverlay();
        startScreen.show();
    }

    private void adjustVolume() {
        System.out.println("Adjusting volume..."); // Debug statement
        hideOverlay();
    }
}