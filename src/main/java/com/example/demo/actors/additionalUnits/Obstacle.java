package com.example.demo.actors.additionalUnits;

import com.example.demo.activityManagers.ActorManager;
import com.example.demo.actors.GameEntity;

public class Obstacle extends GameEntity {

    private static final String IMAGE_NAME = "obstacle.png";
    private static final int IMAGE_HEIGHT = 300;
    private static final int HORIZONTAL_VELOCITY = -10;

    public Obstacle(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    public static int getDimensions() {
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

    public void destroy() {
        ActorManager.getInstance().removeActor(this);
    }

    @Override
    public boolean isCollectible() {
        return false;
    }

}
