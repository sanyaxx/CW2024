package com.example.demo.actors;

/**
 * The {@code Collectible} interface is implemented by any game actor that can be collected by the player.
 * This typically includes items such as coins, fuel, magnets, or other collectibles that are added to the player's inventory or score.
 *
 * The {@code isCollectible()} method is used to determine whether the actor is available for collection by the player.
 * This method is primarily used in collision detection logic, where if the player collides with a collectible object,
 * the object is added to the player's resources (such as coins or power-ups).
 * <p>
 * Example usage:
 * <pre>
 * public class Coin implements Collectible {
 *     &#64;Override
 *     public boolean isCollectible() {
 *         // The coin is always collectible when it's present in the game world
 *         return true;
 *     }
 * }
 * </pre>
 */
public interface Collectible {

    /**
     * Determines if this object is collectible by the player.
     *
     * This method is typically used in collision detection logic to check whether a player or another actor
     * should collect the object. If the object is collectible, the game will handle adding it to the player's score, inventory, etc.
     *
     * @return {@code true} if the object is collectible, {@code false} otherwise.
     */
    boolean isCollectible();
}
