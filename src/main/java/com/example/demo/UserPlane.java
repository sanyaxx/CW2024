package com.example.demo;

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
	private int velocityMultiplier;
	private int numberOfKills = 0;
	private static int score = 0; // Tracks the number of coins collected
	private static int rotationAngle = 0; // Default set to facing East
	// Protected array to hold level scores
	protected int[] levelScores;

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;

		// Initialize the level scores array for 4 levels (for example)
		levelScores = new int[4]; // Adjust size according to the number of levels
	}
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
	public void updateActor() {
		updatePosition();
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
}