package com.example.demo.actors.Planes;

import com.example.demo.activityManagers.ActorManager;
import com.example.demo.actors.GameEntity;

/**
 * Represents a fighter plane in the game. This class provides basic functionality for the fighter planes,
 * including handling health, projectile firing, and rotation.
 * <p>
 * Compared to the previous version, this class has some key changes:
 * - The destruction mechanism now involves notifying the `ActorManager` to remove the destroyed actor.
 * - A new method `incrementHealth()` has been added for health management.
 * - The class now extends `GameEntity` instead of `ActiveActorDestructible`, aligning with the new game entity structure.
 */
public abstract class FighterPlane extends GameEntity {

	/** The health of the fighter plane */
	private int health;

	/**
	 * Constructs a `FighterPlane` instance with the specified image, dimensions, position, and health.
	 * This constructor initializes the fighter plane with the provided parameters, including position and health.
	 *
	 * @param imageName The name of the image used to represent the fighter plane
	 * @param imageHeight The height of the image used for the plane
	 * @param initialXPos The initial x-coordinate of the plane's position
	 * @param initialYPos The initial y-coordinate of the plane's position
	 * @param health The initial health of the plane
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Abstract method to fire a projectile from the fighter plane. This method must be implemented by subclasses.
	 *
	 * @return A new instance of `GameEntity` representing the fired projectile
	 */
	public abstract GameEntity fireProjectile();

	/**
	 * Reduces the health of the fighter plane by one unit. If the health reaches zero, the plane is destroyed,
	 * and it is removed from the game by the `ActorManager`.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.isDestroyed = true;
			ActorManager.getInstance().removeActor(this); // Removes the plane from the actor manager
		}
	}

	/**
	 * Rotates the plane by the specified angle.
	 *
	 * @param angle The angle to rotate the plane to
	 */
	public void rotatePlane(int angle) {
		this.setRotate(angle);
	}

	/**
	 * Calculates the x-coordinate for projectile firing based on the current position and the provided offset.
	 *
	 * @param xPositionOffset The offset to adjust the projectile's x position
	 * @return The adjusted x-coordinate for firing the projectile
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the y-coordinate for projectile firing based on the current position and the provided offset.
	 *
	 * @param yPositionOffset The offset to adjust the projectile's y position
	 * @return The adjusted y-coordinate for firing the projectile
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks whether the plane's health has reached zero.
	 *
	 * @return true if health is zero, false otherwise
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Retrieves the current health of the fighter plane.
	 *
	 * @return The current health of the plane
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Increments the health of the fighter plane by one.
	 */
	public void incrementHealth() {
		this.health++;
	}

	/**
	 * Decrements the health of the fighter plane by one.
	 */
	public void decrementHealth() {
		this.health--;
	}
}
