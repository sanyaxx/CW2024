package com.example.demo.actors.Planes.friendlyPlanes;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Projectiles.userProjectiles.UserProjectile;
import com.example.demo.controller.AppStage;

public class UserPlane extends UserParent {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 80;
	private static final double Y_LOWER_BOUND = 700;
	private static final double INITIAL_X_POSITION = 80.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 80;
	private static final int VERTICAL_VELOCITY = 8;
	private static int PROJECTILE_X_POSITION = 120;
	private static int PROJECTILE_Y_POSITION_OFFSET = 20;
	private double velocityMultiplier;
	private static int rotationAngle;

	public UserPlane(int initialHealth, int bulletCount) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth, bulletCount);
		velocityMultiplier = 0;
		rotationAngle = 0; // Default set to facing East
	}

	public void resetPosition() {
		setLayoutX(INITIAL_X_POSITION);
		setLayoutY(INITIAL_Y_POSITION);
	}

	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}
	}

	@Override
	public void update() {
		updatePosition();
	}

	@Override
	public GameEntity fireProjectile() {
		return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET), rotationAngle);
	}

	private boolean isMoving() {
		return velocityMultiplier != 0;
	}

	public void moveUp() {
		velocityMultiplier = -1;
	}

	public void moveDown() {
		velocityMultiplier = 1;
	}

	public void fallDown() {
		velocityMultiplier = 0.7;
	}

	public void stop() {
		velocityMultiplier = 0;
	}

}