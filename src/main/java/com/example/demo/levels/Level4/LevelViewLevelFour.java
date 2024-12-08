package com.example.demo.levels.Level4;

import com.example.demo.functionalClasses.DisplayWinningParameter;
import com.example.demo.levels.LevelView;
import javafx.scene.Group;

public class LevelViewLevelFour extends LevelView {

    private final Group root;

    public LevelViewLevelFour(Group root, int heartsToDisplay, int bulletCount, int remainingTime) {
        super(root, heartsToDisplay, bulletCount);
        this.root = root;
        this.displayWinningParameter = new DisplayWinningParameter (
                "/com/example/demo/images/fuelIcon.png", remainingTime,
                "/com/example/demo/images/ammoCount.png", bulletCount,
                "/com/example/demo/images/coin.png", 0

        );
    }

    // Overloaded method to update the winning parameters with two values
    public void updateWinningParameterDisplay(int parameterValue1, int parameterValue2, int parameterValue3) {
        displayWinningParameter.updateValue1(parameterValue1);
        displayWinningParameter.updateValue2(parameterValue2);
        displayWinningParameter.updateValue3(parameterValue3);
    }
}
