package com.example.demo.actors.additionalUnits;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.FighterPlane;

/**
 * Represents a finish line in the game that moves horizontally across the screen.
 * The finish line is used as an end goal, often marking the completion of a level
 * or the player’s objective in the game.
 * <p>
 * This class extends `GameEntity`, allowing the finish line to be part of the game world
 * and integrate with the general entity system. It continuously moves horizontally towards
 * the left, simulating the finish line moving past the player.
 * </p>
 */
public class FinishLine extends GameEntity {

    /** The image name used to represent the finish line */
    private static final String IMAGE_NAME = "finishLine.png";

    /** The height of the finish line's image */
    private static final int IMAGE_HEIGHT = 900;

    /** The horizontal velocity at which the finish line moves */
    private static final int HORIZONTAL_VELOCITY = -10;

    /**
     * Constructs a `FinishLine` instance at the specified position.
     *
     * @param initialXPos The initial X-coordinate of the finish line
     * @param initialYPos The initial Y-coordinate of the finish line
     */
    public FinishLine(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    /**
     * Updates the position of the finish line by moving it horizontally.
     * This simulates the finish line moving across the screen towards the player.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Updates the finish line’s position. This method is called every game frame to
     * continuously move the finish line horizontally across the screen.
     */
    @Override
    public void update() {
        updatePosition();
    }

    /**
     * Determines if the finish line is friendly. Finish lines are always considered friendly.
     *
     * @return true, since the finish line is friendly
     */
    @Override
    public boolean isFriendly() {
        return true;
    }

    /**
     * Does nothing because finish lines cannot take damage.
     */
    @Override
    public void takeDamage() {
        // No damage handling for finish line
    }

    /**
     * Determines if the finish line is collectible. Finish lines are not collectible.
     *
     * @return false, since the finish line is not collectible
     */
    @Override
    public boolean isCollectible() {
        return false;
    }
}
