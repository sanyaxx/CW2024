package com.example.demo.actors.additionalUnits;

import com.example.demo.actors.ActiveActorDestructible;

public class Obstacle extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "obstacle.png";
    private static final int IMAGE_HEIGHT = 300;
    private static final int HORIZONTAL_VELOCITY = -5;

    public Obstacle(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    public static int getImageHeight() {
        return IMAGE_HEIGHT;
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    @Override
    public void update() {
        updatePosition();
    }

    @Override
    public boolean isFriendly() {
        return false;
    }

    @Override
    public void takeDamage() {
    }

    @Override
    public boolean isCollectible() {
        return false;
    }

}
