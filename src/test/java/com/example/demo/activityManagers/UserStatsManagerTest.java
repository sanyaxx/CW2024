package com.example.demo.activityManagers;

import com.example.demo.actors.Planes.friendlyPlanes.UserParent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserStatsManagerTest {

    private UserStatsManager userStatsManager;
    private UserParent userMock;
    private LevelManager levelManagerMock;
    private LevelScoreGenerator levelScoreGenerator;

    @BeforeEach
    void setUp() {
        // Create mock objects for dependencies
        userStatsManager = UserStatsManager.getInstance(); // Singleton instance
        userMock = mock(UserParent.class); // Mock the UserParent class
        levelManagerMock = mock(LevelManager.class); // Mock LevelManager class
        levelScoreGenerator = mock(LevelScoreGenerator.class);

        // Mock levelManager to return a specific current level number
        when(levelManagerMock.getCurrentLevelNumber()).thenReturn(1); // Assume current level is 1
    }

    @Test
    void testSingletonInstance() {
        // Verify that the singleton instance is the same for multiple calls
        UserStatsManager instance1 = UserStatsManager.getInstance();
        UserStatsManager instance2 = UserStatsManager.getInstance();
        assertSame(instance1, instance2, "UserStatsManager should return the same instance.");
    }

    @Test
    void testSetLevelScore() {
        // Mock the score setting for a level
        int score = 100;
        userStatsManager.setLevelScore(score);

        // Verify that the score for the first level was set correctly
        assertEquals(score, userStatsManager.getLevelScore(1), "Level score should be set correctly.");
    }

    @Test
    void testGetLevelScore_invalidLevel() {
        // Try to get the score for an invalid level (should throw exception)
        assertThrows(IndexOutOfBoundsException.class, () -> {
            userStatsManager.getLevelScore(999); // Invalid level number
        });
    }

    @Test
    void testCalculateLevelScore() {
        // Mock the behavior of UserParent to return specific values
        when(userMock.getHealth()).thenReturn(80); // Mock user health
        when(userMock.getBulletCount()).thenReturn(10); // Mock bullet count

        // Mock LevelScoreGenerator behavior
        LevelScoreGenerator scoreGeneratorMock = mock(LevelScoreGenerator.class);
        when(scoreGeneratorMock.calculateScore(80, 10)).thenReturn(3);

        // Set the mocked LevelScoreGenerator
        levelScoreGenerator = scoreGeneratorMock;

        // Calculate score and assert
        int calculatedScore = userStatsManager.calculateLevelScore(userMock);
        assertEquals(3, calculatedScore, "Calculated level score should match the mocked score.");
    }

    @Test
    void testStoreLevelStatistics() {
        // Store some statistics for the current level
        int totalKills = 5;
        int totalCoins = 10;

        int initialTotalKills = userStatsManager.getTotalKillCount();
        int initialTotalCoins = userStatsManager.getTotalCoinsCollected();

        userStatsManager.storeLevelStatistics(userMock, totalKills, totalCoins);

        // Verify that the statistics are stored correctly
        assertEquals(totalKills + initialTotalKills, userStatsManager.getTotalKillCount(), "Total kill count should be updated.");
        assertEquals(totalCoins + initialTotalCoins, userStatsManager.getTotalCoinsCollected(), "Total coins collected should be updated.");
    }

    @Test
    void testSetTotalCoinsCollected() {
        // Add coins and verify the total coins collected
        int totalCoins = userStatsManager.getTotalCoinsCollected();
        userStatsManager.setTotalCoinsCollected(50);
        assertEquals(50 + totalCoins, userStatsManager.getTotalCoinsCollected(), "Total coins collected should be updated by 50.");

        totalCoins = userStatsManager.getTotalCoinsCollected();;
        // Add more coins and verify
        userStatsManager.setTotalCoinsCollected(30);
        assertEquals(30 + totalCoins, userStatsManager.getTotalCoinsCollected(), "Total coins collected should be updated by 80.");
    }

    @Test
    void testSetTotalKillCount() {
        // Add kills and verify the total kills count
        userStatsManager.setTotalKillCount(3);
        assertEquals(3, userStatsManager.getTotalKillCount(), "Total kill count should be updated to 3.");

        // Add more kills and verify
        userStatsManager.setTotalKillCount(2);
        assertEquals(5, userStatsManager.getTotalKillCount(), "Total kill count should be updated to 5.");
    }
}
