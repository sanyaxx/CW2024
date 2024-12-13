package com.example.demo.levels.Level3;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.enemyPlanes.EnemyRocket;
import com.example.demo.actors.Planes.friendlyPlanes.UserTank;
import com.example.demo.actors.additionalUnits.Coins;
import com.example.demo.actors.additionalUnits.Magnet;
import com.example.demo.levels.LevelParent;
import com.example.demo.levels.LevelView;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

/**
 * Represents Level 3 in the game, extending LevelParent to define specific gameplay mechanics.
 */
public class Level3 extends LevelParent {

    /**
     * The background image file path for Level 3.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/level3Background.jpg";

    /**
     * The initial health of the player at the start of Level 3.
     */
    private static final int PLAYER_INITIAL_HEALTH = 5;

    /**
     * The initial bullet count of the player at the start of Level 3.
     */
    private static final int PLAYER_BULLET_COUNT = 70;

    /**
     * The time (in seconds) the player needs to survive in Level 3.
     */
    private static final int SURVIVAL_TIME_SECONDS = 45;

    /**
     * The user-controlled tank in Level 3.
     */
    private final UserTank user;

    /**
     * Frame count used to track time in the game loop.
     */
    private int frameCount = 0;

    /**
     * The background image view for Level 3.
     */
    private ImageView background;

    /**
     * The view controller specific to Level 3.
     */
    private LevelViewLevelThree levelView;

    /**
     * The remaining time the player has to survive in Level 3.
     */
    private int remainingTime;

    /**
     * Constructs the Level3 object with the given screen dimensions.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth  the width of the screen
     */
    public Level3(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, PLAYER_BULLET_COUNT);
        this.user = new UserTank(PLAYER_INITIAL_HEALTH, PLAYER_BULLET_COUNT);
        this.background = getBackground();
        this.remainingTime = SURVIVAL_TIME_SECONDS;
        this.magnetRadius = 500;
        initializeLevel(this, user);
    }

    /**
     * Updates the game state by spawning enemies, updating the view, and checking game conditions.
     */
    @Override
    public void update() {
        super.update();
        spawnEnemyUnits();
        updateLevelView();
        updateSurvivalTimer();
        checkGameOverConditions();
    }

    /**
     * Determines if the player has lost the level.
     *
     * @return true if the player has lost, false otherwise
     */
    @Override
    protected boolean hasLevelBeenLost() {
        return user.isDestroyed || user.getBulletCount() == 0; // User loss condition
    }

    /**
     * Determines if the player has won the level.
     *
     * @return true if the player has won, false otherwise
     */
    @Override
    protected boolean hasLevelBeenWon() {
        return (remainingTime == 0); // Win condition specific to this level
    }

    /**
     * Instantiates and returns the level view for Level 3.
     *
     * @return the level view for Level 3
     */
    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelThree(getRoot(), PLAYER_INITIAL_HEALTH, PLAYER_BULLET_COUNT, SURVIVAL_TIME_SECONDS, 0);
        return levelView;
    }

    /**
     * Updates the level view with current player stats such as health, bullet count, remaining time, and coins collected.
     */
    @Override
    protected void updateLevelView() {
        levelView.updateHeartDisplay(user.getHealth());
        levelView.updateWinningParameterDisplay(user.getBulletCount(), remainingTime, user.getCoinsCollected()); // Update the display
    }

    /**
     * Spawns enemy rockets at random directions and positions on the screen.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (Math.random() < 0.05) { // Adjust the probability of firing
            int direction = (int) (Math.random() * 4); // 0: North, 1: East, 2: South, 3: West
            GameEntity newEnemyRocket = switch (direction) {
                case 0 -> new EnemyRocket(screenWidth / 2 - 50, 0, direction);
                case 1 -> new EnemyRocket(screenWidth, screenHeight / 2 - 80, direction);
                case 2 -> new EnemyRocket(screenWidth / 2 - 50, screenHeight, direction);
                case 3 -> new EnemyRocket(0, screenHeight / 2 - 80, direction);
                default -> null; // Fallback
            };
            if (newEnemyRocket != null) {
                actorManager.addActor(newEnemyRocket);
            }
        }
    }

    /**
     * Spawns a magnet at a random position with a certain probability.
     */
    protected void spawnMagnet() {
        if (Math.random() < 0.009) { // Adjust the probability of spawning
            int direction = (int) (Math.random() * 4); // 0: North, 1: East, 2: South, 3: West
            GameEntity newMagnet = switch (direction) {
                case 0 -> new Magnet(screenWidth / 2 - 50, 0, direction);
                case 1 -> new Magnet(screenWidth, screenHeight / 2 - 80, direction);
                case 2 -> new Magnet(screenWidth / 2 - 50, screenHeight, direction);
                case 3 -> new Magnet(0, screenHeight / 2 - 80, direction);
                default -> null; // Fallback
            };
            if (newMagnet != null) {
                actorManager.addActor(newMagnet);
            }
        }
    }

    /**
     * Spawns coins at random positions with a defined spawn probability and max count.
     */
    protected void spawnCoinUnits() {
        currentNumberOfCoins += spawnHandler.spawnActors(
                () -> new Coins(screenWidth, Math.random() * getEnemyMaximumYPosition(), magnetRadius), // Supplier for new coins
                10, // Maximum spawn at a time
                0.15, // Spawn probability
                currentNumberOfCoins, // Current count
                20 // Total allowed
        );
    }

    /**
     * Fires a projectile from the player's tank and adds it to the actor manager.
     */
    @Override
    protected void fireProjectile() {
        GameEntity projectile = user.fireProjectile();
        actorManager.addActor(projectile);
    }

    /**
     * Sets up input handlers for player controls such as movement and firing projectiles.
     */
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

    /**
     * Handles the situation when enemies or other entities penetrate the player's defenses.
     */
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

    /**
     * Initializes and returns the scene for Level 3, including user setup.
     *
     * @return the scene for Level 3
     */
    @Override
    public Scene initializeScene() {
        super.initializeScene();
        user.faceEast();
        return scene;
    }

    /**
     * Updates the survival timer by reducing the remaining time in 1-second intervals.
     */
    protected void updateSurvivalTimer() {
        frameCount++;

        // Check if 2 frames have passed (20 * 50ms = 1000ms)
        if (frameCount >= 20) {
            remainingTime--; // Update remaining time
            frameCount = 0; // Reset the frame count
        }
    }

    /**
     * Checks if the game has ended by either the player losing or winning the level.
     */
    protected void checkGameOverConditions() {
        // Check if the user has lost
        if (hasLevelBeenLost()) {
            overlayHandler.showRedeemLife(getRoot(), user);
        }

        // Check if the user has won
        if (hasLevelBeenWon()) {
            overlayHandler.handleLevelCompletion(getRoot(), user);
        }
    }
}
