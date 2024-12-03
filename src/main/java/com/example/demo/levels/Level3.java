package com.example.demo.levels;

import com.example.demo.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class Level3 extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/level3Background.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static double SCREEN_WIDTH;
    private static double SCREEN_HEIGHT;
    private final UserTank user;

    private final List<ActiveActorDestructible> enemyRockets;

    private ImageView background;
    private LevelViewLevelThree levelView;
    private int remainingTime;

    public Level3(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        this.user = new UserTank(PLAYER_INITIAL_HEALTH);
        this.enemyRockets = new ArrayList<>();
        this.background = getBackground();
        this.coinsCollectedInLevel = 0;
        SCREEN_WIDTH = screenWidth;
        SCREEN_HEIGHT = screenHeight;
        friendlyUnits.add(user);
    }

    @Override
    protected void updateScene() {
        spawnEnemyUnits();
        updateActors();
        updateNumberOfEnemies();
        updateNumberOfCoins();
        handleEnemyPenetration();
        handleUserProjectileCollisions();
        handlePlaneCollisions();
        removeAllDestroyedActors();
        updateLevelView();
        updateSurvivalTimer();
        checkIfGameOver();

    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(user);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            System.out.println("Game Over");
            levelEndHandler.handleLevelLoss(getUser().getScore());
        } else if (remainingTime <= 0) {
            LevelManager levelManager = LevelManager.getInstance();
            GenerateLevelScore scoreCalculator = new GenerateLevelScore(getUser().getHealth(), coinsCollectedInLevel);

            int calculatedScore = scoreCalculator.calculateScore();
            String starImage = scoreCalculator.getStarImage(); // Get the corresponding star image path

            getUser().setLevelScore(levelManager.getCurrentLevelNumber(), calculatedScore);
            levelEndHandler.handleLevelCompletion(getUser().getScore(), starImage, getUser());

            levelManager.incrementCurrentLevelNumber();
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelThree(getRoot(), PLAYER_INITIAL_HEALTH, getUser().getScore());
        return levelView;
    }

    @Override
    protected void updateLevelView() {
        levelView.removeHearts(user.getHealth());
    }

    @Override
    protected void spawnEnemyUnits() {
        if (Math.random() < 0.05) { // Adjust the probability of firing
            int direction = (int) (Math.random() * 4); // 0: North, 1: East, 2: South, 3: West
            ActiveActorDestructible newEnemyRocket = switch (direction) {
                case 0 -> new EnemyRocket(SCREEN_WIDTH / 2 - 50, 0, direction);
                case 1 -> new EnemyRocket(SCREEN_WIDTH, 300, direction);
                case 2 -> new EnemyRocket(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT, direction);
                case 3 -> new EnemyRocket(0, 300, direction);
                default -> null; // Fallback
            };
            if (newEnemyRocket != null) {
                addEnemyUnit(newEnemyRocket);
            }
        }
    }

    @Override
    protected void spawnCoinUnits() {}

    @Override
    protected void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyRockets);
    }

    @Override
    protected void handleUserProjectileCollisions() {
        handleCollisions(userProjectiles, enemyRockets);
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
                    user.faceNorth(); // Shoot NORTH
                }
                if (kc == KeyCode.DOWN) {
                    user.faceSouth(); // Shoot SOUTH
                }
                if (kc == KeyCode.RIGHT) {
                    user.faceEast(); // Shoot EAST
                }
                if (kc == KeyCode.LEFT) {
                    user.faceWest(); // Shoot WEST
                }
                if (kc == KeyCode.SPACE) fireProjectile();
            }
        });
        getRoot().getChildren().add(background);
    }

    @Override
    protected void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyRockets.forEach(plane -> plane.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
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
        return ((Math.abs(enemy.getTranslateX()) > getScreenWidth()) || ((Math.abs(enemy.getTranslateX()) > getScreenHeight())));
    }

    @Override
    public Scene initializeScene() {
        super.initializeScene();
        return scene;
    }

    @Override
    boolean isEnemyPlaneOverlapping(ActiveActorDestructible newEnemyRocket) {
        for (ActiveActorDestructible enemy : enemyRockets) {
            if (newEnemyRocket.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    protected void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(enemyRockets);
        removeDestroyedActors(userProjectiles);
    }

    @Override
    protected boolean userIsDestroyed() {
        return user.isDestroyed();
    }
}