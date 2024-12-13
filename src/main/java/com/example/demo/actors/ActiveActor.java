package com.example.demo.actors;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The {@code ActiveActor} class is an abstract class that represents any actor in the game which has an associated image and can move within the game world.
 * It extends {@link ImageView} from the JavaFX library to display an image on the screen and includes basic movement functionality.
 *
 * This class provides the foundation for all actors that need to move horizontally or vertically within the game.
 * Specific types of actors (such as enemies, collectibles, etc.) will extend this class and implement the required method {@code updatePosition()}
 * to handle specific behavior, such as movement or updates to their state.
 * <p>
 * Example usage:
 * <pre>
 * public class Enemy extends ActiveActor {
 *     &#64;Override
 *     public void updatePosition() {
 *         // Code to update the enemy's position
 *     }
 * }
 * </pre>
 */
public abstract class ActiveActor extends ImageView {

	/**
	 * The location prefix for all images used by active actors.
	 * This is the directory where image assets are stored.
	 */
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Constructor for creating a new active actor with an image, dimensions, and initial position.
	 *
	 * @param imageName      The name of the image file to represent the actor (e.g., "enemy.png").
	 * @param imageHeight    The height of the image in pixels.
	 * @param initialXPos    The initial X position of the actor in the game world.
	 * @param initialYPos    The initial Y position of the actor in the game world.
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Abstract method that subclasses must implement to update the actor's position.
	 * The position update logic is intended to be defined by subclasses based on specific behavior.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by the specified distance.
	 *
	 * @param horizontalMove The distance in pixels to move the actor horizontally.
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by the specified distance.
	 *
	 * @param verticalMove The distance in pixels to move the actor vertically.
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}
}
