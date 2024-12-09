package com.example.demo.levels.Level3;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.enemyPlanes.EnemyRocket;
import com.example.demo.actors.Planes.friendlyPlanes.UserTank;
import com.example.demo.levels.LevelParent;
import com.example.demo.levels.LevelView;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Level3 extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/level3Background.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int SURVIVAL_TIME_SECONDS = 15; // Time to survive in seconds
    private final UserTank user;
    private int frameCount = 0;

    private ImageView background;
    private LevelViewLevelThree levelView;
    private int remainingTime;

    public Level3(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        this.user = new UserTank(PLAYER_INITIAL_HEALTH);
        this.background = getBackground();
        this.remainingTime = SURVIVAL_TIME_SECONDS;

        this.bulletCount = 50;
        userStatsManager.setBulletCount(50);

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
        return user.isDestroyed || bulletCount == 0; // User loss condition
    }

    @Override
    protected boolean hasLevelBeenWon() {
        return (remainingTime == 0); // Win condition specific to this level
    }

    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelThree(getRoot(), PLAYER_INITIAL_HEALTH, bulletCount, SURVIVAL_TIME_SECONDS);
        return levelView;
    }

    @Override
    protected void updateLevelView() {
        levelView.removeHearts(user.getHealth());
        levelView.updateWinningParameterDisplay(bulletCount, remainingTime); // Update the display
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

//    protected void spawnCoinUnits() {
//    if (Math.random() < 0.03) { // Adjust the probability of firing
//        int direction = (int) (Math.random() * 4); // 0: North, 1: East, 2: South, 3: West
//        GameEntity newEnemyRocket = switch (direction) {
//            case 0 -> new Coins(screenWidth / 2 - 50, 0, direction);
//            case 1 -> new Coins(screenWidth, 300, direction);
//            case 2 -> new Coins(screenWidth / 2 - 50, screenHeight, direction);
//            case 3 -> new Coins(0, 300, direction);
//            default -> null; // Fallback
//        };
//        if (newEnemyRocket != null) {
//            actorManager.addActor(newEnemyRocket);
//        }
//    }


    @Override
    protected void spawnCoinUnits() {}

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
                    decrementBulletCount();
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
    protected final void checkGameOverConditions() {
        // Check if the user has lost
        if (hasLevelBeenLost()) {
            levelStateHandler.handleLevelLoss(getRoot(), getUser());
        }

        // Check if the user has won
        if (hasLevelBeenWon()) {
            levelStateHandler.handleLevelCompletion(getRoot(), getUser());
        }
    }

}