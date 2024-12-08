package com.example.demo.levels.Level4;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.Planes.enemyPlanes.EnemyRocket;
import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.actors.additionalUnits.Coins;
import com.example.demo.actors.additionalUnits.FinishLine;
import com.example.demo.actors.additionalUnits.FuelToken;
import com.example.demo.actors.additionalUnits.Obstacle;
import com.example.demo.levels.LevelParent;
import com.example.demo.levels.LevelView;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class Level4 extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/level4Background.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int SURVIVAL_TIME_SECONDS = 8; // Time to survive in seconds
    private static double OBSTACLE_DIMENSIONS = Obstacle.getDimensions();
    private final UserPlane user;
    private int frameCount = 0;

    private int currentNumberOfFuelTokens;
    private int currentNumberOfObstacles;
    private int currentNumberOfFinishLines;

    private ImageView background;
    private LevelViewLevelFour levelView;
    private int remainingTime;
    private boolean isFinishLineSpawned;

    public Level4(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        this.user = getUser();
        this.remainingTime = SURVIVAL_TIME_SECONDS;
        this.background = getBackground();
        this.coinsCollectedInLevel = 0;
        this.isFinishLineSpawned = false;
        this.bulletsLeft = 100;
        
        initializeLevel(this, user);
    }

    @Override
    public void update() {
        super.update();
        updateLevelView();
        updateSurvivalTimer();
        if (remainingTime == 7 && !isFinishLineSpawned) {
            spawnFinishLine();
            isFinishLineSpawned = true; // Set the flag to true to prevent further spawns
        } else {
            spawnEnemyUnits();
            spawnFuelUnits();
        }
        spawnObstacleUnits();
    }


    @Override
    public Scene initializeScene() {
        super.initializeScene();
        user.resetFuelLeft();
        return scene;
    }

    @Override
    protected boolean hasLevelBeenLost() {
        return ((user.isDestroyed()) || ((user.getFuelLeft() == 0) && remainingTime > 0)); // User loss condition
    }

    @Override
    protected boolean hasLevelBeenWon() {
        return (remainingTime == 0); // Win condition specific to this level
    }

    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelFour(getRoot(), PLAYER_INITIAL_HEALTH, SURVIVAL_TIME_SECONDS, bulletsLeft);
        return levelView;
    }

    @Override
    protected void updateLevelView() {
        levelView.removeHearts(user.getHealth());
        levelView.updateWinningParameterDisplay(user.getFuelLeft(), bulletsLeft, coinsCollectedInLevel);
    }

    @Override
    protected void spawnEnemyUnits() {
        currentNumberOfEnemies += spawnHandler.spawnActors(
            () -> new EnemyRocket(screenWidth, Math.random() * getEnemyMaximumYPosition()/2, 1), // Supplier for new enemies
            5, // Maximum spawn at a time
            0.03, // Spawn probability 0.03
            currentNumberOfEnemies, // Current count
            5 // Total allowed
        );
    }

    protected void spawnFuelUnits() {
        currentNumberOfFuelTokens += spawnHandler.spawnActors(
            () -> new FuelToken(screenWidth / 2 * Math.random(), Math.random() * (screenHeight / 2)), // Supplier for new enemies
            4, // Maximum spawn at a time
            0.2, // Spawn probability 0.03
            currentNumberOfFuelTokens, // Current count
            10 // Total allowed
        );
    }

    protected void spawnObstacleUnits() {
        currentNumberOfObstacles += spawnHandler.spawnActors(
            () -> new Obstacle(screenWidth - OBSTACLE_DIMENSIONS, screenHeight - OBSTACLE_DIMENSIONS), // Supplier for new enemies
            5, // Maximum spawn at a time
            0.03, // Spawn probability 0.03
            currentNumberOfObstacles, // Current count
            10 // Total allowed
        );
    }

    protected void spawnFinishLine() {
        ActiveActorDestructible finishLineInstance = new FinishLine(screenWidth, 0);
        actorManager.addActor(finishLineInstance);
    }

    @Override
    protected void handleDefensesPenetration() {
        for (ActiveActorDestructible actor : actorManager.getActiveActors()) {
            if (entityHasPenetratedDefenses(actor)) {
                if (actor instanceof Coins) {
                    currentNumberOfCoins--;
                }
                else if (actor instanceof EnemyRocket) {
                    if (currentNumberOfEnemies > 0) {
                        currentNumberOfEnemies--;
                    }
                }
                else if (actor instanceof FuelToken) {
                    currentNumberOfFuelTokens--;
                }
                else if (actor instanceof Obstacle) {
                    currentNumberOfObstacles--;
                    actor.destroy();
                }
                actor.takeDamage();
            }
        }
    }

    @Override
    protected void initializeBackground() {
        background.setFocusTraversable(true);
        background.setFitHeight(screenHeight);
        background.setFitWidth(screenWidth);
        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                if (kc == KeyCode.UP) {
                    user.moveUp(); // Move up when the up key is pressed
                }
                if (kc == KeyCode.SPACE) {
                    fireProjectile(); // Fire projectile
                    bulletsLeft--;
                }
            }
        });
        background.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                if (kc == KeyCode.UP) {
                    user.fallDown(); // Move down when the up key is released
                }
            }
        });
        getRoot().getChildren().add(background);
    }

    protected void updateSurvivalTimer() {
        frameCount++;

        // Check if 2 frames have passed (20 * 50ms = 1000ms)
        if (frameCount >= 20) {
            System.out.println("Time: " + remainingTime);
            remainingTime--; // Update remaining time
            user.decrementFuel();
            frameCount = 0; // Reset the frame count
        }
    }
}