package com.example.demo.actors;

/**
 * The {@code Updatable} interface represents any actor or component in the game that requires periodic updates during
 * the game loop. It defines a single method, {@code update()}, that must be implemented by any class that implements
 * this interface to perform its update logic.
 * <p>
 * This interface can be used to facilitate the implementation of game actors that need to have their state
 * or behavior updated continuously (e.g., moving characters, updating scores, or checking for collisions).
 * Any class implementing {@code Updatable} will be responsible for defining the specific behavior of its update
 * method, allowing the game engine or framework to call it during the game's execution.
 * <p>
 * Example usage:
 * <pre>
 * public class Player implements Updatable {
 *     private int x, y;
 *
 *     &#64;Override
 *     public void update() {
 *         // Update player position or state
 *         x += 1;
 *         y += 1;
 *     }
 * }
 * </pre>
 */
public interface Updatable {

    /**
     * Updates the state or behavior of the object that implements this interface.
     * This method is typically called during each frame of the game loop to keep the object in sync with the
     * rest of the game world.
     * <p>
     * Classes implementing this interface should define the specific behavior that needs to be updated.
     * This could include actions such as moving, changing state, or checking for events.
     */
    void update();
}
