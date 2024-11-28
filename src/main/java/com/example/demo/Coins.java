package com.example.demo;

public class Coins extends FighterPlane {

    private static final String IMAGE_NAME = "coin.png";
    private static final int IMAGE_HEIGHT = 70;
    private static final int HORIZONTAL_VELOCITY = -10;
    private static final int INITIAL_HEALTH = 1;

    public Coins(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    @Override
    public ActiveActorDestructible fireProjectile(){ return null;}

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    @Override
    public void updateActor() {
        updatePosition();
    }
}
