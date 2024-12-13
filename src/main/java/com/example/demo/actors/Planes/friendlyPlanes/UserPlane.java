package com.example.demo.actors.Planes.friendlyPlanes;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Projectiles.userProjectiles.UserProjectile;

/**
 * Represents the user-controlled plane in the game, with functionality for movement and projectile firing.
 * This class extends the `UserParent` class and provides more refined control over the plane's position,
 * velocity, and the firing mechanism.
 * <p>
 * Compared to the previous implementation, this version introduces more flexible movement control, including
 * specific methods to stop or adjust the plane's velocity multiplier, along with bounds to restrict the plane's
 * vertical movement. It also uses improved organization for projectile firing logic and movement updates.
 */
public class UserPlane extends UserParent {

	/** The image name for the user plane */
	private static final String IMAGE_NAME = "userplane.png";

	/** The upper bound of the vertical plane movement */
	private static final double Y_UPPER_BOUND = 80;

	/** The lower bound of the vertical plane movement */
	private static final double Y_LOWER_BOUND = 700;

	/** The initial x-coordinate position of the plane */
	private static final double INITIAL_X_POSITION = 80.0;

	/** The initial y-coordinate position of the plane */
	private static final double INITIAL_Y_POSITION = 300.0;

	/** The height of the plane's image */
	private static final int IMAGE_HEIGHT = 80;

	/** The vertical velocity factor for the plane's movement */
	private static final int VERTICAL_VELOCITY = 8;

	/** The x-coordinate for projectile firing */
	private static int PROJECTILE_X_POSITION = 120;

	/** The y-coordinate offset for projectile firing position */
	private static int PROJECTILE_Y_POSITION_OFFSET = 20;

	/** The velocity multiplier for controlling movement speed */
	private double velocityMultiplier;

	/** The current rotation angle of the plane */
	private static int rotationAngle;

	/**
	 * Constructs a `UserPlane` instance with the specified health and bullet count.
	 * The plane is initialized at the given starting position and image height, and the movement
	 * multiplier is set to zero (indicating no movement).
	 *
	 * @param initialHealth The initial health of the user plane
	 * @param bulletCount The initial count of bullets available for the plane
	 */
	public UserPlane(int initialHealth, int bulletCount) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth, bulletCount);
		velocityMultiplier = 0;
		rotationAngle = 0; // Default rotation angle set to facing East (0 degrees)
	}

	/**
	 * Resets the position of the plane back to the starting coordinates.
	 * This is useful when respawning or resetting the plane’s position in the game.
	 */
	public void resetPosition() {
		setLayoutX(INITIAL_X_POSITION);
		setLayoutY(INITIAL_Y_POSITION);
	}

	/**
	 * Updates the plane's position based on its current velocity multiplier.
	 * It ensures that the plane does not move beyond the specified vertical bounds.
	 */
	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			// Check if the plane exceeds the bounds and prevent further movement if so
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}
	}

	/**
	 * Updates the user plane's state by calling the `updatePosition` method.
	 * This ensures the plane's movement and actions are updated every frame.
	 */
	@Override
	public void update() {
		updatePosition();
	}

	/**
	 * Fires a projectile from the user plane in the direction the plane is facing.
	 * This returns a new instance of `UserProjectile`, taking into account the current firing position and angle.
	 *
	 * @return A new instance of `UserProjectile` fired from the user plane
	 */
	@Override
	public GameEntity fireProjectile() {
		return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET), rotationAngle);
	}

	/**
	 * Returns whether the plane is currently moving based on the velocity multiplier.
	 *
	 * @return true if the plane is moving, false otherwise
	 */
	private boolean isMoving() {
		return velocityMultiplier != 0;
	}

	/**
	 * Moves the plane upwards by setting the velocity multiplier to -1.
	 * This method is used to control the plane's upward movement.
	 */
	public void moveUp() {
		velocityMultiplier = -1;
	}

	/**
	 * Moves the plane downwards by setting the velocity multiplier to 1.
	 * This method is used to control the plane's downward movement.
	 */
	public void moveDown() {
		velocityMultiplier = 1;
	}

	/**
	 * Makes the plane gradually fall by setting the velocity multiplier to 0.7.
	 * This method simulates gravity or a slow downward drift.
	 */
	public void fallDown() {
		velocityMultiplier = 0.7;
	}

	/**
	 * Stops the plane's movement by setting the velocity multiplier to 0.
	 */
	public void stop() {
		velocityMultiplier = 0;
	}
}
