package com.example.demo.activityManagers;

import com.example.demo.actors.Planes.friendlyPlanes.UserParent;
import com.example.demo.controller.GameLoop;
import com.example.demo.displays.BaseOverlay;
import com.example.demo.displays.OverlayFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;

/**
 * The OverlayHandler class is responsible for managing the game's overlay system, displaying various overlays
 * (e.g., level completion, level loss, pause screen) based on the game's current state.
 * It manages overlays such as "Level Completed", "Level Failed", "Redeem Life", "Paused", and others.
 *
 * <p>This class uses the Singleton design pattern, ensuring only one instance manages all overlays throughout
 * the game. It interacts with other components such as LevelScoreGenerator, UserStatsManager, and LevelStateHandler
 * to handle user interactions and level transitions.</p>
 *
 * <p>Original Source: This class centralizes the management of game overlays, improving code structure and
 * providing a unified interface for handling different overlay states in the game.</p>
 */
public class OverlayHandler {

    /** Singleton instance of the OverlayHandler. */
    private static OverlayHandler instance;

    /** Timeline object used for controlling the game loop and animation. */
    private Timeline timeline;

    /** Factory class used for creating overlays with dynamic content. */
    private OverlayFactory overlayFactory;

    /** Generator for calculating level score and selecting appropriate star images. */
    private LevelScoreGenerator levelScoreGenerator;

    /** Manager for handling user statistics (e.g., coins, kills). */
    private UserStatsManager userStatsManager;

    /** Manager for handling audio (e.g., coins, kills). */
    private AudioHandler audioHandler;

    /** The current user interacting with the game. */
    private UserParent user;

    /** The root node of the game scene, where overlays will be added. */
    private Group root;

    /** The currently displayed overlay. */
    private BaseOverlay currentOverlay;

    /** Timeline for managing the redeem life timer. */
    protected Timeline redeemLifeTimer;

    /** Constant format for displaying coins text in overlays. */
    private static final String COINS_TEXT = "Total Coins: %d coins";

    /** Button configurations for the completed level overlay. */
    private Object[][] completedOverlayButtons;

    /** Button configurations for the lost level overlay. */
    private Object[][] lostOverlayButtons;

    /** Button configurations for the redeem life overlay. */
    private Object[][] redeemLifeButton;

    /** Button configurations for the pause overlay. */
    private Object[][] pauseOverlayButtons;

    /** Button configurations for the next button overlay. */
    private Object[][] nextButton;

    /**
     * Constructor for OverlayHandler, initializes various components and button configurations for overlays.
     */
    public OverlayHandler() {
        this.timeline = GameLoop.getInstance().getTimeline();
        this.levelScoreGenerator = new LevelScoreGenerator();
        this.overlayFactory = OverlayFactory.getInstance();
        this.userStatsManager = UserStatsManager.getInstance();
        this.audioHandler = AudioHandler.getInstance();
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
                {"/com/example/demo/images/volumeButton.png", (Runnable) levelStateHandler::toggleAudio},
                {"/com/example/demo/images/restartButton.png", (Runnable) levelStateHandler::retryLevel}
        };

        this.nextButton = new Object[][]{
                {"/com/example/demo/images/nextButton.png", (Runnable) () -> levelStateHandler.goToLevelLostPage(this.root, this.user)}
        };
    }

    /**
     * Retrieves the singleton instance of OverlayHandler.
     *
     * @return The singleton instance of OverlayHandler.
     */
    public static OverlayHandler getInstance() {
        if (instance == null) {
            instance = new OverlayHandler();
        }
        return instance;
    }

    /**
     * Lazy initialization of LevelStateHandler.
     *
     * @return The instance of LevelStateHandler.
     */
    private LevelStateHandler getLevelStateHandler() {
        return LevelStateHandler.getInstance();
    }

    /**
     * Sets the context for the current overlay based on the root and user.
     *
     * @param root The root node of the game scene.
     * @param user The current user interacting with the game.
     */
    private void setContext(Group root, UserParent user) {
        this.root = root;
        this.user = user;
    }

    /**
     * Displays an overlay with the provided title, message, buttons, and background image.
     *
     * @param title The title of the overlay.
     * @param message The message displayed in the overlay.
     * @param buttons An array of buttons with actions to be displayed in the overlay.
     * @param imagePath The file path to the image that will be displayed in the overlay.
     */
    private void showOverlay(String title, String message, Object[][] buttons, String imagePath) {
        BaseOverlay overlay = overlayFactory.createOverlay(root, title, message, buttons, imagePath);
        currentOverlay = overlay;
        overlay.show();
    }

    /**
     * Hides the currently displayed overlay.
     */
    private void hideOverlay() {
        currentOverlay.hide();
    }

    /**
     * Handles the completion of a level by showing the appropriate overlay with a star image
     * and the total coins collected by the user.
     *
     * @param root The root node of the game scene.
     * @param user The user who completed the level.
     */
    public void handleLevelCompletion(Group root, UserParent user) {
        if (currentOverlay != null) {
            hideOverlay();
        }

        stopTimeline();
        setContext(root, user);

        userStatsManager.storeLevelStatistics(user, user.getInLevelKillCount(), user.getCoinsCollected());
        String starImage = levelScoreGenerator.getStarImagePath(userStatsManager.calculateLevelScore(user));
        String message = String.format(COINS_TEXT, user.getCoinsCollected());

        showOverlay("Level Completed!", message, completedOverlayButtons, starImage);
        audioHandler.playLevelWonSound();
    }

    /**
     * Displays the redeem life overlay and starts a timer to automatically handle level loss after 5 seconds.
     *
     * @param root The root node of the game scene.
     * @param user The user who is redeeming a life.
     */
    public void showRedeemLife(Group root, UserParent user) {
        pauseTimeline();
        setContext(root, user);

        // Hide the current overlay if it exists
        if (currentOverlay != null) {
            hideOverlay();
        }

        // Prepare the message to show
        String message = ("Click on the heart to buy your revival\n\n\n" + String.format(COINS_TEXT, user.getCoinsCollected()) + "\n\nCoins needed: 4");
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

    /**
     * Handles the loss of a level by showing the "Level Failed" overlay and storing level statistics.
     * Plays the lost level sound for an additional effect.
     *
     * @param root The root node of the game scene.
     * @param user The user who lost the level.
     */
    public void handleLevelLoss(Group root, UserParent user) {
        stopTimeline();
        setContext(root, user);
        user.destroyUser();

        if (currentOverlay != null) {
            hideOverlay();
        }
        userStatsManager.storeLevelStatistics(user, user.getInLevelKillCount(), user.getCoinsCollected());

        String starImage = levelScoreGenerator.getStarImagePath(0);
        String message = String.format(COINS_TEXT, user.getCoinsCollected());

        showOverlay("Level Failed!", message, lostOverlayButtons, starImage);
        audioHandler.playLevelLostSound(); // Level Lost sound played
    }

    /**
     * Handles the case where the user has insufficient inventory (coins) to proceed.
     */
    public void insufficientInventory() {
        if (currentOverlay != null) {
            hideOverlay();
        }

        String message = String.format(COINS_TEXT, user.getCoinsCollected());
        showOverlay("Insufficient Balance!", message, nextButton, null);
    }

    /**
     * Handles the pause state of the game by displaying the pause overlay and stopping the game timeline.
     *
     * @param root The root node of the game scene.
     * @param user The user who paused the game.
     */
    public void handleLevelPaused(Group root, UserParent user) {
        stopTimeline();
        setContext(root, user);

        if (currentOverlay != null) {
            hideOverlay();
        }

        String message = String.format(COINS_TEXT, user.getCoinsCollected());
        showOverlay("Paused", message, pauseOverlayButtons, null);
    }

    /**
     * Stops the game timeline.
     */
    private void stopTimeline() {
        timeline.stop();
    }

    /**
     * Pauses the game timeline.
     */
    private void pauseTimeline() {
        timeline.pause();
    }
}
