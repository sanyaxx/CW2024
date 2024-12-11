package com.example.demo.activityManagers;


import com.example.demo.actors.Planes.friendlyPlanes.UserParent;
import com.example.demo.functionalClasses.GenerateLevelScore;

public class UserStatsManager {
    private static UserStatsManager instance;

    private int totalCoinsCollected;
    private int totalKillCount;
    private final LevelManager levelManager = LevelManager.getInstance();
    private final GenerateLevelScore generateLevelScore = GenerateLevelScore.getInstance();

    // Protected array to hold level scores
    protected int[] levelScores;

    // Private constructor to prevent instantiation from outside
    private UserStatsManager() {
        this.totalCoinsCollected = 0;
        this.totalKillCount = 0;
        levelScores = new int[LevelManager.TOTAL_LEVELS_PLUS1 - 1]; // Adjust size according to the number of levels
    }

    // Public static method to get the instance of the Singleton
    public static UserStatsManager getInstance() {
        if (instance == null) {
            instance = new UserStatsManager();
        }
        return instance;
    }

    public void setLevelScore(int score) {
        int level = levelManager.getCurrentLevelNumber() - 1;
        if (level >= 0 && level <= levelScores.length) {
            levelScores[level] = score;
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

    public int calculateLevelScore(UserParent user) {
        return generateLevelScore.calculateScore(user.getHealth(), user.getBulletCount());
    }

    public void storeLevelStatistics(UserParent user, int totalKillCount, int totalCoinsCollected) {
        setLevelScore(calculateLevelScore(user));
        setTotalKillCount(totalKillCount);
        setTotalCoinsCollected(totalCoinsCollected);
    }

    public void setTotalCoinsCollected(int value) {
        totalCoinsCollected += value;
    }

    public int getTotalCoinsCollected() {
        return totalCoinsCollected;
    }

    public void setTotalKillCount(int value) {
        totalKillCount += value;
    }

    public int getTotalKillCount() {
        return totalKillCount;
    }


}
