package com.example.demo.levels.Level1;

import com.example.demo.actors.Planes.enemyPlanes.EnemyPlane;
import com.example.demo.levels.LevelParent;
import com.example.demo.levels.LevelView;

/**
 * The {@code Level1} class represents the first level of the game.
 * It extends the {@link LevelParent} class and provides specific functionality for Level 1, such as spawning enemies,
 * determining win/loss conditions, and managing the level's user interface.
 *
 * In this level, the player must reach a kill target to win, and the game ends if the player's health is zero or they run out of bullets.
 * The level features enemy planes as the primary enemy unit.
 */
public class Level1 extends LevelParent {

	/** Background image for Level 1 */
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/level1Background.jpg";

	/** Kill count required to win the level */
	private static final int KILLS_TO_ADVANCE = 10;

	/** Initial health for the player at the start of the level */
	private static final int PLAYER_INITIAL_HEALTH = 5;

	/** Initial bullet count for the player at the start of the level */
	private static final int PLAYER_BULLET_COUNT = 50;

	/**
	 * Constructor to initialize the first level with specified screen dimensions.
	 *
	 * @param screenHeight The height of the game screen.
	 * @param screenWidth The width of the game screen.
	 */
	public Level1(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, PLAYER_BULLET_COUNT);
		initializeLevel(this, getUser());  // Initialize the level and user
	}

	/**
	 * Determines if the player has lost the level.
	 * The level is lost if the player's health is zero or if the player runs out of bullets.
	 *
	 * @return {@code true} if the player has lost, {@code false} otherwise.
	 */
	@Override
	protected boolean hasLevelBeenLost() {
		return getUser().isDestroyed || getUser().getBulletCount() == 0;  // User loss condition based on health or bullets
	}

	/**
	 * Determines if the player has won the level.
	 * The level is won if the player has reached the required kill count to advance to the next level.
	 *
	 * @return {@code true} if the player has won, {@code false} otherwise.
	 */
	@Override
	protected boolean hasLevelBeenWon() {
		return userHasReachedKillTarget();  // Win condition specific to this level (kill count)
	}

	/**
	 * Spawns enemy units in the level. In Level 1, enemy planes are spawned based on certain conditions like spawn probability.
	 */
	@Override
	protected void spawnEnemyUnits() {
		currentNumberOfEnemies += spawnHandler.spawnActors(
				() -> new EnemyPlane(screenWidth, 100 + (Math.random() * (getEnemyMaximumYPosition() - 100))),  // Supplier for new enemy planes
				5,  // Maximum number of enemies to spawn at a time
				0.20,  // Probability of spawning an enemy
				currentNumberOfEnemies,  // Current number of enemies in the level
				10  // Total maximum number of enemies allowed in the level
		);
	}

	/**
	 * Instantiates and returns the {@link LevelView} for this level, which displays information such as the player's health,
	 * bullet count, and coins collected.
	 *
	 * @return A new {@link LevelView} instance for Level 1.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, PLAYER_BULLET_COUNT, getUser().getCoinsCollected());
	}

	/**
	 * Checks if the player has reached the required kill count to win and advance to the next level.
	 *
	 * @return {@code true} if the player has killed the required number of enemies, {@code false} otherwise.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getInLevelKillCount() >= KILLS_TO_ADVANCE;  // Check if the player reached the kill target
	}

	/**
	 * Updates the state of the level. This includes handling the movement of enemies, generating enemy fire,
	 * spawning new enemies, and updating the level view.
	 */
	@Override
	public void update() {
		super.update();  // Call the parent update method to handle common updates
		generateEnemyFire();  // Generate fire from enemy units
		spawnEnemyUnits();  // Spawn more enemies based on the spawn conditions
		updateLevelView();  // Update the level view to reflect the current status of the level (health, bullets, coins)
	}
}
