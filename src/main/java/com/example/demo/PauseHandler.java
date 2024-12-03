package com.example.demo;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PauseHandler {
    private final Stage stage;
    private final Timeline timeline;
    private final LevelView levelView;
    private final Group root;
    private final PauseOverlay pauseOverlay;

    public PauseHandler(LevelView levelView, Group root) {
        this.root = root;
        this.stage = AppStage.getInstance().getPrimaryStage();
        this.timeline = GameTimeline.getInstance().getTimeline();
        this.levelView = levelView;
        this.pauseOverlay = new PauseOverlay(new Scene(new StackPane(), stage.getWidth(), stage.getHeight()));
    }

    public ResumeButton getResumeButton() {
        return pauseOverlay.getResumeButton(); // Access the ResumeButton from PauseOverlay
    }

    public void pauseGame() {
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.pause(); // Pause the game timeline
            levelView.pauseButton.getPauseButton().setVisible(false);

            // Show the pause overlay if it's not already added
            if (!root.getChildren().contains(pauseOverlay.getOverlay())) {
                root.getChildren().add(pauseOverlay.getOverlay());
            }
            pauseOverlay.showOverlay(); // Show the overlay
            System.out.println("Game paused.");
        }
    }

    public void resumeGame() {
        System.out.println("Resume button clicked."); // Debug statement
        if (timeline.getStatus() == Animation.Status.PAUSED) {
            levelView.pauseButton.getPauseButton().setVisible(true);
            pauseOverlay.hideOverlay(); // Hide the overlay

            // Ensure the overlay is removed from the root if necessary
            root.getChildren().remove(pauseOverlay.getOverlay());
            timeline.play();
            System.out.println("Game resumed."); // Debug statement
        } else {
            System.out.println("Timeline is not paused."); // Debug statement
        }
    }
}