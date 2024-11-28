package com.example.demo;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = -160;
	private static final int LOSS_SCREEN_Y_POSITION = -375;

	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	private final Stage stage;
	public WinningParameter winningParameter;
	public final PauseButton pauseButton;
	public final PauseOverlay pauseOverlay;
	public final LevelCompletedOverlay levelCompletedOverlay;
	public final LevelLostOverlay levelLostOverlay;

	public LevelView(Group root, int heartsToDisplay, int score) {
		this.root = root;
		this.stage = AppStage.getInstance().getPrimaryStage();
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
		this.winningParameter = new WinningParameter("Kill Count", 0, "Coins", score);
		this.pauseButton = new PauseButton();
		this.pauseOverlay = new PauseOverlay(new Scene(new StackPane(), stage.getWidth(), stage.getHeight()));
		this.levelCompletedOverlay = new LevelCompletedOverlay(new Scene(new StackPane(), stage.getWidth(), stage.getHeight()));
		this.levelLostOverlay = new LevelLostOverlay(new Scene(new StackPane(), stage.getWidth(), stage.getHeight()));
	}

	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
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

	public void getPauseOverlay() {
		if (!root.getChildren().contains(pauseOverlay.getOverlay())) {
			root.getChildren().add(pauseOverlay.getOverlay());
		} else {
			System.out.println("Overlay already exists in root");
		}
	}

	public void getLevelCompletedOverlay() {
		root.getChildren().add(levelCompletedOverlay.getOverlay());
	}

	public void getLevelLostOverlay() {
		root.getChildren().add(levelLostOverlay.getOverlay());
	}

	public ResumeButton getResumeButton() {
		return pauseOverlay.getResumeButton(); // Access the ResumeButton from PauseOverlay
	}
}