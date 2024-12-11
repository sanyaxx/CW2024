package com.example.demo.activityManagers;

import com.example.demo.actors.Planes.friendlyPlanes.UserParent;
import com.example.demo.controller.GameLoop;
import com.example.demo.functionalClasses.GenerateLevelScore;
import com.example.demo.screensAndOverlays.BaseOverlay;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;

public class OverlayHandler {

    private static OverlayHandler instance;
    private Timeline timeline;
    private OverlayFactory overlayFactory;
    private GenerateLevelScore generateLevelScore;
    private UserStatsManager userStatsManager;
    private UserParent user;
    private Group root;
    private BaseOverlay currentOverlay;
    protected Timeline redeemLifeTimer;

    private static final String COINS_TEXT = "Total Coins: %d coins";

    private Object[][] completedOverlayButtons;
    private Object[][] lostOverlayButtons;
    private Object[][] redeemLifeButton;
    private Object[][] pauseOverlayButtons;
    private Object[][] nextButton;

    public OverlayHandler() {

        this.timeline = GameLoop.getInstance().getTimeline();
        this.generateLevelScore = new GenerateLevelScore();
        this.overlayFactory = OverlayFactory.getInstance();
        this.userStatsManager = UserStatsManager.getInstance();
        LevelStateHandler levelStateHandler = LevelStateHandler.getInstance();

        this.completedOverlayButtons = new Object[][]{
                {"/com/example/demo/images/menuButton.png", (Runnable) levelStateHandler::goToMainMenu},
                {"/com/example/demo/images/restartButton.png", (Runnable) levelStateHandler::retryLevel},
                {"/com/example/demo/images/nextButton.png", (Runnable) levelStateHandler::goToNextLevel}
        };

        this.lostOverlayButtons = new Object[][]{
                {"/com/example/demo/images/menuButton.png", (Runnable) levelStateHandler::goToMainMenu},
                {"/com/example/demo/images/restartButton.png", (Runnable) levelStateHandler::retryLevel}
        };

        this.redeemLifeButton = new Object[][]{
                {"/com/example/demo/images/heart.png", (Runnable) () -> levelStateHandler.reviveLevel(this.user)}
        };

        this.pauseOverlayButtons = new Object[][]{
                {"/com/example/demo/images/resumeButton.png", (Runnable) levelStateHandler::resumeLevel},
                {"/com/example/demo/images/menuButton.png", (Runnable) levelStateHandler::goToMainMenu},
                {"/com/example/demo/images/volumeButton.png", (Runnable) levelStateHandler::toggleVolume},
                {"/com/example/demo/images/restartButton.png", (Runnable) levelStateHandler::retryLevel}
        };

        this.nextButton = new Object[][]{
                {"/com/example/demo/images/nextButton.png", (Runnable) () -> levelStateHandler.goToLevelLostPage(this.root, this.user)}
        };

    }

    // Static method to get the single instance of LevelStateHandler
    public static OverlayHandler getInstance() {
        if (instance == null) {
            instance = new OverlayHandler();
        }
        return instance;
    }

    // Lazy initialization of LevelStateHandler
    private LevelStateHandler getLevelStateHandler() {
        return LevelStateHandler.getInstance();
    }


    private void setContext(Group root, UserParent user) {
        this.root = root;
        this.user = user;;
    }

    private void showOverlay(String title, String message, Object[][] buttons, String imagePath) {
        BaseOverlay overlay = overlayFactory.createOverlay(root, title, message, buttons, imagePath);
        currentOverlay = overlay;
        overlay.show();
    }

    private void hideOverlay() {
        currentOverlay.hide();
    }

    public void handleLevelCompletion(Group root, UserParent user) {
        if (currentOverlay != null) {
            hideOverlay();
        }

        stopTimeline();
        setContext(root, user);

        userStatsManager.storeLevelStatistics(user, user.getInLevelKillCount(), user.getCoinsCollected());
        String starImage = generateLevelScore.getStarImagePath(userStatsManager.calculateLevelScore(user));
        String message = String.format(COINS_TEXT, user.getCoinsCollected());

        showOverlay("Level Completed!", message, completedOverlayButtons, starImage);
    }

    public void showRedeemLife(Group root, UserParent user) {
        pauseTimeline();
        setContext(root, user);

        // Hide the current overlay if it exists
        if (currentOverlay != null) {
            hideOverlay();
        }

        // Prepare the message to show
        String message = String.format(COINS_TEXT, user.getCoinsCollected());
        showOverlay("Continue level?", message, redeemLifeButton, null);

        // Stop the existing timer if it's running
        if (redeemLifeTimer != null) {
            redeemLifeTimer.stop();
        }

        // Create a new timer for 5 seconds
        redeemLifeTimer = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            handleLevelLoss(root, user);
        }));
        redeemLifeTimer.setCycleCount(1);
        redeemLifeTimer.play();
    }

    public void handleLevelLoss(Group root, UserParent user) {
        stopTimeline();
        setContext(root, user);
        user.destroyUser();

        if (currentOverlay != null) {
            hideOverlay();
        }
        userStatsManager.storeLevelStatistics(user, user.getInLevelKillCount(), user.getCoinsCollected());

        String starImage = generateLevelScore.getStarImagePath(0);
        String message = String.format(COINS_TEXT, user.getCoinsCollected());

        showOverlay("Level Failed!", message, lostOverlayButtons, starImage);
    }

    public void insufficientInventory() {
        if (currentOverlay != null) {
            hideOverlay();
        }

        String message = String.format(COINS_TEXT, user.getCoinsCollected());
        showOverlay("Insufficient Balance!", message, nextButton, null);
    }

    public void handleLevelPaused(Group root, UserParent user) {
        stopTimeline();
        setContext(root, user);

        if (currentOverlay != null) {
            hideOverlay();
        }

        String message = String.format(COINS_TEXT, user.getCoinsCollected());
        showOverlay("Paused", message, pauseOverlayButtons, null);
    }

    private void stopTimeline() {
        timeline.stop();
    }

    private void pauseTimeline() {
        timeline.pause();
    }

}
