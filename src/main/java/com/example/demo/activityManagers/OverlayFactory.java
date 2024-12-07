package com.example.demo.activityManagers;

import com.example.demo.screensAndOverlays.BaseOverlay;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class OverlayFactory {
    private static OverlayFactory instance;
//    private final Scene scene = SceneManager.getInstance().getScene();

    private OverlayFactory() {
    }

    public static OverlayFactory getInstance() {
        if (instance == null) {
            instance = new OverlayFactory();
        }
        return instance;
    }

    /**
     * Creates an overlay with dynamic buttons based on the provided configurations.
     *
     * @param title       The title of the overlay.
     * @param text        The text content to display in the overlay.
     * @param buttonConfigs An array of button configurations, where each element is a
     *                      pair of image path and corresponding action.
     * @param starImagePath Optional image path for the score image.
     * @return A configured BaseOverlay.
     */
    public BaseOverlay createOverlay(Group root, String title, String text, Object[][] buttonConfigs, String starImagePath) {
        // Create the overlay and pass the optional starImagePath
        BaseOverlay overlay = new BaseOverlay(root);

        // Set title
        overlay.setTitle(title);

        // Add text content
        Label textLabel = new Label(text);
        textLabel.setTextFill(Color.SILVER);
        textLabel.setFont(Font.font("Arial", 24));
        overlay.addContent(textLabel);

        // Add buttons dynamically based on buttonConfigs
        for (Object[] config : buttonConfigs) {
            if (config.length != 2) {
                throw new IllegalArgumentException("Each button configuration must have an image path and an action.");
            }

            Object imagePath = config[0];
            Runnable action = (Runnable) config[1];  // Cast the second element as Runnable
            Button button = ButtonFactory.createImageButton((String.valueOf(imagePath)), 100, 100);

            button.setOnAction(event -> {
                overlay.hide();  // Hide the overlay when button is clicked
                action.run();    // Run the action associated with the button
            });

            overlay.addButton(button);
        }

        // Add score image if path is provided
        overlay.addScoreImage(starImagePath);

        return overlay;
    }
}
