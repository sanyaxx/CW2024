package com.example.demo.levels.Level2;

import com.example.demo.actors.Planes.enemyPlanes.Boss;
import com.example.demo.levels.LevelParent;
import com.example.demo.levels.LevelView;

public class Level2 extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/level2Background.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;

    public Level2(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        boss = new Boss(levelView);
		this.coinsCollectedInLevel = 0;

		initializeLevel(this, getUser());
    }

	@Override
	protected boolean hasLevelBeenLost() {
		return userIsDestroyed(); // User loss condition
	}

	@Override
	protected boolean hasLevelBeenWon() {
		return boss.isDestroyed(); // Win condition specific to this level
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			actorManager.addActor(boss);
		}
	}

	@Override
	protected void generateEnemyFire() {
		actorManager.getActiveActors().forEach(actor -> {
			if (actor instanceof Boss) {
				spawnEnemyProjectile(((Boss) actor).fireProjectile());
			}
		});
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

	@Override
	public void update() {
		super.update();
		spawnEnemyUnits();
		updateLevelView();
	}
}
