package com.example.demo.levels;

import com.example.demo.functionalClasses.DisplayHeart;
import com.example.demo.PauseButton;
import com.example.demo.functionalClasses.DisplayWinningParameter;
import javafx.scene.Group;

public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;

	private final Group root;
	private final DisplayHeart heartDisplay;
	public DisplayWinningParameter displayWinningParameter;
	public final PauseButton pauseButton;

	public LevelView(Group root, int heartsToDisplay, int bulletCount) {
		this.root = root;
		this.heartDisplay = new DisplayHeart(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);

		this.displayWinningParameter = new DisplayWinningParameter("/com/example/demo/images/killCount.png", 0, "/com/example/demo/images/ammoCount.png", bulletCount, "/com/example/demo/images/coin.png", 0);
		this.pauseButton = new PauseButton();
	}

	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	// Overloaded method to update the winning parameters with two values
	public void updateWinningParameterDisplay(int parameterValue1, int parameterValue2, int parameterValue3) {
		displayWinningParameter.updateValue1(parameterValue1);
		displayWinningParameter.updateValue2(parameterValue2);
		displayWinningParameter.updateValue3(parameterValue3);
	}


	public void showUIComponents() {
		root.getChildren().add(heartDisplay.getContainer());
		root.getChildren().add(displayWinningParameter.getContainer());
		root.getChildren().add(pauseButton.getPauseButton());
	}
}