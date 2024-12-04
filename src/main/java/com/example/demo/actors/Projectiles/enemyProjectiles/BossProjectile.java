package com.example.demo.actors.Projectiles.enemyProjectiles;

import com.example.demo.actors.Projectiles.Projectile;

public class BossProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_HEIGHT = 40;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;

	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
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
		return false;
	}

	@Override
	public void takeDamage() {
		super.takeDamage();
	}

	@Override
	public boolean isCollectible() {
		return false;
	}

}
