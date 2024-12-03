package com.example.demo;

import javafx.scene.Group;

public class LevelViewLevelFour extends LevelView {

    private final Group root;

    public LevelViewLevelFour(Group root, int heartsToDisplay, int score) {
        super(root, heartsToDisplay, score);
        this.root = root;
        this.winningParameter = new WinningParameter("Fuel Time Left", 10);
    }

    public void updateWinningParameterDisplay(int parameterValue) {
        winningParameter.updateValue1(parameterValue);
    }
}
