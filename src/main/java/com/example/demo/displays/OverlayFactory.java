package com.example.demo.displays;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * A factory class responsible for creating overlay screens with dynamic content, including
 * titles, text, buttons, and optional images. This class ensures a single instance is used
 * for overlay creation via the Singleton pattern.
 */
public class OverlayFactory {

    /** Singleton instance of the OverlayFactory. */
    private static OverlayFactory instance;

    /**
     * Private constructor to prevent direct instantiation of the class.
     * <p>
     * This constructor ensures that only one instance of the `OverlayFactory` is created. It follows
     * the Singleton pattern to manage overlay creation in a centralized and controlled manner.
     * </p>
     */
    private OverlayFactory() {
    }

    /**
     * Returns the singleton instance of the OverlayFactory.
     * <p>
     * This method ensures that only one instance of the `OverlayFactory` exists. If the instance does not
     * exist, it will be created. This is done to follow the Singleton pattern.
     * </p>
     *
     * @return The single instance of the OverlayFactory.
     */
    public static OverlayFactory getInstance() {
        if (instance == null) {
            instance = new OverlayFactory();
        }
        return instance;
    }

    /**
     * Creates an overlay with dynamic buttons based on the provided configurations.
     * The overlay includes a title, text content, buttons, and an optional score image.
     * <p>
     * This method allows for flexible and dynamic creation of overlays, providing customization
     * for the content (title, text) and actions linked to the buttons. It also supports an optional
     * image for the score.
     * </p>
     *
     * @param root           The root node to which the overlay will be added. This is the parent container
     *                       in the scene graph that will hold all the overlay components.
     * @param title          The title of the overlay. This is typically displayed at the top of the overlay.
     * @param text           The text content to display in the overlay. This provides additional information
     *                       to the user, such as instructions, results, or game-related messages.
     * @param buttonConfigs  An array of button configurations, where each element is a pair of image path
     *                       (String) and corresponding action (Runnable). Each configuration represents a
     *                       button with its image and associated action when clicked.
     * @param starImagePath  Optional image path for the score image. This is used to display a star or
     *                       other relevant score-related image in the overlay if provided.
     * @return A configured `BaseOverlay` object with all elements added, ready to be displayed in the game.
     * @throws IllegalArgumentException If any button configuration does not have exactly two elements (image path and action).
     */
    public BaseOverlay createOverlay(Group root, String title, String text, Object[][] buttonConfigs, String starImagePath) {
        // Create the overlay and pass the optional starImagePath
        BaseOverlay overlay = new BaseOverlay(root);

        // Set the title for the overlay
        overlay.setTitle(title);

        // Add the text content to the overlay
        Label textLabel = new Label(text);
        textLabel.setTextFill(Color.SILVER);
        textLabel.setFont(Font.font("Arial", 24));  // Set the font style and size for the text
        overlay.addContent(textLabel);  // Add the label containing the text to the overlay

        // Add buttons dynamically based on the buttonConfigs provided
        for (Object[] config : buttonConfigs) {
            if (config.length != 2) {
                throw new IllegalArgumentException("Each button configuration must have an image path and an action.");
            }

            // Retrieve the image path and the corresponding action from the configuration
            Object imagePath = config[0];
            Runnable action = (Runnable) config[1];  // Cast the second element as a Runnable action

            // Create a button using the provided image path and action
            Button button = ButtonFactory.createImageButton(String.valueOf(imagePath), 100, 100);

            // Define the button's behavior when clicked
            button.setOnAction(event -> {
                overlay.hide();  // Hide the overlay when the button is clicked
                action.run();    // Execute the action associated with the button
            });

            // Add the button to the overlay
            overlay.addButton(button);
        }

        // If a star image path is provided, add the score image to the overlay
        overlay.addScoreImage(starImagePath);

        return overlay;  // Return the fully constructed overlay
    }
}
