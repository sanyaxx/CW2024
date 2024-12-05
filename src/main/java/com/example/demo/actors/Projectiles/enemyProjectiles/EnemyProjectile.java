package com.example.demo.actors.Projectiles.enemyProjectiles;

import com.example.demo.actors.Projectiles.Projectile;

public class EnemyProjectile extends Projectile {

	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 20;
	private static final int HORIZONTAL_VELOCITY = -10;

	public EnemyProjectile(double initialXPos, double initialYPos) {
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
