package com.example.demo.levels;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.ActorManager;
import com.example.demo.Updatable;
import com.example.demo.activityManagers.CollisionHandler;
import com.example.demo.activityManagers.LevelEndHandler;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.Planes.FighterPlane;
import com.example.demo.actors.Planes.enemyPlanes.EnemyPlane;
import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.actors.Projectiles.Projectile;
import com.example.demo.actors.Projectiles.userProjectiles.UserProjectile;
import com.example.demo.actors.additionalUnits.Coins;
import com.example.demo.actors.additionalUnits.FuelToken;
import com.example.demo.gameConfig.GameTimeline;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;


public abstract class LevelParent extends Observable implements Updatable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	protected final Timeline timeline;
	private final UserPlane user;
	protected final Scene scene;
	private final ImageView background;
	public final LevelEndHandler levelEndHandler;
	public final ActorManager actorManager;
	public final CollisionHandler collisionHandler;

//	protected final List<ActiveActorDestructible> friendlyUnits;
//	protected final List<ActiveActorDestructible> enemyUnits;
//	protected final List<ActiveActorDestructible> userProjectiles;
//	protected final List<ActiveActorDestructible> enemyProjectiles;
//	protected final List<ActiveActorDestructible> coinUnits;
	protected final List<ActiveActorDestructible> removeActorsFromScene;

	public int currentNumberOfEnemies;
	protected int currentNumberOfCoins;
//	private int penetratedEnemyCount;
//	private int offScreenCoinCount;
	protected int coinsCollectedInLevel;
	private final LevelView levelView;
	private static final double COIN_SPAWN_PROBABILITY = .15;
	private static final int TOTAL_COINS = 20;

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
        this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = GameTimeline.getInstance().createTimeline();
		this.user = new UserPlane(playerInitialHealth);
//		this.friendlyUnits = new ArrayList<>();
//		this.enemyUnits = new ArrayList<>();
//		this.userProjectiles = new ArrayList<>();
//		this.enemyProjectiles = new ArrayList<>();
//		this.coinUnits = new ArrayList<>();
		this.removeActorsFromScene = new ArrayList<>();

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
//		this.penetratedEnemyCount = 0;
		this.levelEndHandler = new LevelEndHandler(root);
		this.collisionHandler = new CollisionHandler(this);
		this.actorManager = ActorManager.getInstance();

		initializeTimeline();
		actorManager.activeActors.add(user);
//		friendlyUnits.add(user);
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		levelView.showWinningParameterDisplay();
		levelView.showPauseButton();
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	protected void updateScene() {
		spawnEnemyUnits();
		spawnCoinUnits();
		updateActors();

		generateEnemyFire();
//		updateNumberOfEnemies();
//		updateNumberOfCoins();
//		handleCoinCollisions();
		handleDefensesPenetration();
//		handleCoinMovesOffScreen();
//		handleUserProjectileCollisions();
//		handleEnemyProjectileCollisions();
//		handlePlaneCollisions();
		checkCollisions();
//		removeAllDestroyedActors();
//		updateKillCount();
		actorManager.addActorsToScene(root);
		actorManager.removeActorsFromScene(root);
		updateLevelView();
		checkIfGameOver();

	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
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
//		root.getChildren().add(projectile);
//		userProjectiles.add(projectile);
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

	protected void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
//			root.getChildren().add(projectile);
			actorManager.addActor(projectile);
//			enemyProjectiles.add(projectile);
		}
	}

	protected void updateActors() {
		actorManager.getActiveActors().forEach(ActiveActorDestructible::updateActor);
//		friendlyUnits.forEach(plane -> plane.updateActor());
//		enemyUnits.forEach(enemy -> enemy.updateActor());
//		userProjectiles.forEach(projectile -> projectile.updateActor());
//		enemyProjectiles.forEach(projectile -> projectile.updateActor());
//		coinUnits.forEach(coin -> coin.updateActor());
	}



	protected void spawnCoinUnits() {
		// Define how many coins we want to spawn
		int coinsToSpawn = Math.min(5 - currentNumberOfCoins, TOTAL_COINS - currentNumberOfCoins);
		if (currentNumberOfCoins < 3) {
			coinsToSpawn = Math.min(3 - currentNumberOfCoins, TOTAL_COINS - currentNumberOfCoins);
		}

		for (int i = 0; i < coinsToSpawn; i++) {
			if (Math.random() < COIN_SPAWN_PROBABILITY) { // Assuming you have a COIN_SPAWN_PROBABILITY constant
				double newCoinInitialYPosition = Math.random() * getEnemyMaximumYPosition(); // Assuming you have a method to get max Y position for coins
				ActiveActorDestructible newCoin = new Coins(getScreenWidth(), newCoinInitialYPosition);

				// Check for overlapping with existing coins
				if (!isOverlapping(newCoin, actorManager.getActiveActors())) {
					actorManager.addActor(newCoin);
					currentNumberOfCoins++;
				} else {
					// If overlapping, decrement i to try again
					i--;
				}
			}
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

//	protected void handleCoinMovesOffScreen() {
//		for (ActiveActorDestructible coin : coinUnits) {
//			if (enemyHasPenetratedDefenses(coin)) {
//				coin.takeDamage();
//				offScreenCoinCount++;
//				updateNumberOfCoins();
//			}
//		}
//	}

	protected void updateLevelView() {
		levelView.removeHearts(user.getHealth());
		levelView.updateWinningParameterDisplay(user.getNumberOfKills(), user.getScore());
	}

	protected boolean entityHasPenetratedDefenses(ActiveActorDestructible actor) {
		return Math.abs(actor.getTranslateX()) > screenWidth;
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

//	protected void addEnemyUnit(ActiveActorDestructible enemy) {
//		enemyUnits.add(enemy);
//		root.getChildren().add(enemy);
//	}
//
//	protected void addCoinUnit(ActiveActorDestructible coin) {
//		coinUnits.add(coin);
//		root.getChildren().add(coin);
//	}

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

//	protected void updateNumberOfEnemies() {
//		// Update currentNumberOfEnemies to reflect the number of enemies that have not penetrated defenses
//		currentNumberOfEnemies = enemyUnits.size() - penetratedEnemyCount;
//
//		// Reset the penetrated enemy count for the next update
//		penetratedEnemyCount = 0;
//	}

//	protected void updateNumberOfCoins() {
//		// Update currentNumberOfEnemies to reflect the number of enemies that have not penetrated defenses
//		currentNumberOfCoins = coinUnits.size() - offScreenCoinCount;
//
//		// Reset the penetrated enemy count for the next update
//		offScreenCoinCount = 0;
//	}

	protected boolean isOverlapping(ActiveActorDestructible actor, List<ActiveActorDestructible> existingActors) {
		return existingActors.stream()
				.anyMatch(existing -> actor.getBoundsInParent().intersects(existing.getBoundsInParent()));
	}

}

