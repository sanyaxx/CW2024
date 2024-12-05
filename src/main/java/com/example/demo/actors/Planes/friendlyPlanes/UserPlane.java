package com.example.demo.actors.Planes.friendlyPlanes;

import com.example.demo.actors.Projectiles.userProjectiles.UserProjectile;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.Planes.FighterPlane;
import javafx.animation.Animation;
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
	private int numberOfKills = 0;
	private static int score = 0; // Tracks the number of coins collected
	private static int fuel = 10; // Tracks the amount of fuel remaining
	private static int rotationAngle = 0; // Default set to facing East
	private Animation scaleTransition;

	// Protected array to hold level scores
	protected int[] levelScores;

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;

		// Initialize the level scores array for 4 levels (for example)
		levelScores = new int[4]; // Adjust size according to the number of levels
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

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

	public int getScore() {
		return score;
	}

	public void incrementScore() {
		score++;
	}

	public void decrementFuel() {
		fuel--;
	}

	public int getFuelLeft() {
		return fuel;
	}

	public void setFuelLeft() {
		fuel = 10;
	}

	public void incrementFuel() {
		fuel = fuel + 5;
	}

	public void startCooldown() {
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), this);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.0);
		fadeTransition.setCycleCount(6); // Flash 6 times
		fadeTransition.setAutoReverse(true);
		fadeTransition.play();
	}

	public void endCooldown() {
		this.setOpacity(1.0); // Ensure it's fully visible after cooldown
	}

	// Method to set the score for a specific level
	public void setLevelScore(int level, int score) {
		if (level >= 0 && level <= levelScores.length) {
			levelScores[level - 1] = score;
		} else {
			throw new IndexOutOfBoundsException("Invalid level index: " + level);
		}
	}

	// Method to get the score for a specific level
	public int getLevelScore(int level) {
		if (level >= 0 && level <= levelScores.length) {
			return levelScores[level];
		} else {
			throw new IndexOutOfBoundsException("Invalid level index: " + level);
		}
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