package com.example.demo.actors.Planes.enemyPlanes;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Projectiles.enemyProjectiles.EnemyProjectile;
import com.example.demo.actors.Planes.FighterPlane;

public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 45;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -50.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 30.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;

	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public GameEntity fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	@Override
	public void update() {
		updatePosition();
	}

	@Override
	public boolean isFriendly() {
		return false;
	}

	@Override
	public boolean isCollectible() {
		return false;
	}

	@Override
	public void takeDamage() {
		super.takeDamage();
	}

}
