package com.example.demo.actors.additionalUnits;

import com.example.demo.activityManagers.ActorManager;
import com.example.demo.actors.GameEntity;

/**
 * Represents an obstacle in the game that moves horizontally across the screen.
 * The obstacle is an enemy entity that the player must avoid, as it is not friendly.
 * <p>
 * This class extends `GameEntity` and provides functionality for the obstacle's movement,
 * as well as its interaction with other game elements. It is not collectible and does not
 * take damage but is destroyed when it is no longer needed.
 * </p>
 */
public class Obstacle extends GameEntity {

    /** The image name used to represent the obstacle */
    private static final String IMAGE_NAME = "obstacle.png";

    /** The height of the obstacle's image */
    private static final int IMAGE_HEIGHT = 300;

    /** The horizontal velocity at which the obstacle moves */
    private static final int HORIZONTAL_VELOCITY = -10;

    /**
     * Constructs an `Obstacle` instance at the specified position.
     *
     * @param initialXPos The initial X-coordinate of the obstacle
     * @param initialYPos The initial Y-coordinate of the obstacle
     */
    public Obstacle(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    /**
     * Returns the dimensions (height) of the obstacle.
     *
     * @return the height of the obstacle
     */
    public static int getDimensions() {
        return IMAGE_HEIGHT;
    }

    /**
     * Updates the obstacle's position by moving it horizontally at a constant velocity.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Updates the obstacle each frame, moving it horizontally and performing any other necessary actions.
     */
    @Override
    public void update() {
        updatePosition();
    }

    /**
     * Determines if the obstacle is friendly. Obstacles are always considered enemy entities.
     *
     * @return false, since the obstacle is not friendly
     */
    @Override
    public boolean isFriendly() {
        return false;
    }

    /**
     * Handles the destruction of the obstacle when it is no longer needed.
     * The obstacle is removed from the game world.
     */
    @Override
    public void takeDamage() {
        // Obstacles do not take damage, but this method can be overridden in specific cases.
    }

    /**
     * Determines if the obstacle is collectible. Obstacles are not collectible.
     *
     * @return false, since the obstacle is not collectible
     */
    @Override
    public boolean isCollectible() {
        return false;
    }
}
