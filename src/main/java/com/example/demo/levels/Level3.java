    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            System.out.println("Game Over");
            levelEndHandler.handleLevelLoss(getUser().getScore());
        } else if (remainingTime <= 0) {
            LevelManager levelManager = LevelManager.getInstance();
            levelEndHandler.handleLevelCompletion(getUser().getScore(), starImage, getUser());

            levelManager.incrementCurrentLevelNumber();
        }
    }
