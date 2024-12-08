package com.example.demo.levels.Level3;

import com.example.demo.functionalClasses.DisplayWinningParameter;
import com.example.demo.levels.LevelView;
import javafx.scene.Group;

public class LevelViewLevelThree extends LevelView {

    private final Group root;

    public LevelViewLevelThree(Group root, int heartsToDisplay, int bulletCount, int survivalTime) {
        super(root, heartsToDisplay, bulletCount);
        this.root = root;
        this.displayWinningParameter = new DisplayWinningParameter(
                "/com/example/demo/images/ammoCount.png", bulletCount,
                "/com/example/demo/images/fuelTimer.png", survivalTime
        );
    }

    public void updateWinningParameterDisplay(int parameterValue1, int parameterValue2) {
        displayWinningParameter.updateValue1(parameterValue1);
        displayWinningParameter.updateValue2(parameterValue2);
    }
}
