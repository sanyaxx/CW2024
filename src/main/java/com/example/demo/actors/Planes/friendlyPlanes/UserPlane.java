package com.example.demo.actors.Planes.friendlyPlanes;

import com.example.demo.activityManagers.ActorManager;
import com.example.demo.actors.Projectiles.userProjectiles.UserProjectile;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.Planes.FighterPlane;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 40;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double INITIAL_X_POSITION = 80.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 80;
	private static final int VERTICAL_VELOCITY = 8;
	private static int PROJECTILE_X_POSITION = 120;
	private static int PROJECTILE_Y_POSITION_OFFSET = 20;
	private double velocityMultiplier;
	private static int fuel = 10; // Tracks the amount of fuel remaining
	private static int rotationAngle = 0; // Default set to facing East
	private boolean collisionCooldownActive = false;
	private long lastCollisionTime = 0; // Time when the cooldown started
	private static final long COLLISION_COOLDOWN_DURATION = 2_000_000_000L; // 2 seconds in nanoseconds

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
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
	public boolean isFriendly() {
		return true;
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
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
		velocityMultiplier = 0.5;
	}

	public void stop() {
		velocityMultiplier = 0;
	}

	public void decrementFuel() {
		if (fuel > 0) {
			fuel--;
		}
	}

	public int getFuelLeft() {
		return fuel;
	}

	public void resetFuelLeft() {
		fuel = 8;
	}

	public void incrementFuelLeft() {
		fuel += 5;
	}

	@Override
	public boolean isCollectible() {
		return false;
	}

	@Override
	public void takeDamage() {
		if (!collisionCooldownActive) { // Prevent taking damage during cooldown
			decrementHealth();
			if (getHealth() == 0) {
				this.setDestroyed(true);
			}
			startCooldown(); // Start the cooldown when damage is taken
		}
	}

	public void destroyUser() {
		this.setDestroyed(true);
		ActorManager.getInstance().removeActor(this);
	}

	public void reviveUserLife() {
		this.incrementHealth();
		this.setDestroyed(false);
	}

	public void reviveUserFuel() {
		this.resetFuelLeft();
		this.setDestroyed(false);
	}

	public void startCooldown() {
		if (collisionCooldownActive) return;

		collisionCooldownActive = true;
		lastCollisionTime = System.nanoTime();

		// Reset any ongoing fade transition
		setOpacity(1.0);

		FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), this);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.0);
		fadeTransition.setCycleCount(6);
		fadeTransition.setAutoReverse(true);
		fadeTransition.play();

		new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (now - lastCollisionTime >= COLLISION_COOLDOWN_DURATION) {
					endCooldown();
					stop(); // Ensure this AnimationTimer stops
				}
			}
		}.start();
	}

	public void endCooldown() {
		collisionCooldownActive = false;
		this.setOpacity(1.0); // Restore full visibility
	}

	public boolean isCollisionCooldownActive() {
		return collisionCooldownActive;
	}
}