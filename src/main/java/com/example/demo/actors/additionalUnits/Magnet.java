package com.example.demo.actors.additionalUnits;

import com.example.demo.activityManagers.ActorManager;
import com.example.demo.actors.GameEntity;

public class Magnet extends GameEntity {

    private static final String IMAGE_NAME = "magnet.png";
    private static final int IMAGE_HEIGHT = 40;
    private static final int HORIZONTAL_VELOCITY = -8;
    private static final int VERTICAL_VELOCITY = 8; // Speed for vertical movement
    private int direction;

    public Magnet(double initialXPos, double initialYPos, int direction) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
        this.direction = direction; // Set the direction
    }

    @Override
    public void updatePosition() {
        switch (direction) {
            case 0: // Launched from North
                moveVertically(VERTICAL_VELOCITY); // Move up
                break;
            case 1: // Launched from East
                moveHorizontally(HORIZONTAL_VELOCITY); // Move left
                break;
            case 2: // Launched from South
                moveVertically(-VERTICAL_VELOCITY); // Move down
                break;
            case 3: // Launched from West
                moveHorizontally(-HORIZONTAL_VELOCITY); // Move left
                break;
            default:
                break;
        }
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
        ActorManager.getInstance().removeActor(this);
    }

    @Override
    public boolean isCollectible() {
        return true;
    }


}
