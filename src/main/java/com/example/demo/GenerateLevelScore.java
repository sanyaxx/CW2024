package com.example.demo;

public class GenerateLevelScore {
    private int livesRemaining;
    private int coinsCollected;

    // Constructor for lives and coins
    public GenerateLevelScore(int livesRemaining, int coinsCollected) {
        this.livesRemaining = livesRemaining;
        this.coinsCollected = coinsCollected;
    }

    // Constructor for direct score
    public GenerateLevelScore(int score) {
        // Generate star rating based on the provided score
        String starImagePath = getStarImagePath(score);
        // If you need to store the star image path for future reference, you can do so here.
    }

    // Method to get star image path based on score
    private String getStarImagePath(int score) {
        return switch (score) {
            case 3 -> "/com/example/demo/images/3stars.png"; // Path to the 3-star image
            case 2 -> "/com/example/demo/images/2stars.png"; // Path to the 2-star image
            case 1 -> "/com/example/demo/images/1stars.png"; // Path to the 1-star image
            default -> "/com/example/demo/images/0stars.png"; // Path to a no-star image
        };
    }

    // Method to calculate score based on lives and coins
    public int calculateScore() {
        int score = 0;

        // Example scoring logic
        if (livesRemaining > 0) {
            score++; // 1 star for more than 1 life
        }
        if (coinsCollected > 5) {
            score++; // 1 star for collecting more than 5 coins
        }
        if (livesRemaining > 3 && coinsCollected > 10) {
            score++; // 1 star for having at least 1 life left and more than 20 coins
        }

        return score;
    }

    // Method to get the star image based on lives and coins
    public String getStarImage() {
        int score = calculateScore(); // Calculate the score based on lives and coins
        return getStarImagePath(score); // Return the corresponding star image path
    }
}