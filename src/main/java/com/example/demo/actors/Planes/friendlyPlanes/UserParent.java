package com.example.demo.actors.Planes.friendlyPlanes;

import com.example.demo.activityManagers.ActorManager;
import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.FighterPlane;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;

/**
 * Represents the base class for user-controlled planes. This class contains common functionality
 * for user planes, such as managing health, fuel, bullets, collision cooldown, and interactions
 * with game mechanics like magnet activation and the ability to revive. It serves as a parent class
 * for different user plane types and provides common methods for taking damage, firing projectiles,
 * updating state, and handling various power-ups or interactions.
 */
public class UserParent extends FighterPlane {

    /** Indicates whether the collision cooldown is currently active */
    private boolean collisionCooldownActive = false;

    /** Time when the collision cooldown was initiated, stored in nanoseconds */
    private long lastCollisionTime = 0;

    /** Duration of the collision cooldown (2 seconds in nanoseconds) */
    private static final long COLLISION_COOLDOWN_DURATION = 2_000_000_000L;

    /** The fuel capacity of the user plane */
    private int fuelCapacity;

    /** The count of bullets the user plane has available */
    private int bulletCount;

    /** The count of kills made by the user plane during the current level */
    private int inLevelKillCount;

    /** The count of coins collected by the user plane */
    private int coinsCollected;

    /**
     * Constructs a UserParent instance with the specified attributes.
     *
     * @param imageName The image file name representing the user plane
     * @param imageHeight The height of the user plane's image in pixels
     * @param initialXPos The initial x-coordinate of the user plane
     * @param initialYPos The initial y-coordinate of the user plane
     * @param health The initial health of the user plane
     * @param bulletCount The initial count of bullets available to the user plane
     */
    public UserParent(String imageName, int imageHeight, double initialXPos, double initialYPos, int health, int bulletCount) {
        super(imageName, imageHeight, initialXPos, initialYPos, health);
        this.isDestroyed = false;
        this.bulletCount = bulletCount;
        this.inLevelKillCount = 0;
        this.coinsCollected = 0;
    }

    /**
     * Decreases the health of the user plane when it takes damage.
     * If the health reaches zero, the plane is marked as destroyed.
     * Collision cooldown is started to prevent further damage during the cooldown period.
     */
    @Override
    public void takeDamage() {
        if (!collisionCooldownActive) { // Prevent taking damage during cooldown
            decrementHealth();
            if (getHealth() == 0) {
                this.isDestroyed = true;
            }
            startCooldown(); // Start the cooldown when damage is taken
        }
    }

    /**
     * The user plane does not fire projectiles from this base class. This method is overridden
     * by subclasses if projectile behavior is required.
     *
     * @return null, as no projectiles are fired from the user plane
     */
    @Override
    public GameEntity fireProjectile() {
        return null;
    }

    /**
     * The method is left empty in the base class, but it should be overridden by subclasses
     * to implement specific movement behavior.
     */
    @Override
    public void updatePosition() {
    }

    /**
     * The method is left empty in the base class, but it should be overridden by subclasses
     * to implement specific update behavior.
     */
    @Override
    public void update() {
    }

    /**
     * Returns whether the user plane is friendly.
     *
     * @return true, as the user plane is always friendly
     */
    @Override
    public boolean isFriendly() {
        return true;
    }

    /**
     * Determines if the user plane is collectible. The base user plane is never collectible.
     *
     * @return false, as the user plane is not collectible
     */
    @Override
    public boolean isCollectible() {
        return false;
    }

    /**
     * Destroys the user plane and removes it from the actor manager, effectively
     * marking the plane as no longer active in the game.
     */
    public void destroyUser() {
        this.isDestroyed = true;
        ActorManager.getInstance().removeActor(this);
    }

    /**
     * Revives the user plane by incrementing its health, making it active again.
     */
    public void reviveUserLife() {
        this.incrementHealth();
        this.isDestroyed = false;
    }

    /**
     * Revives the user plane's fuel to its full capacity.
     */
    public void reviveUserFuel() {
        this.resetFuelLeft();
        isDestroyed = false;
    }

    /**
     * Resets the fuel capacity of the user plane to a default value.
     */
    public void resetFuelLeft() {
        this.setFuelCapacity(8);
    }

    /**
     * Revives the user plane's bullet count by adding 10 bullets.
     */
    public void reviveBulletCount() {
        this.bulletCount += 10; // Corrected the operator from += to proper increment
    }

    /**
     * Starts the collision cooldown by setting the active flag and initiating a fade transition.
     * The plane will temporarily become invisible during the cooldown period.
     */
    public void startCooldown() {
        if (collisionCooldownActive) return;

        collisionCooldownActive = true;
        lastCollisionTime = System.nanoTime();

        // Reset any ongoing fade transition
        setOpacity(1.0);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), this);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(6);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastCollisionTime >= COLLISION_COOLDOWN_DURATION) {
                    endCooldown();
                    stop(); // Ensure this AnimationTimer stops
                }
            }
        }.start();
    }

    /**
     * Ends the collision cooldown, restoring the user plane's opacity and resetting the cooldown flag.
     */
    public void endCooldown() {
        this.collisionCooldownActive = false;
        this.setOpacity(1.0); // Restore full visibility
    }

    /**
     * Initiates a magnet activation effect with a pulsing scale and fade effect to indicate
     * that the user plane's magnet power-up is active.
     */
    public void initiateMagnetActivated() {
        // Create a pulsing effect for magnet activation
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), this);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setCycleCount(4);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();

        // Optionally, add a fade effect as well
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), this);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.8);
        fadeTransition.setCycleCount(4);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }

    /**
     * Ends the magnet activation effect, restoring the user plane's opacity.
     */
    public void endMagnetActivated() {
        this.setOpacity(1.0); // Restore full visibility if faded
    }

    /**
     * Returns whether the collision cooldown is currently active.
     *
     * @return true if the collision cooldown is active, false otherwise
     */
    public boolean isCollisionCooldownActive() {
        return collisionCooldownActive;
    }

    /**
     * Retrieves the current bullet count of the user plane.
     *
     * @return the number of bullets available to the user plane
     */
    public int getBulletCount() {
        return this.bulletCount;
    }

    /**
     * Decrements the fuel of the user plane by 1, ensuring it doesn't go below 0.
     */
    public void decrementFuel() {
        if (this.fuelCapacity > 0) {
            this.fuelCapacity--;
        }
    }

    /**
     * Decrements the bullet count of the user plane by 1, ensuring it doesn't go below 0.
     */
    public void decrementBulletCount() {
        if (this.bulletCount > 0) {
            this.bulletCount--;
        }
    }

    /**
     * Retrieves the amount of fuel left for the user plane.
     *
     * @return the current fuel capacity of the user plane
     */
    public int getFuelLeft() {
        return this.fuelCapacity;
    }

    /**
     * Increments the user plane's fuel by 5.
     */
    public void incrementFuelLeft() {
        this.fuelCapacity += 5;
    }

    /**
     * Retrieves the kill count of the user plane during the current level.
     *
     * @return the number of kills made during the level
     */
    public int getInLevelKillCount() {
        return inLevelKillCount;
    }

    /**
     * Increments the kill count for the user plane.
     */
    public void incrementKillCount() {
        this.inLevelKillCount++;
    }

    /**
     * Increments the coins collected by the user plane by 1.
     */
    public void incrementCoinsCollected() {
        coinsCollected++;
    }

    /**
     * Decrements the coins collected by the user plane by 4.
     */
    public void decrementCoinsCollected() {
        coinsCollected -= 4;
    }

    /**
     * Sets the fuel capacity for the user plane.
     *
     * @param fuelCapacity the new fuel capacity to set
     */
    public void setFuelCapacity(int fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    /**
     * Retrieves the number of coins collected by the user plane.
     *
     * @return the total number of coins collected
     */
    public int getCoinsCollected() {
        return coinsCollected;
    }
}
