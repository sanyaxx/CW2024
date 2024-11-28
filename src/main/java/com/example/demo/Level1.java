package com.example.demo;

public class Level1 extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	public Level1(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	@Override
	protected void checkIfGameOver() {
		System.out.println("Checking game over conditions...");
		if (userIsDestroyed()) {
			System.out.println("User has been destroyed. Game over.");
			loseGame();
		} else if (userHasReachedKillTarget()) {
			System.out.println("User  has reached kill target. Advancing to next level.");
			endLevel();
			LevelManager levelManager = LevelManager.getInstance();
			levelManager.incrementCurrentLevelNumber();
		}
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();

		// Ensure we have at least 3 and at most 5 enemy planes
		int enemiesToSpawn = Math.min(5 - currentNumberOfEnemies, TOTAL_ENEMIES - currentNumberOfEnemies);
		if (currentNumberOfEnemies < 3) {
			enemiesToSpawn = Math.min(3 - currentNumberOfEnemies, TOTAL_ENEMIES - currentNumberOfEnemies);
		}

		for (int i = 0; i < enemiesToSpawn; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);

				// Check for overlapping with existing enemies
				if (!isEnemyPlaneOverlapping(newEnemy)) {
					addEnemyUnit(newEnemy);
				} else {
					// If overlapping, decrement i to try again
					i--;
				}
			}
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

}
