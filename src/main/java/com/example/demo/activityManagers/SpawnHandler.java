package com.example.demo.activityManagers;

import com.example.demo.actors.ActiveActorDestructible;

import java.util.List;
import java.util.function.Supplier;

public class SpawnHandler {

    private final ActorManager actorManager;

    public SpawnHandler(ActorManager actorManager, double screenWidth, double screenHeight) {
        this.actorManager = actorManager;
    }

    /**
     * Spawns actors of the specified type on the screen based on the given probability.
     *
     * @param actorSupplier   A supplier that creates new instances of the actor.
     * @param maxSpawnCount   The maximum number of actors to spawn.
     * @param spawnProbability The probability of spawning an actor.
     * @param currentCount    The current number of actors of this type.
     * @param maxTotal        The total limit for this type of actor.
     * @return The number of actors successfully spawned.
     */
    public int spawnActors(Supplier<ActiveActorDestructible> actorSupplier, int maxSpawnCount, double spawnProbability,
                           int currentCount, int maxTotal) {
        int spawnedCount = 0;

        int actorsToSpawn = Math.min(maxSpawnCount - currentCount, maxTotal - currentCount);

        for (int i = 0; i < actorsToSpawn; i++) {
            if (Math.random() < spawnProbability) {
                ActiveActorDestructible newActor = actorSupplier.get();

                // Check for overlapping with existing actors
                if (!isOverlapping(newActor, actorManager.getActiveActors())) {
                    actorManager.addActor(newActor);
                    spawnedCount++;
                } else {
                    i--; // Retry spawning if overlapping
                }
            }
        }

        return spawnedCount;
    }

    /**
     * Checks if the given actor overlaps with any existing actors.
     *
     * @param actor            The new actor to check.
     * @param existingActors The list of existing actors.
     * @return True if there is an overlap; false otherwise.
     */
    private boolean isOverlapping(ActiveActorDestructible actor, List<ActiveActorDestructible> existingActors) {
        return existingActors.stream()
                .anyMatch(existing -> actor.getBoundsInParent().intersects(existing.getBoundsInParent()));
    }
}

