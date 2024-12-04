package com.example.demo;
import com.example.demo.actors.ActiveActorDestructible;
import javafx.scene.Group;

import java.util.List;
import java.util.ArrayList;

public class ActorManager {

    // The Singleton instance
    private static ActorManager instance;

    // Lists to manage actors
    private List<ActiveActorDestructible> actorsToAdd;
    private List<ActiveActorDestructible> actorsToRemove;
    public List<ActiveActorDestructible> activeActors;

    // Private constructor to prevent instantiation from outside
    private ActorManager() {
        actorsToAdd = new ArrayList<>();
        actorsToRemove = new ArrayList<>();
        activeActors = new ArrayList<>();
    }

    // Public static method to get the Singleton instance
    public static ActorManager getInstance() {
        // Lazy initialization
        if (instance == null) {
            synchronized (ActorManager.class) {
                if (instance == null) {
                    instance = new ActorManager();
                }
            }
        }
        return instance;
    }

    public void removeActor(ActiveActorDestructible actor) {
        actorsToRemove.add(actor);
    }

    public void addActor(ActiveActorDestructible actor) {
        actorsToAdd.add(actor);
    }

    // Method to add actors to the scene
    public void addActorsToScene(Group root) {
        actorsToAdd.forEach(actor -> {
            // Check if actor is already in the scene before adding it
            if (!root.getChildren().contains(actor)) {
                activeActors.add(actor);
                root.getChildren().add(actor);
            }
        });
        actorsToAdd.clear();
    }


    // Method to remove actors from the scene
    public void removeActorsFromScene(Group root) {
        actorsToRemove.forEach(actor -> {
            root.getChildren().remove(actor);
            activeActors.remove(actor);
        });
        actorsToRemove.clear();
    }

    // Getters for the actor lists (if needed)
    public List<ActiveActorDestructible> getActorsToAdd() {
        return actorsToAdd;
    }

    public List<ActiveActorDestructible> getActorsToRemove() {
        return actorsToRemove;
    }

    public List<ActiveActorDestructible> getActiveActors() {
        return activeActors;
    }

    // Method to update active actors (you can implement custom logic to handle it)
    public void updateActors() {
        // Example of how you might update actors here (just a placeholder)
        for (ActiveActorDestructible actor : activeActors) {
            actor.updateActor();
        }
    }
}

