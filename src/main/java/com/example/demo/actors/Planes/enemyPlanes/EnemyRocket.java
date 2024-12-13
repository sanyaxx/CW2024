package com.example.demo.actors.Planes.enemyPlanes;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.FighterPlane;

/**
 * Represents an enemy rocket that moves in a specified direction (North, East, South, West).
 * This class extends the FighterPlane class and adds specialized movement behavior based on the
 * direction it is launched. The rocket does not fire projectiles and has a fixed speed.
 */
public class EnemyRocket extends FighterPlane {

    /** The image file name representing the enemy rocket */
    private static final String IMAGE_NAME = "enemyRocket.png";

    /** The height of the enemy rocket in pixels */
    private static final int IMAGE_HEIGHT = 80;

    /** The initial health of the enemy rocket */
    private static final int INITIAL_HEALTH = 1;

    /** The fire rate of the enemy rocket, but not used in this class as the rocket doesn't fire projectiles */
    private static final double FIRE_RATE = .01;

    /** The horizontal velocity of the rocket (for horizontal movement) */
    private static final int HORIZONTAL_VELOCITY = -10;

    /** The vertical velocity of the rocket (for vertical movement) */
    private static final int VERTICAL_VELOCITY = 10;

    /** The direction in which the rocket moves (0 = North, 1 = East, 2 = South, 3 = West) */
    private int direction;

    /**
     * Constructs an EnemyRocket instance with an initial position and movement direction.
     *
     * @param initialXPos The initial x-position of the rocket
     * @param initialYPos The initial y-position of the rocket
     * @param direction The direction the rocket will travel (0 = North, 1 = East, 2 = South, 3 = West)
     */
    public EnemyRocket(double initialXPos, double initialYPos, int direction) {
        // Initializes the base class (FighterPlane) with image, height, position, and health
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
        this.direction = direction; // Sets the direction for the rocket's movement
    }

    /**
     * The rocket does not fire projectiles, so this method returns null.
     *
     * @return null, as the enemy rocket does not fire any projectiles
     */
    @Override
    public GameEntity fireProjectile() {
        return null;
    }

    /**
     * Processes the damage taken by the rocket, reducing its health.
     * Calls the parent class's takeDamage() method to handle health reduction.
     */
    @Override
    public void takeDamage() {
        super.takeDamage();
    }

    /**
     * Updates the position of the enemy rocket based on its direction.
     * - North (0): Moves vertically upwards.
     * - East (1): Moves horizontally left.
     * - South (2): Moves vertically downwards.
     * - West (3): Moves horizontally left (from the opposite side).
     *
     * The rocket rotates based on its movement direction before moving.
     */
    @Override
    public void updatePosition() {
        switch (direction) {
            case 0: // Launched from North
                rotatePlane(90); // Rotate to face upwards
                moveVertically(VERTICAL_VELOCITY); // Move upwards
                break;
            case 1: // Launched from East
                rotatePlane(180); // Rotate to face left
                moveHorizontally(HORIZONTAL_VELOCITY); // Move left
                break;
            case 2: // Launched from South
                rotatePlane(-90); // Rotate to face downwards
                moveVertically(-VERTICAL_VELOCITY); // Move downwards
                break;
            case 3: // Launched from West
                rotatePlane(0); // Rotate to face right
                moveHorizontally(-HORIZONTAL_VELOCITY); // Move left
                break;
            default:
                break;
        }
    }

    /**
     * Updates the state of the enemy rocket, calling the updatePosition() method to move it.
     * This method is called every frame to ensure that the rocket moves in the correct direction.
     */
    @Override
    public void update() {
        updatePosition();
    }

    /**
     * Determines if the enemy rocket is friendly. This method always returns false,
     * as the rocket is an enemy in the game.
     *
     * @return false, as the rocket is not friendly
     */
    @Override
    public boolean isFriendly() {
        return false;
    }

    /**
     * Determines if the enemy rocket is collectible. This method always returns false,
     * as the rocket is not collectible.
     *
     * @return false, as the rocket is not collectible
     */
    @Override
    public boolean isCollectible() {
        return false;
    }
}
