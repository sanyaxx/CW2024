package com.example.demo.levels.Level1;

import com.example.demo.actors.Planes.enemyPlanes.EnemyPlane;
import com.example.demo.levels.LevelParent;
import com.example.demo.levels.LevelView;

public class Level1 extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/level1Background.jpg";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	public Level1(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

		this.bulletCount = 30;
		userStatsManager.setBulletCount(30);

		initializeLevel(this, getUser());
	}

	@Override
	protected boolean hasLevelBeenLost() {
		return userIsDestroyed() || bulletCount == 0; // User loss condition
	}

	@Override
	protected boolean hasLevelBeenWon() {
		return userHasReachedKillTarget(); // Win condition specific to this level
	}

	@Override
	protected void spawnEnemyUnits() {
		currentNumberOfEnemies += spawnHandler.spawnActors(
			() -> new EnemyPlane(screenWidth, Math.random() * getEnemyMaximumYPosition()), // Supplier for new enemies
			5, // Maximum spawn at a time
			0.20, // Spawn probability
			currentNumberOfEnemies, // Current count
			10 // Total allowed
		);
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, bulletCount);
	}

	private boolean userHasReachedKillTarget() {
		return killCount >= KILLS_TO_ADVANCE;
	}

	@Override
	public void update() {
		super.update();
		generateEnemyFire();
		spawnEnemyUnits();
		updateLevelView();
	}
}
