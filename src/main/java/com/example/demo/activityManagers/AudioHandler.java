package com.example.demo.activityManagers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * The AudioHandler class is responsible for managing the audio in the game, including playing various sound effects
 * and managing the mute functionality.
 * It follows the Singleton design pattern to ensure there is only one instance of this class used throughout the application.
 */
public class AudioHandler {

    /** Singleton instance of AudioHandler */
    private static AudioHandler instance;

    // Paths to the audio files
    /** Path to the sound when the projectile is fired */
    private final String PROJECTILE_FIRED_SOUND_PATH = "/com/example/demo/audios/fireProjectile.mp3";

    /** Path to the sound when a life is lost */
    private final String LIFE_LOST_SOUND_PATH = "/com/example/demo/audios/loseLife.mp3";

    /** Path to the sound when the level is won */
    private final String LEVEL_WON_SOUND_PATH = "/com/example/demo/audios/winGame.mp3";

    /** Path to the sound when the level is lost */
    private final String LEVEL_LOST_SOUND_PATH = "/com/example/demo/audios/loseGame.mp3";

    // MediaPlayers for playing the sounds
    /** MediaPlayer for the projectile fired sound */
    private MediaPlayer projectileFiredPlayer;

    /** MediaPlayer for the life lost sound */
    private MediaPlayer lifeLostMediaPlayer;

    /** MediaPlayer for the level won sound */
    private MediaPlayer levelWonPlayer;

    /** MediaPlayer for the level lost sound */
    private MediaPlayer levelLostPlayer;

    // Variable to track whether the audio is muted or not
    /** Boolean flag indicating if the audio is muted */
    private boolean isMuted = false;

    /**
     * Private constructor for the AudioHandler class, following the Singleton pattern.
     * Initializes the media players for all sounds.
     */
    private AudioHandler() {
        // Initialize media players for each sound
        Media collisionMedia = new Media(getClass().getResource(PROJECTILE_FIRED_SOUND_PATH).toExternalForm());
        projectileFiredPlayer = new MediaPlayer(collisionMedia);

        Media lifeLostMedia = new Media(getClass().getResource(LIFE_LOST_SOUND_PATH).toExternalForm());
        lifeLostMediaPlayer = new MediaPlayer(lifeLostMedia);

        Media levelWonMedia = new Media(getClass().getResource(LEVEL_WON_SOUND_PATH).toExternalForm());
        levelWonPlayer = new MediaPlayer(levelWonMedia);

        Media levelLostMedia = new Media(getClass().getResource(LEVEL_LOST_SOUND_PATH).toExternalForm());
        levelLostPlayer = new MediaPlayer(levelLostMedia);
    }

    /**
     * Returns the singleton instance of the AudioHandler class.
     * If the instance does not exist, it is created.
     *
     * @return the singleton instance of the AudioHandler class
     */
    public static AudioHandler getInstance() {
        if (instance == null) {
            instance = new AudioHandler();
        }
        return instance;
    }

    /**
     * Stops all currently playing sounds.
     * This method ensures that when a new sound is triggered, all previous sounds are stopped.
     */
    public void stopAllSounds() {
        projectileFiredPlayer.stop();
        lifeLostMediaPlayer.stop();
        levelWonPlayer.stop();
        levelLostPlayer.stop();
    }

    /**
     * Plays the sound for when a projectile is fired.
     * If the audio is not muted, it will stop any currently playing sounds and start the new sound.
     */
    public void playProjectileFiredSound() {
        if (!isMuted) {
            stopAllSounds(); // Stop any currently playing sound
            projectileFiredPlayer.seek(projectileFiredPlayer.getStartTime()); // Rewind to start
            projectileFiredPlayer.play();
        }
    }

    /**
     * Plays the sound for when a life is lost.
     * If the audio is not muted, it will stop any currently playing sounds and start the new sound.
     */
    public void playLifeLostSound() {
        if (!isMuted) {
            stopAllSounds(); // Stop any currently playing sound
            lifeLostMediaPlayer.seek(lifeLostMediaPlayer.getStartTime()); // Rewind to start
            lifeLostMediaPlayer.play();
        }
    }

    /**
     * Plays the sound for when the level is won.
     * If the audio is not muted, it will stop any currently playing sounds and start the new sound.
     */
    public void playLevelWonSound() {
        if (!isMuted) {
            stopAllSounds(); // Stop any currently playing sound
            levelWonPlayer.seek(levelWonPlayer.getStartTime()); // Rewind to start
            levelWonPlayer.play();
        }
    }

    /**
     * Plays the sound for when the level is lost.
     * If the audio is not muted, it will stop any currently playing sounds and start the new sound.
     */
    public void playLevelLostSound() {
        if (!isMuted) {
            stopAllSounds(); // Stop any currently playing sound
            levelLostPlayer.seek(levelLostPlayer.getStartTime()); // Rewind to start
            levelLostPlayer.play();
        }
    }

    /**
     * Toggles the mute state of the audio.
     * If audio is currently muted, it will stop all sounds. If audio is un-muted, sounds will resume playing.
     */
    public void toggleAudio() {
        isMuted = !isMuted; // Toggle mute state
        if (isMuted) {
            stopAllSounds(); // Stop all sounds when muted
        }
    }
}
