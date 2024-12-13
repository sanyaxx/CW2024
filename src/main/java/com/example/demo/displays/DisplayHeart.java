package com.example.demo.displays;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * The {@code DisplayHeart} class is responsible for managing the display of heart icons, typically used to represent
 * the health of the player in the game. It provides a dynamic display that can update the number of hearts based on
 * the user's health status. The class offers functionality to initialize, update, and remove hearts as needed.
 *
 * This class replaces the older {HeartDisplay} class, with significant improvements to its flexibility and usability.
 * - It uses a method, {@code syncHeartsWithUserHealth()}, to dynamically adjust the heart display according to user health,
 *   whereas the old class required static initialization of hearts at the start.
 * - The new class eliminates the need for an explicit removal of hearts (previously done with {@code removeHeart()}) and simplifies
 *   the code flow by integrating all functionality in a single class.
 *
 */
public class DisplayHeart {

	/**
	 * The path to the heart image used for displaying health.
	 */
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";

	/**
	 * The height at which the heart images will be displayed.
	 */
	private static final int HEART_HEIGHT = 50;

	/**
	 * The container (HBox) used to hold the heart images in a horizontal layout.
	 */
	private HBox container;

	/**
	 * The X-position where the heart display container will be placed.
	 */
	private double containerXPosition;

	/**
	 * The Y-position where the heart display container will be placed.
	 */
	private double containerYPosition;

	/**
	 * Constructs a new {@code DisplayHeart} instance with a specific position on the screen and initializes the heart container.
	 *
	 * @param xPosition the X-coordinate for positioning the heart container on the screen.
	 * @param yPosition the Y-coordinate for positioning the heart container on the screen.
	 * @param heartsToDisplay the initial number of hearts to display, based on the user's health.
	 */
	public DisplayHeart(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		initializeContainer();
		syncHeartsWithUserHealth(heartsToDisplay); // Initialize the container with the given number of hearts
	}

	/**
	 * Initializes the container (HBox) that will hold the heart images. The container is positioned based on the X and Y
	 * coordinates provided during the class instantiation.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Synchronizes the number of hearts displayed with the given user health.
	 * This method clears the existing hearts and adds new hearts according to the current health status.
	 *
	 * @param userHealth the current health of the user, determining the number of hearts to display.
	 */
	public void syncHeartsWithUserHealth(int userHealth) {
		// Clear the existing hearts from the container
		container.getChildren().clear();

		// Add hearts based on the user's health
		for (int i = 0; i < userHealth; i++) {
			addHeart(); // Adds each heart image to the container
		}
	}

	/**
	 * Adds a single heart image to the container. The heart image is loaded from the specified path and its size is set
	 * to a fixed height, maintaining its aspect ratio.
	 */
	public void addHeart() {
		// Create a new ImageView for the heart
		ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));

		// Set the properties for the heart image
		heart.setFitHeight(HEART_HEIGHT);  // Set the height for each heart image
		heart.setPreserveRatio(true);  // Preserve the aspect ratio of the image

		// Add the heart to the container (HBox)
		container.getChildren().add(heart);
	}

	/**
	 * Returns the container (HBox) that holds the heart images. This can be added to the root layout of the scene.
	 *
	 * @return the {@code HBox} container that holds the heart images.
	 */
	public HBox getContainer() {
		return container;
	}
}
