package com.example.demo.displays;

import com.example.demo.controller.AppStage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Objects;

/**
 * A class responsible for displaying three winning parameters along with
 * associated images and values. The parameters are shown in a horizontally
 * aligned container with their respective images and values, making it easy
 * to visualize the winning statistics in the UI.
 */
public class DisplayWinningParameter {

    /** The vertical position from the top of the screen where the container is placed */
    private static final double DISPLAY_Y_POSITION = 25;

    /** The value for the first parameter */
    private int value1;

    /** The value for the second parameter */
    private int value2;

    /** The value for the third parameter */
    private int value3;

    /** The path to the image for the first parameter */
    private final String imagePath1;

    /** The path to the image for the second parameter */
    private final String imagePath2;

    /** The path to the image for the third parameter */
    private final String imagePath3;

    /** The ImageView object displaying the image for the first parameter */
    private final ImageView imageView1;

    /** The ImageView object displaying the image for the second parameter */
    private final ImageView imageView2;

    /** The ImageView object displaying the image for the third parameter */
    private final ImageView imageView3;

    /** The Text object displaying the value of the first parameter */
    private final Text valueText1;

    /** The Text object displaying the value of the second parameter */
    private final Text valueText2;

    /** The Text object displaying the value of the third parameter */
    private final Text valueText3;

    /** The container (HBox) that holds the images and values for the three parameters */
    private final HBox container;

    /**
     * Constructor to initialize the DisplayWinningParameter with three parameters,
     * each consisting of an image and an initial value. The constructor sets up
     * the ImageView objects for the images and the styled Text objects for the
     * values, and arranges them in an HBox container.
     *
     * @param imagePath1 The path to the first image.
     * @param initialValue1 The initial value for the first parameter.
     * @param imagePath2 The path to the second image.
     * @param initialValue2 The initial value for the second parameter.
     * @param imagePath3 The path to the third image.
     * @param initialValue3 The initial value for the third parameter.
     */
    public DisplayWinningParameter(String imagePath1, int initialValue1, String imagePath2, int initialValue2, String imagePath3, int initialValue3) {
        this.imagePath1 = imagePath1;
        this.imagePath2 = imagePath2;
        this.imagePath3 = imagePath3;
        this.value1 = initialValue1;
        this.value2 = initialValue2;
        this.value3 = initialValue3;

        // Create ImageView and styled text for all three parameters
        this.imageView1 = createImageView(imagePath1);
        this.valueText1 = createStyledText(value1);

        this.imageView2 = createImageView(imagePath2);
        this.valueText2 = createStyledText(value2);

        this.imageView3 = createImageView(imagePath3);
        this.valueText3 = createStyledText(value3);

        // Initialize the container with three parameters
        this.container = new HBox(20); // 20 pixels spacing between elements
        this.container.getChildren().addAll(imageView1, valueText1, imageView2, valueText2, imageView3, valueText3);

        this.container.setTranslateY(DISPLAY_Y_POSITION); // Set the vertical position
        updatePosition(); // Center the container horizontally
    }

    /**
     * Helper method to create an ImageView from the specified image path.
     * The image is scaled to fit within a width of 50 while preserving its aspect ratio.
     *
     * @param imagePath The path to the image file.
     * @return The created ImageView object with the specified image.
     */
    private ImageView createImageView(String imagePath) {
        // Load the image from the given path
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));

        // Create an ImageView object for the image
        ImageView imageView = new ImageView(image);

        // Set the width of the image to 50 while preserving its aspect ratio
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true); // Ensure the aspect ratio is preserved

        return imageView;
    }

    /**
     * Helper method to create styled text for displaying the parameter values.
     * The text is styled with a large, bold font, a gold color, and a black outline.
     *
     * @param value The value to display in the text.
     * @return The created Text object with the styled value.
     */
    private Text createStyledText(int value) {
        // Create a Text object with the given value
        Text text = new Text(String.valueOf(value));

        // Set the font to a larger size and bold
        text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 36)); // Larger and bolder font

        // Set the text color to gold and add a black outline
        text.setFill(Color.GOLD); // Set a bright and attractive color
        text.setStroke(Color.BLACK); // Add a black outline to make text stand out
        text.setStrokeWidth(2); // Set the outline width

        return text;
    }

    /**
     * Updates the value of the first parameter and refreshes the display to show the new value.
     * This method also re-centers the container after the value is updated.
     *
     * @param newValue The new value to set for the first parameter.
     */
    public void updateValue1(int newValue) {
        value1 = newValue; // Set the new value
        valueText1.setText(String.valueOf(value1)); // Update the displayed text for value1
        updatePosition(); // Update the container position after the value change
    }

    /**
     * Updates the value of the second parameter and refreshes the display to show the new value.
     * This method also re-centers the container after the value is updated.
     *
     * @param newValue The new value to set for the second parameter.
     */
    public void updateValue2(int newValue) {
        value2 = newValue; // Set the new value
        valueText2.setText(String.valueOf(value2)); // Update the displayed text for value2
        updatePosition(); // Update the container position after the value change
    }

    /**
     * Updates the value of the third parameter and refreshes the display to show the new value.
     * This method also re-centers the container after the value is updated.
     *
     * @param newValue The new value to set for the third parameter.
     */
    public void updateValue3(int newValue) {
        if (valueText3 != null) { // Ensure the third parameter exists
            value3 = newValue; // Set the new value
            valueText3.setText(String.valueOf(value3)); // Update the displayed text for value3
            updatePosition(); // Update the container position after the value change
        }
    }

    /**
     * Updates the horizontal position of the container to ensure it is always centered
     * based on the current screen width and container width.
     */
    private void updatePosition() {
        // Get the current screen width
        double screenWidth = AppStage.getInstance().getPrimaryStage().getWidth();

        // Get the current width of the container
        double containerWidth = container.getWidth();

        // Set the horizontal position to center the container on the screen
        this.container.setTranslateX((screenWidth - containerWidth) / 2);
    }

    /**
     * Getter for the display container that holds the images and values.
     *
     * @return The HBox container that holds the three parameter images and values.
     */
    public HBox getContainer() {
        return container; // Return the container
    }
}
