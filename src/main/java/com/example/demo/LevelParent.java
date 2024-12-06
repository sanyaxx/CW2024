package com.example.demo;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.controller.Controller;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;

//BOUNDING BOXES DELETE LATER
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	
	private int currentNumberOfEnemies;
	private int penetratedEnemyCount;
	private final LevelView levelView;
	private int currentLevelNumber; // To track the current level
	protected static final int TOTAL_LEVELS = 3; // Track the total number of levels

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, int levelNumber) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		this.currentLevelNumber = levelNumber; // Set the current level number
		this.penetratedEnemyCount = 0;
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
		levelView.showKillCountDisplay();  // Show the KillCountDisplay
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();
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

	private void initializeBackground() {
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

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			if (actor.isDestroyed()) continue; // Skip if already destroyed
			for (ActiveActorDestructible otherActor : actors1) {
				if (otherActor.isDestroyed()) continue; // Skip if already destroyed
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					// Optional: Add a small buffer or threshold check
					if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
						actor.takeDamage();
						otherActor.takeDamage();
					}
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

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				enemy.takeDamage();
				enemy.destroy();
				penetratedEnemyCount++; // Increment the counter for each enemy that penetrates
				updateNumberOfEnemies();
			}
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	private void updateKillCount() {
		int currentEnemyCount = enemyUnits.size();
		int kills = currentNumberOfEnemies - currentEnemyCount;
		if (kills > 0) { // Only increment if there are new kills
			for (int i = 0; i < kills; i++) {
				user.incrementKillCount();
				levelView.incrementKillCount();
			}
		}
		currentNumberOfEnemies = currentEnemyCount; // Update current number of enemies

		// Reset the penetrated enemy count for the next update
		penetratedEnemyCount = 0;
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	protected String getNextLevelName() {
		if (currentLevelNumber < TOTAL_LEVELS) {
			// Return the next level name based on the current level number
			String nextLevelName = "com.example.demo.Level" + (currentLevelNumber + 1);
			currentLevelNumber++; // Increment the current level number after getting the next level name
			return nextLevelName;
		} else {
			// Handle the case when the last level is reached

			//COMPLETE

			return null;
		}
	}

	public void goToNextLevel(String levelName) {
		setChanged();
		notifyObservers(levelName);
	}

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
		String nextLevel = getNextLevelName();
		if (nextLevel != null) {
			goToNextLevel(nextLevel);
		}
	}

	public void endLevel() {
		timeline.stop();
		// Any additional cleanup before moving to the next level
	}

	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return currentNumberOfEnemies;
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		// Update currentNumberOfEnemies to reflect the number of enemies that have not penetrated defenses
		currentNumberOfEnemies = enemyUnits.size() - penetratedEnemyCount;

		// Reset the penetrated enemy count for the next update
		penetratedEnemyCount = 0;
	}

	boolean isEnemyPlaneOverlapping(ActiveActorDestructible newEnemy) {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (newEnemy.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
				return true;
			}
		}
		return false;
	}
}

