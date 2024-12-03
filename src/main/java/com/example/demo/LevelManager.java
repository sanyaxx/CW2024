package com.example.demo;

import java.util.*;


public class LevelManager extends Observable {
    private static LevelManager instance;
    private int currentLevelNumber; // To track the current level
    protected static final int TOTAL_LEVELS_PLUS1 = 4 + 1; // Track the total number of levels

    // Private constructor initializes currentLevelNumber to 1
    private LevelManager() {
        this.currentLevelNumber = 4;
    }

    public static LevelManager getInstance() {
        if (instance == null) {
            instance = new LevelManager();
        }
        return instance;
    }

    public void showLevelStartScreen(int levelNumber) {
       LevelStartScreen levelStartScreen = new LevelStartScreen(levelNumber);
       levelStartScreen.show(); // Show the level start screen with level name and aim
    }

    // Increment the current level number: called when a level completed
    public void incrementCurrentLevelNumber() {
        currentLevelNumber++;
    }

    public String getNextLevelName() {
        if (currentLevelNumber < TOTAL_LEVELS_PLUS1) {
            return "com.example.demo.levels.Level" + (currentLevelNumber);
        } else {
            return null; // No more levels
        }
    }

    public void goToNextLevel(String levelName) {
        setChanged();
        notifyObservers(levelName);
    }

    public int getCurrentLevelNumber() {
        return currentLevelNumber; // Getter for current level number
    }

}