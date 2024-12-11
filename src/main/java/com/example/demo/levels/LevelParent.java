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


public abstract class LevelParent extends Observable implements Updatable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;

	protected final double screenHeight;
	protected final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final UserPlane user;
	public final Scene scene;
	private final ImageView background;

	public final LevelStateHandler levelStateHandler;
	public final ActorManager actorManager;
	public final CollisionHandler collisionHandler;
	public final LevelManager levelManager;
	protected SpawnHandler spawnHandler;
	public final UserStatsManager userStatsManager;
	public final InputHandler inputHandler;
	public final OverlayHandler overlayHandler;

	public int currentNumberOfEnemies;
	public int currentNumberOfCoins;
	public int currentNumberOfMagnets;
	public int killCount;
	private final LevelView levelView;
	public boolean magnetismActivated;
	public double magnetRadius; // Example radius for magnet effect
	public int frameCount;

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

		levelView.pauseButton.setOnPauseAction(() -> overlayHandler.handleLevelPaused(root, user));
		initializeActorManager();
	}

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		setupBackground();
		setUpInputHandler();
		levelView.showUIComponents();
		return scene;
	}

	private void initializeActorManager() {
		actorManager.setRoot(root);
	}

	public void startGame() {
		background.requestFocus();
		GameLoop.getInstance().getTimeline().play();
	}

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


	protected void setupBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		root.getChildren().add(background);
	}

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

	protected void fireProjectile() {
		GameEntity projectile = user.fireProjectile();
		actorManager.addActor(projectile);
	}

	protected void checkCollisions() {
		collisionHandler.checkCollisions(actorManager.getActiveActors());
	}

	protected void generateEnemyFire() {
		actorManager.getActiveActors().forEach(actor -> {
			if (actor instanceof EnemyPlane) {
				spawnEnemyProjectile(((EnemyPlane) actor).fireProjectile());
			}
		});
	}

	protected void spawnCoinUnits() {
		currentNumberOfCoins += spawnHandler.spawnActors(
				() -> new Coins(screenWidth,100 + (Math.random() * (getEnemyMaximumYPosition() - 100)), magnetRadius), // Supplier for new coins
				5, // Maximum spawn at a time
				0.15, // Spawn probability
				currentNumberOfCoins, // Current count
				20 // Total allowed
		);
	}

	protected void spawnEnemyProjectile(GameEntity projectile) {
		if (projectile != null) {
			actorManager.addActor(projectile);
		}
	}

	protected void spawnMagnet() {
		currentNumberOfMagnets += spawnHandler.spawnActors(
				() -> new Magnet(screenWidth, 100 + (Math.random() * (getEnemyMaximumYPosition() - 100)), 1), // Supplier for new enemies // 1 = launch from east
				1, // Maximum spawn at a time
				1, // Spawn probability
				currentNumberOfMagnets, // Current count
				2 // Total allowed
		);
	}

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

	protected void updateLevelView() {
		levelView.removeHearts(user.getHealth());
		levelView.updateWinningParameterDisplay(user.getInLevelKillCount(), user.getBulletCount(), user.getCoinsCollected());
	}

	protected boolean entityHasPenetratedDefenses(GameEntity actor) {
		return (((Math.abs(actor.getTranslateX()) > (screenWidth))));
	}

	public UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected ImageView getBackground() {
		return background;
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed;
	}

	protected void initializeLevel(LevelParent level, UserParent user){
		actorManager.clearLevel();
		actorManager.addActor(user);
		GameLoop.getInstance().clearUpdatable();
		GameLoop.getInstance().addUpdatable(level);
		GameLoop.getInstance().addUpdatable(actorManager);
	}

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

	protected abstract boolean hasLevelBeenLost();

	protected abstract boolean hasLevelBeenWon();

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



