package com.example.demo.functionalClasses;

public class GenerateLevelScore {
    private static GenerateLevelScore instance;

    // Constructor for lives and coins
    public GenerateLevelScore() {
    }

    public static GenerateLevelScore getInstance() {
        if (instance == null) {
            instance = new GenerateLevelScore();
        }
        return instance;
    }

    // Method to get star image path based on score
    public String getStarImagePath(int score) {
        return switch (score) {
            case 3 -> "/com/example/demo/images/3stars.png"; // Path to the 3-star image
            case 2 -> "/com/example/demo/images/2stars.png"; // Path to the 2-star image
            case 1 -> "/com/example/demo/images/1stars.png"; // Path to the 1-star image
            default -> "/com/example/demo/images/0stars.png"; // Path to a no-star image
        };
    }

    // Method to calculate score based on lives and coins
    public int calculateScore(int livesRemaining, int bulletsRemaining) {
        int score = 0;

        // Example scoring logic
        if (livesRemaining > 0) {
            score++; // 1 star for more than 1 life
        }
        if (bulletsRemaining > 5) {
            score++; // 1 star for saving 5 bullets
        }
        if (livesRemaining >= 3 && bulletsRemaining >= 10) {
            score++; // 1 star for having at least 1 life left and more than 10 bullets
        }

        return score;
    }
}