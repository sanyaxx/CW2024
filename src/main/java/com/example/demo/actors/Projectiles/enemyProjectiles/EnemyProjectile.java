package com.example.demo.actors.Projectiles.enemyProjectiles;

import com.example.demo.actors.Projectiles.Projectile;

/**
 * The {@code EnemyProjectile} class represents a projectile fired by an enemy in the game.
 * It extends the {@link Projectile} class, inheriting common projectile functionality such as movement and damage handling.
 * This class specifically handles the behavior of an enemy projectile, including its movement, damage handling, and collision detection.
 *
 * {@code EnemyProjectile} moves horizontally from right to left at a fixed velocity and is considered a hostile object.
 * It is not collectible by the player and will disappear when it collides with an obstacle or takes damage.
 *
 * Example usage:
 * <pre>
 * EnemyProjectile projectile = new EnemyProjectile(initialXPos, initialYPos);
 * </pre>
 */
public class EnemyProjectile extends Projectile {

	/** The image file name representing the enemy projectile */
	private static final String IMAGE_NAME = "enemyFire.png";

	/** The height of the enemy projectile image */
	private static final int IMAGE_HEIGHT = 20;

	/** The horizontal velocity at which the projectile moves */
	private static final int HORIZONTAL_VELOCITY = -10;

	/**
	 * Constructor to create a new enemy projectile with the specified initial X and Y positions.
	 *
	 * @param initialXPos The initial X position of the projectile.
	 * @param initialYPos The initial Y position of the projectile.
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);  // Initialize the projectile using the superclass constructor
	}

	/**
	 * Updates the position of the enemy projectile by moving it horizontally at a fixed velocity.
	 * The projectile moves from right to left at a constant speed.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);  // Move the projectile horizontally by the specified velocity
	}

	/**
	 * Updates the state of the enemy projectile, which includes updating its position.
	 * This method is called every frame to ensure the projectile keeps moving.
	 */
	@Override
	public void update() {
		updatePosition();  // Calls the updatePosition method to update the projectile's position
	}

	/**
	 * Determines if the projectile is friendly. Since this is an enemy projectile, it is not friendly.
	 *
	 * @return false, as this projectile is hostile and does not belong to the player.
	 */
	@Override
	public boolean isFriendly() {
		return false;  // Enemy projectiles are hostile, so return false
	}

	/**
	 * Handles the enemy projectile taking damage. In this case, the projectile is removed from the game world.
	 * The damage could represent hitting an obstacle or being destroyed upon collision.
	 */
	@Override
	public void takeDamage() {
		super.takeDamage();  // Calls the superclass method to handle the damage (e.g., removal of the projectile)
	}

	/**
	 * Determines if the projectile is collectible. This projectile is not collectible by the player.
	 *
	 * @return false, as this projectile is not collectible and does not interact with the player's inventory.
	 */
	@Override
	public boolean isCollectible() {
		return false;  // Enemy projectiles are not collectible, so return false
	}
}
