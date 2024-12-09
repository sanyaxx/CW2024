package com.example.demo.screensAndOverlays;

import com.example.demo.activityManagers.LevelManager;
import com.example.demo.controller.AppStage;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class LevelStartScreen {
    private final Stage stage; // Reference to the main application stage
    private int levelNumber; // Current level number
    private final LevelManager levelManager; // Singleton instance of LevelManager
    private String levelAim; // Description of the current level's objective
    private String instructionsText; // Instructions for the current level

    /**
     * Constructor initializes the level number and sets the corresponding aim and instructions.
     *
     * @param levelNumber the current level number
     */
    public LevelStartScreen(int levelNumber) {
        this.stage = AppStage.getInstance().getPrimaryStage(); // Get the main stage from AppStage singleton
        this.levelNumber = levelNumber; // Store the level number
        this.levelManager = LevelManager.getInstance(); // Access LevelManager instance
        this.levelAim = getLevelAim(levelNumber); // Determine the level aim
        this.instructionsText = getInstructions(levelNumber); // Determine the instructions for the level
    }

    /**
     * Retrieves the aim or objective of a specific level.
     *
     * @param levelNumber the level number
     * @return the description of the level's objective
     */
    private String getLevelAim(int levelNumber) {
        switch (levelNumber) {
            case 1:
                return "Kill 10 enemy planes while dodging enemy missiles. Remember that your bullets are limited!";
            case 2:
                return "Defeat the boss: x100 harder to kill!";
            case 3:
                return "The Enemy's friends have spotted you! Shoot them down from all 4 directions!";
            case 4:
                return "Plane is OUT OF FUEL! Collect fuel to survive until you reach the finish line!";
            default:
                return "Unknown level aim";
        }

    }

    /**
     * Retrieves the instructions for a specific level.
     *
     * @param levelNumber the level number
     * @return the instructions for the level
     */
    private String getInstructions(int levelNumber) {
        switch (levelNumber) {
            case 1:
                return "→ Use UP & DOWN arrow keys to move.\n" +
                        "→ SPACE to shoot projectiles.\n" +
                        "→ Avoid enemy missiles." +
                        "→ Remember to collect coins!";
            case 2:
                return "→ Use UP & DOWN arrow keys to move.\n" +
                        "→ SPACE to shoot projectiles.\n" +
                        "→ Avoid direct hits from the boss's lasers.\n" +
                        "→ Remember to collect coins!";
            case 3:
                return "→ Use arrow keys to change direction.\n" +
                        "→ SPACE to shoot projectiles.\n" +
                        "→ Remember to collect coins!\n"+
                        "→ THIS LEVEL MUST BE PASSED IN ONE GO (no life redemptions!!!)";
            case 4:
                return "→ UP arrow key to move.\n" +
                        "→ Plane automatically descends when UP key released.\n" +
                        "→ SPACE to shoot projectiles.\n" +
                        "→ Remember to collect coins!";
            default:
                return "No instructions available for this level.";
        }
    }

    /**
     * Displays the level start screen with animated effects and instructions.
     */
    public void show() {
        // Create a background for the screen
        StackPane rootPane = new StackPane();
        ImageView backgroundImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo/images/background1.jpg"))));
        backgroundImageView.setFitWidth(stage.getWidth());
        backgroundImageView.setFitHeight(stage.getHeight());
        backgroundImageView.setPreserveRatio(false);

        // Title Label for the level number
        Label titleLabel = createLabel("LEVEL " + levelNumber, 72, Color.DARKBLUE);

        // Aim Label for the level's objective
        Label aimLabel = createLabel("Aim: " + levelAim, 36, Color.BLUE);

        // Instructions Label for level-specific instructions
        Label instructionsLabel = createLabel(instructionsText, 24, Color.BLACK);
        instructionsLabel.setWrapText(true);

        // Start Game Instruction Label
        Label startLabel = createLabel("Press SPACE to begin", 24, Color.WHITESMOKE);

        // Layout the elements in a vertical box
        VBox layout = new VBox(20, titleLabel, aimLabel, instructionsLabel, startLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setMaxHeight(stage.getHeight() * 0.6);
        layout.setMaxWidth(stage.getWidth() * 0.6); // Ensure labels stay within 60% of the stage width
        layout.setStyle("-fx-padding: 20px; -fx-background-color: rgba(173, 216, 230, 0.8);"); // Sky-blue background with padding

        // Add layout and background to the root pane
        rootPane.getChildren().addAll(backgroundImageView, layout);

        // Create a group to hold the stack pane and other potential elements
        Group rootGroup = new Group();
        rootGroup.getChildren().add(rootPane); // Add StackPane to the Group

        // Set the scene
        Scene scene = new Scene(rootGroup, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Level Start Screen");

        stage.setMaximized(true);
        stage.show();

        // Set action for SPACE key to start the level
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                int nextLevelNumber = levelNumber++;
                levelManager.setCurrentLevelNumber(nextLevelNumber);
                levelManager.goToNextLevel();
            }
        });
    }

    private Label createLabel(String text, int fontSize, Color color) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", fontSize));
        label.setTextFill(color);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0.0, 0, 0);");
        label.setWrapText(true);
        return label;
    }
}
