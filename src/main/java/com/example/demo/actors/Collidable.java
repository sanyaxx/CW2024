package com.example.demo.actors;

/**
 * The {@code Collidable} interface represents any object in the game that can be involved in collision detection.
 * It is typically implemented by actors that can take damage upon collision, and the interface defines behaviors
 * such as taking damage and determining whether the actor is friendly or an opponent.
 *
 * This interface includes the method {@code takeDamage()} to define how an object reacts to collision damage,
 * and {@code isFriendly()} to determine whether the object is considered a friendly or opposing actor.
 * The updated design focuses on removing actors from the active game list directly, rather than marking them as destroyed.
 *
 * The method {@code destroy()} from the original {@code Destructible} interface has been removed because the system
 * now handles actor removal from the active actors array more effectively by using other means.
 * <p>
 * Example usage:
 * <pre>
 * public class Player implements Collidable {
 *     private int health;
 *
 *     &#64;Override
 *     public void takeDamage() {
 *         // Decrease health when collided
 *         health -= 10;
 *     }
 *
 *     &#64;Override
 *     public boolean isFriendly() {
 *         // Return true as player is friendly
 *         return true;
 *     }
 * }
 * </pre>
 */
public interface Collidable {

	/**
	 * Called when this object takes damage as a result of a collision.
	 * The implementation of this method should define how the object reacts to damage (e.g., health reduction,
	 * applying a visual effect).
	 *
	 * This method is typically invoked during collision detection logic to update the state of the object.
	 */
	void takeDamage();

	/**
	 * Determines whether this object is friendly or not. This is used primarily in collision handling
	 * to distinguish between friendly and enemy objects.
	 *
	 * If the object is friendly (e.g., a player or an ally), it may not be harmed by friendly fire or certain
	 * interactions. If it is an enemy, it will likely be damaged by friendly projectiles or effects.
	 *
	 * @return {@code true} if the object is friendly, {@code false} otherwise.
	 */
	boolean isFriendly();
}
