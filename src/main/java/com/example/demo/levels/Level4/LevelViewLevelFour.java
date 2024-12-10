package com.example.demo.levels.Level4;

import com.example.demo.functionalClasses.DisplayWinningParameter;
import com.example.demo.levels.LevelView;
import javafx.scene.Group;

public class LevelViewLevelFour extends LevelView {

    private final Group root;

    public LevelViewLevelFour(Group root, int heartsToDisplay, int bulletCount, int fuelCapacity, int coinsCollected) {
        super(root, heartsToDisplay, bulletCount, coinsCollected);
        this.root = root;
        this.displayWinningParameter = new DisplayWinningParameter (
                "/com/example/demo/images/fuelIcon.png", fuelCapacity,
                "/com/example/demo/images/ammoCount.png", bulletCount,
                "/com/example/demo/images/coin.png", coinsCollected
        );
    }
}
