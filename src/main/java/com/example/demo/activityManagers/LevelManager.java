package com.example.demo.activityManagers;

import com.example.demo.displays.LevelStartScreen;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The LevelManager class is responsible for managing the progression of levels in the game.
 * It tracks the current level, displays level start screens, and handles level transitions.
 * This class uses the Singleton design pattern to ensure a single instance is created,
 * providing a consistent way of managing levels.
 *
 * <p>Original Source: This class is newly introduced to centralize level management and avoid mixing level logic
 *  * with gameplay logic previously found in LevelParent.</p>
 */
public class LevelManager extends PropertyChangeSupport {
    /** Singleton instance of LevelManager. */
    private static LevelManager instance;

    /** Tracks the current level number. */
    private int currentLevelNumber;

    /** The total number of levels in the game plus one for boundary tracking. */
    public static final int TOTAL_LEVELS_PLUS1 = 4 + 1;


    /**
     * Private constructor initializes currentLevelNumber to 1.
     * This ensures the LevelManager starts at level 1 when instantiated.
     */
    private LevelManager() {
        super(LevelManager.class);
        this.currentLevelNumber = 1;
    }

    /**
     * Retrieves the singleton instance of LevelManager.
     * If the instance does not exist, it is created.
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
     * Increments the current level number by 1, ensuring it does not exceed the total number of levels.
     * This method is typically called when a level is completed.
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
        if (currentLevelNumber < TOTAL_LEVELS_PLUS1 - 1) {
            currentLevelNumber++;
        } else {
            System.out.println("No more levels to increment.");
        }
    }

    /**
     * Retrieves the name of the next level.
     *
     * @return The name of the next level, or null if there are no more levels.
     */
    public String getNextLevelName() {
        if (currentLevelNumber < TOTAL_LEVELS_PLUS1) {
            return "com.example.demo.levels.Level" + (currentLevelNumber) + ".Level" + (currentLevelNumber);
        } else {
            return null;
        }
    }

    /**
     * Transitions to the next level by notifying all registered listeners of the level change event.
     * This method fires a property change event with the next level's name.
     */
    public void goToNextLevel() {
        String levelName = getNextLevelName();
        if (levelName != null) {
            firePropertyChange("nextLevel", null, levelName);
        } else {
            System.out.println("No more levels to transition to.");
        }
    }

    /**
     * Checks whether the current level is the last level in the game.
     *
     * @return {@code true} if the current level is the last level, {@code false} otherwise.
     */
    public boolean isLastLevel() {
        return currentLevelNumber == TOTAL_LEVELS_PLUS1 - 1;
    }

    /**
     * Retrieves the current level number.
     *
     * @return The current level number as an integer.
     */
    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    /**
     * Sets the current level number to a specified value.
     * This allows for manual level progression or resetting the level number.
     *
     * @param number The new level number to set.
     */
    public void setCurrentLevelNumber(int number) {
        this.currentLevelNumber = number;
    }

    /**
     * Adds a listener to be notified of level change events.
     *
     * @param listener The {@code PropertyChangeListener} to be added.
     */
    public void addLevelChangeListener(PropertyChangeListener listener) {
        addPropertyChangeListener(listener);
    }

    /**
     * Removes a listener from level change notifications.
     *
     * @param listener The {@code PropertyChangeListener} to be removed.
     */
    public void removeLevelChangeListener(PropertyChangeListener listener) {
        removePropertyChangeListener(listener);
    }

}
