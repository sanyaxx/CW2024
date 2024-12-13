package com.example.demo.actors.Projectiles;

import com.example.demo.activityManagers.ActorManager;
import com.example.demo.actors.GameEntity;

/**
 * The {@code Projectile} class is an abstract class representing a projectile (e.g., bullets, missiles) in the game.
 * It extends {@link GameEntity}, which provides functionality for the actor's position, image, and basic behaviors.
 * A projectile moves within the game world, can be rotated, and can be destroyed upon collision or other events.
 *
 * This class provides the foundation for creating specific types of projectiles with different behaviors and movement logic.
 * Projectiles can be destroyed by taking damage and will be removed from the game world through the actor manager.
 * <p>
 * Example usage:
 * <pre>
 * public class Bullet extends Projectile {
 *     &#64;Override
 *     public void updatePosition() {
 *         // Code to update the bullet's position
 *     }
 * }
 * </pre>
 */
public abstract class Projectile extends GameEntity {

	/**
	 * Constructor for creating a new projectile with an image, dimensions, and initial position.
	 *
	 * @param imageName      The name of the image file to represent the projectile (e.g., "bullet.png").
	 * @param imageHeight    The height of the image in pixels.
	 * @param initialXPos    The initial X position of the projectile in the game world.
	 * @param initialYPos    The initial Y position of the projectile in the game world.
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Rotates the projectile by the specified angle.
	 *
	 * @param angle The angle (in degrees) to rotate the projectile.
	 */
	public void rotateProjectile(int angle) {
		this.setRotate(angle);
	}

	/**
	 * Handles the projectile taking damage. In this case, the projectile is removed from the game world
	 * through the actor manager.
	 */
	@Override
	public void takeDamage() {
		ActorManager.getInstance().removeActor(this);
	}

	/**
	 * Abstract method that must be implemented by subclasses to update the position of the projectile.
	 * The position update logic is intended to be defined by the specific type of projectile.
	 */
	@Override
	public abstract void updatePosition();
}
