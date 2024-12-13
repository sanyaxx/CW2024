package com.example.demo.activityManagers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelScoreGeneratorTest {

    private LevelScoreGenerator scoreGenerator;

    @BeforeEach
    void setUp() {
        scoreGenerator = LevelScoreGenerator.getInstance();
    }

    @Test
    void testSingletonInstance() {
        LevelScoreGenerator instance1 = LevelScoreGenerator.getInstance();
        LevelScoreGenerator instance2 = LevelScoreGenerator.getInstance();
        assertSame(instance1, instance2, "LevelScoreGenerator should return the same instance.");
    }

    @Test
    void testGetStarImagePath() {
        assertEquals("/com/example/demo/images/3stars.png", scoreGenerator.getStarImagePath(3), "Path for 3 stars should be correct.");
        assertEquals("/com/example/demo/images/2stars.png", scoreGenerator.getStarImagePath(2), "Path for 2 stars should be correct.");
        assertEquals("/com/example/demo/images/1stars.png", scoreGenerator.getStarImagePath(1), "Path for 1 star should be correct.");
        assertEquals("/com/example/demo/images/0stars.png", scoreGenerator.getStarImagePath(0), "Path for 0 stars should be correct.");
        assertEquals("/com/example/demo/images/0stars.png", scoreGenerator.getStarImagePath(-1), "Path for invalid scores should default to 0 stars.");
    }

    @Test
    void testCalculateScore() {
        // Test cases for different conditions
        assertEquals(0, scoreGenerator.calculateScore(0, 0), "Score should be 0 when lives and bullets are insufficient.");
        assertEquals(1, scoreGenerator.calculateScore(1, 0), "Score should be 1 for more than 0 lives and no bullets.");
        assertEquals(1, scoreGenerator.calculateScore(0, 6), "Score should be 1 for more than 5 bullets and no lives.");
        assertEquals(2, scoreGenerator.calculateScore(1, 6), "Score should be 2 for more than 0 lives and more than 5 bullets.");
        assertEquals(3, scoreGenerator.calculateScore(3, 10), "Score should be 3 for at least 3 lives and 10 bullets.");
        assertEquals(2, scoreGenerator.calculateScore(2, 10), "Score should be 2 for insufficient lives but enough bullets.");
    }

    @Test
    void testCalculateScoreEdgeCases() {
        // Test edge cases for the scoring logic
        assertEquals(1, scoreGenerator.calculateScore(1, 5), "Score should be 1 for minimal bullets and positive lives.");
        assertEquals(2, scoreGenerator.calculateScore(3, 6), "Score should be 2 for minimal bullets and sufficient lives.");
        assertEquals(3, scoreGenerator.calculateScore(3, 10), "Score should be maximum when both conditions are satisfied.");
    }
}