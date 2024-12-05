package com.example.demo.actors.Projectiles.userProjectiles;

import com.example.demo.actors.Projectiles.Projectile;

public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 15;
	private static final int VERTICAL_VELOCITY = 15;
	private static final int HORIZONTAL_VELOCITY = 15;
	private int direction;

	public UserProjectile(double initialXPos, double initialYPos, int rotationAngle) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
		this.direction = rotationAngle; // Set the direction
	}

	@Override
	public void updatePosition() {
		rotateProjectile(direction);
		switch (direction) {
			case -90: // Shoot North

				moveVertically(-VERTICAL_VELOCITY); // Move up
				break;
			case 0: // Shoot East
				moveHorizontally(HORIZONTAL_VELOCITY); // Move left
				break;
			case 90: // Shoot South
				moveVertically(VERTICAL_VELOCITY); // Move down
				break;
			case 180: // Shoot West
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
	public boolean isCollectible() {
		return false;
	}

	@Override
	public void takeDamage() {
		super.takeDamage();
	}
	
}
