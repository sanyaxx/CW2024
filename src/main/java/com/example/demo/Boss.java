package com.example.demo;

import java.util.*;

public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final int IMAGE_HEIGHT = 60;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 100;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = -100;
	private static final int Y_POSITION_LOWER_BOUND = 475;
	private static final int MIN_SHIELD_DURATION = 100;
	private static final int MAX_SHIELD_DURATION = 250;
	private static final int MIN_NO_SHIELD_DURATION = 150;
	private static final int MAX_NO_SHIELD_DURATION = 450;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;
	private int framesWithoutShieldActivated;
	private final LevelViewLevelTwo levelView;

	public Boss(LevelViewLevelTwo levelView) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		framesWithoutShieldActivated = 0;
		isShielded = false;
		this.levelView = levelView;
		initializeMovePattern();
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
		// Synchronize the shield's position with the boss's position
		levelView.updateShieldPosition(getLayoutX() + getTranslateX(), getLayoutY() + getTranslateY());
	}

	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
//			levelView.updateWinningParameterDisplay(getHealth(), ); // Returns health to LevelViewLevelTwo to update Boss Health Display
		}
	}

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
			if (framesWithShieldActivated >= MIN_SHIELD_DURATION + (int) (Math.random() * (MAX_SHIELD_DURATION - MIN_SHIELD_DURATION))) {
				deactivateShield();
			}
		} else {
			framesWithoutShieldActivated++;
			if (framesWithoutShieldActivated >= MIN_NO_SHIELD_DURATION + (int) (Math.random() * (MAX_NO_SHIELD_DURATION - MIN_NO_SHIELD_DURATION))) {
				activateShield();
			}
		}
	}

	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	private void activateShield() {
		isShielded = true;
		framesWithShieldActivated = 0;
		framesWithoutShieldActivated = 0;
		levelView.showShield();
	}

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		framesWithoutShieldActivated = 0;
		levelView.hideShield();
	}
}