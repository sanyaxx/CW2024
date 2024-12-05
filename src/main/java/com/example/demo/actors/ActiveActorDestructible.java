package com.example.demo.actors;

import com.example.demo.Updatable;

public abstract class ActiveActorDestructible extends ActiveActor implements Destructible, Collectible, Updatable {

	private boolean isDestroyed;

	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	@Override
	public abstract void updatePosition();

	@Override
	public abstract void update();

	@Override
	public abstract boolean isFriendly();

	@Override
	public abstract void takeDamage();

	@Override
	public abstract boolean isCollectible();

	@Override
	public void destroy() {
		setDestroyed(true);
	}

	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public boolean isDestroyed() {
		return this.isDestroyed;
	}

}
