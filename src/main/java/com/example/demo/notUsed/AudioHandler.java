//package com.example.demo;
//
////import javafx.scene.media.Media;
////import javafx.scene.media.MediaPlayer;
//
//public class AudioHandler {
//
//    // Singleton instance
//    private static AudioHandler instance;
//
//    // Paths to your MP3 files
//    private final String COLLISION_SOUND_PATH = "path/to/collision_sound.mp3";
//    private final String LIFE_LOST_SOUND_PATH = "path/to/life_lost_sound.mp3";
//    private final String BACKGROUND_MUSIC_PATH = "path/to/background_music.mp3";
//
//    // Media players for each type of sound
//    private MediaPlayer collisionMediaPlayer;
//    private MediaPlayer lifeLostMediaPlayer;
//    private MediaPlayer backgroundMusicPlayer;
//
//    // Private constructor for Singleton pattern
//    private AudioHandler() {
//        // Initialize media players for each sound
//        Media collisionMedia = new Media(getClass().getResource(COLLISION_SOUND_PATH).toExternalForm());
//        collisionMediaPlayer = new MediaPlayer(collisionMedia);
//
//        Media lifeLostMedia = new Media(getClass().getResource(LIFE_LOST_SOUND_PATH).toExternalForm());
//        lifeLostMediaPlayer = new MediaPlayer(lifeLostMedia);
//
//        Media backgroundMusicMedia = new Media(getClass().getResource(BACKGROUND_MUSIC_PATH).toExternalForm());
//        backgroundMusicPlayer = new MediaPlayer(backgroundMusicMedia);
//        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);  // Loop background music
//    }
//
//    // Method to get the singleton instance
//    public static AudioHandler getInstance() {
//        if (instance == null) {
//            instance = new AudioHandler();
//        }
//        return instance;
//    }
//
//    // Method to play collision sound
//    public void playCollisionSound() {
//        collisionMediaPlayer.seek(collisionMediaPlayer.getStartTime()); // Rewind to start
//        collisionMediaPlayer.play();
//    }
//
//    // Method to play life lost sound
//    public void playLifeLostSound() {
//        lifeLostMediaPlayer.seek(lifeLostMediaPlayer.getStartTime()); // Rewind to start
//        lifeLostMediaPlayer.play();
//    }
//
//    // Method to start playing background music
//    public void startBackgroundMusic() {
//        backgroundMusicPlayer.play();
//    }
//
//    // Method to stop background music
//    public void stopBackgroundMusic() {
//        backgroundMusicPlayer.stop();
//    }
//}
//
