package com.example.demo.activityManagers;

import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.functionalClasses.GenerateLevelScore;
import com.example.demo.gameConfig.AppStage;
import com.example.demo.gameConfig.GameTimeline;
import com.example.demo.levels.LevelParent;
import com.example.demo.screensAndOverlays.*;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.Group;

public class LevelStateHandler {
    private final Timeline timeline;
    private Timeline redeemLifeTimer;
    private final OverlayFactory overlayFactory;
    private final LevelManager levelManager;
    private final GenerateLevelScore levelScore;
    private final UserStatsManager userStatsManager;
    private UserPlane userInstance;
    private Group root;
    private LevelParent levelInstance;

    private static final String COINS_TEXT = "Total Coins: %d coins";

    private final Object[][] completedOverlayButtons;
    private final Object[][] lostOverlayButtons;
    private final Object[][] redeemLifeButton;
    private final Object[][] pauseOverlayButtons;
    private final Object[][] nextButton;

    public LevelStateHandler() {
        this.timeline = GameTimeline.getInstance().getTimeline();
        this.levelManager = LevelManager.getInstance();
        this.levelScore = new GenerateLevelScore();
        this.overlayFactory = OverlayFactory.getInstance();
        this.userStatsManager = UserStatsManager.getInstance();

        this.completedOverlayButtons = new Object[][]{
                {"/com/example/demo/images/menuButton.png", (Runnable) this::goToMainMenu},
                {"/com/example/demo/images/restartButton.png", (Runnable) this::retryLevel},
                {"/com/example/demo/images/nextButton.png", (Runnable) this::goToNextLevel}
        };

        this.lostOverlayButtons = new Object[][]{
                {"/com/example/demo/images/menuButton.png", (Runnable) this::goToMainMenu},
                {"/com/example/demo/images/restartButton.png", (Runnable) this::retryLevel}
        };

        this.redeemLifeButton = new Object[][]{
                {"/com/example/demo/images/heart.png", (Runnable) this::reviveLevel}
        };

        this.pauseOverlayButtons = new Object[][]{
                {"/com/example/demo/images/resumeButton.png", (Runnable) this::resumeLevel},
                {"/com/example/demo/images/menuButton.png", (Runnable) this::goToMainMenu},
                {"/com/example/demo/images/volumeButton.png", (Runnable) this::toggleVolume},
                {"/com/example/demo/images/restartButton.png", (Runnable) this::retryLevel}
        };

        this.nextButton = new Object[][]{
                {"/com/example/demo/images/nextButton.png", (Runnable) this::nextPage}
        };
    }

    private void showOverlay(String title, String message, Object[][] buttons, String imagePath) {
        BaseOverlay overlay = overlayFactory.createOverlay(root, title, message, buttons, imagePath);
        overlay.show();
    }

    public void handleLevelCompletion(Group root, UserPlane user) {
        stopTimeline();
        setContext(root, user, null);

        int calculatedScore = levelScore.calculateScore(user.getHealth(), userStatsManager.getBulletCount());
        userStatsManager.setLevelScore(calculatedScore);

        String starImage = levelScore.getStarImagePath(calculatedScore);
        String message = String.format(COINS_TEXT, userStatsManager.getCoinsCollected());

        showOverlay("Level Completed!", message, completedOverlayButtons, starImage);
    }

    public void showRedeemLife(Group root, UserPlane user, LevelParent level) {
        pauseTimeline();
        setContext(root, user, level);

        String message = String.format(COINS_TEXT, userStatsManager.getCoinsCollected());
        BaseOverlay overlay = overlayFactory.createOverlay(root, "Continue level?", message, redeemLifeButton, null);

        redeemLifeTimer = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            overlay.hide();
            handleLevelLoss(root, user);
        }));
        redeemLifeTimer.setCycleCount(1);

        overlay.show();
        redeemLifeTimer.play();
    }

    public void handleLevelLoss(Group root, UserPlane user) {
        stopTimeline();
        user.destroyUser();

        String starImage = levelScore.getStarImagePath(0);
        String message = String.format(COINS_TEXT, userStatsManager.getCoinsCollected());

        showOverlay("Level Failed!", message, lostOverlayButtons, starImage);
    }

    public void insufficientInventory() {
        String message = String.format(COINS_TEXT, userStatsManager.getCoinsCollected());
        showOverlay("Insufficient Balance!", message, nextButton, null);
    }

    public void handleLevelPaused(Group root, UserPlane user) {
        stopTimeline();
        setContext(root, user, null);

        String message = String.format(COINS_TEXT, userStatsManager.getCoinsCollected());
        showOverlay("Paused", message, pauseOverlayButtons, null);
    }

    private void reviveLevel() {
        if (redeemLifeTimer != null) redeemLifeTimer.stop();

        if (userStatsManager.getCoinsCollected() >= 2) {
            System.out.println("Reviving current level...");
            userStatsManager.decrementCoins();

            if (userInstance.getHealth() <= 0) {
                userInstance.reviveUserLife();
            } else if (userStatsManager.getBulletCount() <= 0) {
                levelInstance.bulletCount += 10;
            } else {
                userInstance.reviveUserFuel();
            }

            playTimeline();
        } else {
            insufficientInventory();
        }
    }

    private void setContext(Group root, UserPlane user, LevelParent level) {
        this.root = root;
        this.userInstance = user;
        this.levelInstance = level;
    }

    private void stopTimeline() {
        timeline.stop();
    }

    private void pauseTimeline() {
        timeline.pause();
    }

    private void playTimeline() {
        timeline.play();
    }

    private void goToMainMenu() {
        System.out.println("Navigating to Main Menu...");
        new StartScreen(AppStage.getInstance().getPrimaryStage()).show();
    }

    private void goToNextLevel() {
        if (levelManager.isLastLevel()) {
            new YouWinScreen(new Group(), userInstance);
        } else {
            levelManager.incrementCurrentLevelNumber();
            levelManager.showLevelStartScreen(levelManager.getCurrentLevelNumber());
        }
    }

    private void retryLevel() {
        levelManager.showLevelStartScreen(levelManager.getCurrentLevelNumber());
    }

    private void nextPage() {
        handleLevelLoss(root, userInstance);
    }

    private void resumeLevel() {
        if (redeemLifeTimer != null) redeemLifeTimer.stop();
        playTimeline();
    }

    private void toggleVolume() {
        System.out.println("Volume toggled...");
        playTimeline();
    }
}
