package com.example.demo.levels;

import java.util.*;

import com.example.demo.activityManagers.*;
import com.example.demo.Updatable;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.Planes.enemyPlanes.EnemyPlane;
import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.actors.additionalUnits.Coins;
import com.example.demo.gameConfig.GameTimeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;


public abstract class LevelParent extends Observable implements Updatable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;

	private final double screenHeight;
	private final double screenWidth;
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

	public int currentNumberOfEnemies;
	protected int currentNumberOfCoins;
	protected int coinsCollectedInLevel;
	private final LevelView levelView;

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
        this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.user = new UserPlane(playerInitialHealth);
		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;

		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;

		this.levelStateHandler = new LevelStateHandler();
		this.collisionHandler = new CollisionHandler(this);
		this.actorManager = ActorManager.getInstance();
		this.levelManager = LevelManager.getInstance();
		this.spawnHandler = new SpawnHandler(actorManager, screenWidth, screenHeight);

		levelView.pauseButton.setOnPauseAction(() -> levelStateHandler.handleLevelPaused(root, user));
		actorManager.setRoot(root);
	}

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		levelView.showHeartDisplay();
		levelView.showWinningParameterDisplay();
		levelView.showPauseButton();
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		GameTimeline.getInstance().getTimeline().play();
	}

	@Override
	public void update() {
		spawnCoinUnits();
		generateEnemyFire();
		checkCollisions();
		checkGameOverConditions();
		handleDefensesPenetration();
	}

	protected void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP) user.moveUp();
				if (kc == KeyCode.DOWN) user.moveDown();
				if (kc == KeyCode.SPACE) fireProjectile();
			}
		});
		background.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
			}
		});
		root.getChildren().add(background);
	}

	protected void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
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
				() -> new Coins(screenWidth, Math.random() * getEnemyMaximumYPosition()), // Supplier for new coins
				5, // Maximum spawn at a time
				0.15, // Spawn probability
				currentNumberOfCoins, // Current count
				20 // Total allowed
		);
	}

	protected void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			actorManager.addActor(projectile);
		}
	}

	protected void handleDefensesPenetration() {
		for (ActiveActorDestructible actor : actorManager.getActiveActors()) {
			if (entityHasPenetratedDefenses(actor)) {
				if (actor instanceof Coins) {
					currentNumberOfCoins--;
				}
				if (actor instanceof EnemyPlane) {
					currentNumberOfEnemies--;
				}
				actor.takeDamage();
			}
		}
	}

	protected void updateLevelView() {
		levelView.removeHearts(user.getHealth());
		levelView.updateWinningParameterDisplay(user.getNumberOfKills(), user.getScore());
	}

	protected boolean entityHasPenetratedDefenses(ActiveActorDestructible actor) {
		return Math.abs(actor.getTranslateX()) > screenWidth + actor.getFitWidth();
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

	protected int getCurrentNumberOfEnemies() {
		return currentNumberOfEnemies;
	}

	protected int getCurrentNumberOfCoins() {
		return currentNumberOfCoins;
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected double getScreenHeight() {
		return screenHeight;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	protected void initializeLevel(LevelParent level, ActiveActorDestructible user){
		actorManager.clearLevel();
		actorManager.addActor(user);
		GameTimeline.getInstance().clearUpdatable();
		GameTimeline.getInstance().addUpdatable(level);
		GameTimeline.getInstance().addUpdatable(actorManager);
	}

	protected final void checkGameOverConditions() {
		int userScore = getUser().getScore(); // Common logic for all levels

		// Check if the user has lost
		if (hasLevelBeenLost()) {
			System.out.println("Level failed. Game over.");
			levelStateHandler.showRedeemLife(root, user);
		}

		// Check if the user has won
		if (hasLevelBeenWon()) {
			System.out.println("Level completed. Advancing to next level.");
			System.out.println("User Score: " + userScore);
			levelStateHandler.handleLevelCompletion(root, user, coinsCollectedInLevel);
		}
	}

	protected abstract boolean hasLevelBeenLost();

	protected abstract boolean hasLevelBeenWon();
}

