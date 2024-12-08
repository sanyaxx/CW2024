package com.example.demo.activityManagers;


public class UserStatsManager {
    private static UserStatsManager instance;

    private int coinsCollected;
    private int numberOfKills;
    private LevelManager levelManager = LevelManager.getInstance();;

    // Protected array to hold level scores
    protected int[] levelScores;

    // Private constructor to prevent instantiation from outside
    private UserStatsManager() {
        this.coinsCollected = 0;
        this.numberOfKills = 0;
        levelScores = new int[LevelManager.TOTAL_LEVELS_PLUS1 - 1]; // Adjust size according to the number of levels
    }

    // Public static method to get the instance of the Singleton
    public static UserStatsManager getInstance() {
        if (instance == null) {
            instance = new UserStatsManager();
        }
        return instance;
    }

    // Method to get the score for a specific level
    public int getCoinsCollected() {
        return coinsCollected;
    }

    public void incrementCoinsCollected() {
        coinsCollected++;
    }

    public int getNumberOfKills() {
        return numberOfKills;
    }

    public void setLevelScore(int score) {
        int level = levelManager.getCurrentLevelNumber() - 1;
        if (level >= 0 && level <= levelScores.length) {
            levelScores[level] += 5;
        } else {
            throw new IndexOutOfBoundsException("Invalid level index: " + level);
        }
    }

    public int getLevelScore(int level) {
        if (level >= 0 && level <= levelScores.length) {
            return levelScores[level - 1];
        } else {
            throw new IndexOutOfBoundsException("Invalid level index: " + level);
        }
    }

    public void incrementKillCount() {
        numberOfKills++;
    }


    public void decrementCoins() { // when life redeemed
        coinsCollected -= 5;
    }

    // Optionally, a method to reset the Singleton instance (e.g., for testing purposes)
    public static void resetInstance() {
        instance = null;
    }
}
