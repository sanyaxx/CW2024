package com.example.demo.notUsed;

import com.example.demo.activityManagers.ButtonFactory;
import com.example.demo.activityManagers.LevelManager;
import com.example.demo.gameConfig.AppStage;
import com.example.demo.screensAndOverlays.StartScreen;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;


public class LevelLostOverlay {
    private final StackPane overlay; // Use StackPane to layer the background and buttons
    private final Scene scene;

    private static final String RESTART_BUTTON_IMAGE = "/com/example/demo/images/restartButton.png";
    private static final String MENU_BUTTON_IMAGE = "/com/example/demo/images/menuButton.png";

    public LevelLostOverlay(Scene scene, int userScore) {
        this.scene = scene;
        Stage stage = AppStage.getInstance().getPrimaryStage();

        // Create a full-screen semi-transparent background
        Rectangle background = new Rectangle(stage.getWidth(), stage.getHeight());
        background.setFill(new Color(0, 0, 0, 0.8)); // Black with 80% opacity for a darker effect

        // Create a message indicating level completion with enhanced styling
        Text completionMessage = new Text("Level Failed!");
        completionMessage.setFont(Font.font("Arial", FontWeight.BOLD, 48)); // Larger font size
        completionMessage.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.SILVER),
                new Stop(1, Color.WHITESMOKE))); // Gradient color
        completionMessage.setEffect(new DropShadow(10, Color.BLACK)); // Add drop shadow for depth
        completionMessage.setStyle("-fx-padding: 20;"); // Add padding

        // Create an instance of GenerateLevelScore to get the star rating
        Image starImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo/images/0stars.png")));
        ImageView starRatingImageView = new ImageView(starImage);
        starRatingImageView.setFitWidth(600); // Set width
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
        Label scoreLabel = new Label("Your Score: " + userScore);
        scoreLabel.setTextFill(Color.SILVER);
        scoreLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;"); // Style the score label

        Button menuButton = ButtonFactory.createImageButton(MENU_BUTTON_IMAGE, 100, 100);
        Button restartButton = ButtonFactory.createImageButton(RESTART_BUTTON_IMAGE, 100, 100);

        // Set actions for the buttons
        menuButton.setOnAction(event -> goToMainMenuPage());
        restartButton.setOnAction(event -> restartLevel());

        // Create an HBox to hold the buttons in a line
        HBox buttonContainer = new HBox(900, restartButton, menuButton);
        buttonContainer.setAlignment(Pos.CENTER); // Center the buttons horizontally
        buttonContainer.setStyle("-fx-padding: 20;"); // Add some padding

        // Create a VBox to center the message and button container vertically
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
        System.out.println("Level Lost overlay is now visible."); // Debug statement
    }

    public void hideOverlay() {
        overlay.setVisible(false);
        scene.getRoot().setEffect(null);
    }

    private void restartLevel() {
        // Logic to go to the next level
        System.out.println("Going to the next level..."); // Debug statement
        hideOverlay();
        LevelManager levelManager = LevelManager.getInstance();
        levelManager.showLevelStartScreen(levelManager.getCurrentLevelNumber());
    }

    private void goToMainMenuPage() {
        // Logic to go to the main menu
        System.out.println("Going to the main menu..."); // Debug statement
        hideOverlay();
        StartScreen startScreen = new StartScreen(AppStage.getInstance().getPrimaryStage());
        startScreen.show();
    }
}
