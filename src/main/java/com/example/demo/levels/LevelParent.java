package com.example.demo.levels;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.*;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;

//BOUNDING BOXES DELETE LATER


public abstract class LevelParent extends Observable {

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

	protected final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	protected final List<ActiveActorDestructible> userProjectiles;
	protected final List<ActiveActorDestructible> enemyProjectiles;
	protected final List<ActiveActorDestructible> coinUnits;

	private int currentNumberOfEnemies;
	private int currentNumberOfCoins;
	private int penetratedEnemyCount;
	private int offScreenCoinCount;
	protected int coinsCollectedInLevel;
	private final LevelView levelView;
	private static final double COIN_SPAWN_PROBABILITY = .15;
	private static final int TOTAL_COINS = 20;

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
        this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = GameTimeline.getInstance().createTimeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
		this.coinUnits = new ArrayList<>();

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		this.penetratedEnemyCount = 0;
		this.levelEndHandler = new LevelEndHandler(root);


		initializeTimeline();
		friendlyUnits.add(user);
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
		updateNumberOfEnemies();
		updateNumberOfCoins();
		handleCoinCollisions();
		handleEnemyPenetration();
		handleCoinMovesOffScreen();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();

		// Call the method to draw bounding boxes
		// drawBoundingBoxes(); // Add this line to visualize the bounding boxes

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
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	protected void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	protected void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	protected void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
		coinUnits.forEach(coin -> coin.updateActor());
	}

	protected void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
		removeDestroyedActors(coinUnits);
	}

	protected void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	protected void handleCoinCollisions() {
		for (ActiveActorDestructible coin : coinUnits) {
			if (coin.isDestroyed()) continue; // Skip if already destroyed
			if (user.getBoundsInParent().intersects(coin.getBoundsInParent())) {
				coin.takeDamage();
				coinsCollectedInLevel++;
				user.incrementScore();
			}
		}
	}

	protected void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	protected void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	protected void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	protected void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			if (actor.isDestroyed()) continue; // Skip if already destroyed
			for (ActiveActorDestructible otherActor : actors1) {
				if (otherActor.isDestroyed()) continue; // Skip if already destroyed
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
				}
			}
		}
	}

	protected void spawnCoinUnits() {
		int currentNumberOfCoins = getCurrentNumberOfCoins();

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
				if (!isCoinOverlapping(newCoin)) {
					addCoinUnit(newCoin); // Assuming you have a method to add the coin to the game
				} else {
					// If overlapping, decrement i to try again
					i--;
				}
			}
		}
	}

//	Draw bounding boxes around all images function
//	private void drawBoundingBoxes() {
//		// Clear previous bounding boxes if any
//		root.getChildren().removeIf(node -> node instanceof Rectangle);
//
//		// Draw the bounding box for the user plane
//		Rectangle userBounds = new Rectangle(user.getBoundsInParent().getMinX(), user.getBoundsInParent().getMinY(),
//				user.getBoundsInParent().getWidth(), user.getBoundsInParent().getHeight());
//		userBounds.setStroke(Color.RED); // Set the outline color
//		userBounds.setFill(Color.TRANSPARENT); // Make it transparent
//		root.getChildren().add(userBounds); // Add to the root group
//
//		// Draw bounding boxes for enemy projectiles
//		for (ActiveActorDestructible projectile : enemyProjectiles) {
//			Rectangle projectileBounds = new Rectangle(projectile.getBoundsInParent().getMinX(), projectile.getBoundsInParent().getMinY(),
//					projectile.getBoundsInParent().getWidth(), projectile.getBoundsInParent().getHeight());
//			projectileBounds.setStroke(Color.BLUE); // Set the outline color
//			projectileBounds.setFill(Color.TRANSPARENT); // Make it transparent
//			root.getChildren().add(projectileBounds); // Add to the root group
//		}
//	}

	protected void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				enemy.takeDamage();
				enemy.destroy();
				penetratedEnemyCount++; // Increment the counter for each enemy that penetrates
				updateNumberOfEnemies();
			}
		}
	}

	protected void handleCoinMovesOffScreen() {
		for (ActiveActorDestructible coin : coinUnits) {
			if (enemyHasPenetratedDefenses(coin)) {
				coin.takeDamage();
				coin.destroy();
				offScreenCoinCount++;
				updateNumberOfCoins();
			}
		}
	}

	protected void updateLevelView() {
		levelView.removeHearts(user.getHealth());
		levelView.updateWinningParameterDisplay(user.getNumberOfKills(), user.getScore());
	}

	private void updateKillCount() {
		int currentEnemyCount = enemyUnits.size();
		int kills = currentNumberOfEnemies - currentEnemyCount;
		if (kills > 0) { // Only increment if there are new kills
			for (int i = 0; i < kills; i++) {
				user.incrementKillCount();
			}
		}
		currentNumberOfEnemies = currentEnemyCount; // Update current number of enemies

		// Reset the penetrated enemy count for the next update
		penetratedEnemyCount = 0;
	}

	protected boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}
		protected UserPlane getUser() {
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

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected void addCoinUnit(ActiveActorDestructible coin) {
		coinUnits.add(coin);
		root.getChildren().add(coin);
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

	protected void updateNumberOfEnemies() {
		// Update currentNumberOfEnemies to reflect the number of enemies that have not penetrated defenses
		currentNumberOfEnemies = enemyUnits.size() - penetratedEnemyCount;

		// Reset the penetrated enemy count for the next update
		penetratedEnemyCount = 0;
	}

	protected void updateNumberOfCoins() {
		// Update currentNumberOfEnemies to reflect the number of enemies that have not penetrated defenses
		currentNumberOfCoins = coinUnits.size() - offScreenCoinCount;

		// Reset the penetrated enemy count for the next update
		offScreenCoinCount = 0;
	}

	boolean isEnemyPlaneOverlapping(ActiveActorDestructible newEnemy) {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (newEnemy.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
				return true;
			}
		}
		return false;
	}

	boolean isCoinOverlapping(ActiveActorDestructible newCoin) {
		for (ActiveActorDestructible coin : coinUnits) {
			if (newCoin.getBoundsInParent().intersects(coin.getBoundsInParent())) {
				return true; // Overlapping found
			}
		}
		return false; // No overlap
	}
}
