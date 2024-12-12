package com.example.demo.actors.Projectiles.userProjectiles;

import com.example.demo.actors.Projectiles.Projectile;

/**
 * The {@code UserProjectile} class represents a projectile fired by the player in the game.
 * It extends the {@link Projectile} class, inheriting common projectile functionality such as movement and destruction.
 * This class handles the specific behavior of the user's projectile, including movement in multiple directions based on its rotation angle.
 * <p>
 * {@code UserProjectile} can be fired in four possible directions: North, East, South, and West. The projectile's movement is controlled by its direction and velocity.
 * It is not collectible by the player and is considered friendly in the context of collision detection.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 * UserProjectile projectile = new UserProjectile(initialXPos, initialYPos, rotationAngle);
 * </pre>
 */
public class UserProjectile extends Projectile {

	/** The name of the image used for the projectile. */
	private static final String IMAGE_NAME = "userfire.png";

	/** The height of the image for the projectile. */
	private static final int IMAGE_HEIGHT = 15;

	/** The vertical velocity at which the projectile moves. */
	private static final int VERTICAL_VELOCITY = 15;

	/** The horizontal velocity at which the projectile moves. */
	private static final int HORIZONTAL_VELOCITY = 15;

	/** The direction in which the projectile is fired, represented as an angle. */
	private int direction;

	/**
	 * Constructor to create a new user projectile with the specified position and rotation angle.
	 * <p>
	 * This constructor initializes the user projectile with an image, height, and position based on the input parameters.
	 * The projectile's direction is set according to the rotation angle provided.
	 * </p>
	 *
	 * @param initialXPos The initial X position of the projectile.
	 * @param initialYPos The initial Y position of the projectile.
	 * @param rotationAngle The rotation angle of the projectile (in degrees).
	 *                      Possible values include -90 (North), 0 (East), 90 (South), and 180 (West).
	 */
	public UserProjectile(double initialXPos, double initialYPos, int rotationAngle) {
		// Call the parent constructor to initialize the projectile image and position
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);

		// Set the direction based on the input rotation angle
		this.direction = rotationAngle;
	}

	/**
	 * Updates the position of the projectile based on its direction. The projectile will move in the specified direction
	 * and apply a horizontal or vertical velocity accordingly.
	 * <p>
	 * The method rotates the projectile according to its direction and moves it either vertically or horizontally based on the angle.
	 * </p>
	 */
	@Override
	public void updatePosition() {
		// Rotate the projectile based on its direction
		rotateProjectile(direction);

		// Move the projectile based on the direction
		switch (direction) {
			case -90: // Shoot North
				moveVertically(-VERTICAL_VELOCITY); // Move up (negative Y direction)
				break;
			case 0: // Shoot East
				moveHorizontally(HORIZONTAL_VELOCITY); // Move right (positive X direction)
				break;
			case 90: // Shoot South
				moveVertically(VERTICAL_VELOCITY); // Move down (positive Y direction)
				break;
			case 180: // Shoot West
				moveHorizontally(-HORIZONTAL_VELOCITY); // Move left (negative X direction)
				break;
			default:
				// If direction is invalid, no movement occurs
				break;
		}
	}

	/**
	 * Updates the projectile's state, including its position.
	 * <p>
	 * This method is a general update function, calling {@link #updatePosition()} to update the position of the projectile.
	 * </p>
	 */
	@Override
	public void update() {
		updatePosition(); // Update the position of the projectile
	}

	/**
	 * Checks if the projectile is friendly. Since the user fires this projectile, it is considered friendly.
	 * <p>
	 * This method returns {@code true} to indicate that the projectile is friendly in the context of collision detection.
	 * </p>
	 *
	 * @return true, as this projectile is friendly.
	 */
	@Override
	public boolean isFriendly() {
		return true; // This projectile is friendly
	}

	/**
	 * Determines if the projectile is collectible. This projectile is not collectible by the player.
	 * <p>
	 * This method returns {@code false} to indicate that the projectile cannot be collected by the player.
	 * </p>
	 *
	 * @return false, as this projectile is not collectible.
	 */
	@Override
	public boolean isCollectible() {
		return false; // This projectile is not collectible by the player
	}

	/**
	 * Handles the projectile taking damage. In this case, it calls the parent method to remove the projectile from the game world.
	 * <p>
	 * This method is a placeholder for any damage logic that may be added in the future, but for now, it simply invokes the
	 * {@link Projectile#takeDamage()} method from the parent class to destroy the projectile when it is damaged.
	 * </p>
	 */
	@Override
	public void takeDamage() {
		super.takeDamage(); // Call the parent class method to handle projectile destruction
	}
}
