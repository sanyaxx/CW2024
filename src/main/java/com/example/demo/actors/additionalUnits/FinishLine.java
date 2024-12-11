package com.example.demo.actors.additionalUnits;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.FighterPlane;

public class FinishLine extends GameEntity {

    private static final String IMAGE_NAME = "finishLine.png";
    private static final int IMAGE_HEIGHT = 900;
    private static final int HORIZONTAL_VELOCITY = -10;

    public FinishLine(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
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
        return true;
    }

    @Override
    public void takeDamage() {
    }

    @Override
    public boolean isCollectible() {
        return true;
    }

}