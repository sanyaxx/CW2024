package com.example.demo.actors.additionalUnits;

import com.example.demo.activityManagers.ActorManager;
import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.friendlyPlanes.UserParent;
import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.actors.Planes.friendlyPlanes.UserTank;
import com.example.demo.controller.AppStage;

public class Coins extends GameEntity {

    private static final String IMAGE_NAME = "coin.png";
    private static final int IMAGE_HEIGHT = 40;
    private static final int HORIZONTAL_VELOCITY = -10;
    private static final double SPEED = 20;

    private boolean magnetActivated; // Initially set to false
    private double userX; // Stores user's X position
    private double userY; // Stores user's Y position
    private final double magnetRadius; // Magnet radius for this coin
    private UserParent userVehicle;

    public Coins(double initialXPos, double initialYPos, double magnetRadius) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
        this.magnetActivated = false;
        this.magnetRadius = magnetRadius;
        this.userVehicle  = ActorManager.getInstance().getUserVehicle();
    }

    @Override
    public void update() {
        updatePosition();
    }

    @Override
    public void updatePosition() {
        if (magnetActivated && isWithinMagnetRadius()) {
            getUserPosition();
            moveTowards();
        } else {
            moveHorizontally(HORIZONTAL_VELOCITY);
        }
    }

    public void getUserPosition() {
        if (userVehicle != null) {
            this.userX = userVehicle.getLayoutX() + userVehicle.getTranslateX(); // Corrected X position
            this.userY = userVehicle.getLayoutY() + userVehicle.getTranslateY(); // Corrected Y position
        }
    }

    public void moveTowards() {
        // Get the current position of this actor (coin)
        double currentX = this.getLayoutX() + this.getTranslateX(); // Corrected X position
        double currentY = this.getLayoutY() + this.getTranslateY(); // Corrected Y position

        // Calculate the difference in positions
        double deltaX = userX - currentX;
        double deltaY = userY - currentY;

        // Calculate the distance (magnitude of the vector)
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance > 0) {
            // Normalize the direction vector
            double directionX = deltaX / distance;
            double directionY = deltaY / distance;

            // Move the coin incrementally towards the user
            double stepX = directionX * SPEED;
            double stepY = directionY * SPEED;

            // Update the position incrementally
            this.setTranslateX(currentX + stepX - this.getLayoutX());
            this.setTranslateY(currentY + stepY - this.getLayoutY());
        }
    }

    // Check if the coin is within the magnet radius
    public boolean isWithinMagnetRadius() {
        if (userVehicle instanceof UserPlane) {
            double deltaX = userX - (this.getLayoutX() + this.getTranslateX()); // Coin's current position (X)
            double deltaY = userY - (this.getLayoutY() + this.getTranslateY()); // Coin's current position (Y)

            // Calculate the distance between the coin and the user
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            // Return true if the distance is within the magnet radius
            return distance <= magnetRadius;
        }
        return true; // UserTank
    }

    @Override
    public boolean isFriendly() {
        return true;
    }

    @Override
    public void takeDamage() {
        ActorManager.getInstance().removeActor(this);
    }

    @Override
    public boolean isCollectible() {
        return true;
    }

    public void setMagnetActivated(boolean magnetActivated) {
        this.magnetActivated = magnetActivated;
    }
}
