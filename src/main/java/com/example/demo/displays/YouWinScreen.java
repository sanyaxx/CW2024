package com.example.demo.displays;

import com.example.demo.activityManagers.LevelScoreGenerator;
import com.example.demo.activityManagers.LevelStateHandler;
import com.example.demo.activityManagers.UserStatsManager;
import com.example.demo.controller.AppStage;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

/**
 * This class is responsible for displaying the "You Win" screen when the player successfully completes the game.
 * It shows the player's total score, total kills, level-wise scores, and a confetti effect.
 * <p>
 * The screen is presented with a title, total score, total kills, and level-by-level scores with corresponding star ratings.
 * A confetti animation is also added to celebrate the player's victory.
 * </p>
 */
public class YouWinScreen {

    /** The scene for displaying the "You Win" screen. */
    private final Scene scene;

    /** The stage (window) in which the scene is displayed. */
    private final Stage stage;

    /** The user stats manager responsible for retrieving the user's statistics. */
    private final UserStatsManager user;

    /**
     * Constructor that initializes the YouWinScreen with the primary stage and user statistics.
     * <p>
     * This constructor sets up the scene, layout, and components for the "You Win" screen, including the title,
     * summary statistics, level-wise scores, and a button to return to the main menu. It also triggers the confetti effect.
     * </p>
     */
    public YouWinScreen() {
        this.stage = AppStage.getInstance().getPrimaryStage(); // Get the primary stage for the application
        this.user = UserStatsManager.getInstance(); // Get the user stats manager instance

        // Create a new root group and scene for the screen
        Group newRoot = new Group();
        this.scene = new Scene(newRoot, stage.getWidth(), stage.getHeight(), Color.BLACK); // Set scene with black background
        stage.setScene(scene); // Set the scene to the primary stage

        // Title: "YOU WON!"
        Text title = new Text("YOU WON!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 100)); // Set font size and style
        title.setFill(Color.GOLD); // Set the text color to gold
        title.setStroke(Color.BLACK); // Set stroke color for text outline
        title.setStrokeWidth(2); // Set stroke width for the outline

        // Center align the title
        VBox titleBox = new VBox(title);
        titleBox.setAlignment(Pos.CENTER); // Align the title in the center of the VBox

        // Summary: Total score and total kills
        Text totalScoreText = new Text("Total Score: " + user.getTotalCoinsCollected());
        totalScoreText.setFont(Font.font("Arial", FontWeight.BOLD, 24)); // Set font style and size for the score text
        totalScoreText.setFill(Color.LIGHTBLUE); // Set text color to light blue

        Text totalKillsText = new Text("Total Kills: " + user.getTotalKillCount());
        totalKillsText.setFont(Font.font("Arial", FontWeight.BOLD, 24)); // Set font style and size for kills text
        totalKillsText.setFill(Color.LIGHTBLUE); // Set text color to light blue

        // Place the total score and kills text inside a VBox for alignment
        VBox summaryBox = new VBox(10, totalScoreText, totalKillsText);
        summaryBox.setAlignment(Pos.CENTER);

        // Level-wise scores
        VBox levelScoresBox = new VBox(20);
        levelScoresBox.setAlignment(Pos.CENTER);
        String[] levelNames = {"", "Level 1", "Level 2", "Level 3", "Level 4"}; // Level names, with an empty entry for alignment

        for (int i = 1; i < levelNames.length; i++) {
            int levelScore = user.getLevelScore(i); // Retrieve score for each level from user stats
            levelScoresBox.getChildren().add(createLevelScoreDisplay(levelNames[i], levelScore)); // Create display for level score
        }

        // Main Menu Button
        Button mainMenuButton = ButtonFactory.createImageButton(("/com/example/demo/images/menuButton.png"), 100, 100);;
        mainMenuButton.setOnAction(e -> {
            // Navigate to the Main Menu when clicked
            LevelStateHandler.getInstance().goToMainMenu();
        });

        // Place the button in a VBox to center it
        VBox buttonBox = new VBox(mainMenuButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Combine all UI components (title, summary, level scores, and button) into the main layout
        VBox mainLayout = new VBox(20, titleBox, summaryBox, levelScoresBox, buttonBox);
        mainLayout.setAlignment(Pos.CENTER); // Center all components
        mainLayout.setPrefSize(stage.getWidth(), stage.getHeight()); // Set layout size to match the stage

        newRoot.getChildren().add(mainLayout); // Add the main layout to the root group

        // Add the confetti effect
        addConfettiEffect(newRoot);

        // Display the stage with the new scene
        stage.show();
    }

    /**
     * Creates a display for each level's score, showing the level name and corresponding star image based on the score.
     * <p>
     * This method generates an HBox that contains the level's name and an image representing the score with stars.
     * </p>
     *
     * @param levelName the name of the level
     * @param score     the score for the level
     * @return an HBox containing the level name and star image
     */
    private HBox createLevelScoreDisplay(String levelName, int score) {
        // Generate star image path based on score
        LevelScoreGenerator scoreGenerator = new LevelScoreGenerator();
        String starImagePath = scoreGenerator.getStarImagePath(score);

        // Create star image based on the score
        Image starImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(starImagePath)));
        ImageView starImageView = new ImageView(starImage);
        starImageView.setFitWidth(200); // Set the width for the star image
        starImageView.setPreserveRatio(true); // Maintain the aspect ratio of the star image

        // Create text for the level name
        Text levelText = new Text(levelName);
        levelText.setFont(Font.font("Arial", FontWeight.NORMAL, 18)); // Set font size and style for level name
        levelText.setFill(Color.LIGHTYELLOW); // Set text color to light yellow

        // Combine level text and star image in an HBox
        HBox levelScoreBox = new HBox(10, levelText, starImageView);
        levelScoreBox.setAlignment(Pos.CENTER); // Center align the HBox

        return levelScoreBox; // Return the HBox containing the level score display
    }

    /**
     * Adds a confetti effect to the screen, where circles fall from random positions.
     * <p>
     * This method generates circles with random colors and animates them falling down the screen in a loop.
     * </p>
     *
     * @param root the root group to which the confetti circles are added
     */
    private void addConfettiEffect(Group root) {
        // Create confetti effect by generating 50 falling circles with random colors
        for (int i = 0; i < 50; i++) {
            Circle confetti = new Circle(5, Color.color(Math.random(), Math.random(), Math.random())); // Random color for each circle
            confetti.setTranslateX(Math.random() * stage.getWidth()); // Random x position
            confetti.setTranslateY(Math.random() * stage.getHeight()); // Random y position

            root.getChildren().add(confetti); // Add the confetti circle to the root group

            // Animate the falling confetti
            TranslateTransition transition = new TranslateTransition(Duration.seconds(3 + Math.random() * 2), confetti);
            transition.setByY(stage.getHeight()); // Make the circle fall down the screen
            transition.setCycleCount(Animation.INDEFINITE); // Repeat the animation indefinitely
            transition.setInterpolator(Interpolator.EASE_OUT); // Smooth fall
            transition.play(); // Start the animation
        }
    }
}
