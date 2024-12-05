package com.example.demo.levels;

import java.util.*;

import com.example.demo.ActorManager;
import com.example.demo.Updatable;
import com.example.demo.activityManagers.CollisionHandler;
import com.example.demo.activityManagers.LevelEndHandler;
import com.example.demo.activityManagers.LevelManager;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.Planes.enemyPlanes.EnemyPlane;
import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.actors.additionalUnits.Coins;
import com.example.demo.functionalClasses.GenerateLevelScore;
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

	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final UserPlane user;
	protected final Scene scene;
	private final ImageView background;
	public final LevelEndHandler levelEndHandler;
	public final ActorManager actorManager;
	public final CollisionHandler collisionHandler;
	public final LevelManager levelManager;

	public int currentNumberOfEnemies;
	protected int currentNumberOfCoins;
	protected int coinsCollectedInLevel;
	private final LevelView levelView;
	private static final double COIN_SPAWN_PROBABILITY = .15;
	private static final int TOTAL_COINS = 20;

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
		this.levelEndHandler = new LevelEndHandler(root);
		this.collisionHandler = new CollisionHandler(this);
		this.actorManager = ActorManager.getInstance();
		this.levelManager = LevelManager.getInstance();
		actorManager.setRoot(root);
	}

	protected abstract void initializeFriendlyUnits();

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
		GameTimeline.getInstance().getTimeline().play();
	}

	@Override
	public void update() {
		spawnCoinUnits();
		generateEnemyFire();
		handleDefensesPenetration();
		checkCollisions();
		checkGameOverConditions();
//		actorManager.updateScene(root);
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

	protected void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			actorManager.addActor(projectile);
		}
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

	protected boolean isOverlapping(ActiveActorDestructible actor, List<ActiveActorDestructible> existingActors) {
		return existingActors.stream()
				.anyMatch(existing -> actor.getBoundsInParent().intersects(existing.getBoundsInParent()));
	}

	protected void initializeLevel(LevelParent level){
		actorManager.clearLevel();
		actorManager.activeActors.add(user);
		GameTimeline.getInstance().clearUpdatable();
		GameTimeline.getInstance().addUpdatable(level);
		GameTimeline.getInstance().addUpdatable(actorManager);
	}

	protected final void checkGameOverConditions() {
		int userScore = getUser().getScore(); // Common logic for all levels

		// Check if the user has lost
		if (hasLevelBeenLost()) {
			System.out.println("Level failed. Game over.");
			levelEndHandler.handleLevelLoss(userScore);
			return; // Exit early
		}

		// Check if the user has won
		if (hasLevelBeenWon()) {
			System.out.println("Level completed. Advancing to next level.");
			System.out.println("User Score: " + userScore);

			GenerateLevelScore scoreCalculator = new GenerateLevelScore(getUser().getHealth(), coinsCollectedInLevel);

			int calculatedScore = scoreCalculator.calculateScore();
			String starImage = scoreCalculator.getStarImage();

			getUser().setLevelScore(levelManager.getCurrentLevelNumber(), calculatedScore);
			levelEndHandler.handleLevelCompletion(userScore, starImage, getUser());

			levelManager.incrementCurrentLevelNumber();
		}
	}

	protected abstract boolean hasLevelBeenLost();

	protected abstract boolean hasLevelBeenWon();

}

