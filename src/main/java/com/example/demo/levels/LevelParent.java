package com.example.demo.levels;

import java.util.*;

import com.example.demo.activityManagers.*;
import com.example.demo.activityManagers.UserStatsManager;
import com.example.demo.actors.Planes.friendlyPlanes.UserParent;
import com.example.demo.actors.Updatable;
import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.enemyPlanes.EnemyPlane;
import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.actors.additionalUnits.Coins;
import com.example.demo.actors.additionalUnits.Magnet;
import com.example.demo.controller.GameLoop;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;


/**
 * The {@code LevelParent} class is an abstract class representing a game level.
 * It defines common behaviors and properties shared by all levels in the game, such as
 * spawning enemies, handling collisions, managing the user interface, and checking win/loss conditions.
 * The class provides mechanisms for level setup, gameplay updates, and actor management, as well as methods
 * for handling special units like coins and magnets.
 */
public abstract class LevelParent extends Observable implements Updatable {

	/** Constant used to adjust the maximum Y position for enemies */
	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;

	/** The height of the screen for this level */
	protected final double screenHeight;

	/** The width of the screen for this level */
	protected final double screenWidth;

	/** The maximum Y position for enemy spawn positions */
	private final double enemyMaximumYPosition;

	/** The root node of the scene for rendering the game elements */
	private final Group root;

	/** The user plane controlled by the player */
	public final UserPlane user;

	/** The scene containing the game */
	public final Scene scene;

	/** The background image view for the level */
	private final ImageView background;

	/** The state handler for the level, managing level states */
	public final LevelStateHandler levelStateHandler;

	/** The actor manager responsible for managing game entities */
	public final ActorManager actorManager;

	/** The collision handler for detecting and handling collisions */
	public final CollisionHandler collisionHandler;

	/** The level manager, handling level progression */
	public final LevelManager levelManager;

	/** The spawn handler for spawning game entities like enemies */
	protected SpawnHandler spawnHandler;

	/** The user stats manager that tracks player statistics like health and score */
	public final UserStatsManager userStatsManager;

	/** The input handler managing user controls */
	public final InputHandler inputHandler;

	/** The overlay handler that manages game overlays like pause and win screens */
	public final OverlayHandler overlayHandler;

	/** The current number of enemies in the level */
	public int currentNumberOfEnemies;

	/** The current number of coins in the level */
	public int currentNumberOfCoins;

	/** The current number of magnets in the level */
	public int currentNumberOfMagnets;

	/** The number of enemies the player has defeated */
	public int killCount;

	/** The level view that displays UI elements such as health and kill count */
	private final LevelView levelView;

	/** Flag indicating whether the magnet effect is activated */
	public boolean magnetismActivated;

	/** The radius within which the magnet effect operates */
	public double magnetRadius;

	/** The frame count used to track the magnet effect's duration */
	public int frameCount;

	/**
	 * Constructor for the {@code LevelParent} class.
	 * Initializes the level with the given parameters and sets up various game handlers and managers.
	 *
	 * @param backgroundImageName The background image for the level.
	 * @param screenHeight The height of the screen.
	 * @param screenWidth The width of the screen.
	 * @param playerInitialHealth The initial health of the player.
	 * @param playerBulletCount The initial bullet count for the player.
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, int playerBulletCount) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.user = new UserPlane(playerInitialHealth, playerBulletCount);
		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		this.currentNumberOfMagnets = 0;
		this.killCount = 0;
		this.magnetismActivated = false;
		this.magnetRadius = 300;
		this.frameCount = 0;

		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;

		this.levelStateHandler = new LevelStateHandler();
		this.collisionHandler = new CollisionHandler(this);
		this.actorManager = ActorManager.getInstance();
		this.levelManager = LevelManager.getInstance();
		this.spawnHandler = new SpawnHandler(actorManager, screenWidth, screenHeight);
		this.userStatsManager = UserStatsManager.getInstance();
		this.inputHandler = new InputHandler();
		this.overlayHandler = OverlayHandler.getInstance();

		levelView.pauseButton.setOnAction(event -> overlayHandler.handleLevelPaused(root, user));
		initializeActorManager();
	}

	/**
	 * Abstract method to spawn enemy units in the level.
	 * This method should be implemented by subclasses to define how enemies are spawned.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Abstract method to instantiate the {@link LevelView} for this level.
	 * The {@link LevelView} displays the user interface for the level, such as health and bullet count.
	 *
	 * @return A new instance of {@link LevelView}.
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Initializes the scene by setting up the background, input handlers, and displaying the UI components.
	 *
	 * @return The initialized scene for the level.
	 */
	public Scene initializeScene() {
		setupBackground();
		setUpInputHandler();
		levelView.showUIComponents();
		return scene;
	}

	/**
	 * Sets up the root in actorManager class
	 */
	private void initializeActorManager() {
		actorManager.setRoot(root);
	}

	/**
	 * Starts the game by playing the game loop.
	 */
	public void startGame() {
		background.requestFocus();
		GameLoop.getInstance().getTimeline().play();
	}

	/**
	 * Calls the functions that need to be updated in every key frame call.
	 */
	@Override
	public void update() {
		if (!user.isCollisionCooldownActive()) {
			checkCollisions();
		}
		spawnCoinUnits();
		checkGameOverConditions();
		handleDefensesPenetration();
		if (!magnetismActivated) {
			spawnMagnet();
		}
	}

	/**
	 * Sets up the background by adjusting its size and adding it to the scene.
	 */
	protected void setupBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		root.getChildren().add(background);
	}

	/**
	 * Configures input handling by mapping user controls to actions (e.g., moving, shooting).
	 */
	protected void setUpInputHandler() {
		inputHandler.setupInputHandlers(
				user,
				user::moveUp, user::stop,
				user::moveDown, user::stop,
				null, null,
				null, null,
				() -> {
					if (!user.isCollisionCooldownActive()) { // Check if cooldown is not active
						fireProjectile(); // Fire the projectile if cooldown is inactive
						getUser().decrementBulletCount();
					}
				}
		);

		background.setOnKeyPressed(inputHandler.getKeyPressHandler(user));
		background.setOnKeyReleased(inputHandler.getKeyReleaseHandler(user));
	}

	/**
	 * Fires a projectile from the user's plane and adds it to the actor manager.
	 */
	protected void fireProjectile() {
		GameEntity projectile = user.fireProjectile();
		actorManager.addActor(projectile);
	}

	/**
	 * Checks for collisions between the active game actors.
	 */
	protected void checkCollisions() {
		collisionHandler.checkCollisions(actorManager.getActiveActors());
	}

	/**
	 * Generates enemy fire by creating projectiles for each enemy plane.
	 */
	protected void generateEnemyFire() {
		actorManager.getActiveActors().forEach(actor -> {
			if (actor instanceof EnemyPlane) {
				spawnEnemyProjectile(((EnemyPlane) actor).fireProjectile());
			}
		});
	}

	/**
	 * Spawns coin units in the level.
	 */
	protected void spawnCoinUnits() {
		currentNumberOfCoins += spawnHandler.spawnActors(
				() -> new Coins(screenWidth, 100 + (Math.random() * (getEnemyMaximumYPosition() - 100)), magnetRadius), // Supplier for new coins
				5, // Maximum spawn at a time
				0.15, // Spawn probability
				currentNumberOfCoins, // Current count
				20 // Total allowed
		);
	}

	/**
	 * Spawns an enemy projectile.
	 *
	 * @param projectile The projectile to be spawned.
	 */
	protected void spawnEnemyProjectile(GameEntity projectile) {
		if (projectile != null) {
			actorManager.addActor(projectile);
		}
	}

	/**
	 * Spawns magnet units in the level.
	 */
	protected void spawnMagnet() {
		currentNumberOfMagnets += spawnHandler.spawnActors(
				() -> new Magnet(screenWidth, 100 + (Math.random() * (getEnemyMaximumYPosition() - 100)), 1), // Supplier for new magnets
				1, // Maximum spawn at a time
				1, // Spawn probability
				currentNumberOfMagnets, // Current count
				2 // Total allowed
		);
	}

	/**
	 * Handles the penetration of defenses by game entities, adjusting counts and dealing damage where appropriate.
	 */
	protected void handleDefensesPenetration() {
		for (GameEntity actor : actorManager.getActiveActors()) {
			if (entityHasPenetratedDefenses(actor)) {
				if (actor instanceof Coins) {
					currentNumberOfCoins--;
				}
				if (actor instanceof EnemyPlane) {
					currentNumberOfEnemies--;
				}
				if (actor instanceof Magnet) {
					currentNumberOfMagnets = 0;
				}
				actor.takeDamage();
			}
		}
	}

	/**
	 * Updates the level view by displaying the player's health, kills, and coins collected.
	 */
	protected void updateLevelView() {
		levelView.updateHeartDisplay(user.getHealth());
		levelView.updateWinningParameterDisplay(user.getInLevelKillCount(), user.getBulletCount(), user.getCoinsCollected());
	}

	/**
	 * Checks if a game entity has penetrated the defenses (i.e., gone beyond the screen boundaries).
	 *
	 * @param actor The game entity to check.
	 * @return {@code true} if the entity has penetrated defenses, {@code false} otherwise.
	 */
	protected boolean entityHasPenetratedDefenses(GameEntity actor) {
		return (((Math.abs(actor.getTranslateX()) > (screenWidth))));
	}

	/**
	 * Gets the user (player) plane.
	 *
	 * @return The user plane.
	 */
	public UserPlane getUser() {
		return user;
	}

	/**
	 * Gets the root.
	 *
	 * @return The root plane.
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * Gets the Imageview of the Background set.
	 *
	 * @return The background imageview.
	 */
	protected ImageView getBackground() {
		return background;
	}

	/**
	 * Gets the maximum enemy Position on the Y AXIS.
	 *
	 * @return The double Y position.
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Gets the value of whether the user (player) is destroyed.
	 *
	 * @return The boolean answer.
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed;
	}

	/**
	 * Initializes the level by adding the user to the level and updating the game loop.
	 *
	 * @param level The level to initialize.
	 * @param user The user to add to the level.
	 */
	protected void initializeLevel(LevelParent level, UserParent user) {
		actorManager.clearLevel();
		actorManager.addActor(user);
		GameLoop.getInstance().clearUpdatable();
		GameLoop.getInstance().addUpdatable(level);
		GameLoop.getInstance().addUpdatable(actorManager);
	}

	/**
	 * Checks the conditions for game over.
	 * If the user has lost or won, it will trigger the respective overlays.
	 */
	protected void checkGameOverConditions() {
		// Check if the user has lost
		if (hasLevelBeenLost()) {
			overlayHandler.showRedeemLife(root, user);
		}

		// Check if the user has won
		if (hasLevelBeenWon()) {
			overlayHandler.handleLevelCompletion(root, user);
		}
	}

	/**
	 * Abstract method to check if the level has been lost.
	 * Should be implemented by subclasses to define the specific loss conditions.
	 *
	 * @return {@code true} if the level has been lost, {@code false} otherwise.
	 */
	protected abstract boolean hasLevelBeenLost();

	/**
	 * Abstract method to check if the level has been won.
	 * Should be implemented by subclasses to define the specific win conditions.
	 *
	 * @return {@code true} if the level has been won, {@code false} otherwise.
	 */
	protected abstract boolean hasLevelBeenWon();

	/**
	 * Activates the magnet effect, pulling coins towards the player.
	 * The magnet effect lasts for a specified duration before being deactivated.
	 */
	public void activateMagnet() {
		frameCount++;
		magnetismActivated = true;
		user.initiateMagnetActivated();

		actorManager.getActiveActors().stream()
				.filter(actor -> actor instanceof Coins) // Process all Coins
				.map(actor -> (Coins) actor)
				.forEach(coinInactive -> {
					coinInactive.setMagnetActivated(true); // Ensure the magnet is activated for all coins
				});

		// Check if 7 frames have passed (140 * 50ms = 50000ms)
		if (frameCount >= 140) {
			deactivateMagnet();
			frameCount = 0; // Reset the frame count
		}
	}

	/**
	 * Deactivates the magnet effect, stopping coins from being attracted to the player.
	 */
	public void deactivateMagnet() {
		actorManager.getActiveActors().stream()
				.filter(actor -> actor instanceof Coins) // Filter for coin instances
				.map(actor -> (Coins) actor) // Cast to Coins
				.forEach(coinActive -> {
					coinActive.setMagnetActivated(false);
				});
		user.endMagnetActivated();
		magnetismActivated = false; // Deactivate the magnet after 5 seconds
		currentNumberOfMagnets = 0;
	}
}
