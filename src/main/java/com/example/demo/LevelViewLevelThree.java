package com.example.demo;

import javafx.scene.Group;

public class LevelViewLevelThree extends LevelView {

    private final Group root;

    public LevelViewLevelThree(Group root, int heartsToDisplay, int score) {
        super(root, heartsToDisplay, score);
        this.root = root;
        this.winningParameter = new WinningParameter("Time left", 60);
    }

    public void updateWinningParameterDisplay(int parameterValue) {
        winningParameter.updateValue1(parameterValue);
    }
}
