package com.example.demo.activityManagers;

import com.example.demo.actors.Planes.friendlyPlanes.UserParent;

/**
 * The {@code UserStatsManager} class is responsible for managing the statistics of the user across different levels of the game.
 * It tracks the total number of coins and kills the user has accumulated, as well as the score for each level.
 * This class implements the Singleton design pattern to ensure that only one instance manages the user stats throughout the game.
 * <p>
 * It provides functionality to calculate, store, and retrieve the user’s scores and statistics, including:
 * - Calculating the score for the current level based on user attributes (health, bullet count).
 * - Storing the score for each level.
 * - Tracking the total kills and total coins collected by the user across all levels.
 * </p>
 */
public class UserStatsManager {

    /** The singleton instance of the UserStatsManager. */
    private static UserStatsManager instance;

    /** The total number of coins collected by the user. */
    private int totalCoinsCollected;

    /** The total number of kills made by the user. */
    private int totalKillCount;

    /** The LevelManager instance for managing level transitions and tracking the current level. */
    private final LevelManager levelManager = LevelManager.getInstance();

    /** The LevelScoreGenerator instance for calculating scores based on user attributes. */
    private final LevelScoreGenerator levelScoreGenerator = LevelScoreGenerator.getInstance();

    /** Array to hold the scores for each level. */
    protected int[] levelScores;

    /**
     * Private constructor to prevent instantiation from outside the class.
     * Initializes the total coins collected, total kills, and the levelScores array with a size based on the total levels.
     */
    private UserStatsManager() {
        this.totalCoinsCollected = 0;
        this.totalKillCount = 0;
        levelScores = new int[LevelManager.TOTAL_LEVELS_PLUS1 - 1]; // Adjust size according to the number of levels
    }

    /**
     * Public static method to get the singleton instance of UserStatsManager.
     * <p>
     * Ensures that only one instance of UserStatsManager is used throughout the game.
     * </p>
     *
     * @return The instance of UserStatsManager.
     */
    public static UserStatsManager getInstance() {
        if (instance == null) {
            instance = new UserStatsManager(); // Create the instance if it doesn't already exist
        }
        return instance;
    }

    /**
     * Sets the score for the current level.
     * <p>
     * This method updates the score for the current level based on the user’s performance.
     * </p>
     *
     * @param score The score to set for the current level.
     * @throws IndexOutOfBoundsException If the level index is invalid.
     */
    public void setLevelScore(int score) {
        int level = levelManager.getCurrentLevelNumber() - 1;
        if (level >= 0 && level < levelScores.length) {
            levelScores[level] = score; // Set the score for the current level
        } else {
            throw new IndexOutOfBoundsException("Invalid level index: " + level); // Handle invalid level index
        }
    }

    /**
     * Retrieves the score for a specified level.
     * <p>
     * This method retrieves the score that was stored for a particular level.
     * </p>
     *
     * @param level The level number for which to retrieve the score.
     * @return The score for the specified level.
     * @throws IndexOutOfBoundsException If the level index is invalid.
     */
    public int getLevelScore(int level) {
        if (level > 0 && level <= levelScores.length) {
            return levelScores[level - 1]; // Retrieve score for the given level (adjusted for 0-based indexing)
        } else {
            throw new IndexOutOfBoundsException("Invalid level index: " + level); // Handle invalid level index
        }
    }

    /**
     * Calculates the score for the current level based on the user’s health and bullet count.
     * <p>
     * This method uses the {@link LevelScoreGenerator} to calculate the score by considering the user’s health and remaining bullet count.
     * </p>
     *
     * @param user The user whose stats are used for the score calculation.
     * @return The calculated score for the level.
     */
    public int calculateLevelScore(UserParent user) {
        return levelScoreGenerator.calculateScore(user.getHealth(), user.getBulletCount()); // Calculate score based on user stats
    }

    /**
     * Stores the user’s statistics for the current level, including the score, total kills, and total coins collected.
     * <p>
     * This method records the stats for the current level, including the score, kills, and coins collected by the user.
     * </p>
     *
     * @param user The user whose statistics are being stored.
     * @param totalKillCount The total kill count accumulated by the user during the level.
     * @param totalCoinsCollected The total number of coins collected by the user during the level.
     */
    public void storeLevelStatistics(UserParent user, int totalKillCount, int totalCoinsCollected) {
        setLevelScore(calculateLevelScore(user)); // Set the level score
        setTotalKillCount(totalKillCount); // Set the total kill count
        setTotalCoinsCollected(totalCoinsCollected); // Set the total number of coins collected
    }

    /**
     * Adds the specified value to the total number of coins collected by the user.
     * <p>
     * This method increments the total coins collected by the given value.
     * </p>
     *
     * @param value The number of coins to add to the total.
     */
    public void setTotalCoinsCollected(int value) {
        totalCoinsCollected += value; // Increment the total coins by the given value
    }

    /**
     * Retrieves the total number of coins collected by the user.
     * <p>
     * This method returns the total coins collected by the user across all levels.
     * </p>
     *
     * @return The total number of coins collected by the user.
     */
    public int getTotalCoinsCollected() {
        return totalCoinsCollected; // Return the total coins collected
    }

    /**
     * Adds the specified value to the total number of kills made by the user.
     * <p>
     * This method increments the total kills made by the user during gameplay by the given value.
     * </p>
     *
     * @param value The number of kills to add to the total.
     */
    public void setTotalKillCount(int value) {
        totalKillCount += value; // Increment the total kill count by the given value
    }

    /**
     * Retrieves the total number of kills made by the user.
     * <p>
     * This method returns the total kills made by the user across all levels.
     * </p>
     *
     * @return The total number of kills made by the user.
     */
    public int getTotalKillCount() {
        return totalKillCount; // Return the total kills made
    }
}
