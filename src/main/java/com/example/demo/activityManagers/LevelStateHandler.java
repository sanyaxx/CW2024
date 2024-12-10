package com.example.demo.activityManagers;

import com.example.demo.actors.Planes.friendlyPlanes.UserParent;
import com.example.demo.functionalClasses.GenerateLevelScore;
import com.example.demo.controller.AppStage;
import com.example.demo.controller.GameLoop;
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
    private UserParent userInstance;
    private Group root;

    private static final String COINS_TEXT = "Total Coins: %d coins";

    private final Object[][] completedOverlayButtons;
    private final Object[][] lostOverlayButtons;
    private final Object[][] redeemLifeButton;
    private final Object[][] pauseOverlayButtons;
    private final Object[][] nextButton;

    public LevelStateHandler() {
        this.timeline = GameLoop.getInstance().getTimeline();
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

    public void handleLevelCompletion(Group root, UserParent user) {
        stopTimeline();
        setContext(root, user);

        int calculatedScore = levelScore.calculateScore(user.getHealth(), user.getBulletCount());
        userStatsManager.setLevelScore(calculatedScore);
        userStatsManager.setTotalCoinsCollected(user.getCoinsCollected());
        userStatsManager.setTotalKillCount(user.getInLevelKillCount());

        String starImage = levelScore.getStarImagePath(calculatedScore);
        String message = String.format(COINS_TEXT, user.getCoinsCollected());

        showOverlay("Level Completed!", message, completedOverlayButtons, starImage);
    }

    public void showRedeemLife(Group root, UserParent user) {
        pauseTimeline();
        setContext(root, user);

        String message = String.format(COINS_TEXT, user.getCoinsCollected());
        BaseOverlay overlay = overlayFactory.createOverlay(root, "Continue level?", message, redeemLifeButton, null);

        redeemLifeTimer = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            overlay.hide();
            handleLevelLoss(root, user);
        }));
        redeemLifeTimer.setCycleCount(1);

        overlay.show();
        redeemLifeTimer.play();
    }

    public void handleLevelLoss(Group root, UserParent user) {
        stopTimeline();
        setContext(root, user);
        user.destroyUser();

        userStatsManager.setTotalCoinsCollected(user.getCoinsCollected());
        userStatsManager.setTotalKillCount(user.getInLevelKillCount());

        String starImage = levelScore.getStarImagePath(0);
        String message = String.format(COINS_TEXT, user.getCoinsCollected());

        showOverlay("Level Failed!", message, lostOverlayButtons, starImage);
    }

    public void insufficientInventory() {
        String message = String.format(COINS_TEXT, userInstance.getCoinsCollected());
        showOverlay("Insufficient Balance!", message, nextButton, null);
    }

    public void handleLevelPaused(Group root, UserParent user) {
        stopTimeline();
        setContext(root, user);

        String message = String.format(COINS_TEXT, user.getCoinsCollected());
        showOverlay("Paused", message, pauseOverlayButtons, null);
    }

    private void reviveLevel() {
        if (redeemLifeTimer != null) redeemLifeTimer.stop();

        if (userInstance.getCoinsCollected() >= 4) {
            System.out.println("Reviving current level...");
            userInstance.decrementCoinsCollected();

            if (userInstance.getHealth() <= 0) {
                userInstance.reviveUserLife();
            } else if (userInstance.getBulletCount() <= 0) {
                userInstance.reviveBulletCount();
            } else {
                userInstance.reviveUserFuel();
            }

            playTimeline();
        } else {
            insufficientInventory();
        }
    }

    private void setContext(Group root, UserParent user) {
        this.root = root;
        this.userInstance = user;
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
            new YouWinScreen();
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
