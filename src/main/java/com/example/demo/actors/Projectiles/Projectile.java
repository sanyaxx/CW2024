package com.example.demo.actors.Projectiles;

import com.example.demo.activityManagers.ActorManager;
import com.example.demo.actors.ActiveActorDestructible;

public abstract class Projectile extends ActiveActorDestructible {

	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	public void rotateProjectile(int angle) {
		this.setRotate(angle);
	}

	@Override
	public void takeDamage() {
		ActorManager.getInstance().removeActor(this);
	}

	@Override
	public abstract void updatePosition();

}
