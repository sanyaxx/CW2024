package com.example.demo;

import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WinningParameter {
    private static final double DISPLAY_X_POSITION = 1000;  // Position from the left
    private static final double DISPLAY_Y_POSITION = 25;   // Position from the top
    private int value1;
    private int value2;
    private final String parameterName1;
    private final String parameterName2;
    private final Text parameterText1;
    private Text parameterText2;
    private final HBox container;

    public WinningParameter(String parameterName1, int initialValue1, String parameterName2, int initialValue2) {
        this.parameterName1 = parameterName1;
        this.value1 = initialValue1;
        this.parameterName2 = parameterName2;
        this.value2 = initialValue2;

        // Create styled text for both parameters
        this.parameterText1 = createStyledText(parameterName1, value1);
        this.parameterText2 = createStyledText(parameterName2, value2);

        // Initialize the container with both parameters
        this.container = new HBox(10); // 10 pixels spacing between elements
        this.container.getChildren().addAll(parameterText1, parameterText2);
        this.container.setTranslateX(DISPLAY_X_POSITION);
        this.container.setTranslateY(DISPLAY_Y_POSITION);
    }

    public WinningParameter(String parameterName1, int initialValue1) {
        this.parameterName1 = parameterName1;
        this.value1 = initialValue1;
        this.parameterName2 = null; // No second parameter
        this.value2 = 0; // Default value for second parameter

        // Create styled text for the first parameter
        this.parameterText1 = createStyledText(parameterName1, value1);

        // Initialize the container with only the first parameter
        this.container = new HBox(10); // 10 pixels spacing between elements
        this.container.getChildren().add(parameterText1);
        this.container.setTranslateX(DISPLAY_X_POSITION);
        this.container.setTranslateY(DISPLAY_Y_POSITION);
    }

    // Helper method to create styled text
    private Text createStyledText(String parameterName, int value) {
        Text text = new Text(parameterName + ": " + value);
        text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 36)); // Larger and bolder font
        text.setFill(Color.GOLD);  // Set a bright and attractive color
        text.setStroke(Color.BLACK); // Add a black outline to make text stand out
        text.setStrokeWidth(2);
        return text;
    }

    // Method to update the value of the first parameter and display
    public void updateValue1(int newValue) {
        value1 = newValue;
        parameterText1.setText(parameterName1 + ": " + value1);
    }

    // Method to update the value of the second parameter and display
    public void updateValue2(int newValue) {
        if (parameterName2 != null) { // Ensure the second parameter exists
            value2 = newValue;
            parameterText2.setText(parameterName2 + ": " + value2);
        }
    }

    // Getter for the display container
    public HBox getContainer() {
        return container;
    }

    // Getter for the values
    public int getValue1() {
        return value1;
    }

    public int getValue2() {
        return value2;
    }
}