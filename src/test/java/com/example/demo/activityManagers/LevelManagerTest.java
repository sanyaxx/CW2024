package com.example.demo.activityManagers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class LevelManagerTest {

    private LevelManager levelManager;

    @BeforeEach
    void setUp() {
        // Ensure the singleton instance is reset for testing
        levelManager = LevelManager.getInstance();
        levelManager.setCurrentLevelNumber(1); // Reset to the first level before each test
    }

    @Test
    void testSingletonInstance() {
        LevelManager instance1 = LevelManager.getInstance();
        LevelManager instance2 = LevelManager.getInstance();
        assertSame(instance1, instance2, "LevelManager should return the same instance.");
    }

    @Test
    void testInitialCurrentLevelNumber() {
        assertEquals(1, levelManager.getCurrentLevelNumber(), "Initial level number should be 1.");
    }

    @Test
    void testIncrementCurrentLevelNumber() {
        levelManager.incrementCurrentLevelNumber();
        assertEquals(2, levelManager.getCurrentLevelNumber(), "Current level should increment to 2.");
    }

    @Test
    void testIncrementBeyondLastLevel() {
        levelManager.setCurrentLevelNumber(LevelManager.TOTAL_LEVELS_PLUS1 - 1);
        levelManager.incrementCurrentLevelNumber();
        assertEquals(LevelManager.TOTAL_LEVELS_PLUS1 - 1, levelManager.getCurrentLevelNumber(),
                "Current level should not exceed the last level.");
    }

    @Test
    void testGetNextLevelName() {
        assertEquals("com.example.demo.levels.Level1.Level1", levelManager.getNextLevelName(),
                "Next level name should match the expected format.");
    }

    @Test
    void testGetNextLevelNameAtLastLevel() {
        levelManager.setCurrentLevelNumber(LevelManager.TOTAL_LEVELS_PLUS1 - 1);
        assertEquals("com.example.demo.levels.Level4.Level4", levelManager.getNextLevelName(),
                "Next level name should be the last level name at the last level.");
    }

    @Test
    void testIsLastLevel() {
        levelManager.setCurrentLevelNumber(LevelManager.TOTAL_LEVELS_PLUS1 - 1);
        assertTrue(levelManager.isLastLevel(), "Should return true for the last level.");
        levelManager.setCurrentLevelNumber(1);
        assertFalse(levelManager.isLastLevel(), "Should return false for levels other than the last.");
    }

    @Test
    void testGoToNextLevel() {
        AtomicReference<String> nextLevelName = new AtomicReference<>();
        levelManager.addLevelChangeListener(evt -> {
            if ("nextLevel".equals(evt.getPropertyName())) {
                nextLevelName.set((String) evt.getNewValue());
            }
        });

        levelManager.goToNextLevel();

        assertEquals("com.example.demo.levels.Level1.Level1", nextLevelName.get(),
                "The next level name should be fired correctly as a property change event.");
    }


    @Test
    void testLevelChangeListener() {
        AtomicReference<String> eventName = new AtomicReference<>();
        PropertyChangeListener listener = evt -> eventName.set(evt.getPropertyName());

        levelManager.addLevelChangeListener(listener);
        levelManager.goToNextLevel();

        assertEquals("nextLevel", eventName.get(), "Listener should capture 'nextLevel' events.");

        levelManager.removeLevelChangeListener(listener);
        eventName.set(null);
        levelManager.goToNextLevel();

        assertNull(eventName.get(), "Listener should no longer receive events after removal.");
    }
}
