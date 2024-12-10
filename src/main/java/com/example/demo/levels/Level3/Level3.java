package com.example.demo.levels.Level3;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.enemyPlanes.EnemyRocket;
import com.example.demo.actors.Planes.friendlyPlanes.UserParent;
import com.example.demo.actors.Planes.friendlyPlanes.UserTank;
import com.example.demo.actors.additionalUnits.Coins;
import com.example.demo.actors.additionalUnits.Magnet;
import com.example.demo.levels.LevelParent;
import com.example.demo.levels.LevelView;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

public class Level3 extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/level3Background.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int PLAYER_BULLET_COUNT = 50;
    private static final int SURVIVAL_TIME_SECONDS = 15; // Time to survive in seconds
    private final UserTank user;
    private int frameCount = 0;

    private ImageView background;
    private LevelViewLevelThree levelView;
    private int remainingTime;

    public Level3(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, PLAYER_BULLET_COUNT);
        this.user = new UserTank(PLAYER_INITIAL_HEALTH, PLAYER_BULLET_COUNT);
        this.background = getBackground();
        this.remainingTime = SURVIVAL_TIME_SECONDS;
        this.magnetRadius = 500;
        initializeLevel(this, user);
    }

    @Override
    public void update() {
        super.update();
        spawnEnemyUnits();
        updateLevelView();
        updateSurvivalTimer();
        checkGameOverConditions();
    }

    @Override
    protected boolean hasLevelBeenLost() {
        return user.isDestroyed || user.getBulletCount() == 0; // User loss condition
    }

    @Override
    protected boolean hasLevelBeenWon() {
        return (remainingTime == 0); // Win condition specific to this level
    }

    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelThree(getRoot(), PLAYER_INITIAL_HEALTH, PLAYER_BULLET_COUNT, SURVIVAL_TIME_SECONDS, 0);
        return levelView;
    }

    @Override
    protected void updateLevelView() {
        levelView.removeHearts(user.getHealth());
        levelView.updateWinningParameterDisplay(user.getBulletCount(), remainingTime, user.getCoinsCollected()); // Update the display
    }

    @Override
    protected void spawnEnemyUnits() {
        if (Math.random() < 0.05) { // Adjust the probability of firing
            int direction = (int) (Math.random() * 4); // 0: North, 1: East, 2: South, 3: West
            GameEntity newEnemyRocket = switch (direction) {
                case 0 -> new EnemyRocket(screenWidth / 2 - 50, 0, direction);
                case 1 -> new EnemyRocket(screenWidth, 350, direction);
                case 2 -> new EnemyRocket(screenWidth / 2 - 50, screenHeight, direction);
                case 3 -> new EnemyRocket(0, 350, direction);
                default -> null; // Fallback
            };
            if (newEnemyRocket != null) {
                actorManager.addActor(newEnemyRocket);
            }
        }
    }

    protected void spawnMagnet() {
    if (Math.random() < 0.009) { // Adjust the probability of spawning
        int direction = (int) (Math.random() * 4); // 0: North, 1: East, 2: South, 3: West
        GameEntity newMagnet = switch (direction) {
            case 0 -> new Magnet(screenWidth / 2 - 50, 0, direction);
            case 1 -> new Magnet(screenWidth, 350, direction);
            case 2 -> new Magnet(screenWidth / 2 - 50, screenHeight, direction);
            case 3 -> new Magnet(0, 350, direction);
            default -> null; // Fallback
        };
        if (newMagnet != null) {
            actorManager.addActor(newMagnet);
        }}
    }

    protected void spawnCoinUnits() {
        currentNumberOfCoins += spawnHandler.spawnActors(
                () -> new Coins(screenWidth, Math.random() * getEnemyMaximumYPosition(), magnetRadius), // Supplier for new coins
                10, // Maximum spawn at a time
                0.15, // Spawn probability
                currentNumberOfCoins, // Current count
                20 // Total allowed
        );
    }

    @Override
    protected void fireProjectile() {
        GameEntity projectile = user.fireProjectile();
        actorManager.addActor(projectile);
    }

    @Override
    protected void setUpInputHandler() {
        inputHandler.setupInputHandlers(
                user,
                user::faceNorth,null,
                user::faceSouth,null,
                user::faceEast,null,
                user::faceWest,null,
                () -> {
                    fireProjectile();
                    user.decrementBulletCount();
                }
        );
        background.setOnKeyPressed(inputHandler.getKeyPressHandler(user));
        background.setOnKeyReleased(inputHandler.getKeyReleaseHandler(user));
    }

    @Override
    protected void handleDefensesPenetration() {
        for (GameEntity actor : actorManager.getActiveActors()) {
            if (entityHasPenetratedDefenses(actor)) {
                if (actor instanceof EnemyRocket) {
                    currentNumberOfEnemies--;
                }
                if (actor instanceof Magnet) {
                    currentNumberOfMagnets = 0;
                }
                actor.takeDamage();
            }
        }
    }

    @Override
    public Scene initializeScene() {
        super.initializeScene();
        user.faceEast();
        return scene;
    }

    protected void updateSurvivalTimer() {
        frameCount++;

        // Check if 2 frames have passed (20 * 50ms = 1000ms)
        if (frameCount >= 20) {
            remainingTime--; // Update remaining time
            frameCount = 0; // Reset the frame count
        }
    }

    @Override
    protected void checkGameOverConditions() {
        // Check if the user has lost
        if (hasLevelBeenLost()) {
            levelStateHandler.showRedeemLife(getRoot(), user);
        }

        // Check if the user has won
        if (hasLevelBeenWon()) {
            levelStateHandler.handleLevelCompletion(getRoot(), user);
        }
    }
}