package com.example.demo.displays;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * A factory class for creating image-based buttons.
 * Provides a static method to create buttons that display an image with
 * specified dimensions, making it easier to generate buttons with images
 * across the application.
 * <p>
 * This class simplifies the process of creating buttons with image graphics,
 * ensuring that the image is scaled properly and the button has a transparent
 * background. Since this is a utility class, it cannot be instantiated.
 * </p>
 */
public class ButtonFactory {

    /**
     * Private constructor to prevent instantiation of the utility class.
     * The ButtonFactory class should only be used through its static methods.
     */
    private ButtonFactory() {
        // Prevent instantiation
    }

    /**
     * Creates a button with an image as its graphic. The button's background
     * is set to transparent, and the image is scaled to the specified width
     * and height while preserving its aspect ratio.
     * <p>
     * This method allows the creation of buttons that use images, such as icons
     * or other visual elements, without needing to manually set up the image view
     * and button styling.
     * </p>
     *
     * @param imagePath The path to the image to display on the button. This should be the
     *                  path relative to the resource directory (e.g., "images/buttonIcon.png").
     *                  The method will use this path to load the image from resources.
     * @param width     The width to which the image should be scaled. This is the desired width
     *                  of the image, and the method will scale the image to match this width.
     * @param height    The height to which the image should be scaled. This is the desired height
     *                  of the image, and the method will scale the image to match this height.
     * @return A {@link Button} object with the specified image as its graphic. The button will
     *         have a transparent background and the image will be resized as specified.
     */
    public static Button createImageButton(String imagePath, double width, double height) {
        // Create a new Button object
        Button button = new Button();

        // Load the image from the given resource path
        Image image = new Image(Objects.requireNonNull(ButtonFactory.class.getResourceAsStream(imagePath)));

        // Create an ImageView to display the image on the button
        ImageView imageView = new ImageView(image);

        // Scale the image to fit the specified width and height
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        // Preserve the aspect ratio of the image
        imageView.setPreserveRatio(true);

        // Set the ImageView as the graphic of the button
        button.setGraphic(imageView);

        // Set the button's background to be transparent
        button.setStyle("-fx-background-color: transparent;");

        // Return the button with the image as its graphic
        return button;
    }
}
