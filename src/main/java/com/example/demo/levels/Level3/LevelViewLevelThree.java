package com.example.demo.levels.Level3;

import com.example.demo.functionalClasses.DisplayWinningParameter;
import com.example.demo.levels.LevelView;
import javafx.scene.Group;

public class LevelViewLevelThree extends LevelView {

    private final Group root;

    public LevelViewLevelThree(Group root, int heartsToDisplay, int score) {
        super(root, heartsToDisplay, score);
        this.root = root;
        this.displayWinningParameter = new DisplayWinningParameter("Time left", 60);
    }

    public void updateWinningParameterDisplay(int parameterValue) {
        displayWinningParameter.updateValue1(parameterValue);
    }
}
