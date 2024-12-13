package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Main class serves as the entry point for launching the game application.
 * It initializes the main game window, sets its properties, and creates instances of necessary controllers and managers for game flow.
 *
 * <p>This version introduces dynamic screen sizing based on the primary screen's dimensions, allowing the game to adapt
 * to different screen resolutions. It integrates with the `AppStage` singleton to store and retrieve the primary stage
 * and registers the `LevelManager` for efficient game level transitions.</p>
 */
public class Main extends Application {

	/**
	 * Default constructor for the Main class.
	 * <p>
	 * This constructor is implicitly provided by the compiler but is explicitly defined here
	 * for documentation purposes. The Main class serves as the entry point for the JavaFX application.
	 */
	public Main() {
		super(); // Call the superclass (Application) constructor
	}

	/**
	 * The width of the game screen, dynamically set based on the primary screen's dimensions.
	 */
	private static final double SCREEN_WIDTH = (int) javafx.stage.Screen.getPrimary().getBounds().getWidth();

	/**
	 * The height of the game screen, dynamically set based on the primary screen's dimensions.
	 */
	private static final double SCREEN_HEIGHT = (int) javafx.stage.Screen.getPrimary().getBounds().getHeight();

	/**
	 * The title of the game window.
	 */
	private static final String TITLE = "Sky Battle";

	/**
	 * The main controller for managing game logic and interactions.
	 */
	private Controller myController;

	/**
	 * Initializes the primary stage and sets up the game environment.
	 *
	 * <p>This method configures the window properties, including screen size, title, and behavior.
	 * It initializes the `Controller` and registers the `AppStage` singleton to store the primary stage
	 * for use across the application. The method also ensures smooth integration with the `LevelManager`
	 * for handling game level transitions.</p>
	 *
	 * @param stage The primary stage for the application window.
	 * @throws ClassNotFoundException If a class specified by name cannot be found.
	 * @throws NoSuchMethodException If the method to be invoked does not exist.
	 * @throws SecurityException If a security manager denies access to a class or method.
	 * @throws InstantiationException If the class cannot be instantiated.
	 * @throws IllegalAccessException If the constructor cannot be accessed.
	 * @throws IllegalArgumentException If the arguments provided to the constructor are invalid.
	 * @throws InvocationTargetException If the constructor throws an exception.
	 */
	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.setTitle(TITLE); // Set the game window's title
		stage.setResizable(false); // Disable window resizing
		stage.setMaximized(true); // Maximize the window to use the full screen
		stage.setHeight(SCREEN_HEIGHT); // Set the screen height dynamically
		stage.setWidth(SCREEN_WIDTH); // Set the screen width dynamically

		// Store the game app stage in the singleton for global access
		AppStage.getInstance().setPrimaryStage(stage);

		// Initialize the controller and launch the game
		myController = new Controller(stage);
		myController.launchGame(); // Start the game, which will display the start page
	}

	/**
	 * The main entry point for launching the game application.
	 *
	 * @param args Command-line arguments (not used in this application).
	 */
	public static void main(String[] args) {
		launch(); // Launch the JavaFX application
	}
}
