package com.example.demo.activityManagers;

/**
 * The {@code LevelScoreGenerator} class is responsible for calculating and determining the score in the game based on the player's
 * performance, including factors like remaining lives and bullets. It also provides the corresponding star image path based
 * on the score.
 *
 * <p>This class follows the Singleton design pattern to ensure only one instance exists for managing score-related logic
 * throughout the game.</p>
 *
 * <p>Original Source: This class is a newly introduced utility to centralize and manage score generation and
 * image path retrieval.</p>
 */
public class LevelScoreGenerator {

    /** The singleton instance of LevelScoreGenerator. */
    private static LevelScoreGenerator instance;

    /**
     * Constructor for {@code LevelScoreGenerator}. Initializes the class instance.
     * The constructor is private to prevent instantiating the class directly outside of the {@link #getInstance()} method.
     */
    public LevelScoreGenerator() {
        // Private constructor to implement Singleton pattern
    }

    /**
     * Retrieves the singleton instance of {@code LevelScoreGenerator}.
     * If the instance is not yet created, it will create a new one.
     *
     * @return The singleton instance of {@code LevelScoreGenerator}.
     */
    public static LevelScoreGenerator getInstance() {
        if (instance == null) {
            instance = new LevelScoreGenerator();  // Lazy initialization of the singleton instance
        }
        return instance;
    }

    /**
     * Returns the file path of the star image corresponding to the given score.
     * The path is determined by the score value, which ranges from 0 to 3.
     *
     * @param score The score used to determine the star image path.
     * @return The file path of the image representing the score in stars.
     * If the score is 3, the path to the 3-star image is returned.
     * If the score is 2, the path to the 2-star image is returned.
     * If the score is 1, the path to the 1-star image is returned.
     * Otherwise, the path to a 0-star image is returned.
     */
    public String getStarImagePath(int score) {
        return switch (score) {
            case 3 -> "/com/example/demo/images/3stars.png"; // Path to the 3-star image
            case 2 -> "/com/example/demo/images/2stars.png"; // Path to the 2-star image
            case 1 -> "/com/example/demo/images/1stars.png"; // Path to the 1-star image
            default -> "/com/example/demo/images/0stars.png"; // Path to a no-star image
        };
    }

    /**
     * Calculates the score based on the remaining lives and bullets of the player.
     * The calculation rewards the player for having more lives and bullets left, with special conditions for high values.
     *
     * @param livesRemaining The number of lives the player has remaining.
     * @param bulletsRemaining The number of bullets the player has left.
     * @return The calculated score based on the player's performance.
     * <p>The score is determined using the following logic:</p>
     * <ul>
     *   <li>If the player has more than 0 lives, 1 star is awarded.</li>
     *   <li>If the player has more than 5 bullets remaining, 1 star is awarded.</li>
     *   <li>If the player has at least 3 lives and at least 10 bullets, 1 additional star is awarded.</li>
     * </ul>
     */
    public int calculateScore(int livesRemaining, int bulletsRemaining) {
        int score = 0;

        // Example scoring logic
        if (livesRemaining > 0) {
            score++; // 1 star for more than 0 lives remaining
        }
        if (bulletsRemaining > 5) {
            score++; // 1 star for having more than 5 bullets remaining
        }
        if (livesRemaining >= 3 && bulletsRemaining >= 10) {
            score++; // 1 additional star for having at least 3 lives and 10 bullets
        }

        return score;  // Return the total calculated score
    }
}
