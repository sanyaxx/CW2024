package com.example.demo;

public class UserTank extends FighterPlane {

    private static final String IMAGE_NAME = "userPlane.png";
    private static final double INITIAL_X_POSITION = (AppStage.getInstance().getPrimaryStage().getWidth())/2;
    private static final double INITIAL_Y_POSITION = (AppStage.getInstance().getPrimaryStage().getHeight())/2 - 20;
    private static final int IMAGE_HEIGHT = 40;
    private static int PROJECTILE_X_POSITION = 655;
    private static int PROJECTILE_Y_POSITION = 300;
    private static final int PLANE_PORJECTILE_GAP = 105;
    private static int rotationAngle = 0;

    public UserTank(int initialHealth) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
    }

    @Override
    public void updatePosition() {
    }

    public void faceNorth() {
        rotatePlane(-90);
        rotationAngle = -90;
        PROJECTILE_X_POSITION = (int) (INITIAL_X_POSITION);
        PROJECTILE_Y_POSITION = (int) (INITIAL_Y_POSITION + PLANE_PORJECTILE_GAP);
    }

    public void faceSouth() {
        rotatePlane(90);
        rotationAngle = 90;
        PROJECTILE_X_POSITION = (int) (INITIAL_X_POSITION);
        PROJECTILE_Y_POSITION = (int) (INITIAL_Y_POSITION - 2* PLANE_PORJECTILE_GAP);
    }

    public void faceEast() {
        rotatePlane(0);
        rotationAngle = 0;
        PROJECTILE_X_POSITION = (int) (INITIAL_X_POSITION + PLANE_PORJECTILE_GAP);
        PROJECTILE_Y_POSITION = (int) INITIAL_Y_POSITION;
    }

    public void faceWest() {
        rotatePlane(180);
        rotationAngle = 180;
        PROJECTILE_X_POSITION = (int) (INITIAL_X_POSITION - PLANE_PORJECTILE_GAP);
        PROJECTILE_Y_POSITION = (int) INITIAL_Y_POSITION;
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        return new UserProjectile(PROJECTILE_X_POSITION, PROJECTILE_Y_POSITION, rotationAngle);
    }


}