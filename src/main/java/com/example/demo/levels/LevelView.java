package com.example.demo.levels;

import com.example.demo.controller.AppStage;
import com.example.demo.displays.DisplayHeart;
import com.example.demo.displays.DisplayWinningParameter;
import com.example.demo.displays.ButtonFactory;
import javafx.scene.Group;
import javafx.scene.control.Button;

/**
 * The LevelView class is responsible for displaying key gameplay components on the screen.
 * It includes the health display (hearts), winning parameters (kill count, ammo count, and coin collection),
 * and a pause button. It provides methods to update and show these components during gameplay.
 */
public class LevelView {

	/** The X position for displaying the heart icons on the screen. */
	private static final double HEART_DISPLAY_X_POSITION = 5;

	/** The Y position for displaying the heart icons on the screen. */
	private static final double HEART_DISPLAY_Y_POSITION = 25;

	/** The root node of the scene where UI components are added. */
	private final Group root;

	/** The display component for showing the player's health as hearts. */
	private final DisplayHeart heartDisplay;

	/** The display component for showing the winning parameters like kill count, ammo, and coins. */
	public DisplayWinningParameter displayWinningParameter;

	/** The pause button for pausing the game. */
	public Button pauseButton;

	/**
	 * Constructor to initialize the LevelView with necessary components such as heart display,
	 * winning parameters display, and a pause button.
	 *
	 * @param root The root node of the scene where the UI components will be added.
	 * @param heartsToDisplay The number of hearts to display based on the player's health.
	 * @param bulletCount The current bullet count to be displayed.
	 * @param coinsCollected The current number of coins collected to be displayed.
	 */
	public LevelView(Group root, int heartsToDisplay, int bulletCount, int coinsCollected) {
		this.root = root; // Initialize the root node
		this.heartDisplay = new DisplayHeart(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay); // Initialize heart display
		this.displayWinningParameter = new DisplayWinningParameter(
				"/com/example/demo/images/killCount.png", 0, // Initialize kill count display
				"/com/example/demo/images/ammoCount.png", bulletCount, // Initialize ammo count display
				"/com/example/demo/images/coin.png", coinsCollected); // Initialize coins collected display
		this.pauseButton = ButtonFactory.createImageButton(("/com/example/demo/images/pauseButton.png"), 100, 100); // Initialize the pause button
	}

	/**
	 * Updates the heart display to reflect the current health of the user.
	 *
	 * @param userHealth The current health of the user.
	 */
	public void updateHeartDisplay(int userHealth) {
		heartDisplay.syncHeartsWithUserHealth(userHealth); // Update hearts based on current health
	}

	/**
	 * Updates the winning parameters display, such as kill count, ammo count, and coins collected.
	 *
	 * @param parameterValue1 The value to display for the first parameter (e.g., kill count).
	 * @param parameterValue2 The value to display for the second parameter (e.g., ammo count).
	 * @param parameterValue3 The value to display for the third parameter (e.g., coins collected).
	 */
	public void updateWinningParameterDisplay(int parameterValue1, int parameterValue2, int parameterValue3) {
		displayWinningParameter.updateValue1(parameterValue1); // Update the first parameter value (e.g., kills)
		displayWinningParameter.updateValue2(parameterValue2); // Update the second parameter value (e.g., ammo)
		displayWinningParameter.updateValue3(parameterValue3); // Update the third parameter value (e.g., coins)
	}

	/**
	 * Adds all UI components (heart display, winning parameters, and pause button) to the scene.
	 * Also positions the pause button on the screen.
	 */
	public void showUIComponents() {
		root.getChildren().add(heartDisplay.getContainer()); // Add heart display to the scene
		root.getChildren().add(displayWinningParameter.getContainer()); // Add winning parameters to the scene
		root.getChildren().add(pauseButton); // Add pause button to the scene

		// Position the pause button in the top-right corner of the screen
		pauseButton.setLayoutX(AppStage.getInstance().getPrimaryStage().getWidth() - 200);
		pauseButton.setLayoutY(10);
	}
}
