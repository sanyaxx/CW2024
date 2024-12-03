//package com.example.demo;
//
//import java.io.*;
//
//public class UserProgress implements Serializable {
//    private int currentLevel;
//    private int score;
//
//    // Getters and Setters
//    public int getCurrentLevel() {
//        return currentLevel;
//    }
//
//    public void setCurrentLevel(int currentLevel) {
//        this.currentLevel = currentLevel;
//    }
//
//    public int getScore() {
//        return score;
//    }
//
//    public void setScore(int score) {
//        this.score = score;
//    }
//}
//
//public class ProgressManager {
//    private static final String FILE_NAME = "userProgress.dat";
//
//    public void saveProgress(UserProgress progress) {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
//            oos.writeObject(progress);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public UserProgress loadProgress() {
//        UserProgress progress = null;
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
//            progress = (User Progress) ois.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return progress;
//    }
//}