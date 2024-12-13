package com.example.demo.displays;

import com.example.demo.controller.AppStage;
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

/**
 * Represents a base overlay component that can be displayed on top of the main game scene.
 * <p>
 * The overlay includes a customizable title, content section, buttons, and an optional score image area.
 * It provides methods for adding content, buttons, and score-related images, as well as showing or hiding the overlay.
 * </p>
 */
public class BaseOverlay {

    /**
     * The root container of the overlay, containing all UI components.
     * <p>
     * The {@link StackPane} allows layering of the background and the main layout.
     * </p>
     */
    protected final StackPane overlay;

    /**
     * The primary {@link Stage} of the application, retrieved using {@link AppStage}.
     * <p>
     * This is used for accessing application-wide stage properties, such as dimensions.
     * </p>
     */
    protected final Stage stage;

    /**
     * The root {@link Group} of the scene to which the overlay is added.
     * <p>
     * This allows the overlay to be displayed on top of the main game content.
     * </p>
     */
    private final Group root;

    /**
     * A {@link Label} used for displaying the overlay's title.
     * <p>
     * The title is styled with a bold font and is center-aligned by default.
     * </p>
     */
    protected final Label titleLabel;

    /**
     * A {@link VBox} container for adding content to the overlay.
     * <p>
     * This section can hold various UI elements such as labels, images, or other nodes.
     * </p>
     */
    protected final VBox contentBox;

    /**
     * An {@link HBox} container for managing buttons in the overlay.
     * <p>
     * Buttons are displayed horizontally with spacing between them.
     * </p>
     */
    protected final HBox buttonContainer;

    /**
     * A semi-transparent {@link Rectangle} that acts as the overlay's background.
     * <p>
     * Covers the entire stage to dim the background content.
     * </p>
     */
    private final Rectangle background;

    /**
     * A {@link VBox} container for displaying score-related images in the overlay.
     * <p>
     * This section is optional and can be used to add visual score elements such as stars.
     * </p>
     */
    private final VBox scoreImageContainer;

    /**
     * Constructs a {@code BaseOverlay} instance, initializing all visual components and layout.
     *
     * @param root The root {@link Group} of the scene, where the overlay will be added.
     */
    public BaseOverlay(Group root) {
        this.root = root;
        this.stage = AppStage.getInstance().getPrimaryStage();

        // Create a full-screen semi-transparent background
        background = new Rectangle(stage.getWidth(), stage.getHeight());
        background.setFill(new Color(0, 0, 0, 0.85)); // Black with 85% opacity

        // Title label
        titleLabel = new Label();
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48)); // Bold font for title
        titleLabel.setTextFill(Color.WHITESMOKE); // White title text
        titleLabel.setAlignment(Pos.CENTER); // Center-align the title

        // Content container
        contentBox = new VBox(20); // Vertical box with 20px spacing
        contentBox.setAlignment(Pos.CENTER); // Center-align content

        // Score image container
        scoreImageContainer = new VBox();
        scoreImageContainer.setAlignment(Pos.CENTER); // Center-align score images

        // Button container
        buttonContainer = new HBox(20); // Horizontal box with 20px spacing
        buttonContainer.setAlignment(Pos.CENTER); // Center-align buttons

        // Main layout container
        VBox layoutContainer = new VBox(20); // Vertical layout with spacing
        layoutContainer.setAlignment(Pos.CENTER); // Align to center vertically and horizontally
        layoutContainer.setStyle("-fx-padding: 30;"); // Padding around content
        layoutContainer.getChildren().addAll(titleLabel, contentBox, scoreImageContainer, buttonContainer);

        // Overlay container
        overlay = new StackPane();
        overlay.getChildren().addAll(background, layoutContainer);
        overlay.setVisible(false); // Initially hidden
    }

    /**
     * Sets the title of the overlay.
     *
     * @param title The text to display as the title of the overlay.
     */
    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    /**
     * Adds one or more UI nodes to the content section of the overlay.
     *
     * @param nodes One or more nodes to add to the overlay's content section.
     */
    public void addContent(javafx.scene.Node... nodes) {
        contentBox.getChildren().addAll(nodes);
    }

    /**
     * Adds a button to the button container in the overlay.
     *
     * @param button The {@link Button} to add to the overlay.
     */
    public void addButton(Button button) {
        buttonContainer.getChildren().add(button);
    }

    /**
     * Adds a score-related image (e.g., a star) to the score image container.
     *
     * @param starImagePath The file path to the image to display.
     *                      If {@code null} or empty, the score image container is cleared.
     */
    public void addScoreImage(String starImagePath) {
        if (starImagePath != null && !starImagePath.isEmpty()) {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(starImagePath)));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(600); // Set fixed width
            imageView.setPreserveRatio(true); // Maintain aspect ratio
            scoreImageContainer.getChildren().add(imageView);
        } else {
            scoreImageContainer.getChildren().clear();
        }
    }

    /**
     * Displays the overlay by adding it to the root container and making it visible.
     */
    public void show() {
        root.getChildren().add(overlay);
        overlay.setVisible(true);
        overlay.toFront();
    }

    /**
     * Hides the overlay by removing it from the root container and making it invisible.
     */
    public void hide() {
        root.getChildren().remove(overlay);
        overlay.setVisible(false);
    }
}
