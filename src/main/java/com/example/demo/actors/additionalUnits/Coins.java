package com.example.demo.actors.additionalUnits;

import com.example.demo.actors.ActiveActorDestructible;

public class Coins extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "coin.png";
    private static final int IMAGE_HEIGHT = 40;
    private static final int HORIZONTAL_VELOCITY = -10;

    public Coins(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
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
    public boolean isFriendly() {
        return true;
    }

    @Override
    public void takeDamage() {
        this.destroy();

    @Override
    public boolean isCollectible() {
        return true;
    }
}
