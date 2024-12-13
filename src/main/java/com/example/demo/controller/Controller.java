package com.example.demo.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.example.demo.activityManagers.LevelManager;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.levels.LevelParent;
import com.example.demo.displays.StartScreen;

/**
 * The {@code Controller} class manages the game's lifecycle and level transitions.
 * It is responsible for launching the game, handling level transitions, and displaying error messages when necessary.
 * <p>
 * This version generalizes the game's level handling by dynamically loading levels using reflection. It provides
 * flexibility for transitions and ensures a smoother user experience.
 * </p><p>
 * With reference to the old Controller code,
 * 'implements Observer' has been modified to implements 'PropertyChangeListener'
 * since Observer was deprecated.
 * </p>
 */
public class Controller implements PropertyChangeListener {

	/**
	 * The primary stage for the application window.
	 * This is where all the scenes are displayed in the game.
	 */
	private final Stage stage;

	/**
	 * Constructor that initializes the controller with the primary stage for the application.
	 * Also registers the controller as a listener for level change events from the {@code LevelManager}.
	 *
	 * @param stage The primary stage for the application window.
	 */
	public Controller(Stage stage) {
		this.stage = stage;
		LevelManager.getInstance().addLevelChangeListener(this);
	}

	/**
	 * Launches the game by displaying the start screen.
	 * This method provides a start screen for the player before transitioning to the first level.
	 *
	 * @throws SecurityException        If there is a security issue during screen initialization.
	 * @throws IllegalArgumentException If the arguments provided during screen initialization are invalid.
	 */
	public void launchGame() throws SecurityException, IllegalArgumentException {
		StartScreen startScreen = new StartScreen(stage);
		startScreen.show();
	}

	/**
	 * Transitions to the specified level by dynamically loading the level class using reflection.
	 * Initializes the level based on the stage's dimensions and starts the game loop for the new level.
	 *
	 * @param className The fully qualified name of the level class to transition to.
	 * @throws ClassNotFoundException    If the specified class cannot be found.
	 * @throws NoSuchMethodException     If the specified class does not have a matching constructor.
	 * @throws SecurityException         If there is a security issue during reflection.
	 * @throws InstantiationException    If the class cannot be instantiated.
	 * @throws IllegalAccessException    If the constructor is inaccessible.
	 * @throws IllegalArgumentException  If the constructor arguments are invalid.
	 * @throws InvocationTargetException If the constructor invocation fails or throws an exception.
	 */
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.startGame();
	}

	/**
	 * Displays an error message in an alert box when an exception occurs.
	 * This method is used to handle errors during level transitions or initialization.
	 *
	 * @param e The exception that was caught and needs to be displayed.
	 */
	private void showError(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(e.getClass().toString());
		alert.show();
	}

	/**
	 * Handles property change events from the {@code LevelManager}.
	 * Specifically, listens for "nextLevel" events to transition to the specified level.
	 *
	 * @param evt The property change event containing the property name and new value.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("nextLevel".equals(evt.getPropertyName())) { // Check if it's a level change event
			String levelClassName = (String) evt.getNewValue();
			try {
				goToLevel(levelClassName); // Transition to the new level
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
					 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				showError(e); // Show error if the level transition fails
			}
		}
	}
}
