package com.example.demo.actors;

public abstract class GameEntity extends ActiveActor implements Collidable, Collectible, Updatable {

	public boolean isDestroyed;

	public GameEntity(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.isDestroyed = false;
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

	public void setPosition(double x, double y) {
		this.setLayoutX(x);
		this.setLayoutY(y);
	}
}
