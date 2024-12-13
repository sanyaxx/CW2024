package com.example.demo.actors.Planes.enemyPlanes;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Projectiles.enemyProjectiles.EnemyProjectile;
import com.example.demo.actors.Planes.FighterPlane;

/**
 * Represents an enemy plane that moves horizontally across the screen and fires projectiles.
 * This class extends the FighterPlane class, inheriting its properties and behaviors such as health, image,
 * and basic movement mechanics. The EnemyPlane is designed to be a basic enemy in the game that can deal damage
 * to the player and move along a simple horizontal path while occasionally firing projectiles.
 */
public class EnemyPlane extends FighterPlane {

	/** The image file name representing the enemy plane */
	private static final String IMAGE_NAME = "enemyplane.png";

	/** The height of the enemy plane in pixels */
	private static final int IMAGE_HEIGHT = 45;

	/** The horizontal velocity at which the enemy plane moves (i.e., how fast it moves leftward across the screen) */
	private static final int HORIZONTAL_VELOCITY = -6;

	/** The offset for the x-position of the projectiles relative to the enemy plane */
	private static final double PROJECTILE_X_POSITION_OFFSET = -50.0;

	/** The offset for the y-position of the projectiles relative to the enemy plane */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 30.0;

	/** The initial health of the enemy plane (i.e., how many hits it can take before being destroyed) */
	private static final int INITIAL_HEALTH = 1;

	/** The fire rate of the enemy plane (i.e., the probability that the enemy fires a projectile each frame) */
	private static final double FIRE_RATE = .01;

	/**
	 * Constructs an EnemyPlane instance at a specific position.
	 *
	 * @param initialXPos The initial x-position of the enemy plane
	 * @param initialYPos The initial y-position of the enemy plane
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		// Calls the constructor of FighterPlane to initialize the parent class properties
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally.
	 * This method is called every frame to ensure that the enemy plane moves leftward across the screen.
	 */
	@Override
	public void updatePosition() {
		// Moves the enemy plane horizontally based on the specified horizontal velocity
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a projectile from the enemy plane based on the fire rate.
	 * If the random chance based on the fire rate is met, a new EnemyProjectile is created.
	 *
	 * @return A new EnemyProjectile if the fire rate condition is met, otherwise null
	 */
	@Override
	public GameEntity fireProjectile() {
		// Checks if a random number is less than the fire rate, determining if the enemy fires a projectile
		if (Math.random() < FIRE_RATE) {
			// Calculates the x and y positions of the projectile relative to the enemy plane's position
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			// Returns a new EnemyProjectile with the calculated position
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		// If the fire rate condition is not met, no projectile is fired
		return null;
	}

	/**
	 * Updates the state of the enemy plane. This method is called every frame and updates
	 * the plane's position by calling updatePosition().
	 */
	@Override
	public void update() {
		// Updates the enemy plane's position
		updatePosition();
	}

	/**
	 * Determines if the enemy plane is friendly. This method always returns false, as the
	 * EnemyPlane is an enemy and should be treated as hostile in the game.
	 *
	 * @return false, as this plane is not friendly
	 */
	@Override
	public boolean isFriendly() {
		return false;
	}

	/**
	 * Determines if the enemy plane is collectible. This method always returns false, as
	 * the EnemyPlane does not drop any collectible items when destroyed.
	 *
	 * @return false, as this plane is not collectible
	 */
	@Override
	public boolean isCollectible() {
		return false;
	}

	/**
	 * Takes damage from an external source, such as a projectile hitting the enemy plane.
	 * This method is inherited from the FighterPlane class and reduces the health of the
	 * enemy plane when it takes damage.
	 */
	@Override
	public void takeDamage() {
		// Calls the parent class method to handle damage and health reduction
		super.takeDamage();
	}
}
