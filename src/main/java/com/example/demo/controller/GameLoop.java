package com.example.demo.controller;

import com.example.demo.actors.Updatable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The GameLoop class is responsible for managing the core game loop, which includes controlling the game's
 * update cycle, maintaining a list of updatable entities, and coordinating the timing of game updates.
 *
 * Previously, the timeline and update logic were implemented directly within individual level classes such as
 * `LevelParent`, which led to duplication and clutter. This class centralizes the game loop logic by creating a
 * singleton `GameLoop` that can be accessed globally, allowing for better control of the game flow, including
 * pause and resume functionality. Additionally, the `GameLoop` manages a list of updatable entities (objects that
 * implement the `Updatable` interface), and invokes their `update()` method on each tick of the game loop.
 *
 * This design simplifies adding, updating, and pausing various game entities, ensuring that game logic remains
 * modular and manageable.
 */
public class GameLoop {

    /** Singleton instance of the game loop */
    private static GameLoop instance;

    /** The game loop's timeline that controls the update cycle */
    private Timeline timeline;

    /** Delay between each frame in milliseconds */
    private static final int MILLISECOND_DELAY = 50;

    /** List of entities that need updating every frame */
    private final List<Updatable> updatables = new CopyOnWriteArrayList<>();

    /**
     * Private constructor to prevent instantiation from outside the class.
     * Initializes the game loop timeline to ensure the loop is started only when needed.
     */
    private GameLoop() {
        initializeTimeline();  // Initialize the timeline for the game loop
    }

    /**
     * Returns the singleton instance of GameLoop.
     * This method ensures there is only one instance of GameLoop used across the application.
     *
     * @return The singleton instance of GameLoop.
     */
    public static synchronized GameLoop getInstance() {
        if (instance == null) {
            instance = new GameLoop();  // Instantiate the GameLoop if it hasn't been created
        }
        return instance;
    }

    /**
     * Initializes the timeline for the game loop.
     * The timeline runs indefinitely and calls the update method at regular intervals,
     * controlled by the {@link #MILLISECOND_DELAY} constant.
     */
    public void initializeTimeline() {
        timeline = new Timeline();  // Create a new Timeline for the game loop
        timeline.setCycleCount(Timeline.INDEFINITE);  // Set the timeline to loop indefinitely
        KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> update());  // Define the update action
        timeline.getKeyFrames().add(gameLoop);  // Add the KeyFrame to the timeline
    }

    /**
     * Gets the current game loop timeline.
     *
     * @return The Timeline controlling the game loop.
     */
    public Timeline getTimeline() {
        return timeline;  // Return the game loop's timeline
    }

    /**
     * Adds an updatable entity to the game loop.
     * The entity will be updated on each tick of the game loop.
     *
     * @param updatable The updatable entity to add to the list.
     */
    public void addUpdatable(Updatable updatable) {
        updatables.add(updatable);  // Add the updatable entity to the list
    }

    /**
     * Clears all updatable entities from the game loop.
     * This is typically used when transitioning between levels or resetting the game state.
     */
    public void clearUpdatable() {
        updatables.clear();  // Clear the list of updatable entities
    }

    /**
     * Calls the update method on each updatable entity in the list.
     * This method is executed every frame by the game loop and ensures that all updatable entities are updated.
     */
    private void update() {
        // Iterate over all updatable entities and call their update method
        for (Updatable updatable : updatables) {
            updatable.update();  // Call the update method for each updatable entity
        }
    }
}
