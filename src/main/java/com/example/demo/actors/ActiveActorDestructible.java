package com.example.demo.actors;

public abstract class ActiveActorDestructible extends ActiveActor implements Destructible, Collectible {

	private boolean isDestroyed;

	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	@Override
	public abstract void updatePosition();

	public abstract void updateActor();

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
