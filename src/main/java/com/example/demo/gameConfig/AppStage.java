package com.example.demo.gameConfig;

import javafx.stage.Stage;

public class AppStage {
    private static AppStage instance;
    private Stage primaryStage; // Reference to the primary stage

    // Private constructor to prevent instantiation
    private AppStage() {
    }

    // Public method to get the singleton instance
    public static AppStage getInstance() {
        if (instance == null) {
            instance = new AppStage();
        }
        return instance;
    }

    // Method to set the primary stage
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    // Method to get the primary stage
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    // Other shared methods can be added here as needed
}