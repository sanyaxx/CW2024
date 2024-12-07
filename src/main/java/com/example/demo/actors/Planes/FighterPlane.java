package com.example.demo.actors.Planes;

import com.example.demo.activityManagers.ActorManager;
import com.example.demo.actors.ActiveActorDestructible;

public abstract class FighterPlane extends ActiveActorDestructible {

	private int health;

	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	public abstract ActiveActorDestructible fireProjectile();
	
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.setDestroyed(true);
			ActorManager.getInstance().removeActor(this);
		}
	}

	public void rotatePlane(int angle) {
		this.setRotate(angle);
	}

	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	private boolean healthAtZero() {
		return health == 0;
	}

	public int getHealth() {
		return health;
	}

	public void incrementHealth() {
		this.health++ ;
	}

	public void decrementHealth() {
		this.health-- ;
	}
}
