package com.example.demo;

import java.util.*;


public class LevelManager extends Observable {
    private static LevelManager instance;
    private int currentLevelNumber; // To track the current level
    private static final int TOTAL_LEVELS = 3; // Track the total number of levels

    // Private constructor initializes currentLevelNumber to 1
    private LevelManager() {
        this.currentLevelNumber = 0;
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

    public String getNextLevelName() {
        if (currentLevelNumber < TOTAL_LEVELS) {
            String nextLevelName = "com.example.demo.Level" + (currentLevelNumber + 1);
            currentLevelNumber++; // Increment the current level number after getting the next level name
            return nextLevelName;
        } else {
            return null; // No more levels
        }
    }

    public void goToNextLevel(String levelName) {
        System.out.println("goToNextLevel Accessed");
        setChanged();
        System.out.println("setChanged");
//        notifyObservers(levelName); // Notify observers with the next level name
        notifyObservers(levelName);
        System.out.println("notifyObservers");
    }

    public int getCurrentLevelNumber() {
        return currentLevelNumber; // Getter for current level number
    }

    public int getNextLevelNumber() {
        if (currentLevelNumber + 1 <= TOTAL_LEVELS) {
            return (currentLevelNumber + 1);
        } else {
            return -1; // No more levels
        }
    }


}