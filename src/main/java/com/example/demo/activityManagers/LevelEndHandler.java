package com.example.demo.activityManagers;

import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.gameConfig.AppStage;
import com.example.demo.gameConfig.GameTimeline;
import com.example.demo.notUsed.WinImage;
import com.example.demo.screensAndOverlays.LevelCompletedOverlay;
import com.example.demo.screensAndOverlays.LevelLostOverlay;
import com.example.demo.screensAndOverlays.YouWinScreen;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LevelEndHandler {
    private static final int WIN_IMAGE_X_POSITION = 355;
    private static final int WIN_IMAGE_Y_POSITION = 175;

    private final Group root;
    private final Timeline timeline;
    private final Stage stage;

    private final WinImage winImage;
    private LevelCompletedOverlay levelCompletedOverlay; // Declare without instantiation
    private LevelLostOverlay levelLostOverlay;
    private YouWinScreen youWinScreen;

    public LevelEndHandler(Group root) {
        this.root = root;
        this.timeline = GameTimeline.getInstance().getTimeline();
        this.stage = AppStage.getInstance().getPrimaryStage();
        this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
    }

    public void handleLevelCompletion(int userScore, String imagePath, UserPlane user) {
        // Stop the game timeline
        timeline.stop();

        // Create the LevelCompletedOverlay with the current scene and parameters
        levelCompletedOverlay = new LevelCompletedOverlay(new Scene(new StackPane(), stage.getWidth(), stage.getHeight()), userScore, imagePath, user);

        // Show the level completed overlay
        levelCompletedOverlay.showOverlay();
        root.getChildren().add(levelCompletedOverlay.getOverlay());
        System.out.println("Level completed!");
    }

    public void handleLevelLoss(int userScore) {
        // Stop the game timeline
        timeline.stop();

        // Create the LevelCompletedOverlay with the current scene and parameters
        levelLostOverlay = new LevelLostOverlay(new Scene(new StackPane(), stage.getWidth(), stage.getHeight()), userScore);

        // Show the level lost overlay
        levelLostOverlay.showOverlay();
        root.getChildren().add(levelLostOverlay.getOverlay());
        System.out.println("Level lost.");
    }

    public void handleGameWon(UserPlane user) {
        // Stop the game timeline
        timeline.stop();
        youWinScreen = new YouWinScreen(user);
//        youWinScreen.showOverlay();
//        root.getChildren().add(youWinScreen.getOverlay());
        System.out.println("Game won!");
    }
}