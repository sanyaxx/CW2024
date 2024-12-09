package com.example.demo.activityManagers;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Updatable;
import javafx.scene.Group;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class ActorManager implements Updatable {

    // Singleton instance
    private static ActorManager instance;
    private static Group root;

    // Lists to manage actors
    private final List<GameEntity> actorsToAdd = Collections.synchronizedList(new ArrayList<>());
    private final List<GameEntity> actorsToRemove = Collections.synchronizedList(new ArrayList<>());
    public final List<GameEntity> activeActors = Collections.synchronizedList(new ArrayList<>());

    // Private constructor to prevent instantiation from outside
    private ActorManager() {}

    // Public static method to get the Singleton instance
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

    public List<GameEntity> getActiveActors() {
        return activeActors;
    }

    // Add an actor to the list of actors to add
    public void addActor(GameEntity actor) {
        synchronized (actorsToAdd) {
            actorsToAdd.add(actor);
        }
    }

    // Add an actor to the list of actors to remove
    public void removeActor(GameEntity actor) {
        synchronized (actorsToRemove) {
            actorsToRemove.add(actor);
        }
    }

    // Add/remove actors from the scene
    public void updateScene(Group root) {
        synchronized (actorsToRemove) {
            for (GameEntity actor : actorsToRemove) {
                root.getChildren().remove(actor);
                activeActors.remove(actor);
            }
            actorsToRemove.clear();
        }

        synchronized (actorsToAdd) {
            for (GameEntity actor : actorsToAdd) {
                if (!root.getChildren().contains(actor)) {
                    activeActors.add(actor);
                    root.getChildren().add(actor);
                }
            }
            actorsToAdd.clear();
        }
    }

    // Update all active actors
    public void updateActors() {
        synchronized (activeActors) {
            for (GameEntity actor : activeActors) {
                actor.update();
            }
        }
    }

    // Set the root node
    public void setRoot(Group rootToSet) {
        root = rootToSet;
    }

    // Clear all actors from the scene and lists
    public void clearLevel() {
        synchronized (actorsToAdd) {
            actorsToAdd.clear();
        }
        synchronized (actorsToRemove) {
            actorsToRemove.clear();
        }
        synchronized (activeActors) {
            activeActors.clear(); // Clear all
        }
        System.out.println("Level cleared: All actors removed.");
    }


    // Update actors and the scene
    @Override
    public void update() {
        updateActors();
        updateScene(root);
    }
}
