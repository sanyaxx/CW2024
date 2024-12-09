package com.example.demo.controller;

import com.example.demo.actors.Updatable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameLoop {

    private static GameLoop instance;

    private Timeline timeline;
    private static final int MILLISECOND_DELAY = 50;
    private final List<Updatable> updatables = new CopyOnWriteArrayList<>();

    // Private constructor
    private GameLoop() {
        initializeTimeline();
    }

    // Singleton instance getter
    public static synchronized GameLoop getInstance() {
        if (instance == null) {
            instance = new GameLoop();
        }
        return instance;
    }

    // Initialize timeline
    public void initializeTimeline() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> update());
        timeline.getKeyFrames().add(gameLoop);
    }

    // Get the timeline
    public Timeline getTimeline() {
        return timeline;
    }

    // Add an updatable object
    public void addUpdatable(Updatable updatable) {
        updatables.add(updatable);
    }

    public void clearUpdatable() {
        updatables.clear();
    }

    // Update all updatables
    private void update() {
        for (Updatable updatable : updatables) {
            updatable.update();
        }
    }
}
