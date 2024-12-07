package com.example.demo.screensAndOverlays;

import com.example.demo.activityManagers.ButtonFactory;
import com.example.demo.gameConfig.AppStage;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Objects;

public class BaseOverlay {
    protected final StackPane overlay;
    protected final Stage stage;
    private final Group root;
    protected final Label titleLabel;
    protected final VBox contentBox;
    protected final HBox buttonContainer;
    private final Rectangle background;
    private final VBox scoreImageContainer;

    public BaseOverlay(Group root) {
        this.root = root;
        this.stage = AppStage.getInstance().getPrimaryStage();

        // Create a full-screen semi-transparent background
        Stage stage = AppStage.getInstance().getPrimaryStage();
        background = new Rectangle(stage.getWidth(), stage.getHeight());
        background.setFill(new Color(0, 0, 0, 0.8)); // Black with 80% opacity

        // Title label
        titleLabel = new Label();
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48)); // Bold font for title
        titleLabel.setTextFill(Color.WHITESMOKE); // White title text
        titleLabel.setAlignment(Pos.CENTER); // Center-align the title

        // Content container (for text, images, etc.)
        contentBox = new VBox(20); // Vertical box with spacing of 20
        contentBox.setAlignment(Pos.CENTER); // Center-align content

        // Optional score image container
        scoreImageContainer = new VBox();
        scoreImageContainer.setAlignment(Pos.CENTER); // Center-align score image

        // Button container
        buttonContainer = new HBox(20); // Horizontal box with spacing of 20
        buttonContainer.setAlignment(Pos.CENTER); // Center-align buttons

        // Main overlay container with spacing between elements
        VBox layoutContainer = new VBox(20); // Vertical box to hold all elements
        layoutContainer.setAlignment(Pos.TOP_CENTER); // Center-align everything
        layoutContainer.setStyle("-fx-padding: 30;"); // Add padding around the content
        layoutContainer.getChildren().addAll(titleLabel, contentBox, scoreImageContainer, buttonContainer);

        // Add all elements to the overlay
        overlay = new StackPane();
        overlay.getChildren().addAll(background, layoutContainer);
        overlay.setVisible(false);
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void addContent(javafx.scene.Node... nodes) {
        contentBox.getChildren().addAll(nodes);
    }

    public void addButton(Button button) {
        buttonContainer.getChildren().add(button);
    }

    /**
     * Adds a score image to the overlay, if provided.
     *
     * @param starImagePath The path to the image file.
     */
    public void addScoreImage(String starImagePath) {
        if (starImagePath != null && !starImagePath.isEmpty()) {
            Image image = new Image(Objects.requireNonNull(ButtonFactory.class.getResourceAsStream(starImagePath)));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(600); // Set fixed width for the image
            imageView.setPreserveRatio(true); // Preserve the aspect ratio
            scoreImageContainer.getChildren().add(imageView);
        } else {
            // Clear the container if no image is provided
            scoreImageContainer.getChildren().clear();
        }
    }

    public void show() {
        // Show the overlay on the root of the scene
        root.getChildren().add(overlay);
        overlay.setVisible(true);
        overlay.toFront();
    }

    public void hide() {
        // Remove the overlay from the root of the scene
        root.getChildren().remove(overlay);
        overlay.setVisible(false);
    }
}
