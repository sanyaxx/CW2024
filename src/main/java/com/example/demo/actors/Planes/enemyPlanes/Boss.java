package com.example.demo.actors.Planes.enemyPlanes;

import com.example.demo.actors.Projectiles.enemyProjectiles.BossProjectile;
import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.FighterPlane;
import com.example.demo.controller.AppStage;
import com.example.demo.levels.Level2.LevelViewLevelTwo;

import java.util.*;

/**
 * The {@code Boss} class represents the boss enemy plane in the game.
 * It extends the {@link FighterPlane} class and adds additional functionality specific to the boss,
 * such as movement patterns, shield management, and projectile firing.
 * <p>
 * The boss moves vertically within a restricted range and has the ability to activate a shield during battle.
 * It fires projectiles at regular intervals based on its fire rate and health status.
 * </p>
 * Example usage:
 * <pre>
 * Boss boss = new Boss(levelView);
 * </pre>
 */
public class Boss extends FighterPlane {

	/**
	 * The image name for the boss plane.
	 */
	private static final String IMAGE_NAME = "bossplane.png";

	/**
	 * The initial X position of the boss on the screen.
	 */
	private static final double INITIAL_X_POSITION = AppStage.getInstance().getPrimaryStage().getWidth() - 500;

	/**
	 * The initial Y position of the boss on the screen.
	 */
	private static final double INITIAL_Y_POSITION = 400;

	/**
	 * The vertical offset for the projectile Y position when fired by the boss.
	 */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;

	/**
	 * The fire rate of the boss, determining how frequently it fires projectiles.
	 */
	private static final double BOSS_FIRE_RATE = .04;

	/**
	 * The height of the boss image.
	 */
	private static final int IMAGE_HEIGHT = 60;

	/**
	 * The vertical velocity at which the boss moves.
	 */
	private static final int VERTICAL_VELOCITY = 8;

	/**
	 * The initial health of the boss.
	 */
	private static final int HEALTH = 5;

	/**
	 * The frequency at which the boss changes its movement pattern.
	 */
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;

	/**
	 * A constant representing no vertical movement.
	 */
	private static final int ZERO = 0;

	/**
	 * The maximum number of frames the boss can move in the same direction before changing direction.
	 */
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;

	/**
	 * The upper bound for the Y position to restrict the boss's vertical movement.
	 */
	private static final int Y_POSITION_UPPER_BOUND = 50;

	/**
	 * The lower bound for the Y position to restrict the boss's vertical movement.
	 */
	private static final int Y_POSITION_LOWER_BOUND = 475;

	/**
	 * The minimum duration for the boss's shield to stay active.
	 */
	private static final int MIN_SHIELD_DURATION = 100;

	/**
	 * The maximum duration for the boss's shield to stay active.
	 */
	private static final int MAX_SHIELD_DURATION = 250;

	/**
	 * The minimum duration for the boss's shield to be inactive.
	 */
	private static final int MIN_NO_SHIELD_DURATION = 150;

	/**
	 * The maximum duration for the boss's shield to be inactive.
	 */
	private static final int MAX_NO_SHIELD_DURATION = 450;

	/**
	 * The movement pattern for the boss, which dictates whether the boss moves up, down, or stays still.
	 */
	private final List<Integer> movePattern;

	/**
	 * A flag indicating whether the boss is shielded or not.
	 */
	private boolean isShielded;

	/**
	 * The number of consecutive moves the boss has made in the same direction.
	 */
	private int consecutiveMovesInSameDirection;

	/**
	 * The index of the current move in the movement pattern.
	 */
	private int indexOfCurrentMove;

	/**
	 * The number of frames the boss's shield has been active.
	 */
	private int framesWithShieldActivated;

	/**
	 * The number of frames the boss's shield has been inactive.
	 */
	private int framesWithoutShieldActivated;

	/**
	 * The level view for the current game level, used for managing the shield's visibility.
	 */
	private final LevelViewLevelTwo levelView;

	/**
	 * Constructor to create a new boss plane with the specified level view.
	 * Initializes the boss's position, movement pattern, and shield status.
	 *
	 * @param levelView The view of the current game level.
	 */
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

	/**
	 * Updates the position of the boss plane by moving vertically based on the current move pattern.
	 * Ensures that the boss stays within the vertical bounds of the screen.
	 */
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

	/**
	 * Updates the state of the boss, including its position and shield status.
	 */
	@Override
	public void update() {
		updatePosition();
		updateShield();
	}

	/**
	 * Determines if the boss is friendly. Since the boss is an enemy, this method returns false.
	 *
	 * @return false, indicating that the boss is not friendly.
	 */
	@Override
	public boolean isFriendly() {
		return false;
	}

	/**
	 * Fires a projectile from the boss. The boss fires a projectile in a given frame based on the fire rate.
	 *
	 * @return A new {@link BossProjectile} if the boss fires, or null if no projectile is fired.
	 */
	@Override
	public GameEntity fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * Handles the boss taking damage. The boss only takes damage if it is not shielded.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	/**
	 * Initializes the movement pattern for the boss, including vertical movement up, down, or staying still.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield status of the boss. The shield may be activated or deactivated randomly within certain durations.
	 */
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

	/**
	 * Returns the next move for the boss based on the movement pattern. The boss can move up, down, or stay still.
	 * The pattern is shuffled periodically to add randomness to the movement.
	 *
	 * @return The vertical movement of the boss (positive for down, negative for up, zero for staying still).
	 */
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

	/**
	 * Determines if the boss fires a projectile in the current frame based on the boss's fire rate.
	 *
	 * @return true if the boss fires a projectile, false otherwise.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Gets the initial Y position for firing a projectile from the boss's plane.
	 *
	 * @return The Y position at which the projectile is fired.
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Activates the shield for the boss, allowing it to block damage.
	 */
	private void activateShield() {
		isShielded = true;
		framesWithShieldActivated = 0;
		framesWithoutShieldActivated = 0;
		levelView.showShield();
	}

	/**
	 * Deactivates the shield for the boss, allowing it to take damage again.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		framesWithoutShieldActivated = 0;
		levelView.hideShield();
	}

	/**
	 * Determines if the boss's projectile is collectible. This method always returns false since the boss's projectiles are not collectible.
	 *
	 * @return false, indicating that the boss's projectiles are not collectible.
	 */
	@Override
	public boolean isCollectible() {
		return false;
	}
}
