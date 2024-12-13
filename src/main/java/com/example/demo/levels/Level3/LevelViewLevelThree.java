package com.example.demo.levels.Level3;

import com.example.demo.displays.DisplayWinningParameter;
import com.example.demo.levels.LevelView;
import javafx.scene.Group;

/**
 * The LevelViewLevelThree class is a specialized view for Level 3 of the game.
 * It extends the LevelView class and includes additional winning parameters,
 * such as survival time, ammo count, and coins collected.
 */
public class LevelViewLevelThree extends LevelView {

    /** The root node of the scene where UI components will be added. */
    private final Group root;

    /**
     * Constructor to initialize the LevelView for Level 3, including health, ammo, survival time, and coins.
     *
     * @param root The root node of the scene where the UI components will be added.
     * @param heartsToDisplay The number of hearts to display based on the player's health.
     * @param bulletCount The current bullet count to be displayed.
     * @param survivalTime The remaining survival time to be displayed.
     * @param coinsCollected The current number of coins collected to be displayed.
     */
    public LevelViewLevelThree(Group root, int heartsToDisplay, int bulletCount, int survivalTime, int coinsCollected) {
        super(root, heartsToDisplay, bulletCount, coinsCollected); // Call the superclass constructor to initialize common UI components
        this.root = root; // Initialize the root node
        // Initialize the displayWinningParameter with ammo count, survival time, and coins collected
        this.displayWinningParameter = new DisplayWinningParameter(
                "/com/example/demo/images/ammoCount.png", bulletCount, // Initialize ammo count display
                "/com/example/demo/images/fuelTimer.png", survivalTime, // Initialize survival time display
                "/com/example/demo/images/coin.png", coinsCollected // Initialize coins collected display
        );
    }
}
