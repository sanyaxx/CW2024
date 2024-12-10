package com.example.demo.actors.Planes.friendlyPlanes;

import com.example.demo.activityManagers.ActorManager;
import com.example.demo.activityManagers.LevelManager;
import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.FighterPlane;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class UserParent extends FighterPlane{

    private boolean collisionCooldownActive = false;
    private long lastCollisionTime = 0; // Time when the cooldown started
    private static final long COLLISION_COOLDOWN_DURATION = 2_000_000_000L; // 2 seconds in nanoseconds
    private int fuelCapacity;
    private int bulletCount;
    private int inLevelKillCount;
    private int coinsCollected;

    public UserParent(String imageName, int imageHeight, double initialXPos, double initialYPos, int health, int bulletCount) {
        super(imageName, imageHeight, initialXPos, initialYPos, health);
        this.isDestroyed = false;
        this.bulletCount = bulletCount;
        this.inLevelKillCount = 0;
        this.coinsCollected = 0;
    }

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

    @Override
    public GameEntity fireProjectile() {
        return null;
    }

    @Override
    public void updatePosition() {
    }

    @Override
    public void update() {
    }

    @Override
    public boolean isFriendly() {
        return true;
    }

    @Override
    public boolean isCollectible() {
        return false;
    }

    public void destroyUser() {
        this.isDestroyed = true;
        ActorManager.getInstance().removeActor(this);
    }

    public void reviveUserLife() {
        this.incrementHealth();
        this.isDestroyed = false;
    }

    public void reviveUserFuel() {
        this.resetFuelLeft();
        isDestroyed = false;
    }

    public void resetFuelLeft() {
        this.setFuelCapacity(8);
    }

    public void reviveBulletCount() {
        this.bulletCount =+ 10;
    }

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

    public void endCooldown() {
        this.collisionCooldownActive = false;
        this.setOpacity(1.0); // Restore full visibility
    }

    public void initiateMagnetActivated() {

    }

    public void endMagnetActivated() {

    }

    public boolean isCollisionCooldownActive() {
        return collisionCooldownActive;
    }

    public void setBulletCount(int bulletCount) {
        this.bulletCount = bulletCount;
    }

    public int getBulletCount() {
        return this.bulletCount;
    }

    public void decrementFuel() {
        if (this.fuelCapacity > 0) {
            this.fuelCapacity--;
        }
    }

    public void decrementBulletCount() {
        if (this.bulletCount > 0) {
            this.bulletCount--;
        }
    }

    public int getFuelLeft() {
        return this.fuelCapacity;
    }

    public void incrementFuelLeft() {
        this.fuelCapacity += 5;
    }

    public int getInLevelKillCount() {
        return inLevelKillCount;
    }

    public void incrementKillCount() {
        this.inLevelKillCount++;
    }

    public void incrementCoinsCollected() {
        coinsCollected++;
    }

    public void decrementCoinsCollected() {
        coinsCollected -= 4;
    }

    public void setFuelCapacity(int fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public int getCoinsCollected() {
        return coinsCollected;
    }
}
