 package com.example.demo.activityManagers;

import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.functionalClasses.GenerateLevelScore;
import com.example.demo.gameConfig.AppStage;
import com.example.demo.gameConfig.GameTimeline;
import com.example.demo.screensAndOverlays.*;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

 public class LevelStateHandler {
     private final Timeline timeline;
     private Timeline redeemLifeTimer;  // Timer for redeem life overlay
     private BaseOverlay redeemLife;
     private OverlayFactory overlayFactory;
     private final LevelManager levelManager;
     private final GenerateLevelScore levelScore;
     private UserPlane userInstance;

     Object[][] completedOverlayButtons = {
             {"/com/example/demo/images/menuButton.png", (Runnable) this::goToMainMenu},
             {"/com/example/demo/images/restartButton.png", (Runnable) this::retryLevel},
             {"/com/example/demo/images/nextButton.png", (Runnable) this::goToNextLevel}
     };

     Object[][] lostOverlayButtons = {
             {"/com/example/demo/images/menuButton.png", (Runnable) this::goToMainMenu},
             {"/com/example/demo/images/restartButton.png", (Runnable) this::retryLevel},
     };

     Object[][] redeemLifeButton = {
             {"/com/example/demo/images/heart.png", (Runnable) this::reviveLevel},
     };

     Object[][] pauseOverlayButtons = {
             {"/com/example/demo/images/resumeButton.png", (Runnable) this::resumeLevel},
             {"/com/example/demo/images/menuButton.png", (Runnable) this::goToMainMenu},
             {"/com/example/demo/images/volumeButton.png", (Runnable) this::toggleVolume},
             {"/com/example/demo/images/restartButton.png", (Runnable) this::retryLevel}

     };

     public LevelStateHandler() {
         this.timeline = GameTimeline.getInstance().getTimeline();
         this.levelManager = LevelManager.getInstance();
         this.levelScore = new GenerateLevelScore();
         this.overlayFactory = OverlayFactory.getInstance();
     }

     public void handleLevelCompletion(Group root, UserPlane user, int coinsCollected) {
         timeline.stop();
         userInstance = user;
         int calculatedScore = levelScore.calculateScore(user.getHealth(), coinsCollected);
         user.setLevelScore(levelManager.getCurrentLevelNumber(), calculatedScore);

         String starImage = levelScore.getStarImagePath(calculatedScore);
         BaseOverlay overlay = overlayFactory.createOverlay(
                 root,
                 "Level Completed!",
                 "Your Game Score: " + user.getScore(),
                 completedOverlayButtons,
                 starImage
         );
         overlay.show();
     }

     public void showRedeemLife(Group root, UserPlane user) {
         timeline.pause();
         userInstance = user;

         BaseOverlay overlay = overlayFactory.createOverlay(
                 root,
                 "Redeem Life!",
                 "Coins Available: " + user.getScore(),
                 redeemLifeButton,
                 null
         );
         redeemLifeTimer = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
             overlay.hide();  // Remove the overlay before triggering the level loss
             handleLevelLoss(root, user);
         }));
         redeemLifeTimer.setCycleCount(1);  // Only execute once after 5 seconds

         overlay.show();
         redeemLifeTimer.play();  // Start the timer
     }

     public void handleLevelLoss(Group root, UserPlane user) {
         user.destroyUser();
         timeline.stop();
         String starImage = levelScore.getStarImagePath(0);
         BaseOverlay overlay = overlayFactory.createOverlay(
                 root,
                 "Level Failed!",
                 "Your Game Score: " + user.getScore(),
                 lostOverlayButtons,
                 starImage
         );
         overlay.show();
     }

     public void handleLevelPaused(Group root, UserPlane user) {
         timeline.stop();
         BaseOverlay overlay = overlayFactory.createOverlay(
                 root,
                 "Paused",
                 "Your Game Score: " + user.getScore(),
                 pauseOverlayButtons,
                 null
         );
         overlay.show();
     }

     // Actions for buttons (implement game-specific logic here)
     public void goToMainMenu() {
         System.out.println("Navigating to Main Menu...");
         StartScreen startScreen = new StartScreen(AppStage.getInstance().getPrimaryStage());
         startScreen.show();
     }

     public void goToNextLevel() {
         System.out.println("Starting next level...");
         levelManager.incrementCurrentLevelNumber();
         levelManager.showLevelStartScreen(levelManager.getCurrentLevelNumber());
     }

     private void retryLevel() {
         System.out.println("Retrying current level...");
         LevelManager levelManager = LevelManager.getInstance();
         levelManager.showLevelStartScreen(levelManager.getCurrentLevelNumber());
     }

     private void reviveLevel() {
         System.out.println("Reviving current level...");
         userInstance.reviveUser();
         timeline.play();

         // Stop the redeem life timer if the user clicked the button
         if (redeemLifeTimer != null) {
             redeemLifeTimer.stop();
         }
     }

     private void resumeLevel() {
         System.out.println("Resuming current level...");
         timeline.play();

         // Stop the redeem life timer if the user clicked the button
         if (redeemLifeTimer != null) {
             redeemLifeTimer.stop();
         }
     }

     private void toggleVolume() {
         System.out.println("Volume toggled...");
         timeline.play();
     }
 }


//    public void handleGameWon(UserPlane user) {
//        timeline.stop();
//        YouWinScreen youWinScreen = new YouWinScreen(user);
//        sceneManager.showOverlay(youWinScreen.getOverlay());
//    }
