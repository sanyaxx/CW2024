package com.example.demo.actors.additionalUnits;

import com.example.demo.activityManagers.ActorManager;
import com.example.demo.actors.GameEntity;

/**
 * Represents a magnet object in the game that can attract certain items (like coins) towards the player.
 * The magnet moves across the screen in different directions, depending on its launch direction.
 * <p>
 * This class extends `GameEntity` and allows the magnet to interact with other game entities by moving
 * in specific directions. It is also collectible and friendly, meaning it does not harm the player.
 * </p>
 */
public class Magnet extends GameEntity {

    /** The image name used to represent the magnet */
    private static final String IMAGE_NAME = "magnet.png";

    /** The height of the magnet's image */
    private static final int IMAGE_HEIGHT = 40;

    /** The horizontal velocity at which the magnet moves */
    private static final int HORIZONTAL_VELOCITY = -8;

    /** The vertical velocity at which the magnet moves */
    private static final int VERTICAL_VELOCITY = 8; // Speed for vertical movement

    /** The direction the magnet is launched from */
    private int direction;

    /**
     * Constructs a `Magnet` instance at the specified position with a given direction.
     *
     * @param initialXPos The initial X-coordinate of the magnet
     * @param initialYPos The initial Y-coordinate of the magnet
     * @param direction The direction in which the magnet moves (0: North, 1: East, 2: South, 3: West)
     */
    public Magnet(double initialXPos, double initialYPos, int direction) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
        this.direction = direction; // Set the direction
    }

    /**
     * Updates the magnet's position by moving it in the specified direction.
     * Depending on the launch direction, the magnet moves horizontally or vertically.
     */
    @Override
    public void updatePosition() {
        switch (direction) {
            case 0: // Launched from North
                moveVertically(VERTICAL_VELOCITY); // Move up
                break;
            case 1: // Launched from East
                moveHorizontally(HORIZONTAL_VELOCITY); // Move left
                break;
            case 2: // Launched from South
                moveVertically(-VERTICAL_VELOCITY); // Move down
                break;
            case 3: // Launched from West
                moveHorizontally(-HORIZONTAL_VELOCITY); // Move left
                break;
            default:
                break;
        }
    }

    /**
     * Updates the magnet's position each frame. This method is called every game frame to ensure the
     * magnet moves according to its launch direction.
     */
    @Override
    public void update() {
        updatePosition();
    }

    /**
     * Determines if the magnet is friendly. Magnets are always considered friendly.
     *
     * @return true, since the magnet is friendly
     */
    @Override
    public boolean isFriendly() {
        return true;
    }

    /**
     * Handles the destruction of the magnet when it is no longer needed.
     * The magnet is removed from the game world when it is taken or destroyed.
     */
    @Override
    public void takeDamage() {
        ActorManager.getInstance().removeActor(this);
    }

    /**
     * Determines if the magnet is collectible. Magnets are considered collectible.
     *
     * @return true, since the magnet is collectible
     */
    @Override
    public boolean isCollectible() {
        return true;
    }
}
