//package com.example.demo.activityManagers;
//
//import com.example.demo.activityManagers.ButtonFactory;
//import javafx.scene.control.Button;
//
//public class ButtonManager {
//
//    /**
//     * Creates an array of buttons based on the provided image paths.
//     *
//     * @param imagePaths Array of image paths for button icons.
//     * @param actionHandler A handler to define actions for each button.
//     * @return An array of created buttons.
//     */
//    public static Button[] createButtons(String[] imagePaths, ButtonActionHandler actionHandler) {
//        Button[] buttons = new Button[imagePaths.length];
//
//        for (int i = 0; i < imagePaths.length; i++) {
//            buttons[i] = ButtonFactory.createImageButton(imagePaths[i]);
//            // Remove text assignment to avoid showing text next to buttons
//            int finalI = i;
//            buttons[i].setOnAction(event -> actionHandler.handleButtonAction(buttons[finalI]));
//        }
//
//        return buttons;
//    }
//
//    @FunctionalInterface
//    public interface ButtonActionHandler {
//        void handleButtonAction(Button button);
//    }
//}