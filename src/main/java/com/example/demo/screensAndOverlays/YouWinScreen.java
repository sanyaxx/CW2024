package com.example.demo.screensAndOverlays;

import com.example.demo.activityManagers.UserStatsManager;
import com.example.demo.functionalClasses.GenerateLevelScore;
import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
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

public class YouWinScreen {
    private final Scene scene;
    private final Stage stage;
    private final UserStatsManager userStatsManager;

    public YouWinScreen(Group root, UserPlane user) {
        this.stage = AppStage.getInstance().getPrimaryStage();
        this.userStatsManager = UserStatsManager.getInstance();

        // Create a fresh root and scene
        Group newRoot = new Group();
        this.scene = new Scene(newRoot, stage.getWidth(), stage.getHeight(), Color.BLACK);
        stage.setScene(scene);

        // Title: "YOU WON!"
        Text title = new Text("YOU WON!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 100));
        title.setFill(Color.GOLD);
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(2);

        // Center align the title
        VBox titleBox = new VBox(title);
        titleBox.setAlignment(Pos.CENTER);

        // Summary: Total score and total kills
        Text totalScoreText = new Text("Total Score: " + userStatsManager.getCoinsCollected());
        totalScoreText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        totalScoreText.setFill(Color.LIGHTBLUE);

        Text totalKillsText = new Text("Total Kills: " + userStatsManager.getNumberOfKills());
        totalKillsText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        totalKillsText.setFill(Color.LIGHTBLUE);

        VBox summaryBox = new VBox(10, totalScoreText, totalKillsText);
        summaryBox.setAlignment(Pos.CENTER);

        // Level-wise scores
        VBox levelScoresBox = new VBox(20);
        levelScoresBox.setAlignment(Pos.CENTER);
        String[] levelNames = {"", "Level 1", "Level 2", "Level 3", "Level 4"}; // first index left empty for correct level number logic

        for (int i = 1; i < levelNames.length; i++) {
            int levelScore = userStatsManager.getLevelScore(i); // Get level score from user
            levelScoresBox.getChildren().add(createLevelScoreDisplay(levelNames[i], levelScore));
        }

        // Main Menu Button
        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        mainMenuButton.setOnAction(e -> {
            // Navigate to Main Menu
            StartScreen startScreen = new StartScreen(stage);
            startScreen.show();
        });

        VBox buttonBox = new VBox(mainMenuButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Combine everything in the main layout
        VBox mainLayout = new VBox(20, titleBox, summaryBox, levelScoresBox, buttonBox);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPrefSize(stage.getWidth(), stage.getHeight()); // Match scene size

        newRoot.getChildren().add(mainLayout);

        // Add confetti effect
        addConfettiEffect(newRoot);

        // Display the stage
        stage.show();
    }

    private HBox createLevelScoreDisplay(String levelName, int score) {
        // Generate star image path based on score
        GenerateLevelScore scoreGenerator = new GenerateLevelScore();
        String starImagePath = scoreGenerator.getStarImagePath(score);

        // Create star image
        Image starImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(starImagePath)));
        ImageView starImageView = new ImageView(starImage);
        starImageView.setFitWidth(200); // Set width for star image
        starImageView.setPreserveRatio(true);

        // Level label
        Text levelText = new Text(levelName);
        levelText.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        levelText.setFill(Color.LIGHTYELLOW);

        // Combine label and image in HBox
        HBox levelScoreBox = new HBox(10, levelText, starImageView);
        levelScoreBox.setAlignment(Pos.CENTER);

        return levelScoreBox;
    }

    private void addConfettiEffect(Group root) {
        // Example logic for confetti effect
        for (int i = 0; i < 50; i++) {
            Circle confetti = new Circle(5, Color.color(Math.random(), Math.random(), Math.random()));
            confetti.setTranslateX(Math.random() * stage.getWidth());
            confetti.setTranslateY(Math.random() * stage.getHeight());

            root.getChildren().add(confetti);

            // Animation for falling confetti
            TranslateTransition transition = new TranslateTransition(Duration.seconds(3 + Math.random() * 2), confetti);
            transition.setByY(stage.getHeight());
            transition.setCycleCount(Animation.INDEFINITE);
            transition.setInterpolator(Interpolator.EASE_OUT);
            transition.play();
        }
    }
}
