package com.example.demo.actors.Planes.friendlyPlanes;

import com.example.demo.actors.Projectiles.userProjectiles.UserProjectile;
import com.example.demo.actors.GameEntity;
import com.example.demo.controller.AppStage;

/**
 * Represents a specific type of user-controlled plane, the tank. This class extends the `UserParent`
 * class and overrides functionality to implement unique behavior, such as facing different directions
 * (north, south, east, west) and firing projectiles accordingly. The tank has distinct image properties
 * and starting positions, providing specialized movement and attack patterns.
 */
public class UserTank extends UserParent {

    /** The image name for the user tank */
    private static final String IMAGE_NAME = "userTank.png";

    /** The initial x-coordinate position of the tank */
    private static final double INITIAL_X_POSITION = (AppStage.getInstance().getPrimaryStage().getWidth()) / 2 - 100;

    /** The initial y-coordinate position of the tank */
    private static final double INITIAL_Y_POSITION = (AppStage.getInstance().getPrimaryStage().getHeight()) / 2 - 80;

    /** The height of the tank's image */
    private static final int IMAGE_HEIGHT = 100;

    /** The x-coordinate position for projectile firing */
    private static int PROJECTILE_X_POSITION = 655;

    /** The y-coordinate position for projectile firing */
    private static int PROJECTILE_Y_POSITION = 300;

    /** The current rotation angle of the tank */
    private static int rotationAngle = 0;

    /**
     * Constructs a UserTank instance with the specified health and bullet count.
     * This tank will be positioned at the center of the screen, and its attributes
     * will be initialized with the provided values.
     *
     * @param initialHealth The initial health of the user tank
     * @param bulletCount The initial count of bullets available to the user tank
     */
    public UserTank(int initialHealth, int bulletCount) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth, bulletCount);
    }

    /**
     * Rotates the tank to face north (upwards) and adjusts the projectile firing position accordingly.
     */
    public void faceNorth() {
        rotatePlane(-90);
        rotationAngle = -90;
        PROJECTILE_X_POSITION = (int) (INITIAL_X_POSITION) + 50;
        PROJECTILE_Y_POSITION = (int) (INITIAL_Y_POSITION);
    }

    /**
     * Rotates the tank to face south (downwards) and adjusts the projectile firing position accordingly.
     */
    public void faceSouth() {
        rotatePlane(90);
        rotationAngle = 90;
        PROJECTILE_X_POSITION = (int) (INITIAL_X_POSITION) + 50;
        PROJECTILE_Y_POSITION = (int) (INITIAL_Y_POSITION) + 100;
    }

    /**
     * Rotates the tank to face east (right) and adjusts the projectile firing position accordingly.
     */
    public void faceEast() {
        rotatePlane(0);
        rotationAngle = 0;
        PROJECTILE_X_POSITION = (int) (INITIAL_X_POSITION + 100);
        PROJECTILE_Y_POSITION = (int) INITIAL_Y_POSITION + 35;
    }

    /**
     * Rotates the tank to face west (left) and adjusts the projectile firing position accordingly.
     */
    public void faceWest() {
        rotatePlane(180);
        rotationAngle = 180;
        PROJECTILE_X_POSITION = (int) (INITIAL_X_POSITION);
        PROJECTILE_Y_POSITION = (int) INITIAL_Y_POSITION + 30;
    }

    /**
     * Updates the position of the user tank. This method is called each frame to ensure that
     * the tank's position and state are kept in sync.
     */
    @Override
    public void update() {
        updatePosition();
    }

    /**
     * Fires a projectile from the user tank, creating a new `UserProjectile` entity based on
     * the current firing direction and position.
     *
     * @return A new instance of the `UserProjectile` class fired from the user tank
     */
    @Override
    public GameEntity fireProjectile() {
        return new UserProjectile(PROJECTILE_X_POSITION, PROJECTILE_Y_POSITION, rotationAngle);
    }
}
