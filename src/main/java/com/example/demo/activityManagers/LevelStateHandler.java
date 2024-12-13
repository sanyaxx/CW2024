package com.example.demo.activityManagers;

import com.example.demo.actors.Planes.friendlyPlanes.UserParent;
import com.example.demo.controller.AppStage;
import com.example.demo.controller.GameLoop;
import com.example.demo.displays.*;
import javafx.animation.Timeline;
import javafx.scene.Group;

/**
 * The LevelStateHandler class is responsible for managing the state transitions of the game levels.
 * It handles actions such as navigating between levels, retrying levels, resuming the game, and reviving the player.
 * This class also interfaces with other game components like the LevelManager and OverlayHandler to manage game states and user interactions.
 *
 * <p>LevelStateHandler uses the Singleton design pattern to ensure only one instance is used throughout the game's lifecycle.</p>
 */
public class LevelStateHandler {

    /** Singleton instance of LevelStateHandler. */
    private static LevelStateHandler instance;

    /** The timeline that controls the flow of the game. */
    private final Timeline timeline;

    /** Singleton instance of LevelManager to manage levels. */
    private final LevelManager levelManager;

    /** Singleton instance of OverlayHandler to handle game overlays. */
    private OverlayHandler overlayHandler;

    /** Singleton instance of AudioHandler to handle game audio controls. */
    private AudioHandler audioHandler;

    /**
     * Constructor for LevelStateHandler. Initializes the Timeline and LevelManager for managing the game state.
     */
    public LevelStateHandler() {
        this.timeline = GameLoop.getInstance().getTimeline(); // Initialize the game timeline
        this.levelManager = LevelManager.getInstance(); // Access LevelManager instance
        this.audioHandler = AudioHandler.getInstance();
    }

    /**
     * Static method to get the single instance of LevelStateHandler.
     *
     * @return The singleton instance of LevelStateHandler.
     */
    public static LevelStateHandler getInstance() {
        if (instance == null) {
            instance = new LevelStateHandler(); // Create instance if it doesn't exist
        }
        return instance;
    }

    /**
     * Lazy initialization of OverlayHandler.
     *
     * @return The instance of OverlayHandler.
     */
    private OverlayHandler getOverlayHandler() {
        if (overlayHandler == null) {
            overlayHandler = OverlayHandler.getInstance(); // Initialize OverlayHandler if not already done
        }
        return overlayHandler;
    }

    /**
     * Starts the game timeline to resume the game's activity.
     */
    private void playTimeline() {
        timeline.play(); // Start the game timeline
    }

    /**
     * Navigates to the main menu screen of the game.
     */
    public void goToMainMenu() {
        System.out.println("Navigating to Main Menu...");
        new StartScreen(AppStage.getInstance().getPrimaryStage()).show(); // Show the main menu screen
    }

    /**
     * Moves the game to the next level. If the current level is the last, it displays the "You Win" screen.
     * Otherwise, it increments the current level number and shows the start screen for the next level.
     */
    public void goToNextLevel() {
        if (levelManager.isLastLevel()) {
            new YouWinScreen(); // Display "You Win" screen if the current level is the last
        } else {
            levelManager.incrementCurrentLevelNumber(); // Increment to the next level
            levelManager.showLevelStartScreen(levelManager.getCurrentLevelNumber()); // Show start screen for the next level
        }
    }

    /**
     * Revives the player by either restoring health, bullet count, or fuel depending on the player's current state.
     * This action also costs the player coins. If the player does not have enough coins, it shows an insufficient inventory overlay.
     *
     * @param user The user who is being revived.
     */
    public void reviveLevel(UserParent user) {
        if (getOverlayHandler().redeemLifeTimer != null) getOverlayHandler().redeemLifeTimer.stop(); // Stop the redeem life timer if running

        if (user.getCoinsCollected() >= 4) {
            System.out.println("Reviving current level...");
            user.decrementCoinsCollected(); // Deduct coins for revival

            if (user.getHealth() <= 0) {
                user.reviveUserLife(); // Revive the player's life if health is 0
            } else if (user.getBulletCount() <= 0) {
                user.reviveBulletCount(); // Revive bullet count if it's 0
            } else {
                user.reviveUserFuel(); // Revive fuel if it's 0
            }
            playTimeline(); // Resume the game timeline
        } else {
            getOverlayHandler().insufficientInventory(); // Show insufficient inventory overlay if coins are not enough
        }
    }

    /**
     * Retries the current level by showing the start screen for the current level number.
     */
    public void retryLevel() {
        levelManager.showLevelStartScreen(levelManager.getCurrentLevelNumber()); // Show the start screen again for the current level
    }

    /**
     * Navigates to the level lost page and displays the appropriate overlay for the user.
     *
     * @param root The root node of the current scene.
     * @param user The user whose level has been lost.
     */
    public void goToLevelLostPage(Group root, UserParent user) {
        getOverlayHandler().handleLevelLoss(root, user); // Handle the level loss and show overlay
    }

    /**
     * Resumes the level after pausing. It stops the redeem life timer (if running) and restarts the timeline.
     */
    public void resumeLevel() {
        if (getOverlayHandler().redeemLifeTimer != null) getOverlayHandler().redeemLifeTimer.stop(); // Stop redeem life timer if running
        playTimeline(); // Restart the game timeline
    }

    /**
     * Toggles the volume of the game (mute/unmute).
     */

    public void toggleAudio() {
        System.out.println("Volume toggled...");
        audioHandler.toggleAudio();
        playTimeline(); // Resume the game timeline after toggling volume
    }
}
