package com.example.demo.levels.Level3;

import com.example.demo.functionalClasses.DisplayWinningParameter;
import com.example.demo.levels.LevelView;
import javafx.scene.Group;

public class LevelViewLevelThree extends LevelView {

    private final Group root;

    public LevelViewLevelThree(Group root, int heartsToDisplay, int bulletCount, int survivalTime, int coinsCollected) {
        super(root, heartsToDisplay, bulletCount, coinsCollected);
        this.root = root;
        this.displayWinningParameter = new DisplayWinningParameter (
                "/com/example/demo/images/ammoCount.png", bulletCount,
                "/com/example/demo/images/fuelTimer.png", survivalTime,
                "/com/example/demo/images/coin.png", coinsCollected
        );
    }
}
