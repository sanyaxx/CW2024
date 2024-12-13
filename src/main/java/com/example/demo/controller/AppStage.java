package com.example.demo.controller;

import javafx.stage.Stage;

/**
 * The {@code AppStage} class is a Singleton that manages the primary {@link Stage} of the JavaFX application.
 * <p>
 * It provides a centralized way to access and manage the primary stage, ensuring that only one instance exists
 * throughout the application. This class is crucial for setting up and controlling the main window.
 * </p>
 */
public class AppStage {

    /**
     * The singleton instance of {@code AppStage}.
     * <p>
     * This ensures only one instance of the class exists at any time, following the Singleton design pattern.
     * </p>
     */
    private static AppStage instance;

    /**
     * The primary {@link Stage} of the JavaFX application.
     * <p>
     * This reference is used to manage the main application window, including its initialization and updates.
     * </p>
     */
    private Stage primaryStage;

    /**
     * Private constructor to prevent direct instantiation of {@code AppStage}.
     * <p>
     * This ensures the Singleton pattern is enforced, allowing controlled access to the single instance via
     * {@link #getInstance()}.
     * </p>
     */
    private AppStage() {}

    /**
     * Retrieves the singleton instance of {@code AppStage}.
     * <p>
     * If the instance does not exist, it creates a new instance. Subsequent calls will return the existing instance.
     * </p>
     *
     * @return The singleton instance of {@code AppStage}.
     */
    public static AppStage getInstance() {
        if (instance == null) {
            instance = new AppStage();
        }
        return instance;
    }

    /**
     * Sets the primary {@link Stage} for the JavaFX application.
     * <p>
     * This method should be called during the application's initialization to set the reference to the primary stage.
     * </p>
     *
     * @param stage The primary {@link Stage} of the application. Must not be {@code null}.
     */
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Retrieves the primary {@link Stage} of the JavaFX application.
     * <p>
     * This method provides access to the main application window for other parts of the application.
     * </p>
     *
     * @return The primary {@link Stage} of the application, or {@code null} if it has not been set.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
