package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShieldImage extends ImageView {
	
	private static final String IMAGE_NAME = "/images/shield.png";
	private static final int SHIELD_SIZE = 100;
	
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		//this.setImage(new Image(IMAGE_NAME));
		this.setImage(new Image(getClass().getResource("/com/example/demo/images/shield.png").toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	public void showShield() {
		this.setVisible(true);
		this.toFront();
		System.out.println("[DEBUG]ShieldImage Shield activated.");
	}
	
	public void hideShield() {
		this.setVisible(false);
		System.out.println("[DEBUG]ShieldImage Shield deactivated.");
	}

	public void updatePosition(double xPosition, double yPosition) {
		this.setLayoutX(xPosition + 50);
		this.setLayoutY(yPosition);
	}
}
