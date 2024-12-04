package com.example.demo.levels.Level4;

import com.example.demo.activityManagers.LevelManager;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.Planes.enemyPlanes.EnemyRocket;
import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.actors.additionalUnits.FinishLine;
import com.example.demo.actors.additionalUnits.FuelToken;
import com.example.demo.actors.additionalUnits.Obstacle;
import com.example.demo.functionalClasses.GenerateLevelScore;
import com.example.demo.levels.LevelParent;
import com.example.demo.levels.LevelView;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class Level4 extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/level4Background.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int SURVIVAL_TIME_SECONDS = 30; // Time to survive in seconds
    private static double SCREEN_WIDTH;
    private static double SCREEN_HEIGHT;
    private static double OBSTACLE_DIMENSIONS;
    private final UserPlane user;
    private boolean collisionCooldownActive = false;

    // Timing variables for obstacle spawning
    private static final long OBSTACLE_SPAWN_INTERVAL_NANOS = 500_000_000L; // 1 second in nanoseconds
    private long lastObstacleSpawnTime = 0; // Track the last spawn time
    private long lastCollisionTime = 0;
    private int currentNumberOfFuelUnits;
    private int offScreenFuelCount;
    private static final long COLLISION_COOLDOWN_DURATION = 3_000_000_000L; // 3 seconds in nanoseconds
    private boolean finishLineSpawned = false;

    private final List<ActiveActorDestructible> enemyRockets;
    private final List<ActiveActorDestructible> obstacles;
    private final List<ActiveActorDestructible> fuelUnits;// List for mountains
    private final List<ActiveActorDestructible> finishLine;

    private ImageView background;
    private LevelViewLevelFour levelView;
    private AnimationTimer timer;
    private int remainingTime;

    public Level4(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        this.user = getUser();
        this.remainingTime = SURVIVAL_TIME_SECONDS;
        this.fuelUnits = new ArrayList<>();
        this.enemyRockets = new ArrayList<>();
        this.obstacles = new ArrayList<>();
        this.finishLine = new ArrayList<>();
        this.background = getBackground();
        this.coinsCollectedInLevel = 0;

        SCREEN_WIDTH = screenWidth;
        SCREEN_HEIGHT = screenHeight;
        OBSTACLE_DIMENSIONS = Obstacle.getImageHeight();
    }

        @Override
        protected void updateScene() {
            if (!finishLineSpawned) {
                spawnEnemyUnits();
                spawnFuelUnits();
            }
            spawnObstacleUnits();
            updateActors();
            generateEnemyFire();
            updateNumberOfEnemies();
            handleEnemyPenetration();
            handleFuelMovesOffScreen();
            updateNumberOfFuelUnits();
            handleUserCollisionsWithFuel();
            handleUserCollisionWithObstacles();
            handleUserProjectileCollisions();
            handlePlaneCollisions();
            removeAllDestroyedActors();
            updateLevelView();
            checkIfGameOver();
            handleUserCollisionWithFinishLine(); // Check for collision with finish line
        }

        // Call the method to draw bounding boxes
        // drawBoundingBoxes(); // Add this line to visualize the bounding boxes


    @Override
    public Scene initializeScene() {
        initializeBackground();
        initializeFriendlyUnits();
        levelView.showHeartDisplay();
        levelView.showWinningParameterDisplay();
        levelView.showPauseButton();
        user.setFuelLeft();
        startSurvivalTimer();
        return scene;
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(user);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed() || user.getFuelLeft() <= 0) {
            System.out.println("Game Over");
            timer.stop();
            levelEndHandler.handleLevelLoss(getUser().getScore());
        }
    }

    @Override
    protected boolean userIsDestroyed() {
        return user.isDestroyed();
    }

    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelFour(getRoot(), PLAYER_INITIAL_HEALTH, getUser ().getScore());
        return levelView;
    }

    @Override
    protected void updateLevelView() {
        levelView.removeHearts(user.getHealth());
        levelView.updateWinningParameterDisplay(user.getFuelLeft());
    }

    @Override
    protected void spawnEnemyUnits() {
        if (Math.random() < 0.03) {
            ActiveActorDestructible newEnemyRocket = new EnemyRocket(SCREEN_WIDTH, Math.random() * (SCREEN_HEIGHT / 2), 1);
            addEnemyUnit(newEnemyRocket);
        }
    }


    protected void spawnFuelUnits() {
        if (Math.random() < 0.03) { // Adjust the probability of spawning Fuels
            ActiveActorDestructible newFuel = new FuelToken(SCREEN_WIDTH * Math.random(), Math.random() * (SCREEN_HEIGHT / 2));
            fuelUnits.add(newFuel);
            getRoot().getChildren().add(newFuel);
        }
    }

    protected void spawnObstacleUnits() {
        long currentTime = System.nanoTime(); // Get the current time in nanoseconds

        // Check if enough time has passed since the last obstacle was spawned
        if (currentTime - lastObstacleSpawnTime >= OBSTACLE_SPAWN_INTERVAL_NANOS) {
            // Spawn the obstacle at a fixed position
            ActiveActorDestructible newObstacle = new Obstacle(SCREEN_WIDTH - OBSTACLE_DIMENSIONS, SCREEN_HEIGHT - OBSTACLE_DIMENSIONS);
            if (!isOverlapping(newObstacle, obstacles)) {
                obstacles.add(newObstacle);
                getRoot().getChildren().add(newObstacle);
                newObstacle.toFront();

                // Update the last spawn time
                lastObstacleSpawnTime = currentTime;
            }
        }
    }


    private void spawnFinishLine() {
        System.out.println("Spawn function reached");
        ActiveActorDestructible finishLineInstance = new FinishLine(SCREEN_WIDTH, 0); // Adjust position as needed
        finishLine.add(finishLineInstance);
        finishLineInstance.toFront();
        getRoot().getChildren().add(finishLineInstance);
        finishLineSpawned = true; // Set the flag to true after spawning
    }

    @Override
    protected void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyRockets);
    }

    @Override
    protected void handleUserProjectileCollisions() {
        handleCollisions(userProjectiles, enemyRockets);
    }


    protected void handleUserCollisionsWithFuel() {
        for (ActiveActorDestructible fuel : fuelUnits) {
            if (fuel.isDestroyed()) continue; // Skip if already destroyed
            if (user.getBoundsInParent().intersects(fuel.getBoundsInParent())) {
                fuel.takeDamage();
                user.incrementFuel();
            }
        };
    }

    protected void handleUserCollisionWithFinishLine() {
        for (ActiveActorDestructible line : finishLine) {
            if (user.getBoundsInParent().intersects(line.getBoundsInParent())) {
                timer.stop();

                LevelManager levelManager = LevelManager.getInstance();
                GenerateLevelScore scoreCalculator = new GenerateLevelScore(getUser().getHealth(), coinsCollectedInLevel);

                int calculatedScore = scoreCalculator.calculateScore();
                String starImage = scoreCalculator.getStarImage(); // Get the corresponding star image path

                getUser().setLevelScore(levelManager.getCurrentLevelNumber(), calculatedScore);
                levelEndHandler.handleLevelCompletion(getUser().getScore(), starImage, getUser());

                levelManager.incrementCurrentLevelNumber();
            }
        }
    }

    @Override
    protected void addEnemyUnit(ActiveActorDestructible newRocket) {
        enemyRockets.add(newRocket);
        getRoot().getChildren().add(newRocket);
    }

    @Override
    protected void initializeBackground() {
        background.setFocusTraversable(true);
        background.setFitHeight(SCREEN_HEIGHT);
        background.setFitWidth(SCREEN_WIDTH);
        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                if (kc == KeyCode.UP) {
                    user.moveUp(); // Move up when the up key is pressed
                }
                if (kc == KeyCode.SPACE) {
                    fireProjectile(); // Fire projectile
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

    @Override
    protected void updateActors() {
        super.updateActors();
        enemyRockets.forEach(enemyRocket -> enemyRocket.updateActor());
        obstacles.forEach(obstacle -> obstacle.updateActor());
        fuelUnits.forEach(fuel -> fuel.updateActor());
        finishLine.forEach(line -> line.updateActor());
    }

    @Override
    protected void fireProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile();
        getRoot().getChildren().add(projectile);
        userProjectiles.add(projectile);
    }

    @Override
    protected void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : enemyRockets) {
            if (enemyHasPenetratedDefenses(enemy)) {
                enemy.takeDamage();
                enemy.destroy();
            }
        }
    }

    @Override
    protected boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return (Math.abs(enemy.getTranslateX()) > getScreenWidth());
    }

    protected void handleUserCollisionWithObstacles() {
        long currentTime = System.nanoTime();

        // Check if cooldown is active
        if (collisionCooldownActive) {
            // Check if 2 seconds have passed
            if (currentTime - lastCollisionTime >= COLLISION_COOLDOWN_DURATION) {
                collisionCooldownActive = false; // Reset cooldown
                user.endCooldown();
            } else {
                return; // Exit the method if still in cooldown
            }
        }
        for (ActiveActorDestructible obstacle : obstacles) {
            if (user.getBoundsInParent().intersects(obstacle.getBoundsInParent())) {
                user.takeDamage(); // User takes damage upon collision with mountains

                user.resetPosition(); // move back to resetPosition

                // Activate cooldown
                collisionCooldownActive = true;
                user.startCooldown();
                lastCollisionTime = currentTime; // Record the time of the collision
                break; // Exit the loop after the first collision
            }
        }
    }

    private void startSurvivalTimer() {
        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1_000_000_000) { // 1 second
                    remainingTime--;
                    user.decrementFuel();
                    lastUpdate = now;

                    // Check if there are 6 seconds left to spawn the finish line
                    if (remainingTime == 6 && !finishLineSpawned) {
                        spawnFinishLine(); // Call method to spawn finish line
                    }

                    // Check if time is up
                    if (remainingTime <= 0) {
                        stop();
                    }
                }
            }
        };
        timer.start();
    }

    protected void updateNumberOfFuelUnits() {
        currentNumberOfFuelUnits = fuelUnits.size() - offScreenFuelCount;
        offScreenFuelCount = 0;
    }

    protected void handleFuelMovesOffScreen() {
        for (ActiveActorDestructible fuel : fuelUnits) {
            if (enemyHasPenetratedDefenses(fuel)) {
                fuel.takeDamage();
                offScreenFuelCount++;
                updateNumberOfCoins();
            }
        }
    }

    @Override
    protected void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyRockets);
        removeDestroyedActors(fuelUnits);
    }
}