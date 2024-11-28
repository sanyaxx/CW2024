//package com.example.demo;
//
//import javafx.scene.control.Label;
//
//import java.io.*;
//
//public class RegisterNewUser {
//    private static final String FILE_PATH = "users.txt"; // Path to the text file storing usernames and levels
//    private Label messageLabel;
//    private static String username;
//
//    public RegisterNewUser(String Username) {
//        username = username;
//    }
//
////    @Override
////    public void start(Stage primaryStage) {
////        primaryStage.setTitle("User  Login");
////
////        usernameField = new TextField();
////        usernameField.setPromptText("Enter your username");
////
////        Button loginButton = new Button("Login");
////        loginButton.setOnAction(e -> loginExistingUser ());
////
////        Button registerButton = new Button("Register");
////        registerButton.setOnAction(e -> registerNewUser ());
////
////        messageLabel = new Label();
////        messageLabel.setStyle("-fx-text-fill: red; -fx-opacity: 0;"); // Start invisible
////
////        VBox layout = new VBox(10);
////        layout.getChildren().addAll(usernameField, loginButton, registerButton, messageLabel);
////        layout.setPadding(new javafx.geometry.Insets(20));
////
////        Scene scene = new Scene(layout, 300, 200);
////        primaryStage.setScene(scene);
////        primaryStage.show();
////    }
//
//
//    private void loginExistingUser () {
//        int level = checkUserExists(username);
//
//        if (level != -1) {
//            // Start the game logic at the retrieved level
//            System.out.println("Welcome back! Starting at level " + level);
//            // Here you would call your game start logic
//        } else {
//            showMessage("Invalid username!", 3000);
//        }
//    }
//
//    private void registerNewUser () {
//        if (isUsernameUnique(username)) {
//            registerUser (username);
//            // Start the game logic at level 1
//            System.out.println("Registration successful! Starting at level 1");
//            // Here you would call your game start logic
//        } else {
//            showMessage("Username already exists! Choose another.", 3000);
//        }
//    }
//
//    private int checkUserExists(String username) {
//        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split("\t");
//                if (parts[0].equals(username)) {
//                    return Integer.parseInt(parts[1]); // Return the level number
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return -1; // Username not found
//    }
//
//    private boolean isUsernameUnique(String username) {
//        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split("\t");
//                if (parts[0].equals(username)) {
//                    return false; // Username already exists
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return true; // Username is unique
//    }
//
//    private void registerUser (String username) {
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
//            bw.write(username + "\t1\n"); // Register with level 1
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void showMessage(String message, int duration) {
//        messageLabel.setText(message);
//        messageLabel.setOpacity(1); // Make it visible
//        new javafx.animation.PauseTransition(javafx.util.Duration.millis(duration))
//                .setOnFinished(e -> messageLabel.setOpacity(0)) // Fade out after duration
//                .play();
//    }
//
//}
