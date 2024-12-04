package com.example.demo.actors.additionalUnits;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.Planes.FighterPlane;

public class FinishLine extends FighterPlane {

    private static final String IMAGE_NAME = "finishLine.png";
    private static final int IMAGE_HEIGHT = 720;
    private static final int HORIZONTAL_VELOCITY = -10;
    private static final int INITIAL_HEALTH = 1;

    public FinishLine(double initialXPos, double initialYPos) {
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

    @Override
    public boolean isFriendly() {
        return true;
    }

    @Override
    public boolean isCollectible() {
        return false;
    }

}