package com.example.demo.gameConfig;

import javafx.animation.Timeline;

public class GameTimeline {
    private static GameTimeline instance;
    private Timeline timeline; // Reference to the game timeline

    // Private constructor to prevent instantiation
    private GameTimeline() {
    }

    // Public method to get the singleton instance
    public static GameTimeline getInstance() {
        if (instance == null) {
            instance = new GameTimeline();
        }
        return instance;
    }

    public Timeline createTimeline() {
        this.timeline = new Timeline();
        return timeline;
    }

    public Timeline getTimeline() {
        return timeline;
    }

}