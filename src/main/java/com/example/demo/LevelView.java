package com.example.demo;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;

	private final Group root;
	private final HeartDisplay heartDisplay;
	public WinningParameter winningParameter;
	public final PauseButton pauseButton;
	public final PauseHandler pauseHandler;

	public LevelView(Group root, int heartsToDisplay, int score) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winningParameter = new WinningParameter("Kill Count", 0, "Coins", score);
		this.pauseButton = new PauseButton();
		this.pauseHandler = new PauseHandler(this, root);

		// Set the action for the pause button
		pauseButton.setOnPauseAction(pauseHandler::pauseGame);

		// Set the action for the resume button
		(pauseHandler.getResumeButton()).setOnResumeAction(pauseHandler::resumeGame);
	}


	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	public void showWinningParameterDisplay() {
		root.getChildren().add(winningParameter.getContainer());
	}

	// Overloaded method to update the winning parameters with two values
	public void updateWinningParameterDisplay(int parameterValue1, int parameterValue2) {
		winningParameter.updateValue1(parameterValue1);
		winningParameter.updateValue2(parameterValue2);
	}

	// Add the pause button to the root
	public void showPauseButton() {
		root.getChildren().add(pauseButton.getPauseButton());
	}
}