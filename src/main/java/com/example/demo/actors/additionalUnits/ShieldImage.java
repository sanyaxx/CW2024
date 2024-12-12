package com.example.demo.actors.additionalUnits;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a visual shield in the game, displayed as an image that can be shown or hidden.
 * The shield is used to provide protection for game entities, and its visibility can be toggled based on game logic.
 * <p>
 * This class extends `ImageView` to manage the shield's image and position, providing methods to show, hide, and update
 * the shield's position dynamically.
 * </p>
 */
public class ShieldImage extends ImageView {

	/** The image file for the shield */
	private static final String IMAGE_NAME = "/images/shield.png";

	/** The size of the shield */
	private static final int SHIELD_SIZE = 100;

	/**
	 * Constructs a `ShieldImage` instance at the specified position.
	 *
	 * @param xPosition The X-coordinate of the shield's initial position
	 * @param yPosition The Y-coordinate of the shield's initial position
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		// Load the shield image from the resources
		this.setImage(new Image(getClass().getResource("/com/example/demo/images/shield.png").toExternalForm()));
		this.setVisible(false); // Initially hide the shield
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	/**
	 * Makes the shield visible by setting its visibility to true and bringing it to the front.
	 */
	public void showShield() {
		this.setVisible(true);
		this.toFront(); // Bring the shield to the front layer
	}

	/**
	 * Hides the shield by setting its visibility to false.
	 */
	public void hideShield() {
		this.setVisible(false);
	}

	/**
	 * Updates the position of the shield to the specified coordinates.
	 * The shield is positioned relative to the specified X and Y coordinates with an additional offset for proper placement.
	 *
	 * @param xPosition The new X-coordinate for the shield
	 * @param yPosition The new Y-coordinate for the shield
	 */
	public void updatePosition(double xPosition, double yPosition) {
		this.setLayoutX(xPosition + 50); // Adjust position with an additional offset for better alignment
		this.setLayoutY(yPosition);
	}
}
