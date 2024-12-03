package com.example.demo;

public class Obstacle extends ActiveActorDestructible{

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
    public void updateActor() {
        updatePosition();
    }

    @Override
    public void takeDamage() {
    }

}
