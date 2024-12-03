package com.example.demo.levels.Level4;

import com.example.demo.functionalClasses.DisplayWinningParameter;
import com.example.demo.levels.LevelView;
import javafx.scene.Group;

public class LevelViewLevelFour extends LevelView {

    private final Group root;

    public LevelViewLevelFour(Group root, int heartsToDisplay, int score) {
        super(root, heartsToDisplay, score);
        this.root = root;
        this.displayWinningParameter = new DisplayWinningParameter("Fuel Time Left", 10);
    }

    public void updateWinningParameterDisplay(int parameterValue) {
        displayWinningParameter.updateValue1(parameterValue);
    }
}
