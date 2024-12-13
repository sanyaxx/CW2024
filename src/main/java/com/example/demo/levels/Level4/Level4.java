package com.example.demo.levels.Level4;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.enemyPlanes.EnemyRocket;
import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.actors.additionalUnits.*;
import com.example.demo.levels.LevelParent;
import com.example.demo.levels.LevelView;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

/**
 * The Level4 class defines the fourth level of the game. This level challenges the user to survive for a fixed amount of time
 * while managing fuel, avoiding obstacles, and shooting down enemies.
 */
public class Level4 extends LevelParent {

    /** The background image for level 4. */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/level4Background.jpg";

    /** The initial health of the player at the start of the level. */
    private static final int PLAYER_INITIAL_HEALTH = 5;

    /** The amount of time the player must survive in seconds. */
    private static final int SURVIVAL_TIME_SECONDS = 45;

    /** The fuel capacity of the player at the start of the level. */
    private static final int PLAYER_FUEL_CAPACITY = 8;

    /** The initial bullet count for the player at the start of the level. */
    private static final int PLAYER_BULLET_COUNT = 100;

    /** The dimensions of obstacles. */
    private static double OBSTACLE_DIMENSIONS = Obstacle.getDimensions();

    /** The UserPlane instance representing the player's aircraft. */
    private final UserPlane user;

    /** A frame counter used to track the time for survival and fuel consumption. */
    private int frameCount = 0;

    /** The current number of fuel tokens in the level. */
    private int currentNumberOfFuelTokens;

    /** The current number of obstacles in the level. */
    private int currentNumberOfObstacles;

    /** The background image view for the level. */
    private ImageView background;

    /** The view that displays the level's UI elements (health, fuel, etc.). */
    private LevelViewLevelFour levelView;

    /** The remaining time for the player to survive. */
    private int remainingTime;

    /** A flag indicating whether the finish line has been spawned. */
    private boolean isFinishLineSpawned;

    /**
     * Constructs a Level4 instance.
     *
     * @param screenHeight The height of the screen for the game.
     * @param screenWidth The width of the screen for the game.
     */
    public Level4(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, PLAYER_BULLET_COUNT);
        this.user = getUser();
        user.setFuelCapacity(PLAYER_FUEL_CAPACITY);
        this.background = getBackground();

        this.remainingTime = SURVIVAL_TIME_SECONDS;
        this.isFinishLineSpawned = false;

        initializeLevel(this, user);
    }

    /**
     * Updates the state of the level. This method spawns enemies, fuel tokens, obstacles,
     * and the finish line while updating the survival timer.
     */
    @Override
    public void update() {
        super.update();
        updateLevelView();
        updateSurvivalTimer();

        // If 7 seconds are left, spawn the finish line
        if (remainingTime == 7 && !isFinishLineSpawned) {
            spawnFinishLine();
            isFinishLineSpawned = true; // Set the flag to prevent further spawns
        } else {
            spawnEnemyUnits();
            spawnFuelUnits();
        }
        spawnObstacleUnits();
    }

    /**
     * Initializes the scene for the level, including resetting the player's fuel.
     *
     * @return The Scene object representing the level.
     */
    @Override
    public Scene initializeScene() {
        super.initializeScene();
        user.resetFuelLeft();
        return scene;
    }

    /**
     * Determines if the level has been lost. The level is lost if the player is destroyed,
     * runs out of fuel while still needing to survive, or runs out of bullets.
     *
     * @return True if the level has been lost, otherwise false.
     */
    @Override
    protected boolean hasLevelBeenLost() {
        return ((user.isDestroyed) || ((user.getFuelLeft() == 0) && remainingTime > 0)) || user.getBulletCount() == 0;
    }

    /**
     * Determines if the level has been won. The level is won if the player has survived for the full duration.
     *
     * @return True if the level has been won, otherwise false.
     */
    @Override
    protected boolean hasLevelBeenWon() {
        return (remainingTime == 0); // Win condition specific to this level
    }

    /**
     * Instantiates the LevelView object for this level, which manages UI elements such as health,
     * fuel, bullets, and coins.
     *
     * @return The LevelView object.
     */
    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelFour(getRoot(), PLAYER_INITIAL_HEALTH, PLAYER_FUEL_CAPACITY, PLAYER_BULLET_COUNT, getUser().getCoinsCollected());
        return levelView;
    }

    /**
     * Updates the level view, including updating the heart display (health) and other game parameters.
     */
    @Override
    protected void updateLevelView() {
        levelView.updateHeartDisplay(user.getHealth());
        levelView.updateWinningParameterDisplay(user.getFuelLeft(), user.getBulletCount(), user.getCoinsCollected());
    }

    /**
     * Spawns enemy units in the level. This method controls the spawning of enemy rockets.
     */
    @Override
    protected void spawnEnemyUnits() {
        currentNumberOfEnemies += spawnHandler.spawnActors(
                () -> new EnemyRocket(screenWidth, (100 + (Math.random() * (getEnemyMaximumYPosition() - 100)))/2, 1), // Supplier for new enemies
                5, // Maximum spawn at a time
                0.03, // Spawn probability
                currentNumberOfEnemies, // Current count
                5 // Total allowed
        );
    }

    /**
     * Spawns fuel tokens in the level.
     */
    protected void spawnFuelUnits() {
        currentNumberOfFuelTokens += spawnHandler.spawnActors(
                () -> new FuelToken(screenWidth / 0.75 * Math.random(), (100 + (Math.random() * (getEnemyMaximumYPosition() - 100)))/2), // Supplier for new enemies
                4, // Maximum spawn at a time
                0.2, // Spawn probability
                currentNumberOfFuelTokens, // Current count
                10 // Total allowed
        );
    }

    /**
     * Spawns obstacles in the level.
     */
    protected void spawnObstacleUnits() {
        currentNumberOfObstacles += spawnHandler.spawnActors(
                () -> new Obstacle(screenWidth - OBSTACLE_DIMENSIONS, screenHeight - OBSTACLE_DIMENSIONS), // Supplier for new enemies
                5, // Maximum spawn at a time
                0.06, // Spawn probability
                currentNumberOfObstacles, // Current count
                10 // Total allowed
        );
    }

    /**
     * Spawns the finish line at the top of the screen.
     */
    protected void spawnFinishLine() {
        GameEntity finishLineInstance = new FinishLine(screenWidth, 0);
        actorManager.addActor(finishLineInstance);
    }

    /**
     * Handles the penetration of defenses by actors (e.g., coins, enemy rockets, fuel tokens, obstacles, magnets).
     * If an actor penetrates defenses, appropriate actions are taken.
     */
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

    /**
     * Sets up input handlers for user controls such as movement and projectile firing.
     */
    @Override
    protected void setUpInputHandler() {
        inputHandler.setupInputHandlers(
                user,
                user::moveUp, user::fallDown,
                null, null,
                null, null,
                null, null,
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

    /**
     * Updates the survival timer for the level. Decreases the remaining time and fuel over time.
     */
    protected void updateSurvivalTimer() {
        frameCount++;

        // Check if 2 frames have passed (20 * 50ms = 1000ms)
        if (frameCount >= 20) {
            remainingTime--; // Update remaining time
            user.decrementFuel(); // Decrease fuel over time
            frameCount = 0; // Reset the frame count
        }
    }
}
