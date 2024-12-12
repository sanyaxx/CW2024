package com.example.demo.levels.Level2;

import com.example.demo.actors.additionalUnits.ShieldImage;
import com.example.demo.displays.DisplayWinningParameter;
import com.example.demo.levels.LevelView;
import javafx.scene.Group;

/**
 * The LevelViewLevelTwo class is a specialized view for Level 2 of the game.
 * It extends the LevelView class and includes additional elements, such as the shield display and boss health parameter.
 */
public class LevelViewLevelTwo extends LevelView {

	/** The X position for the shield's initial display. */
	private static final int SHIELD_X_POSITION = 1150;

	/** The Y position for the shield's initial display. */
	private static final int SHIELD_Y_POSITION = 500;

	/** The root node of the scene where UI components will be added. */
	private final Group root;

	/** The shield image object that represents the shield for the user. */
	private final ShieldImage shieldImage;

	/**
	 * Constructor to initialize the LevelView for Level 2, including hearts, bullet count, and the boss health display.
	 * It also initializes the shield image display and sets the position of the shield.
	 *
	 * @param root The root node of the scene where the UI components will be added.
	 * @param heartsToDisplay The number of hearts to display based on the player's health.
	 * @param bossHealth The health of the boss that is displayed.
	 * @param bulletCount The current bullet count to be displayed.
	 * @param coinsCollected The current number of coins collected to be displayed.
	 */
	public LevelViewLevelTwo(Group root, int heartsToDisplay, int bossHealth, int bulletCount, int coinsCollected) {
		super(root, heartsToDisplay, bulletCount, coinsCollected); // Call the superclass constructor to initialize common UI components
		this.root = root; // Initialize the root node
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION); // Initialize shield image
		// Initialize the displayWinningParameter with boss health, ammo count, and coins collected
		this.displayWinningParameter = new DisplayWinningParameter(
				"/com/example/demo/images/killCount.png", bossHealth, // Initialize boss health display
				"/com/example/demo/images/ammoCount.png", bulletCount, // Initialize ammo count display
				"/com/example/demo/images/coin.png", coinsCollected // Initialize coins collected display
		);
		addImagesToRoot(); // Add shield image to the root node
	}

	/**
	 * Adds the shield image to the root node, making it visible in the scene.
	 */
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage); // Add shield to the root
	}

	/**
	 * Activates the shield and makes it visible on the screen.
	 */
	public void showShield() {
		System.out.println("[DEBUG]LevelViewL2 Shield activated.");
		shieldImage.showShield(); // Display the shield
	}

	/**
	 * Deactivates the shield and hides it from the screen.
	 */
	public void hideShield() {
		System.out.println("[DEBUG]LevelViewL2 Shield deactivated.");
		shieldImage.hideShield(); // Hide the shield
	}

	/**
	 * Updates the position of the shield image to a new X and Y coordinate.
	 *
	 * @param xPosition The new X position for the shield.
	 * @param yPosition The new Y position for the shield.
	 */
	public void updateShieldPosition(double xPosition, double yPosition) {
		shieldImage.updatePosition(xPosition, yPosition); // Update shield position
	}
}
