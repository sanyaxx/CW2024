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

