package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

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
 * In the previous version of this class, the game only launched into {@code LevelOne} directly, with a fixed reference to
 * that level. The new version has been generalized to allow any level to be launched by dynamically loading the
 * level class using reflection. This provides flexibility and allows the game to easily transition between different levels.
 * The class also shows a start screen before beginning the game, ensuring a smoother user experience.
 * </p>
 */
public class Controller implements Observer {

	/**
	 * The primary stage for the application window.
	 * This is where all the scenes are displayed in the game.
	 */
	private final Stage stage;

	/**
	 * Constructor that initializes the controller with the primary stage for the application.
	 *
	 * @param stage The primary stage for the application window.
	 */
	public Controller(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Launches the game by displaying the start screen.
	 * This method was added in the updated version to provide a start screen before transitioning to gameplay.
	 * <p>
	 * The start screen offers the player a chance to interact before starting the game.
	 * </p>
	 *
	 * @throws SecurityException If there is a security issue during the instantiation.
	 * @throws IllegalArgumentException If the arguments for invoking methods are invalid.
	 */
	public void launchGame() throws SecurityException, IllegalArgumentException {
		StartScreen startScreen = new StartScreen(stage);
		startScreen.show();
	}

	/**
	 * Transitions to the specified level using reflection. This method dynamically loads the level class by name
	 * and initializes it based on the stage's dimensions. The game loop is then started for the newly loaded level.
	 *
	 * @param className The fully qualified name of the level class to transition to.
	 * @throws ClassNotFoundException If the class cannot be found.
	 * @throws NoSuchMethodException If the class does not have a matching constructor.
	 * @throws SecurityException If there is a security issue during method access.
	 * @throws InstantiationException If the class cannot be instantiated.
	 * @throws IllegalAccessException If the constructor is not accessible.
	 * @throws IllegalArgumentException If the arguments passed to the constructor are invalid.
	 * @throws InvocationTargetException If the constructor throws an exception.
	 */
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
		myLevel.addObserver(this);
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.startGame();
	}

	/**
	 * Displays an error message when an exception is thrown during level transition or initialization.
	 * <p>
	 * This method is invoked when an error occurs during the process of transitioning between levels.
	 * The exception type is displayed to help the developer debug the issue.
	 * </p>
	 *
	 * @param e The exception that was caught.
	 */
	private void showError(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(e.getClass().toString());
		alert.show();
	}

	/**
	 * Called when the observed object notifies its observers of a change. This method is used to handle level
	 * transitions when a level sends a signal to transition to the next one.
	 * <p>
	 * The argument passed from the observable object is expected to be the class name of the next level to load.
	 * </p>
	 *
	 * @param arg0 The observable object.
	 * @param arg1 The argument passed by the observable object, typically the next level's class name.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			goToLevel((String) arg1);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			showError(e);
		}
	}
}
