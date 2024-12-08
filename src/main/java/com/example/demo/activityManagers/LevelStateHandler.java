 package com.example.demo.activityManagers;

import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.functionalClasses.GenerateLevelScore;
import com.example.demo.gameConfig.AppStage;
import com.example.demo.gameConfig.GameTimeline;
import com.example.demo.levels.LevelParent;
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
     private final UserStatsManager userStatsManager;
     private UserPlane userInstance;
     private Group root;
     private LevelParent levelInstance;

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
     Object[][] nextButton = {
             {"/com/example/demo/images/nextButton.png", (Runnable) this::nextPage}
     };


     public LevelStateHandler() {
         this.timeline = GameTimeline.getInstance().getTimeline();
         this.levelManager = LevelManager.getInstance();
         this.levelScore = new GenerateLevelScore();
         this.overlayFactory = OverlayFactory.getInstance();
         this.userStatsManager = UserStatsManager.getInstance();
     }

     public void handleLevelCompletion(Group root1, UserPlane user) {
         timeline.stop();
         userInstance = user;
         root = root1;

         System.out.println("Lives remaining: " + user.getHealth());
         System.out.println("Coins collected: " + userStatsManager.getBulletCount());
         int calculatedScore = levelScore.calculateScore(user.getHealth(), userStatsManager.getBulletCount());
         System.out.println("Level score: " + calculatedScore);
         userStatsManager.setLevelScore(calculatedScore);

         System.out.println("Level score: " + userStatsManager.getLevelScore(levelManager.getCurrentLevelNumber()));
         String starImage = levelScore.getStarImagePath(calculatedScore);
         System.out.println("Level score image: " + starImage);
         BaseOverlay overlay = overlayFactory.createOverlay(
                 root,
                 "Level Completed!",
                 "Total Coins: " + userStatsManager.getCoinsCollected(),
                 completedOverlayButtons,
                 starImage
         );
         overlay.show();
     }

     public void showRedeemLife(Group root1, UserPlane user, LevelParent level) {
         timeline.pause();
         userInstance = user;
         root = root1;
         levelInstance = level;

         BaseOverlay overlay = overlayFactory.createOverlay(
                 root,
                 "Continue level?",
                 "Total Coins: " + userStatsManager.getCoinsCollected() + " coins",
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
                 "Total Coins: " + userStatsManager.getCoinsCollected() + " coins",
                 lostOverlayButtons,
                 starImage
         );
         overlay.show();
     }

     public void insufficientInventory(Group root, UserPlane user) {
         BaseOverlay overlay = overlayFactory.createOverlay(
                 root,
                 "Insufficient Balance!",
                 "Total Coins: " + userStatsManager.getCoinsCollected() + " coins",
                 nextButton,
                 null
         );
         overlay.show();
     }

     public void handleLevelPaused(Group root, UserPlane user) {
         timeline.stop();
         BaseOverlay overlay = overlayFactory.createOverlay(
                 root,
                 "Paused",
                 "Total Coins: " + userStatsManager.getCoinsCollected() + " coins",
                 pauseOverlayButtons,
                 null
         );
         overlay.show();
     }

     // Actions for buttons (implement game-specific logic here)
     public void nextPage() {
         handleLevelLoss(root, userInstance);
     }

     public void goToMainMenu() {
         System.out.println("Navigating to Main Menu...");
         StartScreen startScreen = new StartScreen(AppStage.getInstance().getPrimaryStage());
         startScreen.show();
     }

     public void goToNextLevel() {
         System.out.println("Current Level: " + levelManager.getCurrentLevelNumber());
         if (levelManager.getCurrentLevelNumber() == LevelManager.TOTAL_LEVELS_PLUS1 - 1) {
             // Create a new root and pass it to the YouWinScreen
             Group newRoot = new Group();
             new YouWinScreen(newRoot, userInstance); // Use new root
         } else {
             System.out.println("Starting next level...");
             levelManager.incrementCurrentLevelNumber();
             levelManager.showLevelStartScreen(levelManager.getCurrentLevelNumber());
         }
     }

     private void retryLevel() {
         System.out.println("Retrying current level...");
         levelManager.showLevelStartScreen(levelManager.getCurrentLevelNumber());
     }

     private void reviveLevel() {
         // Stop the redeem life timer if the user clicked the button
         if (redeemLifeTimer != null) {
             redeemLifeTimer.stop();
         }
         if ((userStatsManager.getCoinsCollected()) >= 2){
             System.out.println("Reviving current level...");
             if (userInstance.getHealth() <= 0) { // Health = 0
                 userStatsManager.decrementCoins();
                 userInstance.reviveUserLife();
             }  // Bullets = 0
             else if (userStatsManager.getBulletCount() <= 0){
                 userStatsManager.decrementCoins();
                 levelInstance.bulletCount += 10;
             } else { // Fuel = 0
                 userStatsManager.decrementCoins();
                 userInstance.reviveUserFuel();
             }
             timeline.play();
         } else {
            insufficientInventory(root, userInstance);
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



