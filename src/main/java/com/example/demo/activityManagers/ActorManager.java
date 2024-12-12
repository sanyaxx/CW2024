package com.example.demo.activityManagers;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.friendlyPlanes.UserParent;
import com.example.demo.actors.Updatable;
import javafx.scene.Group;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Manages all actors in the game, including adding, removing, and updating them.
 * This class uses a Singleton design pattern to ensure there is only one instance managing the actors.
 * <p>
 * This implementation improves memory efficiency and performance by consolidating actors into a single list,
 * properly removing them from the scene when destroyed or off-screen, and streamlining the update process.
 * </p>
 */
public class ActorManager implements Updatable {

    /**
     * Singleton instance of the ActorManager. Ensures only one instance exists globally.
     */
    private static ActorManager instance;

    /**
     * The root {@link Group} node for the JavaFX scene. This node contains all visual elements.
     */
    private static Group root;

    /**
     * A thread-safe list of actors to be added during the next update cycle.
     * <p>
     * Using {@link Collections#synchronizedList(List)} ensures thread safety when multiple threads add actors.
     * </p>
     */
    private final List<GameEntity> actorsToAdd = Collections.synchronizedList(new ArrayList<>());

    /**
     * A thread-safe list of actors to be removed during the next update cycle.
     * <p>
     * Actors are added to this list when they are flagged for removal, ensuring they are removed cleanly during the update process.
     * </p>
     */
    private final List<GameEntity> actorsToRemove = Collections.synchronizedList(new ArrayList<>());

    /**
     * A thread-safe list of currently active actors in the game.
     * <p>
     * This list is updated each frame to include only actors that are still active and visible.
     * </p>
     */
    public final List<GameEntity> activeActors = Collections.synchronizedList(new ArrayList<>());

    /**
     * Private constructor to prevent direct instantiation.
     * <p>
     * This ensures the Singleton pattern is upheld and only one {@link ActorManager} instance is created.
     * </p>
     */
    private ActorManager() {}

    /**
     * Retrieves the Singleton instance of {@link ActorManager}.
     *
     * @return The Singleton instance of {@link ActorManager}.
     */
    public static ActorManager getInstance() {
        if (instance == null) {
            synchronized (ActorManager.class) {
                if (instance == null) {
                    instance = new ActorManager();
                }
            }
        }
        return instance;
    }

    /**
     * Gets the list of currently active actors in the game.
     *
     * @return A thread-safe {@link List} of {@link GameEntity} objects representing active actors.
     */
    public List<GameEntity> getActiveActors() {
        return activeActors;
    }

    /**
     * Adds a {@link GameEntity} to the list of actors to be added during the next update cycle.
     *
     * @param actor The {@link GameEntity} to be added to the game.
     *              Must not be null and should implement necessary update or render logic.
     */
    public void addActor(GameEntity actor) {
        synchronized (actorsToAdd) {
            actorsToAdd.add(actor);
        }
    }

    /**
     * Adds a {@link GameEntity} to the list of actors to be removed during the next update cycle.
     *
     * @param actor The {@link GameEntity} to be removed from the game.
     *              Must be an active actor in the current scene.
     */
    public void removeActor(GameEntity actor) {
        synchronized (actorsToRemove) {
            actorsToRemove.add(actor);
        }
    }

    /**
     * Updates the scene by adding and removing actors based on their status.
     * <p>
     * Actors flagged for removal are removed from the {@link Group} root and the active list.
     * New actors are added to the root and marked as active.
     * </p>
     *
     * @param root The root {@link Group} node of the scene, which manages all visual elements.
     */
    public void updateScene(Group root) {
        synchronized (actorsToRemove) {
            for (GameEntity actor : actorsToRemove) {
                root.getChildren().remove(actor);
                activeActors.remove(actor);
            }
            actorsToRemove.clear(); // Clears the removal list after processing
        }

        synchronized (actorsToAdd) {
            for (GameEntity actor : actorsToAdd) {
                if (!root.getChildren().contains(actor)) {
                    activeActors.add(actor);
                    root.getChildren().add(actor);
                }
            }
            actorsToAdd.clear(); // Clears the addition list after processing
        }
    }

    /**
     * Updates all currently active actors by calling their respective {@code update} methods.
     * <p>
     * Ensures each actor is refreshed and their state is updated for the current game frame.
     * </p>
     */
    public void updateActors() {
        synchronized (activeActors) {
            for (GameEntity actor : activeActors) {
                actor.update();
            }
        }
    }

    /**
     * Retrieves the {@link UserParent} object representing the player's controlled vehicle.
     *
     * @return The {@link UserParent} object if it exists in the active actors list, or {@code null} if not found.
     */
    public UserParent getUserVehicle() {
        return activeActors.stream()
                .filter(actor -> actor instanceof UserParent)
                .map(actor -> (UserParent) actor) // Cast to UserParent
                .findFirst() // Returns the first found UserParent
                .orElse(null); // Returns null if no UserParent is found
    }

    /**
     * Sets the root {@link Group} node for the scene.
     * <p>
     * This root node manages all visual elements and is used by the {@code updateScene} method.
     * </p>
     *
     * @param rootToSet The {@link Group} node to be used as the root of the scene.
     *                  Must not be null and should be initialized before use.
     */
    public void setRoot(Group rootToSet) {
        root = rootToSet;
    }

    /**
     * Clears all actors from the scene and internal lists.
     * <p>
     * This is used to reset the game state for a new level or a restart, ensuring no outdated actors remain in the scene.
     * </p>
     */
    public void clearLevel() {
        synchronized (actorsToAdd) {
            actorsToAdd.clear();
        }
        synchronized (actorsToRemove) {
            actorsToRemove.clear();
        }
        synchronized (activeActors) {
            activeActors.clear(); // Clears all active actors
        }
        System.out.println("Level cleared: All actors removed.");
    }

    /**
     * Updates the state of the game by updating both actors and the scene.
     * <p>
     * This method is called during each game loop iteration to ensure all visual and logical components are synchronized.
     * </p>
     */
    @Override
    public void update() {
        updateActors();
        updateScene(root);
    }
}
