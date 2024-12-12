package com.example.demo.actors;

/**
 * The {@code GameEntity} class represents an abstract actor in the game that is both collidable and collectible.
 * It extends the {@link ActiveActor} class and implements the {@link Collidable}, {@link Collectible}, and {@link Updatable} interfaces.
 * This class combines multiple responsibilities that include handling collisions, determining collectibility, and updating the game entity's state.
 * <p>
 * The {@code GameEntity} class is designed to be extended by other specific game entities (such as enemies, items, etc.)
 * that have behaviors for taking damage, updating positions, and being collectible.
 * <p>
 * The class also tracks the destruction state of the entity, which helps in handling collision and removal logic.
 * <p>
 * Example Usage:
 * <pre>
 * public class Enemy extends GameEntity {
 *     &#64;Override
 *     public void updatePosition() {
 *         // Implementation for updating the position of the enemy
 *     }
 *
 *     &#64;Override
 *     public void update() {
 *         // Implementation for updating the enemy state
 *     }
 *
 *     &#64;Override
 *     public boolean isFriendly() {
 *         return false;
 *     }
 *
 *     &#64;Override
 *     public void takeDamage() {
 *         // Implementation for taking damage
 *     }
 *
 *     &#64;Override
 *     public boolean isCollectible() {
 *         return false;
 *     }
 * }
 * </pre>
 */
public abstract class GameEntity extends ActiveActor implements Collidable, Collectible, Updatable {

	/**
	 * Flag indicating whether the game entity is destroyed or not.
	 * This helps in tracking if the entity should be removed from the game.
	 */
	public boolean isDestroyed;

	/**
	 * Constructor to initialize a new game entity with the specified parameters.
	 *
	 * @param imageName      The name of the image to represent the entity.
	 * @param imageHeight    The height of the image.
	 * @param initialXPos    The initial X position of the entity.
	 * @param initialYPos    The initial Y position of the entity.
	 */
	public GameEntity(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.isDestroyed = false;
	}

	/**
	 * Abstract method to update the position of the game entity.
	 * This method is intended to be implemented by subclasses to define the movement behavior of the entity.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Abstract method to update the game entity's state.
	 * This method is called periodically to update the entity's logic.
	 */
	@Override
	public abstract void update();

	/**
	 * Abstract method to determine if the entity is friendly.
	 * This method is used for collision handling, where the behavior varies depending on whether the entity is friendly.
	 *
	 * @return {@code true} if the entity is friendly, {@code false} otherwise.
	 */
	@Override
	public abstract boolean isFriendly();

	/**
	 * Abstract method to handle the game entity taking damage.
	 * This method is called when the entity collides with something that should cause it damage.
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Abstract method to determine if the entity is collectible.
	 * This method is used in collision detection to determine if the entity can be collected by the player.
	 *
	 * @return {@code true} if the entity is collectible, {@code false} otherwise.
	 */
	@Override
	public abstract boolean isCollectible();
}
