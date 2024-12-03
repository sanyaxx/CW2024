package com.example.demo;

public class EnemyRocket extends FighterPlane{

    private static final String IMAGE_NAME = "enemyRocket.png";
    private static final int IMAGE_HEIGHT = 80;
    private static final int INITIAL_HEALTH = 1;
    private static final double FIRE_RATE = .01;
    private static final int HORIZONTAL_VELOCITY = -10;
    private static final int VERTICAL_VELOCITY = 10; // Speed for vertical movement
    private int direction;

    public EnemyRocket(double initialXPos, double initialYPos, int direction) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
        this.direction = direction; // Set the direction
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        return null;
    }

    @Override
    public void takeDamage() {
        super.takeDamage();
//        levelView.updateWinningParameterDisplay(getHealth());
    }

    @Override
    public void updatePosition() {
        switch (direction) {
            case 0: // Launched from North
                rotatePlane(90);
                moveVertically(VERTICAL_VELOCITY); // Move up
                break;
            case 1: // Launched from East
                rotatePlane(180);
                moveHorizontally(HORIZONTAL_VELOCITY); // Move left
                break;
            case 2: // Launched from South
                rotatePlane(-90);
                moveVertically(-VERTICAL_VELOCITY); // Move down
                break;
            case 3: // Launched from West
                rotatePlane(0);
                moveHorizontally(-HORIZONTAL_VELOCITY); // Move left
                break;
            default:
                break;
        }
    }

    @Override
    public void updateActor() {
        updatePosition();
    }
}
