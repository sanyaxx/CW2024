package com.example.demo;

import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Objects;

public class LevelCompletedOverlay {
    private final StackPane overlay; // Use StackPane to layer the background and buttons
    private final Scene scene;
    private final NextLevelButton nextLevelButton;
    private final StartPageButton startPageButton;
    private final Text completionMessage; // Using Text for the title
    private final Label scoreLabel; // Label to display the user's score
    private final Stage stage;
    private final ImageView starRatingImageView; // ImageView to display the star rating image
    private LevelEndHandler levelEndHandler;
    private final UserPlane user;

    public LevelCompletedOverlay(Scene scene, int userScore, String imagePath, UserPlane user) {
        this.scene = scene;
        this.user = user;
        this.levelEndHandler = new LevelEndHandler(new Group());
        this.stage = AppStage.getInstance().getPrimaryStage();
        this.nextLevelButton = new NextLevelButton();
        this.startPageButton = new StartPageButton();

        // Create a full-screen semi-transparent background
        Rectangle background = new Rectangle(stage.getWidth(), stage.getHeight());
        background.setFill(new Color(0, 0, 0, 0.8)); // Black with 80% opacity for a darker effect

        // Create a message indicating level completion with enhanced styling
        completionMessage = new Text("Level Completed!");
        completionMessage.setFont(Font.font("Arial", FontWeight.BOLD, 48)); // Larger font size
        completionMessage.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.SILVER),
                new Stop(1, Color.WHITESMOKE))); // Gradient color
        completionMessage.setEffect(new DropShadow(10, Color.BLACK)); // Add drop shadow for depth
        completionMessage.setStyle("-fx-padding: 20;"); // Add padding

        // Create an instance of GenerateLevelScore to get the star rating
        Image starImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        starRatingImageView = new ImageView(starImage);
        starRatingImageView.setFitWidth(600); // Set desired width
        starRatingImageView.setPreserveRatio(true); // Preserve aspect ratio

        // Add animation to the star image
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), starRatingImageView);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.2); // Enlarge to 120%
        scaleTransition.setToY(1.2);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();

        // Create a label to display the user's score
        scoreLabel = new Label("Your Score: " + userScore);
        scoreLabel.setTextFill(Color.SILVER);
        scoreLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;"); // Style the score label

        // Set actions for the buttons
        startPageButton.setOnStartPageAction(this::goToStartPage);
        nextLevelButton.setOnNextLevelAction(this::goToNextLevel);

        // Create an HBox to hold the buttons in the new order
        HBox buttonContainer = new HBox(900, startPageButton.getStartPageButton(), nextLevelButton.getNextLevelButton());
        buttonContainer.setAlignment(Pos.CENTER); // Center the buttons horizontally
        buttonContainer.setStyle("-fx-padding: 20;"); // Add some padding

        // Create a VBox to center the message, image, score, and button container vertically
        VBox vbox = new VBox(20, completionMessage, starRatingImageView, scoreLabel, buttonContainer);
        vbox.setAlignment(Pos.CENTER); // Center the content vertically
        vbox.setStyle("-fx-padding: 30;"); // Add some padding around the buttons

        // Create the overlay
        overlay = new StackPane();
        overlay.getChildren().addAll(background, vbox);
        overlay.setVisible(false); // Initially hidden

        // Add the overlay to the existing scene
        StackPane root = (StackPane) scene.getRoot();
        root.getChildren().add(overlay);
    }

    public StackPane getOverlay() {
        return overlay; // Return the overlay
    }

    public void showOverlay() {
        overlay.setVisible(true);
        overlay.toFront();
        GaussianBlur blur = new GaussianBlur(50); // Increase blur for a more pronounced effect
        scene.getRoot().setEffect(blur);
        System.out.println("Level Completed overlay is now visible."); // Debug statement
    }

    public void hideOverlay() {
        overlay.setVisible(false);
        scene.getRoot().setEffect(null);
    }

    private void goToNextLevel() {
        // Logic to go to the next level
        System.out.println("Going to the next level..."); // Debug statement
        hideOverlay();
        LevelManager levelManager = LevelManager.getInstance();
        levelManager.showLevelStartScreen(levelManager.getCurrentLevelNumber());
    }

    private void goToStartPage() {
        // Logic to go to the main menu
        System.out.println("Going to the main menu..."); // Debug statement
        hideOverlay();
        StartPage startPage = new StartPage(AppStage.getInstance().getPrimaryStage());
        startPage.show();
    }
}