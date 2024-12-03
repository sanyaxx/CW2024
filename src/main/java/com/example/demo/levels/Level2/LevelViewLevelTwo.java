package com.example.demo.levels.Level2;

import com.example.demo.actors.additionalUnits.ShieldImage;
import com.example.demo.functionalClasses.DisplayWinningParameter;
import com.example.demo.levels.LevelView;
import javafx.scene.Group;

public class LevelViewLevelTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 1150;
	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private final ShieldImage shieldImage;
	
	public LevelViewLevelTwo(Group root, int heartsToDisplay, int score) {
		super(root, heartsToDisplay, score);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		this.displayWinningParameter = new DisplayWinningParameter("Boss Health", 100, "Coins", score);
		addImagesToRoot();
	}
	
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
	}
	
	public void showShield() {
		System.out.println("[DEBUG]LevelViewL2 Shield activated.");
		shieldImage.showShield();
	}

	public void hideShield() {
		System.out.println("[DEBUG]LevelViewL2 Shield deactivated.");
		shieldImage.hideShield();
	}

	public void updateShieldPosition(double xPosition, double yPosition) {
		shieldImage.updatePosition(xPosition, yPosition);
	}


}