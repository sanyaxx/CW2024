package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class GameWon {
    private final Scene scene; // Scene for this page
    private final Stage stage;

    public GameWon(UserPlane user) {
        this.stage = AppStage.getInstance().getPrimaryStage();

        // Create a full-screen semi-transparent background
        Group root = new Group();
        Rectangle background = new Rectangle(stage.getWidth(), stage.getHeight());
        background.setFill(new Color(0, 0, 0, 0.8)); // Black with 80% opacity for a darker effect

        // Create "You Win!" message
        Text winMessage = new Text("You Win!");
        winMessage.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        winMessage.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.SILVER),
                new Stop(1, Color.WHITESMOKE))); // Gradient color

        // Create an ImageView for the "You Win!" image
//        Image winImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo/images/youwin.png")));
//        ImageView winImageView = new ImageView(winImage);
//        winImageView.setFitWidth(600); // Set width
//        winImageView.setPreserveRatio(true); // Preserve aspect ratio

        // Create a VBox to hold the win message and scores
        VBox scoreSummary = new VBox(20);
        scoreSummary.setAlignment(Pos.CENTER);
        scoreSummary.setStyle("-fx-padding: 20;");

        // Array of level names
        String[] levelNames = {"Level 1", "Level 2", "Level 3", "Level 4"};

        // Loop through each level to create labels and image views
        for (int i = 0; i < levelNames.length; i++) {
            int score = user.getLevelScore(i); // Get the score for each level from the user
            scoreSummary.getChildren().add(createLevelScoreDisplay(levelNames[i] + ": ", score));
        }

        // Create a button to return to the main menu
        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(e -> {
            // Logic to return to the main menu
            System.out.println("Returning to the main menu..."); // Debug statement
            // Implement the logic to show the main menu
            // For example, you can set the scene to the main menu scene
            // stage.setScene(mainMenuScene);
        });

        // Add components to the root
        VBox vbox = new VBox(20, winMessage, scoreSummary, mainMenuButton);
        vbox.setAlignment(Pos.CENTER);
        root.getChildren().addAll(background, vbox);

        // Create the scene
        this.scene = new Scene(root, stage.getWidth(), stage.getHeight());

        // Set the scene to the primary stage
        stage.setScene(scene);
        stage.show(); // Show the stage
    }

    private HBox createLevelScoreDisplay(String levelName, int score) {
        // Create an instance of GenerateLevelScore to get the star rating
        GenerateLevelScore completedLevelScore = new GenerateLevelScore(score);
        String starImagePath = completedLevelScore.getStarImage();
        Image starImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(starImagePath)));
        ImageView starImageView = new ImageView(starImage);
        starImageView.setFitWidth(100); // Set desired width for the star image
        starImageView.setPreserveRatio(true); // Preserve aspect ratio

        // Create a label for the level name and score
        Label levelLabel = new Label(levelName + score);
        levelLabel.setTextFill(Color.LIGHTBLUE); // Change color of level name and score
        levelLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 24)); // Set font style and size

        // Create an HBox to hold the label and star image, centering them
        HBox levelScoreDisplay = new HBox(10, levelLabel, starImageView);
        levelScoreDisplay.setAlignment(Pos.CENTER); // Align items to the center

        return levelScoreDisplay; // Return the HBox containing the level score display
    }
}