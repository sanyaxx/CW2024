package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import com.example.demo.AppStage;
import com.example.demo.LevelManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static final double SCREEN_WIDTH = (int) javafx.stage.Screen.getPrimary().getBounds().getWidth();
	private static final double SCREEN_HEIGHT = (int) javafx.stage.Screen.getPrimary().getBounds().getHeight();;
	private static final String TITLE = "Sky Battle";
	private Controller myController;

	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.setTitle(TITLE);
		stage.setResizable(true);
		stage.setMaximized(true);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);

		LevelManager levelManager = LevelManager.getInstance();

//		Store the game app stage in this class to access it in other classes
		AppStage.getInstance().setPrimaryStage(stage);

		myController = new Controller(stage); // Initialize the controller
		myController.launchGame(); // Launch the game which will show the Start Page
		levelManager.addObserver(myController); // Register observer
	}

	public static void main(String[] args) {
		launch();
	}
}