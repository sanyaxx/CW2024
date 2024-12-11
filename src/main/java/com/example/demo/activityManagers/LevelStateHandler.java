package com.example.demo.activityManagers;

import com.example.demo.actors.Planes.friendlyPlanes.UserParent;
import com.example.demo.controller.AppStage;
import com.example.demo.controller.GameLoop;
import com.example.demo.screensAndOverlays.*;
import javafx.animation.Timeline;
import javafx.scene.Group;

public class LevelStateHandler {
    private static LevelStateHandler instance;
    private final Timeline timeline;
    private final LevelManager levelManager;
    private OverlayHandler overlayHandler;

    public LevelStateHandler() {
        this.timeline = GameLoop.getInstance().getTimeline();
        this.levelManager = LevelManager.getInstance();
    }

    // Static method to get the single instance of LevelStateHandler
    public static LevelStateHandler getInstance() {
        if (instance == null) {
            instance = new LevelStateHandler();
        }
        return instance;
    }

    // Lazy initialization of OverlayHandler
    private OverlayHandler getOverlayHandler() {
        if (overlayHandler == null) {
            overlayHandler = OverlayHandler.getInstance();
        }
        return overlayHandler;
    }

    private void playTimeline() {
        timeline.play();
    }

    public void goToMainMenu() {
        System.out.println("Navigating to Main Menu...");
        new StartScreen(AppStage.getInstance().getPrimaryStage()).show();
    }

    public void goToNextLevel() {
        if (levelManager.isLastLevel()) {
            new YouWinScreen();
        } else {
            levelManager.incrementCurrentLevelNumber();
            levelManager.showLevelStartScreen(levelManager.getCurrentLevelNumber());
        }
    }

    public void reviveLevel(UserParent user) {
        if (getOverlayHandler().redeemLifeTimer != null) getOverlayHandler().redeemLifeTimer.stop();

        if (user.getCoinsCollected() >= 4) {

            System.out.println("Reviving current level...");
            user.decrementCoinsCollected();

            if (user.getHealth() <= 0) {
                user.reviveUserLife();

            } else if (user.getBulletCount() <= 0) {
                user.reviveBulletCount();

            } else {
                user.reviveUserFuel();
            }
            playTimeline();
        } else {
            getOverlayHandler().insufficientInventory();
        }
    }

    public void retryLevel() {
        levelManager.showLevelStartScreen(levelManager.getCurrentLevelNumber());
    }

    public void goToLevelLostPage(Group root, UserParent user) {
        getOverlayHandler().handleLevelLoss(root, user);
    }

    public void resumeLevel() {
        if (getOverlayHandler().redeemLifeTimer != null) getOverlayHandler().redeemLifeTimer.stop();
        playTimeline();
    }

    public void toggleVolume() {
        System.out.println("Volume toggled...");
        playTimeline();
    }
}
