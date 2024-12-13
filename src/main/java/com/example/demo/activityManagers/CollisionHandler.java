package com.example.demo.activityManagers;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.FighterPlane;
import com.example.demo.actors.Planes.friendlyPlanes.UserParent;
import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.actors.Projectiles.Projectile;
import com.example.demo.actors.Projectiles.userProjectiles.UserProjectile;
import com.example.demo.actors.additionalUnits.Coins;
import com.example.demo.actors.additionalUnits.FuelToken;
import com.example.demo.actors.additionalUnits.Magnet;
import com.example.demo.levels.LevelParent;

import java.util.List;

/**
 * The {@code CollisionHandler} class is responsible for detecting and handling collisions between game actors.
 * <p>
 * The collision system has been refactored to handle all types of collisions (between friendly/enemy planes, projectiles, and collectibles)
 * in a unified manner. This reduces redundancy and makes the code more maintainable and extensible.
 * </p>
 */
public class CollisionHandler {

    /** The parent level object representing the current game level. */
    private LevelParent levelParent;

    /**
     * Constructor for the {@code CollisionHandler} class.
     * Initializes the collision handler with the provided level parent, which contains the current game level.
     *
     * @param levelParent The parent object representing the current level of the game.
     *                    This parameter is used to access and manage the game state and entities in the level.
     */
    public CollisionHandler(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    /**
     * Handles collision detection between all active actors in the game.
     * <p>
     * This method iterates through all active actors and checks if any of them intersect.
     * It checks for both collectible interactions and non-collectible collisions between entities such as planes and projectiles.
     * </p>
     *
     * @param activeActors The list of all currently active actors in the game, including planes, projectiles, and collectibles.
     *                     The method checks each pair of actors in this list to detect and handle collisions.
     */
    public void checkCollisions(List<GameEntity> activeActors) {
        // Iterate over all active actors
        for (int i = 0; i < activeActors.size(); i++) {
            GameEntity actor1 = activeActors.get(i);

            for (int j = i + 1; j < activeActors.size(); j++) {
                GameEntity actor2 = activeActors.get(j);

                // Check if the actors intersect
                if (actor1.getBoundsInParent().intersects(actor2.getBoundsInParent())) {
                    // Handle collectible interactions
                    if (actor1.isCollectible() ^ actor2.isCollectible()) {
                        handleCollectibleInteraction(actor1, actor2);
                    }
                    // Handle collisions between non-collectible actors
                    else if (!actor1.isCollectible() && !actor2.isCollectible()) {
                        if ((actor1.isFriendly() ^ actor2.isFriendly())) {
                            handleNonCollectibleCollision(actor1, actor2);
                        }
                    }
                }
            }
        }
    }

    /**
     * Handles the interaction between a collectible and a non-collectible actor.
     * <p>
     * This method processes the effect of a collectible item when it collides with a non-collectible actor.
     * Collectible items are typically consumed upon collision, and their effects are applied to the player's state (e.g., coins, fuel, magnets).
     * </p>
     *
     * @param actor1 The first actor involved in the interaction.
     *               This can be a collectible item (e.g., a coin, fuel token, or magnet).
     * @param actor2 The second actor involved in the interaction.
     *               This can be a non-collectible actor (e.g., a user plane).
     */
    protected void handleCollectibleInteraction(GameEntity actor1, GameEntity actor2) {
        // Check if actor1 is a collectible and actor2 is non-collectible
        if (actor1.isCollectible() && !actor2.isCollectible() && actor2 instanceof UserParent) {
            actor1.takeDamage(); // Consume the collectible
            handleCollectibleEffect(actor1); // Apply the effect of the collectible
        }
        // Check if actor2 is a collectible and actor1 is non-collectible
        else if (!actor1.isCollectible() && actor1 instanceof UserParent && actor2.isCollectible()) {
            actor2.takeDamage(); // Consume the collectible
            handleCollectibleEffect(actor2); // Apply the effect of the collectible
        }
    }

    /**
     * Handles collisions between two non-collectible actors, such as projectiles and planes.
     * <p>
     * This method processes the interaction when two non-collectible actors collide, such as when a projectile hits a plane.
     * It handles damage, updating the player's kill count, and managing cooldowns for friendly planes.
     * </p>
     *
     * @param actor1 The first actor involved in the collision.
     *               This can be a projectile, friendly plane, or enemy plane.
     * @param actor2 The second actor involved in the collision.
     *               This can be a projectile, friendly plane, or enemy plane.
     */
    protected void handleNonCollectibleCollision(GameEntity actor1, GameEntity actor2) {
        // Ignore collisions between projectiles
        if (actor1 instanceof Projectile && actor2 instanceof Projectile) {
            return;
        }

        // Handle friendly/enemy plane collisions or projectile hits
        if ((actor1 instanceof FighterPlane && actor2 instanceof FighterPlane) ||
                (actor1 instanceof UserProjectile && actor2 instanceof FighterPlane) ||
                (actor1 instanceof FighterPlane && actor2 instanceof UserProjectile)) {
            levelParent.actorManager.getUserVehicle().incrementKillCount();
            // Decrease the number of enemies if applicable
            if (levelParent.currentNumberOfEnemies > 0) {
                levelParent.currentNumberOfEnemies--;
            }
        }

        // Apply damage to both actors involved in the collision
        actor1.takeDamage();
        actor2.takeDamage();

        // Start cooldown for the UserPlane involved in the collision
        if (actor1 instanceof UserPlane) {
            ((UserPlane) actor1).startCooldown();
            levelParent.audioHandler.playLifeLostSound();  // Life lost sound effect played
        } else if (actor2 instanceof UserPlane) {
            ((UserPlane) actor2).startCooldown();
            levelParent.audioHandler.playLifeLostSound();
        }

    }

    /**
     * Applies the effect of a collectible item, such as coins, fuel tokens, or magnets.
     * <p>
     * This method handles the logic for what happens when the player collects a specific item, such as updating their coin count,
     * refilling their fuel, or activating a magnet to attract items.
     * </p>
     *
     * @param actor The actor representing the collectible item.
     *              This can be a coin, fuel token, or magnet.
     */
    protected void handleCollectibleEffect(GameEntity actor) {
        // Handle the effect of a coin collectible
        if (actor instanceof Coins) {
            levelParent.actorManager.getUserVehicle().incrementCoinsCollected();
            if (levelParent.currentNumberOfCoins > 0) {
                levelParent.currentNumberOfCoins--;
                levelParent.audioHandler.playCoinCollectedSound();
            }
        }
        // Handle the effect of a fuel token collectible
        else if (actor instanceof FuelToken) {
            levelParent.actorManager.getUserVehicle().incrementFuelLeft();
        }
        // Handle the effect of a magnet collectible
        else if (actor instanceof Magnet) {
            levelParent.activateMagnet();
        }
    }

}
