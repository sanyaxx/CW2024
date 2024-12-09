package com.example.demo.activityManagers;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.FighterPlane;
import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.actors.Projectiles.Projectile;
import com.example.demo.actors.Projectiles.userProjectiles.UserProjectile;
import com.example.demo.actors.additionalUnits.Coins;
import com.example.demo.actors.additionalUnits.FuelToken;
import com.example.demo.levels.LevelParent;

import java.util.List;


public class CollisionHandler {

    private LevelParent levelParent;

    public CollisionHandler(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    public void checkCollisions(List<GameEntity> activeActors) {
        for (int i = 0; i < activeActors.size(); i++) {
            GameEntity actor1 = activeActors.get(i);

            for (int j = i + 1; j < activeActors.size(); j++) {
                GameEntity actor2 = activeActors.get(j);

                if (actor1.getBoundsInParent().intersects(actor2.getBoundsInParent())) {
                    if (actor1.isCollectible() ^ actor2.isCollectible()) {
                        handleCollectibleInteraction(actor1, actor2);
                    } else if (!actor1.isCollectible() && !actor2.isCollectible()){
                        if ((actor1.isFriendly() ^ actor2.isFriendly())) {
                            handleNonCollectibleCollision(actor1, actor2);
                        }
                    }
                }
            }
        }
    }

    protected void handleCollectibleInteraction(GameEntity actor1, GameEntity actor2) {
        if (actor1.isCollectible() && !actor2.isCollectible() && actor2 instanceof UserPlane) {
            actor1.takeDamage();
            handleCollectibleEffect(actor1);
        } else if (!actor1.isCollectible() && actor1 instanceof UserPlane && actor2.isCollectible()) {
            actor2.takeDamage();
            handleCollectibleEffect(actor2);
        }
    }

    protected void handleNonCollectibleCollision(GameEntity actor1, GameEntity actor2) {
        if (actor1 instanceof Projectile && actor2 instanceof Projectile) {
            return;
        }

        if ((actor1 instanceof FighterPlane && actor2 instanceof FighterPlane) ||
                (actor1 instanceof UserProjectile && actor2 instanceof FighterPlane) ||
                (actor1 instanceof FighterPlane && actor2 instanceof UserProjectile)) {
            levelParent.userStatsManager.incrementKillCount();
            levelParent.killCount++;
            if (levelParent.currentNumberOfEnemies > 0) { // ensures that the number is only decremented if > 0
                levelParent.currentNumberOfEnemies--;
            }
        }

        actor1.takeDamage();
        actor2.takeDamage();

        // Start cooldown for the UserPlane involved in the collision
        if (actor1 instanceof UserPlane) {
            ((UserPlane) actor1).startCooldown();
        } else if (actor2 instanceof UserPlane) {
            ((UserPlane) actor2).startCooldown();
        }
    }

    protected void handleCollectibleEffect(GameEntity actor) {
        if (actor instanceof Coins) {
            levelParent.userStatsManager.incrementCoinsCollected();
        } else if (actor instanceof FuelToken) {
            levelParent.getUser().incrementFuelLeft();
        }
    }

}
