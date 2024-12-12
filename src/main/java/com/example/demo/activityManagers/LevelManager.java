package com.example.demo.activityManagers;

import com.example.demo.displays.LevelStartScreen;

import java.util.*;

/**
 * The LevelManager class is responsible for managing the progression of levels in the game. It tracks the current level,
 * shows level start screens, and handles level transitions. This class uses the Singleton design pattern to ensure only
 * one instance is created and provides a consistent way of managing levels.
 *
 * <p>Original Source: This class is newly introduced to centralize level management and avoid mixing level logic
 * with gameplay logic previously found in LevelParent.</p>
 */
public class LevelManager extends Observable {

    /** The singleton instance of the LevelManager class. */
    private static LevelManager instance;

    /** The current level number. */
    private int currentLevelNumber;

    /** Constant representing the total number of levels plus 1 (for easier level tracking). */
    public static final int TOTAL_LEVELS_PLUS1 = 4 + 1; // Total levels plus 1

    /**
     * Private constructor initializes currentLevelNumber to 1.
     * This ensures the LevelManager starts at level 1 when instantiated.
     */
    private LevelManager() {
        this.currentLevelNumber = 1;
    }

    /**
     * Retrieves the single instance of the LevelManager.
     *
     * @return The singleton instance of LevelManager.
     */
    public static LevelManager getInstance() {
        if (instance == null) {
            instance = new LevelManager();
        }
        return instance;
    }

    /**
     * Shows the level start screen for the given level number.
     * Displays the level's name and objective to the player.
     *
     * @param levelNumber The level number for which to show the start screen.
     */
    public void showLevelStartScreen(int levelNumber) {
        LevelStartScreen levelStartScreen = new LevelStartScreen(levelNumber);
        levelStartScreen.show(); // Show the level start screen with level name and aim
    }

    /**
     * Increments the current level number, ensuring that it doesn't exceed the total number of levels.
     * This method is called when a level is completed, advancing to the next level.
     */
    public void incrementCurrentLevelNumber() {
        if (currentLevelNumber < TOTAL_LEVELS_PLUS1 - 1) { // Only increment if not at the last level
            currentLevelNumber++;
        } else {
            System.out.println("No more levels to increment.");
        }
    }

    /**
     * Retrieves the name of the next level based on the current level number.
     *
     * @return The name of the next level, or null if there are no more levels.
     */
    public String getNextLevelName() {
        if (currentLevelNumber < TOTAL_LEVELS_PLUS1) {
            return "com.example.demo.levels.Level" + (currentLevelNumber) + ".Level" + (currentLevelNumber);
        } else {
            return null; // No more levels
        }
    }

    /**
     * Advances to the next level by notifying all observers with the next level's name.
     * This method triggers the transition to the next level.
     */
    public void goToNextLevel() {
        String levelName = getNextLevelName();
        setChanged();
        notifyObservers(levelName); // Notify observers with the next level's name
    }

    /**
     * Checks if the current level is the last level in the game.
     * This helps determine if the game is about to end.
     *
     * @return True if the current level is the last level, false otherwise.
     */
    public boolean isLastLevel() {
        return (currentLevelNumber == TOTAL_LEVELS_PLUS1 - 1); // Check if the current level is the last level
    }

    /**
     * Retrieves the current level number.
     *
     * @return The current level number.
     */
    public int getCurrentLevelNumber() {
        return currentLevelNumber; // Getter for the current level number
    }

    /**
     * Sets the current level number to a specified value.
     * This allows for manual level progression or resetting the level number.
     *
     * @param number The new level number to set.
     */
    public void setCurrentLevelNumber(int number) {
        currentLevelNumber = number; // Setter for current level number
    }
}
