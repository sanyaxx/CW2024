	@Override
	protected void checkIfGameOver() {
		int userScore = getUser().getScore(); // Store user score for reuse

		if (userIsDestroyed()) {
			System.out.println("User  has been destroyed. Game over.");
			levelEndHandler.handleLevelLoss(userScore);
			return; // Exit the method early
		}

		if (userHasReachedKillTarget()) {
			System.out.println("User  has reached kill target. Advancing to next level.");
			System.out.println("User  Score: " + userScore);

			LevelManager levelManager = LevelManager.getInstance();
			levelEndHandler.handleLevelCompletion(userScore, starImage, getUser());

			levelManager.incrementCurrentLevelNumber();
		}
	}

