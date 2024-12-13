package com.example.demo.actors.additionalUnits;

import com.example.demo.activityManagers.ActorManager;
import com.example.demo.actors.GameEntity;

/**
 * Represents a fuel token in the game that moves horizontally across the screen.
 * The fuel token is collectible by the player and is used to restore or increase
 * the player's fuel, health, or other gameplay-related metrics.
 * <p>
 * This class extends `GameEntity`, allowing the fuel token to be part of the game world
 * and interact with other entities such as the player’s vehicle.
 * </p>
 */
public class FuelToken extends GameEntity {

    /** The image name used to represent the fuel token */
    private static final String IMAGE_NAME = "fuel.png";

    /** The height of the fuel token's image */
    private static final int IMAGE_HEIGHT = 55;

    /** The horizontal velocity at which the fuel token moves */
    private static final int HORIZONTAL_VELOCITY = -8;

    /**
     * Constructs a `FuelToken` instance at the specified position.
     *
     * @param initialXPos The initial X-coordinate of the fuel token
     * @param initialYPos The initial Y-coordinate of the fuel token
     */
    public FuelToken(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    /**
     * Updates the position of the fuel token by moving it horizontally.
     * This simulates the fuel token moving across the screen towards the player.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Updates the fuel token’s position. This method is called every game frame to
     * continuously move the fuel token horizontally across the screen.
     */
    @Override
    public void update() {
        updatePosition();
    }

    /**
     * Determines if the fuel token is friendly. Fuel tokens are always considered friendly.
     *
     * @return true, since the fuel token is friendly
     */
    @Override
    public boolean isFriendly() {
        return true;
    }

    /**
     * Handles the destruction of the fuel token when it is no longer needed.
     * The fuel token is removed from the game world when it is taken by the player.
     */
    @Override
    public void takeDamage() {
        ActorManager.getInstance().removeActor(this);
    }

    /**
     * Determines if the fuel token is collectible. Fuel tokens are considered collectible.
     *
     * @return true, since the fuel token is collectible
     */
    @Override
    public boolean isCollectible() {
        return true;
    }
}
