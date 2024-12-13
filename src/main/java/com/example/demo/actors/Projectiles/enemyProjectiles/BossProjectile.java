package com.example.demo.actors.Projectiles.enemyProjectiles;

import com.example.demo.actors.Projectiles.Projectile;

/**
 * The {@code BossProjectile} class represents a projectile fired by the boss character in the game.
 * It extends the {@link Projectile} class, inheriting common projectile functionality such as movement and damage handling.
 * This class handles the specific behavior of the boss's projectile, including movement and collision detection.
 * <p>
 * {@code BossProjectile} moves horizontally from right to left at a fixed velocity and is considered an enemy projectile.
 * It is not collectible by the player and is hostile in the context of collision detection.
 * </p>
 * Example usage:
 * <pre>
 * BossProjectile projectile = new BossProjectile(initialYPos);
 * </pre>
 */
public class BossProjectile extends Projectile {

	/**
	 * The image name for the boss's projectile.
	 */
	private static final String IMAGE_NAME = "fireball.png";

	/**
	 * The height of the boss projectile image.
	 */
	private static final int IMAGE_HEIGHT = 40;

	/**
	 * The horizontal velocity at which the boss projectile moves (leftward).
	 */
	private static final int HORIZONTAL_VELOCITY = -15;

	/**
	 * The initial X position of the boss projectile when it is fired.
	 */
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * Constructor to create a new boss projectile with the specified Y position.
	 * Initializes the projectile with the provided Y position and a fixed X position for the initial location.
	 *
	 * @param initialYPos The initial Y position of the projectile.
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Updates the position of the boss projectile by moving it horizontally at a fixed velocity.
	 * The projectile moves leftward on each update.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the boss projectile, which includes updating its position.
	 * This method calls the {@link #updatePosition()} method to move the projectile.
	 */
	@Override
	public void update() {
		updatePosition();
	}

	/**
	 * Determines if the projectile is friendly. Since this is an enemy projectile, it is not friendly.
	 *
	 * @return false, as this projectile is hostile.
	 */
	@Override
	public boolean isFriendly() {
		return false;
	}

	/**
	 * Handles the boss projectile taking damage. In this case, the projectile is removed from the game world.
	 * This method overrides the {@link Projectile#takeDamage()} method to handle the boss projectile's destruction.
	 */
	@Override
	public void takeDamage() {
		super.takeDamage();
	}

	/**
	 * Determines if the projectile is collectible. This projectile is not collectible by the player.
	 *
	 * @return false, as this projectile is not collectible.
	 */
	@Override
	public boolean isCollectible() {
		return false;
	}
}
