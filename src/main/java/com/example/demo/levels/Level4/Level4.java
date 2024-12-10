package com.example.demo.levels.Level4;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.enemyPlanes.EnemyRocket;
import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.actors.additionalUnits.*;
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
    private static final int SURVIVAL_TIME_SECONDS = 9; // Time to survive in seconds
    private static final int PLAYER_FUEL_CAPACITY = 8;
    private static final int PLAYER_BULLET_COUNT = 100;
    private static double OBSTACLE_DIMENSIONS = Obstacle.getDimensions();
    private final UserPlane user;
    private int frameCount = 0;

    private int currentNumberOfFuelTokens;
    private int currentNumberOfObstacles;

    private ImageView background;
    private LevelViewLevelFour levelView;
    private int remainingTime;
    private boolean isFinishLineSpawned;

    public Level4(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, PLAYER_BULLET_COUNT);
        this.user = getUser();
        user.setFuelCapacity(PLAYER_FUEL_CAPACITY);
        this.background = getBackground();

        this.remainingTime = SURVIVAL_TIME_SECONDS;
        this.isFinishLineSpawned = false;
        
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
        return ((user.isDestroyed) || ((user.getFuelLeft() == 0) && remainingTime > 0)) || user.getBulletCount() == 0; // User loss condition
    }

    @Override
    protected boolean hasLevelBeenWon() {
        return (remainingTime == 0); // Win condition specific to this level
    }

    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelFour(getRoot(), PLAYER_INITIAL_HEALTH, PLAYER_FUEL_CAPACITY, PLAYER_BULLET_COUNT, getUser().getCoinsCollected());
        return levelView;
    }

    @Override
    protected void updateLevelView() {
        levelView.removeHearts(user.getHealth());
        levelView.updateWinningParameterDisplay(user.getFuelLeft(), user.getBulletCount(), user.getCoinsCollected());
    }

    @Override
    protected void spawnEnemyUnits() {
        currentNumberOfEnemies += spawnHandler.spawnActors(
            () -> new EnemyRocket(screenWidth, (100 + (Math.random() * (getEnemyMaximumYPosition() - 100)))/2, 1), // Supplier for new enemies
            5, // Maximum spawn at a time
            0.03, // Spawn probability 0.03
            currentNumberOfEnemies, // Current count
            5 // Total allowed
        );
    }

    protected void spawnFuelUnits() {
        currentNumberOfFuelTokens += spawnHandler.spawnActors(
            () -> new FuelToken(screenWidth / 0.75 * Math.random(),(100 + (Math.random() * (getEnemyMaximumYPosition() - 100)))/2), // Supplier for new enemies
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
            0.06, // Spawn probability 0.03
            currentNumberOfObstacles, // Current count
            10 // Total allowed
        );
    }

    protected void spawnFinishLine() {
        GameEntity finishLineInstance = new FinishLine(screenWidth, 0);
        actorManager.addActor(finishLineInstance);
    }

    @Override
    protected void handleDefensesPenetration() {
        for (GameEntity actor : actorManager.getActiveActors()) {
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
                    actorManager.removeActor(actor);
                }
                else if (actor instanceof Magnet) {
                    currentNumberOfMagnets--;
                }
                actor.takeDamage();
            }
        }
    }

    @Override
    protected void setUpInputHandler() {
        inputHandler.setupInputHandlers(
                user,
                user::moveUp,user::fallDown,
                null,null,
                null,null,
                null,null,
                () -> {
                    if (!user.isCollisionCooldownActive()) { // Check if cooldown is not active
                        fireProjectile(); // Fire the projectile if cooldown is inactive
                        user.decrementBulletCount(); // Decrement bullet count
                    }
                }
        );
        background.setOnKeyPressed(inputHandler.getKeyPressHandler(user));
        background.setOnKeyReleased(inputHandler.getKeyReleaseHandler(user));
    }

    protected void updateSurvivalTimer() {
        frameCount++;

        // Check if 2 frames have passed (20 * 50ms = 1000ms)
        if (frameCount >= 20) {
            remainingTime--; // Update remaining time
            user.decrementFuel();
            frameCount = 0; // Reset the frame count
        }
    }
}