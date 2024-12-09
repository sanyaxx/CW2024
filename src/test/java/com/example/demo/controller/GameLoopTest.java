package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.actors.Updatable;
import javafx.animation.Timeline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class GameLoopTest {

    private GameLoop gameLoop;
    private Updatable mockUpdatable;

    @BeforeEach
    void setUp() {
        // Create a fresh GameLoop instance for each test
        gameLoop = GameLoop.getInstance();
        mockUpdatable = mock(Updatable.class);
    }

    @Test
    void testSingletonBehavior() {
        // Ensure GameLoop instance is a singleton
        GameLoop anotherInstance = GameLoop.getInstance();
        assertSame(gameLoop, anotherInstance, "GameLoop should be a singleton.");
    }

    @Test
    void testTimelineInitialization() {
        // Get the timeline from the GameLoop and check its properties
        Timeline timeline = gameLoop.getTimeline();

        // Verify that the timeline is initialized with a cycle count of INDEFINITE
        assertEquals(Timeline.INDEFINITE, timeline.getCycleCount(), "Timeline should be infinite.");
    }

    @Test
    void testAddUpdatableAndUpdate() {
        // Add an updatable to the game loop
        gameLoop.addUpdatable(mockUpdatable);

        // Run the game loop update method manually (simulate timeline triggering)
        gameLoop.getTimeline().getKeyFrames().get(0).getOnFinished().handle(null);

        // Verify that the update method of the updatable was called
        verify(mockUpdatable, times(1)).update();
    }

    @Test
    void testClearUpdatables() {
        // Add an updatable to the game loop
        gameLoop.addUpdatable(mockUpdatable);

        // Clear the updatables
        gameLoop.clearUpdatable();

        // Run the game loop update method again
        gameLoop.getTimeline().getKeyFrames().get(0).getOnFinished().handle(null);

        // Verify that the update method was NOT called after clearing
        verify(mockUpdatable, times(0)).update();
    }
}
