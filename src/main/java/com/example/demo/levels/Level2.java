package com.example.demo.levels;

import com.example.demo.*;

public class Level2 extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/level2Background.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;

    public Level2(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        boss = new Boss(levelView);
    }

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			levelEndHandler.handleLevelLoss(getUser().getScore());
		}
		else if (boss.isDestroyed()) {
			LevelManager levelManager = LevelManager.getInstance();
			levelEndHandler.handleLevelCompletion(getUser().getScore(), starImage, getUser());

			levelManager.incrementCurrentLevelNumber();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		System.out.println("Score: " + getUser().getScore());
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH, getUser().getScore());
		return levelView;
	}

	@Override
	protected void updateLevelView() {
		levelView.removeHearts(getUser().getHealth());
		levelView.updateWinningParameterDisplay(boss.getHealth(), getUser().getScore());
	}
}
