package com.example.demo.actors.additionalUnits;

import com.example.demo.activityManagers.ActorManager;
import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.friendlyPlanes.UserParent;
import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.controller.AppStage;

/**
 * Represents a coin in the game that can be collected by the player. The coin has the ability to move towards
 * the player's vehicle when a magnet power-up is activated, with the radius of attraction being adjustable.
 * <p>
 * This class handles the coin's behavior in the game, including its movement and collection. When the magnet is
 * activated, the coin will attempt to move towards the player's vehicle if it is within the specified magnet radius.
 * </p>
 * <p>
 * The class extends `GameEntity`, allowing it to integrate with the general entity system of the game and handle
 * basic functionalities like position management and collision handling.
 * </p>
 */
public class Coins extends GameEntity {

    /** The name of the image used to represent the coin */
    private static final String IMAGE_NAME = "coin.png";

    /** The height of the coin's image */
    private static final int IMAGE_HEIGHT = 40;

    /** Horizontal velocity for the coin's movement when the magnet is not activated */
    private static final int HORIZONTAL_VELOCITY = -10;

    /** Speed at which the coin moves towards the player when the magnet is activated */
    private static final double SPEED = 20;

    /** Indicates whether the magnet is activated (initially false) */
    private boolean magnetActivated;

    /** Stores the current X position of the user vehicle (player's plane or tank) */
    private double userX;

    /** Stores the current Y position of the user vehicle */
    private double userY;

    /** The radius within which the coin will be attracted to the user's vehicle */
    private final double magnetRadius;

    /** Reference to the user's vehicle (either a plane or a tank) */
    private UserParent userVehicle;

    /**
     * Constructs a `Coins` instance at the specified position with a specified magnet radius.
     *
     * @param initialXPos The initial X-coordinate of the coin
     * @param initialYPos The initial Y-coordinate of the coin
     * @param magnetRadius The radius within which the coin will be attracted to the user
     */
    public Coins(double initialXPos, double initialYPos, double magnetRadius) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
        this.magnetActivated = false;
        this.magnetRadius = magnetRadius;
        this.userVehicle = ActorManager.getInstance().getUserVehicle();
    }

    /**
     * Updates the position of the coin. If the magnet is activated and the coin is within the attraction radius,
     * it will move towards the user. Otherwise, it moves horizontally with a fixed velocity.
     */
    @Override
    public void update() {
        updatePosition();
    }

    /**
     * Updates the position of the coin. If the magnet is activated and the coin is within the magnet radius,
     * it moves towards the user's vehicle. Otherwise, it moves horizontally with a fixed velocity.
     */
    @Override
    public void updatePosition() {
        if (magnetActivated && isWithinMagnetRadius()) {
            getUserPosition();
            moveTowards();
        } else {
            moveHorizontally(HORIZONTAL_VELOCITY);
        }
    }

    /**
     * Retrieves the current position of the user's vehicle (plane or tank).
     */
    public void getUserPosition() {
        if (userVehicle != null) {
            this.userX = userVehicle.getLayoutX() + userVehicle.getTranslateX();
            this.userY = userVehicle.getLayoutY() + userVehicle.getTranslateY();
        }
    }

    /**
     * Moves the coin towards the user's vehicle. The coin will incrementally move in the direction of the user,
     * taking into account the current position and the desired direction.
     */
    public void moveTowards() {
        double currentX = this.getLayoutX() + this.getTranslateX();
        double currentY = this.getLayoutY() + this.getTranslateY();

        // Calculate the direction vector from the coin to the user
        double deltaX = userX - currentX;
        double deltaY = userY - currentY;

        // Calculate the distance between the coin and the user
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance > 0) {
            // Normalize the direction vector and calculate the movement step
            double directionX = deltaX / distance;
            double directionY = deltaY / distance;

            double stepX = directionX * SPEED;
            double stepY = directionY * SPEED;

            // Update the position of the coin incrementally
            this.setTranslateX(currentX + stepX - this.getLayoutX());
            this.setTranslateY(currentY + stepY - this.getLayoutY());
        }
    }

    /**
     * Checks if the coin is within the magnet radius of the user's vehicle.
     *
     * @return true if the coin is within the magnet radius, false otherwise
     */
    public boolean isWithinMagnetRadius() {
        if (userVehicle instanceof UserPlane) {
            // For planes, return true if the coin is within the width of the screen
            return (this.getLayoutX() + this.getTranslateX()) <= AppStage.getInstance().getPrimaryStage().getWidth();
        }
        return true; // For the user tank, all coins on screen are attracted
    }

    /**
     * Determines if the coin is friendly. Coins are always considered friendly.
     *
     * @return true, since the coin is friendly
     */
    @Override
    public boolean isFriendly() {
        return true;
    }

    /**
     * Removes the coin from the game. This method is called when the coin is destroyed.
     */
    @Override
    public void takeDamage() {
        ActorManager.getInstance().removeActor(this);
    }

    /**
     * Determines if the coin can be collected by the user.
     *
     * @return true, since coins are always collectible
     */
    @Override
    public boolean isCollectible() {
        return true;
    }

    /**
     * Activates or deactivates the magnet effect for this coin.
     *
     * @param magnetActivated true to activate the magnet effect, false to deactivate
     */
    public void setMagnetActivated(boolean magnetActivated) {
        this.magnetActivated = magnetActivated;
    }
}
