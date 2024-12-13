package com.example.demo.displays;

import com.example.demo.activityManagers.LevelManager;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * This class is responsible for displaying the start screen of the game.
 * It sets up the background, title, buttons for starting the game and quitting,
 * and manages the actions when these buttons are clicked.
 */
public class StartScreen {
    /**
     * The file path for the background image of the start screen.
     */
    private static final String BACKGROUND_IMAGE = "/com/example/demo/images/startPageBackground.png";

    /**
     * The main stage where the start screen will be displayed.
     */
    private Stage stage;

    /**
     * The button that starts the game.
     */
    private Button startButton;

    /**
     * The button that quits the game.
     */
    private Button quitButton;

    /**
     * The file path for the start button image.
     */
    private static final String START_BUTTON_IMAGE = "/com/example/demo/images/startButton.png";

    /**
     * The file path for the quit button image.
     */
    private static final String QUIT_BUTTON_IMAGE = "/com/example/demo/images/quitButton.png";

    /**
     * Constructor to initialize the StartScreen with a reference to the main stage.
     *
     * @param stage the main application stage
     */
    public StartScreen(Stage stage) {
        this.stage = stage; // Initialize the stage reference
    }

    /**
     * Displays the start screen with background, title, and buttons.
     * The start and quit buttons are configured, and actions are assigned to them.
     */
    public void show() {
        // Set up the background image
        ImageView backgroundImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(BACKGROUND_IMAGE))));
        backgroundImageView.setFitWidth(stage.getWidth());
        backgroundImageView.setFitHeight(stage.getHeight());
        backgroundImageView.setPreserveRatio(false);

        // Create an ImageView for the game title
        ImageView titleImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo/images/gameTitle1.png"))));
        titleImageView.setFitWidth(400);
        titleImageView.setPreserveRatio(true);
        titleImageView.setLayoutX((stage.getWidth() - titleImageView.getFitWidth()) / 2); // Center the title
        titleImageView.setLayoutY(20); // Set Y position for title

        // Create buttons dynamically using ButtonFactory
        startButton = ButtonFactory.createImageButton(START_BUTTON_IMAGE, 350, 400);
        startButton.setLayoutX(stage.getWidth() / 2 - 30); // Set X position for start button
        startButton.setLayoutY(stage.getHeight() - 200); // Set Y position for start button

        quitButton = ButtonFactory.createImageButton(QUIT_BUTTON_IMAGE, 350, 400);
        quitButton.setLayoutX(stage.getWidth() / 2 - 360); // Set X position for quit button
        quitButton.setLayoutY(stage.getHeight() - 200); // Set Y position for quit button

        // Set actions for the buttons
        startButton.setOnAction(event -> startGame());
        quitButton.setOnAction(event -> quitGame());

        // Create a root pane and add the background and layout
        Group root = new Group();
        root.getChildren().addAll(backgroundImageView, titleImageView, startButton, quitButton);

        // Set the scene
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("SKY BATTLE");

        stage.setMaximized(true);
        stage.show();
    }

    /**
     * Starts the game by navigating to the current level's start screen.
     */
    public void startGame() {
        LevelManager levelManager = LevelManager.getInstance();
        // Navigate to the current level's start screen
        levelManager.showLevelStartScreen(levelManager.getCurrentLevelNumber());
    }

    /**
     * Exits the game by terminating the application.
     */
    public void quitGame() {
        System.exit(0);
    }
}
