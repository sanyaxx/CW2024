package com.example.demo.activityManagers;

import com.example.demo.actors.GameEntity;

import java.util.List;
import java.util.function.Supplier;

/**
 * The SpawnHandler class is responsible for managing the spawning of game actors, such as enemies or items,
 * based on given probabilities and constraints. It centralizes the spawning logic, making it reusable across different
 * levels of the game, with adjustable parameters such as spawn probability, spawn limits, and the maximum number of actors.
 *
 * <p>In the previous implementation, each level, such as {com.example.demo.controller.LevelOne}, had its own
 * logic for spawning actors. For example, in LevelOne, the spawn logic was written directly within the class, where enemy units
 * were spawned based on a fixed probability. This approach was repetitive and hard to maintain as the game expanded. The new
 * centralized spawn logic in {SpawnHandler} addresses these issues by decoupling the spawning logic from the levels,
 * allowing for greater flexibility and maintainability.</p>
 */
public class SpawnHandler {

    /**
     * The actor manager responsible for handling the game actors.
     */
    private final ActorManager actorManager;

    /**
     * The width of the game screen, used for placing actors within bounds.
     */
    private final double screenWidth;

    /**
     * The height of the game screen, used for placing actors within bounds.
     */
    private final double screenHeight;

    /**
     * Constructs a new SpawnHandler instance.
     *
     * @param actorManager The ActorManager responsible for managing game actors.
     * @param screenWidth The width of the game screen.
     * @param screenHeight The height of the game screen.
     */
    public SpawnHandler(ActorManager actorManager, double screenWidth, double screenHeight) {
        this.actorManager = actorManager;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Spawns actors of the specified type on the screen based on the given probability.
     *
     * <p>This method generates a number of actors within the allowed spawn limit, checking for overlap with existing actors
     * to ensure they don't spawn on top of each other. This approach was not present in the older LevelOne class's
     * {spawnEnemyUnits} method, which simply spawned enemies based on a fixed probability and did not consider
     * potential overlaps with other actors.</p>
     *
     * <p>By using this method, you can adjust spawn behavior dynamically for various levels by specifying parameters such
     * as the maximum number of actors to spawn, spawn probability, and the current count of active actors.</p>
     *
     * @param actorSupplier A supplier that creates new instances of the actor.
     * @param maxSpawnCount The maximum number of actors to spawn.
     * @param spawnProbability The probability of spawning each actor.
     * @param currentCount The current number of actors of this type already spawned.
     * @param maxTotal The total limit for this type of actor.
     * @return The number of actors successfully spawned.
     */
    public int spawnActors(Supplier<GameEntity> actorSupplier, int maxSpawnCount, double spawnProbability,
                           int currentCount, int maxTotal) {
        int spawnedCount = 0;

        // Calculate the maximum number of actors that can still be spawned
        int actorsToSpawn = Math.min(maxSpawnCount - currentCount, maxTotal - currentCount);
        for (int i = 0; i < actorsToSpawn; i++) {
            if (Math.random() < spawnProbability) {
                GameEntity newActor = actorSupplier.get();

                // Check for overlap with existing actors
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
     * <p>In the older LevelOne class's {@code spawnEnemyUnits} method, no check was made for actor overlap,
     * meaning actors could spawn on top of each other. The new approach here ensures that no two actors spawn in the
     * same position, preventing issues such as actors colliding immediately upon spawn or occupying the same space.</p>
     *
     * @param actor The new actor to check.
     * @param existingActors The list of existing actors.
     * @return True if there is an overlap; false otherwise.
     */
    private boolean isOverlapping(GameEntity actor, List<GameEntity> existingActors) {
        return existingActors.stream()
                .anyMatch(existing -> actor.getBoundsInParent().intersects(existing.getBoundsInParent()));
    }
}
