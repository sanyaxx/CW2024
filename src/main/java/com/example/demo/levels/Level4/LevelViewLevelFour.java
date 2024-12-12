package com.example.demo.levels.Level4;

import com.example.demo.displays.DisplayWinningParameter;
import com.example.demo.levels.LevelView;
import javafx.scene.Group;

/**
 * The LevelViewLevelFour class is a specialized view for Level 4 of the game.
 * It extends the LevelView class and includes additional winning parameters,
 * such as fuel capacity, ammo count, and coins collected.
 */
public class LevelViewLevelFour extends LevelView {

    /** The root node of the scene where UI components will be added. */
    private final Group root;

    /**
     * Constructor to initialize the LevelView for Level 4, including health, ammo, fuel, and coins.
     *
     * @param root The root node of the scene where the UI components will be added.
     * @param heartsToDisplay The number of hearts to display based on the player's health.
     * @param bulletCount The current bullet count to be displayed.
     * @param fuelCapacity The current fuel capacity to be displayed.
     * @param coinsCollected The current number of coins collected to be displayed.
     */
    public LevelViewLevelFour(Group root, int heartsToDisplay, int bulletCount, int fuelCapacity, int coinsCollected) {
        super(root, heartsToDisplay, bulletCount, coinsCollected); // Call the superclass constructor to initialize common UI components
        this.root = root; // Initialize the root node
        // Initialize the displayWinningParameter with fuel capacity, ammo count, and coins collected
        this.displayWinningParameter = new DisplayWinningParameter(
                "/com/example/demo/images/fuelIcon.png", fuelCapacity, // Initialize fuel display
                "/com/example/demo/images/ammoCount.png", bulletCount, // Initialize ammo count display
                "/com/example/demo/images/coin.png", coinsCollected // Initialize coins collected display
        );
    }
}
