package com.example.demo.levels.Level2;

import com.example.demo.actors.Planes.enemyPlanes.Boss;
import com.example.demo.levels.LevelParent;
import com.example.demo.levels.LevelView;

/**
 * The {@code Level2} class represents the second level of the game. It introduces a boss enemy with unique behavior and custom win/loss conditions.
 * This class extends {@link LevelParent} and overrides methods to implement specific gameplay features for this level,
 * including spawning the boss, handling the boss's attacks, and updating the user interface with level-specific information.
 */
public class Level2 extends LevelParent {

	/** Background image for Level 2 */
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/level2Background.jpg";

	/** Initial health for the player in this level */
	private static final int PLAYER_INITIAL_HEALTH = 5;

	/** Initial bullet count for the player in this level */
	private static final int PLAYER_BULLET_COUNT = 100;

	/** The boss enemy specific to Level 2 */
	private final Boss boss;

	/** The health of the boss used to track the boss's status */
	private final int bossHealth;

	/** The custom level view specific to Level 2 */
	private LevelViewLevelTwo levelView;

	/**
	 * Constructor to initialize {@code Level2} with specific parameters like screen size, background image, player health,
	 * bullet count, and the boss's health. This constructor also initializes the game level, sets up the boss, and prepares the environment.
	 *
	 * @param screenHeight The height of the screen for this level.
	 * @param screenWidth The width of the screen for this level.
	 */
	public Level2(double screenHeight, double screenWidth) {
		// Initialize the parent class with necessary parameters
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, PLAYER_BULLET_COUNT);

		// Initialize the boss enemy and set its health
		boss = new Boss(levelView);
		bossHealth = boss.getHealth();

		// Initialize the level with the player
		initializeLevel(this, getUser());
	}

	/**
	 * Determines if the player has lost the level.
	 * The level is lost if the player is destroyed or runs out of bullets.
	 *
	 * @return {@code true} if the level is lost (player destroyed or no bullets remaining), {@code false} otherwise.
	 */
	@Override
	protected boolean hasLevelBeenLost() {
		return userIsDestroyed() || getUser().getBulletCount() == 0;  // Loss condition based on user health or bullets
	}

	/**
	 * Determines if the player has won the level.
	 * The level is won if the boss is destroyed.
	 *
	 * @return {@code true} if the level is won (boss destroyed), {@code false} otherwise.
	 */
	@Override
	protected boolean hasLevelBeenWon() {
		return boss.isDestroyed;  // Win condition specific to this level: boss destroyed
	}

	/**
	 * Spawns enemy units for this level. In Level 2, the only enemy is the boss.
	 */
	@Override
	protected void spawnEnemyUnits() {
		// Add the boss enemy to the actor manager, which handles all game actors
		actorManager.addActor(boss);
	}

	/**
	 * Generates enemy fire by iterating over active actors and checking if the actor is the boss.
	 * If it is the boss, it spawns projectiles fired by the boss.
	 */
	@Override
	protected void generateEnemyFire() {
		actorManager.getActiveActors().forEach(actor -> {
			if (actor instanceof Boss) {  // Check if the actor is a boss
				// Spawn projectiles fired by the boss
				spawnEnemyProjectile(((Boss) actor).fireProjectile());
			}
		});
	}

	/**
	 * Instantiates and returns a custom level view for this specific level, displaying important information such as health,
	 * bullet count, and coins collected. This method initializes the custom {@link LevelViewLevelTwo} UI.
	 *
	 * @return The custom level view for this level.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		// Create a custom level view with player health, boss health, bullet count, and coins collected
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH, bossHealth, PLAYER_BULLET_COUNT, getUser().getCoinsCollected());
		return levelView;
	}

	/**
	 * Updates the level view with the latest game data, such as player health, boss health, bullet count, and coins collected.
	 * This method updates the user interface elements accordingly.
	 */
	@Override
	protected void updateLevelView() {
		// Update the heart display with the current player health
		levelView.updateHeartDisplay(getUser().getHealth());

		// Update the winning parameters display, showing the boss's health, player's bullet count, and coins collected
		levelView.updateWinningParameterDisplay(boss.getHealth(), getUser().getBulletCount(), getUser().getCoinsCollected());
	}

	/**
	 * Updates the game state on each game tick. This includes updating the level, generating enemy fire, spawning enemy units,
	 * and refreshing the level view. This method is called every frame of the game to ensure the game state remains current.
	 */
	@Override
	public void update() {
		super.update();  // Call the parent class's update method

		// Generate fire from enemy units (in this case, the boss)
		generateEnemyFire();

		// Spawn enemy units (the boss)
		spawnEnemyUnits();

		// Update the user interface with the latest data
		updateLevelView();
	}
}