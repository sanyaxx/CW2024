package com.example.demo.levels;

import com.example.demo.functionalClasses.DisplayHeart;
import com.example.demo.PauseButton;
import com.example.demo.activityManagers.PauseHandler;
import com.example.demo.functionalClasses.DisplayWinningParameter;
import javafx.scene.Group;

public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;

	private final Group root;
	private final DisplayHeart heartDisplay;
	public DisplayWinningParameter displayWinningParameter;
	public final PauseButton pauseButton;
	public final PauseHandler pauseHandler;

	public LevelView(Group root, int heartsToDisplay, int score) {
		this.root = root;
		this.heartDisplay = new DisplayHeart(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.displayWinningParameter = new DisplayWinningParameter("Kill Count", 0, "Coins", score);
		this.pauseButton = new PauseButton();
		this.pauseHandler = new PauseHandler(this, root);

		// Set the action for the pause button
		pauseButton.setOnPauseAction(pauseHandler::pauseGame);

		// Set the action for the resume button
		(pauseHandler.getResumeButton()).setOnAction(event -> pauseHandler.resumeGame());
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
		root.getChildren().add(displayWinningParameter.getContainer());
	}

	// Overloaded method to update the winning parameters with two values
	public void updateWinningParameterDisplay(int parameterValue1, int parameterValue2) {
		displayWinningParameter.updateValue1(parameterValue1);
		displayWinningParameter.updateValue2(parameterValue2);
	}

	// Add the pause button to the root
	public void showPauseButton() {
		root.getChildren().add(pauseButton.getPauseButton());
	}
}