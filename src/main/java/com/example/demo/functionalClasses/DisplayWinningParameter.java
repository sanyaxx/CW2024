package com.example.demo.functionalClasses;

import com.example.demo.gameConfig.AppStage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Objects;

public class DisplayWinningParameter {
    private static final double DISPLAY_Y_POSITION = 25; // Position from the top
    private int value1;
    private int value2;
    private int value3;
    private final String imagePath1;
    private final String imagePath2;
    private final String imagePath3;
    private final ImageView imageView1;
    private final ImageView imageView2;
    private final ImageView imageView3;
    private final Text valueText1;
    private final Text valueText2;
    private final Text valueText3;
    private final HBox container;

    // Constructor for three parameters
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

        this.container.setTranslateY(DISPLAY_Y_POSITION);
        updatePosition(); // Center the container
    }

    // Constructor for two parameters
    public DisplayWinningParameter(String imagePath1, int initialValue1, String imagePath2, int initialValue2) {
        this.imagePath1 = imagePath1;
        this.imagePath2 = imagePath2;
        this.imagePath3 = null; // No third parameter
        this.value1 = initialValue1;
        this.value2 = initialValue2;
        this.value3 = 0;

        // Create ImageView and styled text for the first two parameters
        this.imageView1 = createImageView(imagePath1);
        this.valueText1 = createStyledText(value1);

        this.imageView2 = createImageView(imagePath2);
        this.valueText2 = createStyledText(value2);

        this.imageView3 = null; // No third image
        this.valueText3 = null; // No third text

        // Initialize the container with two parameters
        this.container = new HBox(20); // 20 pixels spacing between elements
        this.container.getChildren().addAll(imageView1, valueText1, imageView2, valueText2);

        this.container.setTranslateY(DISPLAY_Y_POSITION);
        updatePosition(); // Center the container
    }

    // Helper method to create an ImageView
    private ImageView createImageView(String imagePath) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50); // Adjust size of the image as needed
        imageView.setPreserveRatio(true);
        return imageView;
    }

    // Helper method to create styled text
    private Text createStyledText(int value) {
        Text text = new Text(String.valueOf(value));
        text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 36)); // Larger and bolder font
        text.setFill(Color.GOLD); // Set a bright and attractive color
        text.setStroke(Color.BLACK); // Add a black outline to make text stand out
        text.setStrokeWidth(2);
        return text;
    }

    // Method to update the value of the first parameter and its display
    public void updateValue1(int newValue) {
        value1 = newValue;
        valueText1.setText(String.valueOf(value1));
        updatePosition(); // Update position whenever value changes
    }

    // Method to update the value of the second parameter and its display
    public void updateValue2(int newValue) {
        value2 = newValue;
        valueText2.setText(String.valueOf(value2));
        updatePosition(); // Update position whenever value changes
    }

    // Method to update the value of the third parameter and its display
    public void updateValue3(int newValue) {
        if (valueText3 != null) { // Ensure the third parameter exists
            value3 = newValue;
            valueText3.setText(String.valueOf(value3));
            updatePosition(); // Update position whenever value changes
        }
    }

    // Method to update the position of the container
    private void updatePosition() {
        double screenWidth = AppStage.getInstance().getPrimaryStage().getWidth(); // Get the current screen width
        double containerWidth = container.getWidth(); // Get the current width of the container
        this.container.setTranslateX((screenWidth - containerWidth) / 2); // Center the container
    }

    // Getter for the display container
    public HBox getContainer() {
        return container;
    }
}
