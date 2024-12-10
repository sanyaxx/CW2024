package com.example.demo.actors.Planes.friendlyPlanes;

import com.example.demo.actors.Projectiles.userProjectiles.UserProjectile;
import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.FighterPlane;
import com.example.demo.controller.AppStage;

public class UserTank extends UserParent {

    private static final String IMAGE_NAME = "userTank.png";
    private static final double INITIAL_X_POSITION = (AppStage.getInstance().getPrimaryStage().getWidth())/2 - 100;
    private static final double INITIAL_Y_POSITION = (AppStage.getInstance().getPrimaryStage().getHeight())/2 - 80;
    private static final int IMAGE_HEIGHT = 100;
    private static int PROJECTILE_X_POSITION = 655;
    private static int PROJECTILE_Y_POSITION = 300;
    private static int rotationAngle = 0;

    public UserTank(int initialHealth, int bulletCount) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth, bulletCount);
    }

    public void faceNorth() {
        rotatePlane(-90);
        rotationAngle = -90;
        PROJECTILE_X_POSITION = (int) (INITIAL_X_POSITION) + 50;
        PROJECTILE_Y_POSITION = (int) (INITIAL_Y_POSITION);
    }

    public void faceSouth() {
        rotatePlane(90);
        rotationAngle = 90;
        PROJECTILE_X_POSITION = (int) (INITIAL_X_POSITION) + 50;
        PROJECTILE_Y_POSITION = (int) (INITIAL_Y_POSITION) + 100;
    }

    public void faceEast() {
        rotatePlane(0);
        rotationAngle = 0;
        PROJECTILE_X_POSITION = (int) (INITIAL_X_POSITION + 100);
        PROJECTILE_Y_POSITION = (int) INITIAL_Y_POSITION + 35;
    }

    public void faceWest() {
        rotatePlane(180);
        rotationAngle = 180;
        PROJECTILE_X_POSITION = (int) (INITIAL_X_POSITION);
        PROJECTILE_Y_POSITION = (int) INITIAL_Y_POSITION + 30;
    }

    @Override
    public void update() {
        updatePosition();
    }

    @Override
    public GameEntity fireProjectile() {
        return new UserProjectile(PROJECTILE_X_POSITION, PROJECTILE_Y_POSITION, rotationAngle);
    }

}
