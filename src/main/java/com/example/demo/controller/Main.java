package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import com.example.demo.activityManagers.LevelManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Main class serves as the entry point for launching the game application.
 * It initializes the main game window, sets its properties, and creates instances of necessary controllers and managers for game flow.
 * <p>
 * The updated version of this class introduces dynamic screen sizing based on the primary screen's dimensions,
 * allowing the game to adapt to different screen resolutions, unlike the fixed dimensions used in the older version.
 * Additionally, the class is integrated with {@link AppStage} to store and retrieve the primary stage for future use,
 * and the {@link LevelManager} is registered to handle game level transitions efficiently.
 * </p>
 */
public class Main extends Application {

	/** The width of the primary screen. */
	private static final double SCREEN_WIDTH = (int) javafx.stage.Screen.getPrimary().getBounds().getWidth();

	/** The height of the primary screen. */
	private static final double SCREEN_HEIGHT = (int) javafx.stage.Screen.getPrimary().getBounds().getHeight();

	/** The title of the game window. */
	private static final String TITLE = "Sky Battle";

	/** The controller responsible for managing game flow. */
	private Controller myController;

	/**
	 * Default constructor.
	 * <p>
	 * This constructor is automatically provided by the JavaFX {@link Application} class and does not require explicit implementation.
	 * It is responsible for setting up the primary stage and initializing the game environment in the {@link #start(Stage)} method.
	 * </p>
	 */
	public Main() {
		// Default constructor is provided by the Application class.
		// No explicit initialization is required.
	}

	/**
	 * Initializes the primary stage and sets up the game environment. This method configures the window properties,
	 * including the screen size and title. It also initializes the {@link Controller} and {@link LevelManager} to manage game logic
	 * and transitions. The {@link AppStage} singleton is used to store the stage so it can be accessed from other parts of the game.
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
		// Set the window properties such as title, resizable behavior, and size
		stage.setTitle(TITLE); // Set the title of the game window
		stage.setResizable(false); // Make the window non-resizable
		stage.setMaximized(true); // Maximize the window to fit the screen
		stage.setHeight(SCREEN_HEIGHT); // Set the height based on screen size
		stage.setWidth(SCREEN_WIDTH); // Set the width based on screen size

		LevelManager levelManager = LevelManager.getInstance(); // Get the LevelManager instance

		// Store the game app stage in this class to access it from other classes
		AppStage.getInstance().setPrimaryStage(stage);

		// Initialize the controller and launch the game
		myController = new Controller(stage);
		myController.launchGame(); // Show the Start Page of the game

		// Register the controller as an observer to handle level transitions
		levelManager.addObserver(myController);
	}

	/**
	 * The main entry point for launching the game application.
	 *
	 * @param args Command-line arguments (not used in this case).
	 */
	public static void main(String[] args) {
		launch(); // Launch the JavaFX application
	}
}
