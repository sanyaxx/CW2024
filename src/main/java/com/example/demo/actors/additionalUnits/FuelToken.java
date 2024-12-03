package com.example.demo.actors.additionalUnits;

import com.example.demo.actors.ActiveActorDestructible;

public class FuelToken extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "fuel.png";
    private static final int IMAGE_HEIGHT = 55;
    private static final int HORIZONTAL_VELOCITY = -8;

    public FuelToken(double initialXPos, double initialYPos) {
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
    public void takeDamage() {
        this.destroy();
    }
}
